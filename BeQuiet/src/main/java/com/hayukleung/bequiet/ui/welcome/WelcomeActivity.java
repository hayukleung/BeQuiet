package com.hayukleung.bequiet.ui.welcome;

import android.os.Bundle;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.db.DBKeys;
import com.hayukleung.bequiet.ui.Activities;
import com.hayukleung.bequiet.ui.FullScreenActivity;
import com.hayukleung.bequiet.ui.guide.GuideFragment;
import com.hayukleung.bequiet.ui.main.MainActivity;
import com.hayukleung.bequiet.ui.main.MainFragment;
import com.hayukleung.bequiet.ui.view.Fan;
import com.mdroid.DBUtils;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends FullScreenActivity {

  @Bind(R.id.fan) Fan mFan;

  /**
   * 此页面停留时间
   */
  private static final int TIME = 2000;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    ButterKnife.bind(this);
  }

  @Override protected void onResume() {
    super.onResume();
    getHandler().sendAction(new Runnable() {
      @Override public void run() {
        start();
        mFan.stop();
        finish();
      }
    }, TIME);

    mFan.start(new Fan.FanHelper() {
      @Override public float getSpeedX() {
        return 20; // 20dB
      }
    });
  }

  @Override protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  private void start() {
    Activities.startActivity(WelcomeActivity.this, MainActivity.class, MainFragment.class);
    if (DBUtils.read(DBKeys.GUIDE_SHOW, true)) {
      Activities.startActivity(WelcomeActivity.this, FullScreenActivity.class, GuideFragment.class);
    }
  }

  @Override public void onBackPressed() {
  }
}
