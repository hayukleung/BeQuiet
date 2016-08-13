package com.hayukleung.bequiet.ui.view;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.ui.skin.Skin;
import com.mdroid.utils.Ln;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * 分贝风扇
 */
public class Fan extends RelativeLayout {

  @Bind(R.id.fan_background) View mFanBackground;
  @Bind(R.id.fan_src) View mFanSrc;

  private ObjectAnimator mObjectAnimator;
  private float mLatestSpeed = 0;
  private float mLatestValue = 0;
  private float mSpeed;
  /**
   * 是否已经设置了大小
   */
  private boolean mSizeSet = false;

  private static final int DB_L = 45;
  private static final int DB_M = 55;
  private static final int DB_H = 65;
  private static final int DB_X = 70;

  @DrawableRes private int mFanSrcL;
  @DrawableRes private int mFanSrcM;
  @DrawableRes private int mFanSrcH;
  @DrawableRes private int mFanSrcX;
  @DrawableRes private int mCurrentFanSrc;

  public Fan(Context context) {
    this(context, null);
  }

  public Fan(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Fan(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    ButterKnife.bind(this, inflate(context, R.layout.layout_fan, this));

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Fan);
    mSpeed = typedArray.getFloat(R.styleable.Fan_FANSpeed, 0f);

    mFanBackground.setBackgroundResource(
        typedArray.getResourceId(R.styleable.Fan_FANBackground, 0));

    mFanSrcL = typedArray.getResourceId(R.styleable.Fan_FANSrcL, 0);
    mFanSrcM = typedArray.getResourceId(R.styleable.Fan_FANSrcM, 0);
    mFanSrcH = typedArray.getResourceId(R.styleable.Fan_FANSrcH, 0);
    mFanSrcX = typedArray.getResourceId(R.styleable.Fan_FANSrcX, 0);

    mFanSrc.setBackgroundResource(mFanSrcL);

    typedArray.recycle();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Ln.e("Fan-->onMeasure");
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    Ln.e("Fan-->onLayout");

    if (!mSizeSet || changed) {
      // 2的2次方比1约等于3比2
      int size = getWidth() / 3 * 2;
      ViewGroup.LayoutParams params;

      params = mFanBackground.getLayoutParams();
      params.width = size;
      params.height = size;
      mFanBackground.setLayoutParams(params);

      params = mFanSrc.getLayoutParams();
      params.width = size;
      params.height = size;
      mFanSrc.setLayoutParams(params);
      mSizeSet = true;
    }
  }

  /**
   * 设置风扇（扇叶与背景）
   *
   * @param skin
   */
  public void setSkin(Skin skin) {
    if (null == skin) {
      return;
    }
    setFanSrcResource(skin.getDrawableResId());
    setBackgroundResource(skin.getBackgroundResId());
  }

  /**
   * 设置扇叶
   *
   * @param resId
   */
  public void setFanSrcResource(@DrawableRes int resId) {
    setFanSrcDrawable(SkinManager.getInstance().getDrawable(mCurrentFanSrc = resId));
  }

  /**
   * 设置扇叶
   *
   * @param drawable
   */
  private void setFanSrcDrawable(@Nullable Drawable drawable) {
    mFanSrc.setBackgroundDrawable(drawable);
  }

  /**
   * 设置扇叶
   *
   * @param color
   */
  public void setFanSrcColor(@ColorInt int color) {
    mFanSrc.setBackgroundColor(color);
  }

  /**
   * 设置风扇背景
   *
   * @param resId
   */
  @Override public void setBackgroundResource(@DrawableRes int resId) {
    mFanBackground.setBackgroundResource(resId);
  }

  /**
   * 设置风扇背景
   *
   * @param color
   */
  @Override public void setBackgroundColor(@ColorInt int color) {
    mFanBackground.setBackgroundColor(color);
  }

  /**
   * 设置风扇背景
   *
   * @param background
   */
  @Override public void setBackgroundDrawable(Drawable background) {
    mFanBackground.setBackgroundDrawable(background);
  }

  /**
   * 开始动画
   *
   * @param fanHelper 等于null时使用默认速率mSpeed
   */
  public void start(FanHelper fanHelper) {
    if (null != mObjectAnimator && mObjectAnimator.isRunning()) {
      return;
    }
    mObjectAnimator = ObjectAnimator.ofFloat(mFanSrc, "rotation", 0.0f, 360f).setDuration(1000);
    mObjectAnimator.setRepeatCount(-1);
    mObjectAnimator.setInterpolator(new TimeInterpolator() {
      @Override public float getInterpolation(float input) {
        final float speed = null == fanHelper ? mSpeed : fanHelper.getSpeedX();
        if (mLatestSpeed < DB_L && speed >= DB_L) {
        }
        if (mLatestSpeed < DB_M && speed >= DB_M && mCurrentFanSrc == mFanSrcL) {
          setFanSrcResource(mFanSrcM);
        }
        if (mLatestSpeed < DB_H && speed >= DB_H && mCurrentFanSrc != mFanSrcX) {
          setFanSrcResource(mFanSrcH);
        }
        if (mLatestSpeed < DB_X && speed >= DB_X) {
          setFanSrcResource(mFanSrcX);
        }
        if (mLatestSpeed > DB_H && speed <= DB_H) {
          setFanSrcResource(mFanSrcH);
        }
        if (mLatestSpeed > DB_M && speed <= DB_M) {
          setFanSrcResource(mFanSrcM);
        }
        if (mLatestSpeed > DB_L && speed <= DB_L) {
          setFanSrcResource(mFanSrcL);
        }
        mLatestSpeed = speed;
        return mLatestValue += Math.pow(2, speed / 10) / 4800;
      }
    });
    mObjectAnimator.start();
  }

  /**
   * 停止动画
   */
  public void stop() {
    if (null != mObjectAnimator && mObjectAnimator.isRunning()) {
      mObjectAnimator.cancel();
    }
    mFanSrc.clearAnimation();
  }

  public interface FanHelper {
    /**
     * 获取分贝值，单位dB
     *
     * @return
     */
    float getSpeedX();
  }
}
