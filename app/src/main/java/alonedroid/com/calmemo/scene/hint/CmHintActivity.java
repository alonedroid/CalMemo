package alonedroid.com.calmemo.scene.hint;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

@EActivity(R.layout.activity_cm_hint)
public class CmHintActivity extends ActionBarActivity {

    @AfterViews
    void onAfterViews() {
        FragmentManager manager = getFragmentManager();
        CmHintFragment fragment = CmHintFragment_.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_cm_hint, fragment);
        transaction.commit();
    }

    public static Intent newIntent() {
        CmHintActivity_.IntentBuilder_ builder_ = CmHintActivity_.intent(CmApplication.getContext());
        return builder_.get();
    }
}
