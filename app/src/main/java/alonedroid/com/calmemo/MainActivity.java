package alonedroid.com.calmemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.scene.calendar.CmCalendarActivity;
import io.realm.Realm;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Click
    public void take_a_picture(View view) {
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
    }


    public void savePhoto(Bitmap bitmap) {
        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));
        realm.beginTransaction();

        CmPhoto photo = realm.createObject(CmPhoto.class);
        photo.setDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        photo.setTime(new SimpleDateFormat("HHmmss").format(new Date()));
        photo.setPhoto(CmUtility.decodeBitmap(bitmap));
        realm.commitTransaction();

        bitmap.recycle();
    }

    @Click
    public void show_calendar(View view) {
        startActivity(new Intent(this, CmCalendarActivity.class));
    }
}
