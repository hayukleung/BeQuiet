package com.hayukleung.bequiet.ui.guide;

import android.view.View;

public class SCPositionAnimation extends SCPageAnimation {

  private final int mOriginalX;
  private final int mPerValueX;
  private final int mOriginalY;
  private final int mPerValueY;

  /**
   * @param forPage page to apply animation
   */

  public SCPositionAnimation(int forPage, int fromX, int toX, int fromY, int toY) {
    super(forPage);
    this.mOriginalX = fromX;
    this.mPerValueX = (toX - fromX);
    this.mOriginalY = fromY;
    this.mPerValueY = (toY - fromY);
  }

  public void applyTransformation(View onView, float positionOffset) {
    onView.setTranslationX((int) (mOriginalX + mPerValueX * positionOffset));
    onView.setTranslationY((int) (mOriginalY + mPerValueY * positionOffset));
    onView.requestLayout();
  }
}
