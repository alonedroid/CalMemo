package alonedroid.com.calmemo.scene.cover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.scene.calendar.CmCalendarAdapter;

public class CmCoverActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        FragmentManager manager = getSupportFragmentManager();
        CmCoverFragment testFragment = CmCoverFragment_.newInstance(100, 100);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.cm_test_root, testFragment);
        transaction.commit();
    }
}
