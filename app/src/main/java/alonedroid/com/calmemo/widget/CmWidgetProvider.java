package alonedroid.com.calmemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import alonedroid.com.calmemo.R;

public class CmWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // ウィジェットレイアウトの初期化
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.cm_widget_layout);

        // ボタンイベントを登録
        remoteViews.setOnClickPendingIntent(R.id.button, clickButton(context));

        // テキストフィールドに"初期画面"と表示
        remoteViews.setTextViewText(R.id.title, "初期画面");

        // アップデートメソッド呼び出し
        pushWidgetUpdate(context, remoteViews);
    }

    public static PendingIntent clickButton(Context context) {
        // クリック回数を増加
        CmWidgetIntentReceiver.clickCount ++;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction("UPDATE_WIDGET");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // アップデート
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, CmWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}