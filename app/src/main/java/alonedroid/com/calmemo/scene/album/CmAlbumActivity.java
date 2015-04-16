package alonedroid.com.calmemo.scene.album;

import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import alonedroid.com.calmemo.R;

@EActivity(R.layout.activity_cm_album)
public class CmAlbumActivity extends ActionBarActivity {

    @Extra
    String argDisplayDate;

    @AfterViews
    void onAfterViews() {
        CmAlbumFragment fragment = CmAlbumFragment_.newInstance(this.argDisplayDate);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_album, fragment)
                .commit();
    }
}
