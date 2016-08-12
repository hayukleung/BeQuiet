package solid.ren.skinlibrary.attr;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * chargerlink_v2
 * solid.ren.skinlibrary.attr
 * RippleColorAttr.java
 *
 * by hayukleung
 * at 2016-08-09 17:22
 */
public class RippleColorAttr extends SkinAttr {

  @Override public void apply(View view) {
    if (view instanceof FloatingActionButton) {
      FloatingActionButton floatingActionButton = (FloatingActionButton) view;
      if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
        floatingActionButton.setRippleColor(SkinManager.getInstance().getColor(attrValueRefId));
      }
    }
  }
}
