package solid.ren.skinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:46
 */
public class SrcAttr extends SkinAttr {

  @Override public void apply(View view) {

    if (view instanceof ImageView) {
      ImageView imageView = (ImageView) view;
      if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
        imageView.setImageResource(attrValueRefId);
      } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
        Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
        imageView.setImageDrawable(drawable);
      }
    }
  }
}
