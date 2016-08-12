package solid.ren.skinlibrary.attr;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * chargerlink_v2
 * solid.ren.skinlibrary.attr
 * BackgroundTintAttr.java
 *
 * by hayukleung
 * at 2016-08-09 16:07
 */
public class BackgroundTintAttr extends SkinAttr {

  @Override public void apply(View view) {
    if (view instanceof FloatingActionButton) {
      FloatingActionButton floatingActionButton = (FloatingActionButton) view;
      if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
        floatingActionButton.setBackgroundTintList(SkinManager.getInstance().getColorStateList(attrValueRefId));
      }
    }
  }
}
