package alonedroid.com.calmemo.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import alonedroid.com.calmemo.R;

public class CmWidgetIntentReceiver extends BroadcastReceiver {
    public static int clickCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("UPDATE_WIDGET")) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.cm_widget_layout);

            // テキストをクリック回数を元に更新
            remoteViews.setTextViewText(R.id.title, "クリック回数: " + CmWidgetIntentReceiver.clickCount);

            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);

            // もう一回クリックイベントを登録(毎回登録しないと上手く動かず)
            remoteViews.setOnClickPendingIntent(R.id.button, CmWidgetProvider.clickButton(context));

            CmWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
        }
    }
}