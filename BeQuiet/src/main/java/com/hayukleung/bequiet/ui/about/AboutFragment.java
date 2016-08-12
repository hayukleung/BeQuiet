package com.hayukleung.bequiet.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.ui.BaseFragmentNoToolbar;
import com.hayukleung.bequiet.ui.UiUtils;
import com.mdroid.app.TranslucentStatusCompat;

/**
 * 皮肤
 */
public class AboutFragment extends BaseFragmentNoToolbar {

  @Override protected String getName() {
    return "关于";
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
  }

  @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.content_about, parent, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    UiUtils.requestStatusBarLight(this, true);
  }
}
