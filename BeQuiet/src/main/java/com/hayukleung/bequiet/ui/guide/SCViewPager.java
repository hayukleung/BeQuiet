package com.hayukleung.bequiet.ui.guide;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import java.util.ArrayList;

public class SCViewPager extends ViewPager {

  private ArrayList<SCViewAnimation> mViewAnimation;

  public SCViewPager(Context context) {
    super(context);
    init();
  }

  public SCViewPager(Context context, AttributeSet attr) {
    super(context, attr);
    init();
  }

  private void init() {
    this.mViewAnimation = new ArrayList<SCViewAnimation>();
    addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < mViewAnimation.size(); i++) {
          mViewAnimation.get(i).applyAnimation(position, positionOffset);
        }
      }

      @Override public void onPageSelected(int position) {
      }

      @Override public void onPageScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE) initialize();
      }
    });
  }

  public void addAnimation(SCViewAnimation inViewAnimation) {
    mViewAnimation.add(inViewAnimation);
  }

  public void initialize() {
    int position = getCurrentItem();
    for (int i = 0; i < mViewAnimation.size(); i++) {
      SCViewAnimation animation = mViewAnimation.get(i);
      for (int j = 0; j < getAdapter().getCount(); j++) {
        animation.applyAnimation(j, position == j ? 0 : 1);
      }
    }
  }
}