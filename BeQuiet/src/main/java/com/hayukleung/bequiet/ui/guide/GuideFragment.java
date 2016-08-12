package com.hayukleung.bequiet.ui.guide;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.db.DBKeys;
import com.hayukleung.bequiet.ui.BaseFragment;
import com.hayukleung.bequiet.ui.skin.Skin;
import com.hayukleung.bequiet.ui.skin.Utils;
import com.hayukleung.bequiet.ui.view.Fan;
import com.mdroid.DBUtils;
import com.mdroid.utils.AndroidUtils;

/**
 * 引导页
 */
public class GuideFragment extends BaseFragment {

  private static final int NUM_PAGES = 4;

  @Bind(R.id.word_p1) TextView mWordP1;
  @Bind(R.id.fan_1) Fan mFan1;
  @Bind(R.id.guide_1) PercentRelativeLayout mGuide1;

  @Bind(R.id.word_p2) TextView mWordP2;
  @Bind(R.id.fan_2) Fan mFan2;
  @Bind(R.id.guide_2) PercentRelativeLayout mGuide2;

  @Bind(R.id.word_p3) TextView mWordP3;
  @Bind(R.id.fan_3) Fan mFan3;
  @Bind(R.id.guide_3) PercentRelativeLayout mGuide3;

  @Bind(R.id.word_p4) TextView mWordP4;
  @Bind(R.id.fan_4) Fan mFan4;
  @Bind(R.id.start_app) View mStartApp;
  @Bind(R.id.guide_4) PercentRelativeLayout mGuide4;

  @Bind(R.id.pager) SCViewPager mPager;

  private int mWidth;
  private int mHeight;

  private int mCurrentPage = 0;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mWidth = AndroidUtils.getWidth(getActivity());
    mHeight = AndroidUtils.getHeight(getActivity());
  }

  @Override protected String getName() {
    return "引导页";
  }

  @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.content_guide, parent, false);
  }

  @OnClick({ R.id.start_app }) public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_app: {
        DBUtils.write(DBKeys.GUIDE_SHOW, false);
        getActivity().finish();
        break;
      }
    }
  }

  @Override public boolean onBackPressed() {
    return true;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SCViewPagerAdapter adapter = new SCViewPagerAdapter(getChildFragmentManager());
    adapter.setNumberOfPage(NUM_PAGES);
    adapter.setFragmentBackgroundColor(R.color.white);
    mPager.setAdapter(adapter);

    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override public void onPageSelected(int position) {
        switch (mCurrentPage = position) {
          case 0: {
            mFan1.start(new Fan.FanHelper() {
              @Override public float getSpeedX() {
                return 80;
              }
            });
            mFan2.stop();
            mFan3.stop();
            mFan4.stop();
            break;
          }
          case 1: {
            mFan1.stop();
            mFan2.start(new Fan.FanHelper() {
              @Override public float getSpeedX() {
                return 60;
              }
            });
            mFan3.stop();
            mFan4.stop();
            break;
          }
          case 2: {
            mFan1.stop();
            mFan2.stop();
            mFan3.start(new Fan.FanHelper() {
              @Override public float getSpeedX() {
                return 40;
              }
            });
            mFan4.stop();
            break;
          }
          case 3: {
            mFan1.stop();
            mFan2.stop();
            mFan3.stop();
            mFan4.start(new Fan.FanHelper() {
              @Override public float getSpeedX() {
                return 20;
              }
            });
            break;
          }
        }
      }

      @Override public void onPageScrollStateChanged(int state) {
      }
    });

    int page0 = 0, page1 = 1, page2 = 2, page3 = 3;
    SCViewAnimation wordP1 = new SCViewAnimation(mWordP1);
    wordP1.addPageAnimation(new SCPositionAnimation(page0, 0, -mWidth, 0, -mHeight / 3));
    wordP1.addPageAnimation(new SCAlphaAnimation(page0, 1, 0));
    mPager.addAnimation(wordP1);
    SCViewAnimation fan1 = new SCViewAnimation(mFan1);
    fan1.addPageAnimation(new SCPositionAnimation(page0, 0, -mWidth * 2, 0, 0));
    mPager.addAnimation(fan1);

    SCViewAnimation wordP2 = new SCViewAnimation(mWordP2);
    wordP2.addPageAnimation(new SCPositionAnimation(page0, mWidth, 0, -mHeight / 3, 0)); // 进入
    wordP2.addPageAnimation(new SCAlphaAnimation(page0, 0, 1));
    wordP2.addPageAnimation(new SCPositionAnimation(page1, 0, -mWidth, 0, -mHeight / 3)); // 退出
    wordP2.addPageAnimation(new SCAlphaAnimation(page1, 1, 0));
    mPager.addAnimation(wordP2);

    SCViewAnimation fan2 = new SCViewAnimation(mFan2);
    fan2.addPageAnimation(new SCPositionAnimation(page0, mWidth * 2, 0, 0, 0)); // 进入
    fan2.addPageAnimation(new SCAlphaAnimation(page0, 1, 1));
    fan2.addPageAnimation(new SCPositionAnimation(page1, 0, 0, 0, 0)); // 退出
    fan2.addPageAnimation(new SCAlphaAnimation(page1, 1, 0));
    mPager.addAnimation(fan2);

    SCViewAnimation wordP3 = new SCViewAnimation(mWordP3);
    wordP3.addPageAnimation(new SCPositionAnimation(page1, mWidth, 0, -mHeight / 3, 0)); // 进入
    wordP3.addPageAnimation(new SCAlphaAnimation(page1, 0, 1));
    wordP3.addPageAnimation(new SCPositionAnimation(page2, 0, -mWidth, 0, -mHeight / 3)); // 退出
    wordP3.addPageAnimation(new SCAlphaAnimation(page2, 1, 0));
    mPager.addAnimation(wordP3);
    SCViewAnimation fan3 = new SCViewAnimation(mFan3);
    fan3.addPageAnimation(new SCPositionAnimation(page1, mWidth * 2, 0, 0, 0)); // 进入
    fan3.addPageAnimation(new SCAlphaAnimation(page1, 0, 1));
    fan3.addPageAnimation(new SCPositionAnimation(page2, 0, mWidth * 2, 0, 0)); // 退出
    fan3.addPageAnimation(new SCAlphaAnimation(page2, 1, 0));
    mPager.addAnimation(fan3);

    SCViewAnimation wordP4 = new SCViewAnimation(mWordP4);
    wordP4.addPageAnimation(new SCPositionAnimation(page2, mWidth, 0, -mHeight / 3, 0)); // 进入
    wordP4.addPageAnimation(new SCAlphaAnimation(page2, 0, 1));
    wordP4.addPageAnimation(new SCPositionAnimation(page3, 0, mWidth, 0, -mHeight / 3)); // 退出
    wordP4.addPageAnimation(new SCAlphaAnimation(page3, 1, 0));
    mPager.addAnimation(wordP4);
    SCViewAnimation fan4 = new SCViewAnimation(mFan4);
    fan4.addPageAnimation(new SCPositionAnimation(page2, 0, 0, mHeight * 10, 0)); // 进入
    fan4.addPageAnimation(new SCPositionAnimation(page3, 0, 0, 0, mHeight * 10)); // 退出
    mPager.addAnimation(fan4);

    SCViewAnimation startApp = new SCViewAnimation(mStartApp);
    startApp.addPageAnimation(new SCPositionAnimation(page2, 0, 0, mHeight * 10, 0)); // 进入
    startApp.addPageAnimation(new SCPositionAnimation(page3, 0, 0, 0, mHeight * 10)); // 退出
    mPager.addAnimation(startApp);

    mPager.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    switch (mCurrentPage) {
      case 0: {
        mFan1.start(new Fan.FanHelper() {
          @Override public float getSpeedX() {
            return 80;
          }
        });
        break;
      }
      case 1: {
        mFan2.start(new Fan.FanHelper() {
          @Override public float getSpeedX() {
            return 60;
          }
        });
        break;
      }
      case 2: {
        mFan3.start(new Fan.FanHelper() {
          @Override public float getSpeedX() {
            return 40;
          }
        });
        break;
      }
      case 3: {
        mFan4.start(new Fan.FanHelper() {
          @Override public float getSpeedX() {
            return 20;
          }
        });
        break;
      }
    }
  }

  @Override public void onPause() {
    mFan1.stop();
    mFan2.stop();
    mFan3.stop();
    mFan4.stop();
    super.onPause();
  }
}
