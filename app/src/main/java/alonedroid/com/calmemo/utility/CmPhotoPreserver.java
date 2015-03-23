package alonedroid.com.calmemo.utility;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

public class CmPhotoPreserver {
    private static final String PREFIX_PNG = ".png";
    private static final String PREFIX_JPG = ".jpg";
    private static final String MIMETYPE_JPG = "jpeg";

    private String mFolderPath;
    private String mFileName;

    private String mFileNameDate;
    private String mFileNameTime;

    private Bitmap mBitmap;

    public CmPhotoPreserver(Bitmap bitmap) {
        this.mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        makeSaveFolder();
        generateFileName();
    }

    public void saveBitmap_png() throws IOException {
        saveBitmap(PREFIX_PNG, Bitmap.CompressFormat.PNG);
    }

    public void saveBitmap_jpg() throws IOException {
        saveBitmap(PREFIX_JPG, Bitmap.CompressFormat.JPEG);
    }

    private void saveBitmap(String prefix, Bitmap.CompressFormat file_type) throws IOException {
        if (this.mBitmap.isRecycled()) {
            return;
        }

        File file = new File(this.mFolderPath, this.mFileName + prefix);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        this.mBitmap.compress(file_type, 100, bos);
        bos.close();

        this.mBitmap.recycle();
    }

    private void makeSaveFolder() {
        final String SAVE_DIR = File.separator + CmApplication.getContext().getString(R.string.app_name) + File.separator;
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        this.mFolderPath = file.getAbsolutePath();
    }

    private void generateFileName() {
        Date date = new Date();
        this.mFileNameDate = new SimpleDateFormat("yyyyMMdd").format(date);
        this.mFileNameTime = new SimpleDateFormat("HHmmss").format(date);
        this.mFileName = this.mFileNameDate + "_" + this.mFileNameTime;
    }

    public String getFileNameDate() {
        return this.mFileNameDate;
    }

    public String getFileNameTime() {
        return this.mFileNameTime;
    }
}