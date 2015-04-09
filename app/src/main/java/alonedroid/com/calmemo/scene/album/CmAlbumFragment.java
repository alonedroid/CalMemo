package alonedroid.com.calmemo.scene.album;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import alonedroid.com.calmemo.BitmapUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.fragment.ImageListFragment;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.realm.RealmAccessor;
import hugo.weaving.DebugLog;

@EFragment(R.layout.fragment_album)
public class CmAlbumFragment extends Fragment {

    private static final String ARG_DISPLAY_DATE = "argDisplayDate";

    @FragmentArg
    String argDisplayDate;

    @Bean
    RealmAccessor accessor;

    public static CmAlbumFragment newInstance(String displayDate) {
        Bundle args = new Bundle();
        args.putString(ARG_DISPLAY_DATE, displayDate);

        CmAlbumFragment fragment = new CmAlbumFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    void onAfterViews() {
        setImageListFragment();
    }

    void setImageListFragment(){
        List<CmPhoto> list = this.accessor.getPhotosByDate(this.argDisplayDate);
        Fragment fragment = ImageListFragment.newInstance(list.toArray(new CmPhoto[0]));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_album, fragment)
                .commit();
    }
}
