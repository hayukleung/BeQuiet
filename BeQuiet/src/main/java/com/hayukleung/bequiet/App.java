package com.hayukleung.bequiet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.os.StrictMode;
import cn.sharesdk.framework.ShareSDK;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TypeAdapters;
import com.hayukleung.bequiet.json.IntegerAdapter;
import com.hayukleung.bequiet.ui.skin.attr.FANBackgroundAttr;
import com.hayukleung.bequiet.ui.skin.attr.FANSrcAttr;
import com.mdroid.Library;
import com.mdroid.PausedHandler;
import com.mdroid.lifecycle.LifecycleDispatcher;
import com.squareup.leakcanary.RefWatcher;
import java.lang.ref.WeakReference;
import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

public class App extends SkinBaseApplication {

  private static App mInstance;
  protected Activity mActivity;
  protected boolean mVisible = false;
  protected PausedHandler mHandler;
  private Gson mGson;

  private RefWatcher mRefWatcher;

  private boolean mShowTimeOut0;
  private boolean mShowTimeOut1;

  public static App Instance() {
    return mInstance;
  }

  public static void setShowTimeOut0(boolean show) {
    mInstance.mShowTimeOut0 = show;
  }

  public static void setShowTimeOut1(boolean show) {
    mInstance.mShowTimeOut1 = show;
  }

  public static boolean isShowTimeOut() {
    return mInstance.mShowTimeOut0 || mInstance.mShowTimeOut1;
  }

  public static PausedHandler getMainHandler() {
    return mInstance.mHandler;
  }

  public static boolean isVisible() {
    return mInstance.mVisible;
  }

  public static synchronized Gson getGson() {
    if (mInstance.mGson == null) {
      mInstance.mGson = new GsonBuilder().registerTypeAdapterFactory(
          TypeAdapters.newFactory(int.class, Integer.class, new IntegerAdapter())).create();
    }
    return mInstance.mGson;
  }

  private static SharedPreferences getPreferences() {
    return mInstance.getSharedPreferences("configuration.pref", Context.MODE_MULTI_PROCESS);
  }

  private static SharedPreferences getSharePreferences() {
    return mInstance.getSharedPreferences("shared.pref",
        Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
  }

  public static RefWatcher getRefWatcher() {
    return mInstance.mRefWatcher;
  }

  @Override public void onCreate() {

    super.onCreate();

    SkinConfig.setCanChangeStatusColor(true);
    SkinConfig.addSupportAttr("FANSrcL", new FANSrcAttr());
    SkinConfig.addSupportAttr("FANSrcM", new FANSrcAttr());
    SkinConfig.addSupportAttr("FANSrcH", new FANSrcAttr());
    SkinConfig.addSupportAttr("FANSrcX", new FANSrcAttr());
    SkinConfig.addSupportAttr("FANBackground", new FANBackgroundAttr());

    mInstance = this;
    init();
  }

  private void init() {
    Library.init(this);

    mHandler = new Handler(this);

    if (BuildConfig.DEBUG && false) {
      StrictMode.setThreadPolicy(
          new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDialog().build());
      StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        builder.detectLeakedRegistrationObjects()
            //                        .detectActivityLeaks()
            .detectCleartextNetwork().detectFileUriExposure();
      }
      builder.detectLeakedClosableObjects()
          .detectLeakedSqlLiteObjects()
          .penaltyDeath()
          .penaltyLog();
      StrictMode.setVmPolicy(builder.build());
    }

    // Share sdk
    ShareSDK.initSDK(getApplicationContext());

    ActivityLifecycle lifecycle = new ActivityLifecycle();
    lifecycle.setVisibleListener(new ActivityLifecycle.VisibleListener() {
      @Override public void statusChange(boolean visible) {
        if (visible) {
          mHandler.resume();
        } else {
          mHandler.pause();
        }
      }
    });
    LifecycleDispatcher.registerActivityLifecycleCallbacks(this, lifecycle);

    Toost.initialize(getApplicationContext());
  }

  @Override public void onTerminate() {
    Toost.terminate();
    super.onTerminate();
  }

  private static class Handler extends PausedHandler {
    private WeakReference<App> mApp;

    public Handler(App app) {
      mApp = new WeakReference<>(app);
    }

    @Override protected void processMessage(Message message) {
    }
  }
}
