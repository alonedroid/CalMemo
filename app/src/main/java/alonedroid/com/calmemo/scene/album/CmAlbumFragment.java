package alonedroid.com.calmemo.scene.album;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import alonedroid.com.calmemo.CmUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.realm.RealmAccessor;
import hugo.weaving.DebugLog;

@EFragment(R.layout.fragment_album)
public class CmAlbumFragment extends Fragment {

    private static final String ARG_DISPLAY_DATE = "argDisplayDate";

    @ViewById
    ImageView CmAlbumImage;

    @Bean
    RealmAccessor accessor;

    @DebugLog
    public static CmAlbumFragment newInstance(String displayDate) {
        Bundle args = new Bundle();
        args.putString(ARG_DISPLAY_DATE, displayDate);

        CmAlbumFragment fragment = new CmAlbumFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    void onAfterViews() {
        List<CmPhoto> list = this.accessor.getPhotosByDate(getArguments().getString(ARG_DISPLAY_DATE));
        this.CmAlbumImage.setImageBitmap(CmUtility.decodeBitmapString(list.get(0).getCm_photo()));
    }
}
