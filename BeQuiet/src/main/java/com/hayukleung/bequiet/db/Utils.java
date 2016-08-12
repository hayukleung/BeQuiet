package com.hayukleung.bequiet.db;

import de.greenrobot.common.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * chargerlink_v2
 * com.hayukleung.bequiet.db
 * Utils.java
 *
 * by hayukleung
 * at 2016-08-11 18:08
 */
public class Utils {

  public static void Test(String[] args) {
    try {
      copyDirectory(new File("/data/data/com.duduchong/databases/"), new File("/data/"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void copyDirectory(File srcDir, File destDir) throws IOException {
    if (destDir.exists()) {
      if (!destDir.isDirectory()) {
        throw new IOException("Destination '" + destDir + "' exists but is not a directory");
      }
    } else {
      if (!destDir.mkdirs()) {
        throw new IOException("Destination '" + destDir + "' directory cannot be created");
      }
    }
    if (!destDir.canWrite()) {
      throw new IOException("Destination '" + destDir + "' cannot be written to");
    }
    // recurse
    File[] files = srcDir.listFiles();
    if (files == null) {
      // null if security restricted
      throw new IOException("Failed to list contents of " + srcDir);
    }
    for (int i = 0; i < files.length; i++) {
      File copiedFile = new File(destDir, files[i].getName());
      if (files[i].isDirectory()) {
        copyDirectory(files[i], copiedFile);
      } else {
        FileUtils.copyFile(files[i], copiedFile);
      }
    }
  }
}
