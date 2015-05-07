package alonedroid.com.calmemo.scene.calendar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

@EActivity(R.layout.activity_cm_calendar)
public class CmCalendarActivity extends ActionBarActivity {

    private CmCalendarFragment fragment;

    @AfterViews
    void onAfterViews() {
        this.fragment = CmCalendarFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_cm_calendar, this.fragment)
                .commit();
    }

    public static Intent newIntent() {
        CmCalendarActivity_.IntentBuilder_ builder_ = CmCalendarActivity_.intent(CmApplication.getContext());
        return builder_.get();
    }

    public void setActivityAnimation() {
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }
}
