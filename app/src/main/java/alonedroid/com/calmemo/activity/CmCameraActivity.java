package alonedroid.com.calmemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.CmUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import hugo.weaving.DebugLog;
import io.realm.Realm;

public class CmCameraActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cm_camera);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bitmap b = (Bitmap) data.getExtras().get("data");
            savePhoto(b);

            b.recycle();
        }

        finish();
    }

    @DebugLog
    public void savePhoto(Bitmap bitmap) {
        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));
        realm.beginTransaction();

        CmPhoto photo = realm.createObject(CmPhoto.class);
        photo.setCm_date(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        photo.setCm_time(new SimpleDateFormat("HHmmss").format(new Date()));
        photo.setCm_photo(CmUtility.decodeBitmap(bitmap));
        photo.setCm_action("");
        realm.commitTransaction();

        bitmap.recycle();
    }
}
