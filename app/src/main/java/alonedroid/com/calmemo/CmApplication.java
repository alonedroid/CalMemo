package alonedroid.com.calmemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class CmApplication extends Application {
    private static Context mContext;
    public static int mDisplayWidth;
    public static int mDisplayHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        initDisplaySize();
        setInitParameter();
    }

    private void initDisplaySize() {
        Point size = new Point();
        WindowManager wm = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);
        CmApplication.mDisplayWidth = size.x;
        CmApplication.mDisplayHeight = size.y;
    }

    private void setInitParameter() {
        CmApplication.mContext = getApplicationContext();
    }

    public static Context getContext() {
        return CmApplication.mContext;
    }

    public static String getResourceString(int resourceId) {
        return CmApplication.mContext.getString(resourceId);
    }
}