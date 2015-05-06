package alonedroid.com.calmemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

@EApplication
public class CmApplication extends Application {

    @SystemService
    WindowManager windowManager;

    private static Context context;

    public static int displayWidth;

    public static int displayHeight;

    @AfterInject
    void onAfterInject() {
        initDisplaySize();
        setInitParameter();
    }

    private void initDisplaySize() {
        Point size = new Point();
        this.windowManager.getDefaultDisplay().getSize(size);
        CmApplication.displayWidth = size.x;
        CmApplication.displayHeight = size.y;
    }

    private void setInitParameter() {
        CmApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return CmApplication.context;
    }

    public static String getResourceString(int resourceId) {
        return CmApplication.context.getString(resourceId);
    }

    public static int divideDisplayWidth(int part) {
        return CmApplication.displayWidth / part;
    }

    public static int divideDisplayHeight(int part) {
        return CmApplication.displayHeight / part;
    }

    public static void show(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void log(String... params) {
        for (String param : params) {
            Log.d(CmApplication.class.getSimpleName(), param);
        }
    }
}