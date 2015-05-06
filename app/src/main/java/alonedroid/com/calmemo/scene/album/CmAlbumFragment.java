package alonedroid.com.calmemo.scene.album;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.fragment.ImageListFragment;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.realm.RealmAccessor;
import alonedroid.com.calmemo.scene.photographs.CmPhotographsActivity;

@EFragment(R.layout.fragment_cm_album)
public class CmAlbumFragment extends Fragment {

    private static final int REQUEST_CODE = 1001;

    @FragmentArg
    String argDisplayDate;

    @ViewById
    TextView cmAlbumHeader;

    @Bean
    RealmAccessor accessor;

    ImageListFragment imageListFragment;

    CmPhoto photo;

    int photoIndex;

    public static CmAlbumFragment newInstance(String displayDate) {
        CmAlbumFragment_.FragmentBuilder_ builder_ = CmAlbumFragment_.builder();
        builder_.argDisplayDate(displayDate);
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        this.cmAlbumHeader.setText(this.argDisplayDate);
        setImageListFragment();
    }

    void setImageListFragment() {
        List<CmPhoto> list = this.accessor.getPhotosByDate(this.argDisplayDate);
        ImageListFragment fragment = ImageListFragment.newInstance(list.toArray(new CmPhoto[list.size()]));
        fragment.setClickedListener(this::OnImageClickedListener);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_album, fragment)
                .commit();

        this.imageListFragment = fragment;
    }

    private void OnImageClickedListener(int index, CmPhoto bean) {
        Intent intent = CmPhotographsActivity.newIntent(bean.getCmDateTime().toString(), bean.getCmPhoto());
        startActivityForResult(intent, REQUEST_CODE);
        ((CmAlbumActivity) getActivity()).setActivityAnimation();
        this.photo = bean;
        this.photoIndex = index;
    }

    @OnActivityResult(REQUEST_CODE)
    void resultPhotographs(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        this.accessor.deleteCmPhoto(this.photo);
        this.imageListFragment.markDelete(this.photoIndex);
        if (this.imageListFragment.isAllDeleted()) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    @Click
    void albumCalendarIcon(View v) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.accessor.close();
    }
}
