package com.hayukleung.bequiet.ui.skin;

import com.hayukleung.bequiet.db.DBKeys;
import com.mdroid.DBUtils;

/**
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.skin
 * Utils.java
 *
 * by hayukleung
 * at 2016-08-12 14:34
 */
public class Utils {

  public static void write(int id) {
    DBUtils.write(DBKeys.FAN_CHOSEN, id);
  }

  public static int read() {
    return DBUtils.read(DBKeys.FAN_CHOSEN, 0);
  }
}
