package com.hayukleung.bequiet.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import com.hayukleung.bequiet.EventBus;
import com.hayukleung.bequiet.eventbus.NotifyType;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import solid.ren.skinlibrary.base.SkinBaseFragment;

public abstract class BaseFragment extends SkinBaseFragment implements NotifyType.INotify {

  private List<WeakReference<Subscription>> mSubscriptions;

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

  public void addSubscription(Subscription subscription) {
    if (mSubscriptions == null) {
      mSubscriptions = new ArrayList<>(3);
    }
    mSubscriptions.add(new WeakReference<>(subscription));
  }

  @Override public void onDestroy() {
    super.onDestroy();
    destroy();
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

  @Override public void destroy() {
    super.destroy();
    if (mSubscriptions != null) {
      for (WeakReference<Subscription> reference : mSubscriptions) {
        Subscription s = reference.get();
        if (s != null && !s.isUnsubscribed()) {
          s.unsubscribe();
        }
      }
    }
  }
}
