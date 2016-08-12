package com.hayukleung.bequiet.ui;

import android.os.Bundle;
import android.view.View;
import com.hayukleung.bequiet.EventBus;
import com.hayukleung.bequiet.eventbus.NotifyType;
import com.mdroid.utils.AndroidUtils;
import solid.ren.skinlibrary.base.SkinBaseActivity;

public class BaseActivity extends SkinBaseActivity implements NotifyType.INotify {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.bus().register(this);
  }

  @Override public void finish() {
    /**隐藏软键盘**/
    View view = getWindow().peekDecorView();
    if (view != null) {
      AndroidUtils.hideInputMethod(this, view.getWindowToken());
    }
    super.finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.bus().unregister(this);
  }

  @Override public void onNotify(NotifyType type) {
  }

  /**
   * @return Return a simple name
   */
  protected String getName() {
    return getClass().getCanonicalName();
  }
}
