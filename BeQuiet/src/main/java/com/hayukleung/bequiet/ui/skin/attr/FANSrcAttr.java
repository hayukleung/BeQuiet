package com.hayukleung.bequiet.ui.skin.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.hayukleung.bequiet.ui.view.Fan;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.skin.attr
 * FANBackgroundAttr.java
 *
 * by hayukleung
 * at 2016-08-11 15:33
 */
public class FANSrcAttr extends SkinAttr {

  @Override public void apply(View view) {
    if (view instanceof Fan) {
      Fan fan = (Fan) view;
      if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
        int color = SkinManager.getInstance().getColor(attrValueRefId);
        fan.setImageColor(color);
      } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
        Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
        fan.setImageDrawable(bg);
      }
    }
  }
}
