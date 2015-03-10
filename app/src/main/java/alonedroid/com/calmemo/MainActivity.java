package alonedroid.com.calmemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.scene.CmCalendarActivity;
import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, CmCalendarActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test(View view) {
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
}
