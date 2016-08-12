package com.hayukleung.bequiet.ui.guide;

import android.view.View;

public abstract class SCPageAnimation {
  public int mPage;

  public SCPageAnimation(int page) {
    mPage = page;
  }

  public abstract void applyTransformation(View onView, float positionOffset);
}