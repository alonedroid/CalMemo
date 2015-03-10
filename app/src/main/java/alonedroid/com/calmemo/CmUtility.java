package alonedroid.com.calmemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class CmUtility {
    public static String decodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String bitmapString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        bitmap.recycle();

        return bitmapString;
    }

    public static Bitmap decodeBitmapString(String bitmap_str){
        byte[] bytes = Base64.decode(bitmap_str.getBytes(),Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}