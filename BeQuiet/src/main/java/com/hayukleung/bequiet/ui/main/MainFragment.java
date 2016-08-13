package com.hayukleung.bequiet.ui.main;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.decibel.DecibelRecorder;
import com.hayukleung.bequiet.decibel.DecibelUI;
import com.hayukleung.bequiet.ui.Activities;
import com.hayukleung.bequiet.ui.BaseFragmentNoToolbar;
import com.hayukleung.bequiet.ui.FullScreenActivity;
import com.hayukleung.bequiet.ui.FullScreenDialog;
import com.hayukleung.bequiet.ui.UiUtils;
import com.hayukleung.bequiet.ui.dialog.Dialogs;
import com.hayukleung.bequiet.ui.guide.GuideFragment;
import com.hayukleung.bequiet.ui.skin.SkinFragment;
import com.hayukleung.bequiet.ui.view.Fan;
import com.mdroid.app.TranslucentStatusCompat;
import com.mdroid.utils.PermissionsChecker;
import com.orhanobut.dialogplus.DialogPlus;
import java.util.Locale;

/**
 * 主界面
 */
public class MainFragment extends BaseFragmentNoToolbar implements DecibelUI {

  @Bind(R.id.root) View mRoot;
  @Bind(R.id.decibel) TextView mDecibel;
  @Bind(R.id.fan) Fan mFan;
  @Bind(R.id.action) FloatingActionButton mAction;
  @Bind(R.id.option) View mOption;

  private DecibelRecorder mDecibelRecorder;
  private boolean mRunning = false;

  @Override protected String getName() {
    return getString(R.string.app_name);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
  }

  @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.content_main, parent, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    UiUtils.requestStatusBarLight(this, true);
//    ((SkinBaseActivity) getActivity()).dynamicAddView(mAction, "backgroundTint", R.color.main_color_normal);
//    ((SkinBaseActivity) getActivity()).dynamicAddView(mAction, "rippleColor", R.color.main_color_pressed);
//    ((SkinBaseActivity) getActivity()).dynamicAddView(mRoot, "background", R.color.bgH1);
  }

  @Override public void onResume() {
    super.onResume();
    if (PermissionsChecker.lacksPermissions(getActivity(), Manifest.permission.RECORD_AUDIO)) {
      // 缺少麦克风权限
      Dialogs.requestPermission(getActivity(), "麦克风录音权限");
      return;
    }
    if (mRunning) {
      if (null == mDecibelRecorder) {
        mDecibelRecorder = new DecibelRecorder(this);
      }
      mDecibelRecorder.on();
    }
  }

  @Override public void onPause() {
    if (null != mDecibelRecorder) {
      mRunning = mDecibelRecorder.isRunning();
      mDecibelRecorder.off();
    }
    mFan.stop();
    super.onPause();
  }

  @Override public void onStarted() {
    getHandler().sendAction(new Runnable() {
      @Override public void run() {
        mFan.start(mDecibelRecorder);
      }
    });
  }

  @Override public void onRunning(double decibel, double mean) {
    getHandler().sendAction(new Runnable() {
      @Override public void run() {
        mDecibel.setText(String.format(Locale.CHINA, "%f dB", decibel));
      }
    });
  }

  @Override public void onStopping(double decibel, double mean) {
    getHandler().sendAction(new Runnable() {
      @Override public void run() {
        mDecibel.setText("STOPPING");
      }
    });
  }

  @Override public void onStopped() {
    getHandler().sendAction(new Runnable() {
      @Override public void run() {
        mDecibel.setText("OFF");
        mFan.stop();
      }
    });
  }

  @OnClick({R.id.action, R.id.option})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.action: {
        if (null == mDecibelRecorder) {
          mDecibelRecorder = new DecibelRecorder(this);
        }
        mDecibelRecorder.toggle();
        break;
      }
      case R.id.option: {
        option(getActivity());
        break;
      }
    }
  }

  /**
   * 选项
   *
   * @param activity
   */
  private void option(Activity activity) {
    FullScreenDialog fullScreenDialog =
        new FullScreenDialog.Builder(activity).contentLayoutRes(R.layout.dialog_option)
            .build()
            .show();
    DialogPlus dialog = fullScreenDialog.getDialog();
    dialog.findViewById(R.id.demo).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Activities.startActivity(activity, FullScreenActivity.class, GuideFragment.class);
        dialog.dismiss();
      }
    });
    dialog.findViewById(R.id.skins).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Activities.startActivity(activity, SkinFragment.class);
        dialog.dismiss();
      }
    });
    dialog.findViewById(R.id.contacts).setVisibility(View.GONE);
//    dialog.findViewById(R.id.contacts).setOnClickListener(new View.OnClickListener() {
//      @Override public void onClick(View v) {
//        Activities.startActivity(activity, AboutFragment.class);
//        dialog.dismiss();
//      }
//    });
    dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dialog.dismiss();
      }
    });
  }
}
