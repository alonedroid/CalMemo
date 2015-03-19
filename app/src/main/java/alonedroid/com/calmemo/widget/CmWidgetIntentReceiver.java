package alonedroid.com.calmemo.widget;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import alonedroid.com.calmemo.MainActivity;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.activity.CmCameraActivity;
import hugo.weaving.DebugLog;

public class CmWidgetIntentReceiver extends BroadcastReceiver {
    @DebugLog
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getString(R.string.cm_widget_receive_action))) {

            Intent camera_intent = new Intent(context, CmCameraActivity.class);
            camera_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(camera_intent);
        }
    }
}