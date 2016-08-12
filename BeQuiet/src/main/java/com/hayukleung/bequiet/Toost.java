package com.hayukleung.bequiet;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.mdroid.utils.AndroidUtils;

public class Toost {
  private static final int REMOVE_MESSAGE = 0x991;
  private static final int REMOVE_WARNING = 0x992;
  private static final long DELAY_MESSAGE = 1500;
  private static final long DELAY_WARMING = 2500;
  private static Toost INSTANCE;
  private final WindowManager mWindowManager;
  private Context mContext;
  private Handler mHandler;
  private Toast mMessage;
  private Toast mWarning;

  private Toost(Context context) {
    mContext = context;
    mHandler = new Handler(new Callback());
    mWindowManager =
        (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    ToastBuilder builder = new ToastBuilder();
    builder.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
        AndroidUtils.dp2px(context, 60));
    builder.setView(LayoutInflater.from(context).inflate(R.layout.toast_message_layout, null));
    builder.setDelay(DELAY_MESSAGE);
    builder.setKey(REMOVE_MESSAGE);
    mMessage = builder.build();

    builder.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
        AndroidUtils.dp2px(context, 60));
    builder.setView(LayoutInflater.from(context).inflate(R.layout.toast_warning_layout, null));
    builder.setDelay(DELAY_WARMING);
    builder.setKey(REMOVE_WARNING);
    mWarning = builder.build();
  }

  public static void message(int resid) {
    message(INSTANCE.mContext.getString(resid));
  }

  public static void message(String message) {
    Toast messageToast = INSTANCE.mMessage;
    messageToast.setText(message);
    INSTANCE.show(messageToast);
  }

  public static void warning(String message) {
    Toast messageToast = INSTANCE.mWarning;
    messageToast.setText(message);
    INSTANCE.show(messageToast);
  }

  public static void warning(int resid) {
    String text = INSTANCE.mContext.getString(resid);
    warning(text);
  }

  public static void networkWarning() {
    message(R.string.network_error_tips);
  }

  public static void initialize(Context context) {
    INSTANCE = new Toost(context);
  }

  public static void terminate() {
    INSTANCE.recycle();
    INSTANCE = null;
  }

  private void show(Toast toast) {
    if (toast.isShowing()) {
      mHandler.removeMessages(toast.mKey);
      dismiss(toast, true);
    }
    mWindowManager.addView(toast.mView, toast.mLayoutParams);
    Message msg = mHandler.obtainMessage(toast.mKey, toast);
    mHandler.sendMessageDelayed(msg, toast.mDelay);
  }

  private void dismiss(Toast toast) {
    dismiss(toast, false);
  }

  private void dismiss(Toast toast, boolean removeViewImmediate) {
    if (removeViewImmediate) {
      mWindowManager.removeViewImmediate(toast.mView);
    } else {
      mWindowManager.removeView(toast.mView);
    }
  }

  private void recycle() {
    mHandler.removeMessages(REMOVE_MESSAGE);
    mHandler.removeMessages(REMOVE_WARNING);
    if (mMessage.isShowing()) mWindowManager.removeViewImmediate(mMessage.mView);
    if (mWarning.isShowing()) mWindowManager.removeViewImmediate(mWarning.mView);
  }

  private static class Toast {
    public final long mDelay;
    private final View mView;
    private final WindowManager.LayoutParams mLayoutParams;
    private final int mKey;

    public Toast(View view, WindowManager.LayoutParams windowManagerParams, long delay, int key) {
      mLayoutParams = windowManagerParams;
      mView = view;
      mDelay = delay;
      mKey = key;
    }

    public boolean isShowing() {
      return mView.isShown();
    }

    public void setText(String s) {
      TextView tv = (TextView) mView.findViewById(android.R.id.message);
      if (tv == null) {
        throw new RuntimeException("This Toast was not created with Toast.makeText()");
      }
      tv.setText(s);
    }
  }

  private static class ToastBuilder {

    private int mGravity;
    private int mXOffset;
    private int mYOffset;

    private View mView;

    private long mDelay;
    private int mKey;

    public void setGravity(int gravity, int xOffset, int yOffset) {
      mGravity = gravity;
      mXOffset = xOffset;
      mYOffset = yOffset;
    }

    public void setView(View view) {
      mView = view;
    }

    private void setDelay(long delay) {
      mDelay = delay;
    }

    public Toast build() {
      WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();

      windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
      windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
      windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
          | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
      windowManagerParams.format = PixelFormat.TRANSLUCENT;
      windowManagerParams.windowAnimations = R.style.Animation_Toast;

      windowManagerParams.type = WindowManager.LayoutParams.TYPE_TOAST;

      windowManagerParams.gravity = mGravity;
      windowManagerParams.x = mXOffset;
      windowManagerParams.y = mYOffset;

      if (mDelay < 500) {
        mDelay = 500;
      } else if (mDelay > 5000) {
        mDelay = 5000;
      }
      return new Toast(mView, windowManagerParams, mDelay, mKey);
    }

    public void setKey(int key) {
      mKey = key;
    }
  }

  private class Callback implements Handler.Callback {
    @Override public boolean handleMessage(Message msg) {
      switch (msg.what) {
        case REMOVE_MESSAGE:
        case REMOVE_WARNING: {
          Toast toast = (Toast) msg.obj;
          dismiss(toast);
          break;
        }
      }
      return true;
    }
  }
}
