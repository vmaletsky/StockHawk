package com.sam_chordas.android.stockhawk.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.StockTaskService;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;
import com.sam_chordas.android.stockhawk.ui.StockChartActivity;

/**
 * Created by v.maletskiy on 26.09.2016.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    private String LOG_TAG = getClass().getSimpleName();

    public static final String STOCK_ACTION =
            "com.sam_chordas.android.stockhawk.widget.STOCK_ACTION";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stocks_widget);

            Intent intent = new Intent(context, StockChartActivity.class);
            intent.setAction(STOCK_ACTION);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list, pendingIntent);
            views.setRemoteAdapter(R.id.widget_list,
                    new Intent(context, StockWidgetRemoteViewsService.class));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
    }
}
