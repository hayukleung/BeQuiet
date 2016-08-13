package com.hayukleung.bequiet.ui.skin;

import com.hayukleung.bequiet.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤配置
 */
public class Config {
  
  public static final List<Skin> SKIN_LIST = new ArrayList<>(3);
  static {
    SKIN_LIST.add(new Skin(0, R.drawable.ic_fan_01, R.drawable.bg_fan_01));
    SKIN_LIST.add(new Skin(1, R.drawable.ic_fan_02, R.drawable.bg_fan_02));
  }
}
