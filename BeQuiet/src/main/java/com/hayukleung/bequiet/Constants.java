package com.hayukleung.bequiet;

import android.text.TextUtils;
import java.net.URLEncoder;

/**
 * 常量配置
 */
public class Constants {
  private static final int ENVIRONMENT_F = 0;  // 生产模式
  private static final int ENVIRONMENT_T = 1;  // 测试模式
  private static final int ENVIRONMENT_D = 2;  // 开发模式
  public static String HOST_CLOUD = isF() ? "http://www.chargercloud.com"
      : isT() ? "http://test.chargercloud.com" : "http://test.chargercloud.com";

  public static String HOST_EVCLUB = isF() ? "https://m.evclub.com"
      : isT() ? "http://test.m.evclub.com" : "http://dev.m.evclub.com:8880";

  public static String HOST_CHARGERLINK =
      isF() ? "http://www.chargerlink.com" : "http://test.chargerlink.com";

  public static String Socket_Host =
      isT() ? "test.m.evclub.com" : isD() ? "dev.m.evclub.com" : "m.evclub.com";

  public static boolean isF() {
    return BuildConfig.serviceEnvironment == ENVIRONMENT_F;
  }

  public static boolean isT() {
    return BuildConfig.serviceEnvironment == ENVIRONMENT_T;
  }

  public static boolean isD() {
    return BuildConfig.serviceEnvironment == ENVIRONMENT_D;
  }

  public static String getSmallPicture(String url) {
    if (TextUtils.isEmpty(url)) return null;
    return url.concat("-small");
  }

  public static String getMediumPicture(String url) {
    if (TextUtils.isEmpty(url)) return null;
    return url.concat("-medium");
  }

  public static String getLargePicture(String url) {
    if (TextUtils.isEmpty(url)) return null;
    return url.concat("-large");
  }

  public static String postUrl(String id) {
    return HOST_EVCLUB + "/html/post/" + id;
  }

  /**
   * 充电点点评分享的链接
   *
   * @param id
   */
  public static String postPlugCommentShareUrl(String id) {
    try {
      return HOST_EVCLUB + "/html/comment?id=" + URLEncoder.encode(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 微信，朋友圈，qq空间分享的链接
   *
   * @param id
   */
  public static String postPlugOtherShareUrl(String id) {
    try {
      return HOST_EVCLUB + "/html/plug?id=" + URLEncoder.encode(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
