package com.hayukleung.bequiet.ui.widget.clock;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.hayukleung.base.ResourceManager;
import com.hayukleung.bequiet.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.widget
 * ClockService.java
 *
 * by hayukleung
 * at 2016-08-30 16:44
 */
public class ClockService extends Service {
  private Timer timer;

  @Override public void onCreate() {
    super.onCreate();
    timer = new Timer();
    timer.schedule(new MyTimerTask(), 0, 1000);
  }

  private final class MyTimerTask extends TimerTask {

    @Override public void run() {

      Date date = new Date();

      String h = new SimpleDateFormat("hh", Locale.CHINA).format(date);
      String m = new SimpleDateFormat("mm", Locale.CHINA).format(date);
      String s = new SimpleDateFormat("ss", Locale.CHINA).format(date);

      // 获取Widgets管理器
      AppWidgetManager widgetManager = AppWidgetManager.getInstance(getApplicationContext());
      // widgetManager所操作的Widget对应的远程视图即当前Widget的layout文件
      RemoteViews remoteView =
          new RemoteViews(getPackageName(), R.layout.app_widget_clock);
      // TODO
      // remoteView.setTextViewText(R.id.time, time);

      remoteView.setImageViewResource(R.id.time_h, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_hour_%s", h)));
      remoteView.setImageViewResource(R.id.time_m, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_min_%s", m)));
      remoteView.setImageViewResource(R.id.time_s, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_sec_%s", s)));

      // 当点击Widgets时触发的世界
      // remoteView.setOnClickPendingIntent(viewId, pendingIntent)
      ComponentName componentName =
          new ComponentName(getApplicationContext(), ClockAppWidgetProvider.class);
      widgetManager.updateAppWidget(componentName, remoteView);
    }
  }

  @Override public void onDestroy() {
    timer.cancel();
    timer = null;
    super.onDestroy();
  }

  @Override public IBinder onBind(Intent intent) {
    return null;
  }
}