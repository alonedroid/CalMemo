package alonedroid.com.calmemo.utility;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import lombok.Getter;

@EBean
public class CmPhotoPreserver {

    @RootContext
    Context context;

    @StringRes
    String prefixPng;

    @StringRes
    String prefixJpg;

    @StringRes
    String mimetypeJpg;

    @StringRes
    String mimetypePng;

    @Getter
    private Uri imageUri;

    @Getter
    private String fileNameDate;

    @Getter
    private String fileNameTime;

    private Bitmap bitmap;

    private String folderPath;

    private String fileName;

    private String fileNamePng;

    private String fileNameJpg;

    public void init(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        makeSaveFolder();
        generateFileName();
    }

    public void saveBitmapPng() throws IOException {
        saveBitmap(this.prefixPng, Bitmap.CompressFormat.PNG);
    }

    public void saveBitmapJpg() throws IOException {
        saveBitmap(this.prefixJpg, Bitmap.CompressFormat.JPEG);
    }

    private void saveBitmap(String prefix, Bitmap.CompressFormat file_type) throws IOException {
        if (this.bitmap.isRecycled()) {
            return;
        }

        File file = new File(this.folderPath, this.fileName + prefix);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        this.bitmap.compress(file_type, 100, bos);
        bos.close();

        this.bitmap.recycle();
    }

    private void makeSaveFolder() {
        final String SAVE_DIR = File.separator + CmApplication.getContext().getString(R.string.app_name) + File.separator;
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        this.folderPath = file.getAbsolutePath();
    }

    private void generateFileName() {
        Date date = new Date();
        this.fileNameDate = new SimpleDateFormat("yyyyMMdd").format(date);
        this.fileNameTime = new SimpleDateFormat("HHmmss").format(date);
        this.fileName = this.fileNameDate + "_" + this.fileNameTime;
        this.fileNamePng = this.fileName + this.prefixPng;
        this.fileNameJpg = this.fileName + this.prefixJpg;
    }

    public Uri preparedContentPng() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, this.fileNamePng);
        values.put(MediaStore.Images.Media.MIME_TYPE, this.mimetypePng);
        this.imageUri = this.context.getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return this.imageUri;
    }

    public void destructContent(){
        this.context.getContentResolver()
                .delete(this.imageUri, null, null);
    }
}