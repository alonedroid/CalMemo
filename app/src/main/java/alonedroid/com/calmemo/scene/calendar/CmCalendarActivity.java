package alonedroid.com.calmemo.scene.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import alonedroid.com.calmemo.R;

public class CmCalendarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager pager = new ViewPager(this);
        pager.setId(R.id.id_fragment_container);
        pager.setAdapter(new CmCalendarAdapter(getSupportFragmentManager()));
        setContentView(pager);

//        FrameLayout f = new FrameLayout(this);
//        f.setId(R.id.id_fragment_container);
//        setContentView(f);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        CmCalendarFragment fragment = CmCalendarFragment.newInstance("", "");
//        fragmentTransaction.replace(R.id.id_fragment_container, fragment);
//        fragmentTransaction.commit();
    }
}
