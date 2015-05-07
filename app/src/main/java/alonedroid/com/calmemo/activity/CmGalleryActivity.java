package alonedroid.com.calmemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.res.IntegerRes;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.RealmAccessor;
import alonedroid.com.calmemo.utility.BitmapUtility;
import alonedroid.com.calmemo.utility.CalendarUtility;

@EActivity(R.layout.activity_main)
public class CmGalleryActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 1001;

    @StringRes
    String cmLoadImageFailed;

    @StringRes
    String messageFailedDatetime;

    @StringRes
    String resultDate;

    @IntegerRes
    int saveImageLength;

    @Bean
    RealmAccessor accessor;

    @Bean
    CalendarUtility calendarUtility;

    public static Intent newIntent() {
        CmGalleryActivity_.IntentBuilder_ builder_ = CmGalleryActivity_.intent(CmApplication.getContext());
        return builder_.get();
    }

    @AfterInject
    void onAfterInject() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnActivityResult(REQUEST_CODE)
    void resultPhotographs(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            finish();
            return;
        }

        String filePath = getFilePath(data);
        if (filePath == null) return;

        String datetime = BitmapUtility.getDatetime(filePath);
        if (datetime == null) {
            CmApplication.show(this.messageFailedDatetime);
        } else {
            this.calendarUtility.setDateTime(datetime);
        }

        savePhoto(filePath, this.calendarUtility.getDate(), this.calendarUtility.getTime());
        Intent intent = new Intent();
        intent.putExtra(this.resultDate, this.calendarUtility.getDate());
        setResult(RESULT_OK, intent);
        finish();
    }

    private String getDateFromFormat(String format, Calendar c) {
        return new SimpleDateFormat(format).format(c.getTime());
    }

    private String getFilePath(Intent data) {
        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cur = getContentResolver().query(data.getData(), columns, null, null, null);
        cur.moveToNext();

        String filePath = cur.getString(0);
        if (filePath == null) {
            CmApplication.show(this.cmLoadImageFailed);
        }

        return filePath;
    }

    private void savePhoto(String filePath, String date, String time) {
        try {
            Bitmap bitmap = BitmapUtility.resize(Uri.parse("file://" + filePath), this.saveImageLength, this.saveImageLength);
            this.accessor.savePhotoRealm(bitmap, date, time);
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