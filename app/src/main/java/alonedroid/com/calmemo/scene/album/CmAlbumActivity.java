package alonedroid.com.calmemo.scene.album;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import alonedroid.com.calmemo.R;

public class CmAlbumActivity extends ActionBarActivity {

    public static String ARG_DISPLAY_DATE = "argDisplayDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        String displayDate = getIntent().getStringExtra(ARG_DISPLAY_DATE);

        FragmentManager manager = getSupportFragmentManager();
        CmAlbumFragment fragment = CmAlbumFragment_.newInstance(displayDate);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_album, fragment);
        transaction.commit();
    }
}
