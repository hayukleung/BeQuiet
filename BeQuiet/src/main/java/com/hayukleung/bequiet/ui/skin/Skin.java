package com.hayukleung.bequiet.ui.skin;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.widget.Checkable;
import com.hayukleung.bequiet.R;
import com.hayukleung.bequiet.db.DBKeys;
import com.mdroid.DBUtils;

/**
 * 皮肤
 */
public class Skin implements Checkable {

  private int id;

  @ColorRes private int drawableResId;
  @ColorRes private int backgroundResId;

  private boolean checked = false;

  public Skin(int id, int drawableResId, int backgroundResId) {
    this.id = id;
    this.drawableResId = drawableResId;
    this.backgroundResId = backgroundResId;
    this.checked = id == Utils.read();
  }

  public int getId() {
    return id;
  }

  @DrawableRes public int getDrawableResId() {
    return drawableResId;
  }

  @DrawableRes public int getBackgroundResId() {
    return backgroundResId;
  }

  @Override public void setChecked(boolean checked) {
    this.checked = checked;
  }

  @Override public boolean isChecked() {
    return this.checked;
  }

  @Override public void toggle() {
    this.checked = !this.checked;
  }

  @Override public boolean equals(Object o) {
    return null != o && (o instanceof Skin) && this.id == ((Skin) o).id;
  }

  @Override public int hashCode() {
    return this.id;
  }
}
