package alonedroid.com.calmemo.scene.photographs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

@EActivity(R.layout.activity_cm_photographs)
public class CmPhotographsActivity extends ActionBarActivity {

    @Extra
    String argDisplayImageKey;

    @Extra
    String argDisplayImageString;

    @AfterViews
    void onAfterViews() {
        FragmentManager manager = getFragmentManager();
        CmPhotographsFragment fragment = CmPhotographsFragment_.newInstance(this.argDisplayImageKey, this.argDisplayImageString);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_cm_photographs, fragment);
        transaction.commit();
    }

    public static Intent newIntent(String key, String image) {
        CmPhotographsActivity_.IntentBuilder_ builder_ = CmPhotographsActivity_.intent(CmApplication.getContext());
        builder_.argDisplayImageKey(key);
        builder_.argDisplayImageString(image);
        return builder_.get();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
}
