package alonedroid.com.calmemo;

import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;

import alonedroid.com.calmemo.scene.calendar.CmCalendarActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @AfterInject
    void onAfterInject() {
        startActivity(CmCalendarActivity.newIntent());
        finish();
    }
}
