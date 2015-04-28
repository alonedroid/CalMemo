package alonedroid.com.calmemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.res.DimensionPixelSizeRes;
import org.androidannotations.annotations.res.IntegerRes;

import java.io.IOException;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.utility.BitmapUtility;
import alonedroid.com.calmemo.utility.CmPhotoPreserver;
import io.realm.Realm;

@EActivity(R.layout.activity_cm_camera)
public class CmCameraActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 1001;

    @IntegerRes
    int saveImageLength;

    @Bean
    CmPhotoPreserver preserver;

    @AfterInject
    void onAfterInject() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.preserver.preparedContentPng());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnActivityResult(REQUEST_CODE)
    void resultCameraActivity(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            this.preserver.destructContent();
        } else {
            savePhoto();
        }

        finish();
    }

    private void savePhoto() {
        try {
            Bitmap bitmap = BitmapUtility.resize(this.preserver.getImageUri(), this.saveImageLength, this.saveImageLength);
            this.preserver.init(bitmap);
            savePhotoRealm(bitmap, this.preserver.getFileNameDate(), this.preserver.getFileNameTime());
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePhotoRealm(Bitmap bitmap, String date, String time) {
        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));
        realm.beginTransaction();

        CmPhoto photo = realm.createObject(CmPhoto.class);
        photo.setCmDate(date);
        photo.setCmTime(time);
        photo.setCmPhoto(BitmapUtility.decodeBitmap(bitmap));
        photo.setCmAction("");
        realm.commitTransaction();

        bitmap.recycle();
    }
}
