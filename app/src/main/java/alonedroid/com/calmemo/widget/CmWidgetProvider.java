package alonedroid.com.calmemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import alonedroid.com.calmemo.R;

public class CmWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_cm_layout);

        Intent intent = new Intent(context.getString(R.string.cm_widget_receive_action));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.cm_widget_button, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}