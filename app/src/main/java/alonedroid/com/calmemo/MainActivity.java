package alonedroid.com.calmemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.scene.calendar.CmCalendarActivity;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById(R.id.action_name)
    EditText mActionName;

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
        photo.setCm_date(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        photo.setCm_time(new SimpleDateFormat("HHmmss").format(new Date()));
        photo.setCm_photo(CmUtility.decodeBitmap(bitmap));
        photo.setCm_action("");
        realm.commitTransaction();

        bitmap.recycle();
    }

    @Click
    public void show_calendar(View view) {
        startActivity(new Intent(this, CmCalendarActivity.class));
    }

    @Click
    public void commit_action(View view) {
        String cm_action = this.mActionName.getText().toString();

        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));
        realm.beginTransaction();

        CmPhoto photo = realm.createObject(CmPhoto.class);
        photo.setCm_date_time(new Date());
        photo.setCm_date(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        photo.setCm_time(new SimpleDateFormat("HHmmss").format(new Date()));
        photo.setCm_photo(cm_action);
        photo.setCm_action(cm_action);
        realm.commitTransaction();

        Toast.makeText(this, "save", Toast.LENGTH_LONG).show();
    }

    @Click
    public void search(View view) {
        String cm_action = this.mActionName.getText().toString();

        String latest_action_time = getCmAction(cm_action);

        Toast.makeText(this, latest_action_time, Toast.LENGTH_LONG).show();
    }

    public String getCmAction(String cm_action) {
        Realm realm = Realm.getInstance(this, getString(R.string.realm_instance));

        RealmQuery<CmPhoto> query = realm.where(CmPhoto.class);
        query.equalTo(CmPhoto.CM_ACTION, cm_action);
        RealmResults<CmPhoto> resultAll = query.findAll();
        RealmResults<CmPhoto> result =
                realm.where(CmPhoto.class)
                        .equalTo(CmPhoto.CM_ACTION, cm_action)
//                        .or()
//                        .equalTo("name", "Chip")
                        .findAll();

//        RealmResults<CmPhoto> sortedAscending  = result.sort("age");
//
        RealmResults<CmPhoto> sortedDescending =
                result.sort(CmPhoto.CM_DATE_TIME, RealmResults.SORT_ORDER_DECENDING);


        return sortedDescending.get(0).getCm_date();

    }
}
