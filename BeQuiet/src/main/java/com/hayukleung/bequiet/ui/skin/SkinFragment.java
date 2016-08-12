package com.hayukleung.bequiet.ui.skin;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.ui.BaseFragmentNoToolbar;
import com.hayukleung.bequiet.ui.UiUtils;
import com.mdroid.app.TranslucentStatusCompat;
import com.mdroid.view.recyclerView.GridSpacingItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤
 */
public class SkinFragment extends BaseFragmentNoToolbar {

  @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

  private List<Skin> mSkinList = new ArrayList<>();

  @Override protected String getName() {
    return "皮肤";
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
  }

  @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.content_skin, parent, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    UiUtils.requestStatusBarLight(this, true);

    mRecyclerView.setLayoutManager(
        new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
    mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
    mRecyclerView.setAdapter(new SkinAdapter(getActivity(), mSkinList));

    mSkinList.clear();
    mSkinList.addAll(Config.SKIN_LIST);

    mRecyclerView.getAdapter().notifyDataSetChanged();
  }
}
