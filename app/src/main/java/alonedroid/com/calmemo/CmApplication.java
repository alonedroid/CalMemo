package alonedroid.com.calmemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

@EApplication
public class CmApplication extends Application {

    @SystemService
    WindowManager windowManager;

    private static Context mContext;
    private static int mDisplayWidth;
    private static int mDisplayHeight;

    @AfterInject
    void onAfterInject() {
        initDisplaySize();
        setInitParameter();
    }

    private void initDisplaySize() {
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
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

    public static int divideDisplayWidth(int part) {
        return CmApplication.mDisplayWidth / part;
    }

    public static int divideDisplayHeight(int part) {
        return CmApplication.mDisplayHeight / part;
    }
}