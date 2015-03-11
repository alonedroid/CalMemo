package alonedroid.com.calmemo;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class CmApplication extends Application {
    public static int mDisplayWidth;
    public static int mDisplayHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        initDisplaySize();
    }

    /**
     * アプリの描画領域をゲット・セット
     */
    private void initDisplaySize() {
        WindowManager wm = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        CmApplication.mDisplayWidth = size.x;
        CmApplication.mDisplayHeight = size.y;
    }
}