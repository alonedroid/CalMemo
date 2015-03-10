package alonedroid.com.calmemo.scene;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Calendar;

import alonedroid.com.calmemo.R;

public class CmCalendarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout f = new FrameLayout(this);
        f.setId(R.id.id_fragment_container);
        setContentView(f);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CmCalendarFragment fragment = CmCalendarFragment.newInstance("", "");
        fragmentTransaction.replace(R.id.id_fragment_container, fragment);
        fragmentTransaction.commit();

    }
}
