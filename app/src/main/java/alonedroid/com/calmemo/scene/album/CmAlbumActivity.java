package alonedroid.com.calmemo.scene.album;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import alonedroid.com.calmemo.CmApplication;
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

    public static Intent newIntent(String date) {
        CmAlbumActivity_.IntentBuilder_ builder_ = CmAlbumActivity_.intent(CmApplication.getContext());
        builder_.argDisplayDate(date);
        return builder_.get();
    }

    public void setActivityAnimation() {
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
}
