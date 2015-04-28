package alonedroid.com.calmemo.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import alonedroid.com.calmemo.CmApplication;

public class BitmapUtility {

    public static String decodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String bitmapString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        bitmap.recycle();

        return bitmapString;
    }

    public static Bitmap decodeBitmapString(String bitmap_str) {
        byte[] bytes = Base64.decode(bitmap_str.getBytes(), Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap resize(Uri uri, int maxWidth, int maxHeight) throws IOException {
        BitmapFactory.Options decodeOptions = getOptions(uri);

        int actualWidth = decodeOptions.outWidth;
        int actualHeight = decodeOptions.outHeight;
        int desiredWidth = getResizedDimension(maxWidth, maxHeight, actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(maxHeight, maxWidth, actualHeight, actualWidth);

        decodeOptions.inJustDecodeBounds = false;
        decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);

        InputStream inputStream = CmApplication.getContext().getContentResolver().openInputStream(uri);
        Bitmap tempBitmap = BitmapFactory.decodeStream(inputStream, null, decodeOptions);
        inputStream.close();

        Bitmap bitmap;
        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
            bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
            tempBitmap.recycle();
        } else {
            bitmap = tempBitmap;
        }
        return bitmap;
    }

    private static BitmapFactory.Options getOptions(Uri uri) throws IOException {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream inputStream = CmApplication.getContext().getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(inputStream, null, decodeOptions);
        inputStream.close();

        return decodeOptions;
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        if ((maxPrimary == 0) && (maxSecondary == 0)) {
            return actualPrimary;
        }

        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;

        if ((resized * ratio) < maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    private static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }
}