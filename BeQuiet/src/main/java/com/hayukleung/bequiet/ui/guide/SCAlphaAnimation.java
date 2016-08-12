package com.hayukleung.bequiet.ui.guide;

import android.view.View;

/**
 * 引导页透明度动画
 */
public class SCAlphaAnimation extends SCPageAnimation {

  private final float mOriginalAlpha;
  private final float mPerValueAlpha;

  public SCAlphaAnimation(int page, float fromAlpha, float toAlpha) {
    super(page);
    this.mOriginalAlpha = fromAlpha;
    this.mPerValueAlpha = (toAlpha - fromAlpha);
  }

  @Override public void applyTransformation(View onView, float positionOffset) {
    onView.setAlpha(mOriginalAlpha + mPerValueAlpha * positionOffset);
  }
}
