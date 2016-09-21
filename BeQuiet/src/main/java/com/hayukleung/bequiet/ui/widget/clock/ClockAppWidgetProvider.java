package com.hayukleung.bequiet.ui.widget.clock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.RemoteViews;
import com.hayukleung.bequiet.R;
import com.mdroid.utils.Ln;

/**
 * 写轮眼小部件
 *
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.widget.clock
 * ClockAppWidgetProvider.java
 *
 * by hayukleung
 * at 2016-08-26 14:58
 */
public class ClockAppWidgetProvider extends AppWidgetProvider {

  public ClockAppWidgetProvider() {
    super();
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    Ln.e("Shyaringan Widget --> onUpdate");
    final int N = appWidgetIds.length;
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_clock);

      // for android
      if (isIntentCorrect(context, "com.android.deskclock", "com.android.deskclock.DeskClock")) {
        remoteViews.setOnClickPendingIntent(R.id.clock, getClockPendingIntent(context, "com.android.deskclock", "com.android.deskclock.DeskClock"));
      }
      // for nexus
      if (isIntentCorrect(context, "com.google.android.deskclock", "com.android.deskclock.DeskClock")) {
        remoteViews.setOnClickPendingIntent(R.id.clock, getClockPendingIntent(context, "com.google.android.deskclock", "com.android.deskclock.DeskClock"));
      }
      // for 奇酷360
      if (isIntentCorrect(context, "com.yulong.android.xtime", "yulong.xtime.ui.main.XTimeActivity")) {
        remoteViews.setOnClickPendingIntent(R.id.clock, getClockPendingIntent(context, "com.yulong.android.xtime", "yulong.xtime.ui.main.XTimeActivity"));
      }
      // TODO for other android phone

      // remoteViews.setFloat(R.id.shyaringan, "setRotation", 0);
      appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    context.startService(new Intent(context, ClockService.class));
  }

  @Override public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
    Ln.e("Shyaringan Widget --> onDeleted");
  }

  @Override public void onEnabled(Context context) {
  }

  @Override public void onDisabled(Context context) {
    context.stopService(new Intent(context, ClockService.class));
  }

  private boolean isIntentCorrect(Context context, final String pkgName, final String clzName) {
    Intent clockIntent;
    ResolveInfo resolved;

    clockIntent = new Intent().setComponent(new ComponentName(pkgName, clzName));
    resolved = context.getPackageManager().resolveActivity(clockIntent, PackageManager.MATCH_DEFAULT_ONLY);
    return null != resolved;
  }

  private PendingIntent getClockPendingIntent(Context context, final String pkgName, final String clzName) {
    Intent clockIntent;

    clockIntent = new Intent().setComponent(new ComponentName(pkgName, clzName));
    return PendingIntent.getActivity(context, 2, clockIntent, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
