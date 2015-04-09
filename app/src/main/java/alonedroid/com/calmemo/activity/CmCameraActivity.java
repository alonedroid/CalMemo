package alonedroid.com.calmemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.io.IOException;

import alonedroid.com.calmemo.BitmapUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.utility.CmPhotoPreserver;
import hugo.weaving.DebugLog;
import io.realm.Realm;

public class CmCameraActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cm_camera);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || requestCode != REQUEST_CODE) {
            return;
        }

        try {
            Bitmap b = (Bitmap) data.getExtras().get("data");
            CmPhotoPreserver provider = new CmPhotoPreserver(b);
            provider.saveBitmap_png();
            savePhotoRealm(b, provider.getFileNameDate(), provider.getFileNameTime());

            b.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

    @DebugLog
    public void savePhotoRealm(Bitmap bitmap, String date, String time) {
        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));
        realm.beginTransaction();

        CmPhoto photo = realm.createObject(CmPhoto.class);
        photo.setCm_date(date);
        photo.setCm_time(time);
        photo.setCm_photo(BitmapUtility.decodeBitmap(bitmap));
        photo.setCm_action("");
        realm.commitTransaction();

        bitmap.recycle();
    }
}
