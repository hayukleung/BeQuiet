package com.hayukleung.bequiet.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.Toost;
import com.hayukleung.bequiet.eventbus.NotifyType;
import com.hayukleung.bequiet.ui.BaseActivity;
import com.squareup.otto.Subscribe;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity {

  private long mBackTime;

  @Override public void onCreate(Bundle savedInstanceState) {
    requestDisableSwipe();
    super.onCreate(savedInstanceState);
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
  }

  @Override protected String getName() {
    return "主页面";
  }

  @Override public void onBackPressed() {
    long time = SystemClock.elapsedRealtime();
    if (time - mBackTime > 2000) {
      Toost.message(R.string.back_pressed_tips);
      mBackTime = time;
      return;
    }
    super.onBackPressed();
  }

  /**
   * 处理EventBus事件
   */
  @Subscribe @Override public void onNotify(NotifyType type) {
    switch (type.getType()) {
    }
  }
}
