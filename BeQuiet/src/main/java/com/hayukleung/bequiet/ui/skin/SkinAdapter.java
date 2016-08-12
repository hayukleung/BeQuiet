package com.hayukleung.bequiet.ui.skin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.ui.view.Fan;
import com.mdroid.utils.Ln;
import com.mdroid.view.recyclerView.RecyclerArrayAdapter;
import java.util.List;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * 皮肤数据适配器
 */
public class SkinAdapter extends RecyclerArrayAdapter<Skin, RecyclerView.ViewHolder> {

  public SkinAdapter(@NonNull Activity activity, @NonNull List<Skin> data) {
    super(activity, data);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FanHolder(mInflater.inflate(R.layout.item_skin, parent, false));
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    FanHolder fanHolder = (FanHolder) holder;
    Skin skin = getItem(position);
    fanHolder.fan.setSkin(skin);
    if (skin.isChecked()) {
      fanHolder.fan.start(new Fan.FanHelper() {
        @Override public float getSpeedX() {
          return 40;
        }
      });
    } else {
      fanHolder.fan.stop();
    }
    fanHolder.item.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        for (Skin s : getData()) {
          s.setChecked(false);
        }
        skin.setChecked(true);

        // TODO
        if (0 != skin.getId()) {
          SkinManager.getInstance().loadSkin("theme.skin", new ILoaderListener() {
                @Override public void onStart() {
                  Ln.e("skin-->切换中");
                }

                @Override public void onSuccess() {
                  Ln.e("skin-->切换成功");
                }

                @Override public void onFailed(String errMsg) {
                  Ln.e("skin-->切换失败：" + errMsg);
                }

                @Override public void onProgress(int progress) {
                  Ln.e("skin-->下载中：" + progress);
                }
              }
          );
        } else {
          SkinManager.getInstance().restoreDefaultTheme();
        }

        notifyDataSetChanged();

        Utils.write(skin.getId());
      }
    });
    fanHolder.item.setActivated(skin.isChecked());
  }

  static class FanHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item) FrameLayout item;
    @Bind(R.id.fan) Fan fan;

    public FanHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
