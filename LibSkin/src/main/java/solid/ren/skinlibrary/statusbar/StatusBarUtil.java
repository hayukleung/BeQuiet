package solid.ren.skinlibrary.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

/**
 * 设置状态栏的颜色，依赖了SystemBarTintManager类
 */
public class StatusBarUtil {

  private Activity activity;
  private int color;

  public StatusBarUtil(Activity activity, int color) {

    this.activity = activity;
    this.color = color;
  }

  /**
   * 记得在布局文件根组件上添加android:fitsSystemWindows="true"
   */
  public void setStatusBarBackColor() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(activity);
      tintManager.setStatusBarTintEnabled(true);
      // tintManager.setStatusBarTintResource(color);
      tintManager.setStatusBarTintColor(color);
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) private void setTranslucentStatus(boolean on) {

    WindowManager.LayoutParams winParams = activity.getWindow().getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    activity.getWindow().setAttributes(winParams);
  }
}
