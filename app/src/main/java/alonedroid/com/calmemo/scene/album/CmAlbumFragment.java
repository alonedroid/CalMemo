package alonedroid.com.calmemo.scene.album;

import android.content.Intent;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.List;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.fragment.ImageListFragment;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.realm.RealmAccessor;
import alonedroid.com.calmemo.scene.photographs.CmPhotographsActivity_;

@EFragment(R.layout.fragment_album)
public class CmAlbumFragment extends Fragment {

    @FragmentArg
    String argDisplayDate;

    @Bean
    RealmAccessor accessor;

    public static CmAlbumFragment newInstance(String displayDate) {
        CmAlbumFragment_.FragmentBuilder_ builder_ = CmAlbumFragment_.builder();
        builder_.argDisplayDate(displayDate);
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        setImageListFragment();
    }

    void setImageListFragment() {
        List<CmPhoto> list = this.accessor.getPhotosByDate(this.argDisplayDate);
        ImageListFragment fragment = ImageListFragment.newInstance(list.toArray(new CmPhoto[0]));
        fragment.setClickedListener(this::OnImageClickedListener);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_album, fragment)
                .commit();
    }

    private void OnImageClickedListener(CmPhoto bean) {
        Intent intent = CmPhotographsActivity_.getIntent(bean.getCmDateTime().toString(), bean.getCmPhoto());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO 一覧が変更されていれば更新する
    }
}
