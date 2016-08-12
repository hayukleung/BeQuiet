package com.hayukleung.bequiet.decibel;

import android.support.annotation.WorkerThread;

/**
 * 分贝值获取
 */
public interface DecibelUI {

  /**
   * 开始检测
   */
  @WorkerThread
  void onStarted();

  /**
   * 获取分贝值
   *
   * @param decibel decibel = 10 * lg(mean)
   * @param mean mean = 10 ^ (decibel / 10)
   */
  @WorkerThread
  void onRunning(double decibel, double mean);

  /**
   * 阻尼停止中
   *
   * @param decibel
   * @param mean
   */
  @WorkerThread
  void onStopping(double decibel, double mean);

  /**
   * 监测停止
   */
  @WorkerThread
  void onStopped();
}
