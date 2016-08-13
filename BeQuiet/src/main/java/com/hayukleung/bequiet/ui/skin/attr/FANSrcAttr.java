package com.hayukleung.bequiet.ui.skin.attr;

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
        fan.setFanSrcColor(color);
      } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
        // fan.setImageDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
        // 因为有个扇叶动态变更的需求，这里的SkinManager方法内置在Fan内部
        fan.setFanSrcResource(attrValueRefId);
      }
    }
  }
}
