package alonedroid.com.calmemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.res.IntegerRes;

import java.io.IOException;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.RealmAccessor;
import alonedroid.com.calmemo.utility.BitmapUtility;
import alonedroid.com.calmemo.utility.CmPhotoPreserver;

@EActivity(R.layout.activity_main)
public class CmCameraActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 1001;

    @IntegerRes
    int saveImageLength;

    @Bean
    RealmAccessor accessor;

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
            this.accessor.savePhotoRealm(bitmap, this.preserver.getFileNameDate(), this.preserver.getFileNameTime());
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        this.accessor.close();
        super.onDestroy();
    }
}
