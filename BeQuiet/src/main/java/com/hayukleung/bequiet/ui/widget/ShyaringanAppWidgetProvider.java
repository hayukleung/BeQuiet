package com.hayukleung.bequiet.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import com.mdroid.utils.Ln;

/**
 * 写轮眼小部件
 *
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.widget
 * ShyaringanAppWidgetProvider.java
 *
 * by hayukleung
 * at 2016-08-26 14:58
 */
public class ShyaringanAppWidgetProvider extends AppWidgetProvider {

  public ShyaringanAppWidgetProvider() {
    super();
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    Ln.e("Shyaringan Widget --> onUpdate");
    final int N = appWidgetIds.length;
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
      // RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_initial_layout_shyaringan);
      // TODO
      // remoteViews.setFloat(R.id.shyaringan, "setRotation", 0);

      // appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
  }
}
