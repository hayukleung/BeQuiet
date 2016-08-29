package com.hayukleung.bequiet.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import com.hayukleung.bequiet.EventBus;
import com.hayukleung.bequiet.eventbus.NotifyType;
import solid.ren.skinlibrary.base.SkinBaseFragment;

public abstract class BaseFragment extends SkinBaseFragment implements NotifyType.INotify {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.bus().register(this);
  }

  @Override public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    return super.onCreateAnimation(transit, enter, nextAnim);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  public void prePause() {
  }

  @Override public void onDestroy() {
    super.onDestroy();
    // destroy();
    EventBus.bus().unregister(this);
  }

  @Override public void onNotify(NotifyType type) {
    switch (type.getType()) {
    }
  }

  /**
   * @return Return a simple name
   */
  protected abstract String getName();
}
