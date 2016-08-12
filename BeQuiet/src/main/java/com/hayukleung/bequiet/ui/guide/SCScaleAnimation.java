package com.hayukleung.bequiet.ui.guide;

import android.view.View;

public class SCScaleAnimation extends SCPageAnimation {

  private final float mOriginalX;
  private final float mPerValueX;
  private final float mOriginalY;
  private final float mPerValueY;

  /**
   * @param forPage page to apply animation
   */
  public SCScaleAnimation(int forPage, float fromX, float toX, float fromY, float toY) {
    super(forPage);
    this.mOriginalX = fromX;
    this.mPerValueX = (toX - fromX);
    this.mOriginalY = fromY;
    this.mPerValueY = (toY - fromY);
  }

  @Override public void applyTransformation(View onView, float positionOffset) {
    onView.setScaleX(mOriginalX + mPerValueX * positionOffset);
    onView.setScaleY(mOriginalY + mPerValueY * positionOffset);
  }
}
