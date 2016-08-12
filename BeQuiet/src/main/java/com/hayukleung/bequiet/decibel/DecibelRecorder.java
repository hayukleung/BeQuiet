package com.hayukleung.bequiet.decibel;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.hayukleung.bequiet.ui.view.Fan;
import com.mdroid.utils.Ln;
import java.lang.ref.WeakReference;

/**
 *  贝测量仪
 */
public class DecibelRecorder implements Fan.FanHelper {

  private static final int SAMPLE_RATE_IN_HZ = 8000;
  private static final int BUFFER_SIZE =
      AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
          AudioFormat.ENCODING_PCM_16BIT);
  private AudioRecord mAudioRecord;

  /**
   * 是否运行中
   */
  private boolean mRunning = false;

  private static final Object LOCK = new Object();

  private WeakReference<DecibelUI> mDecibelUIWeakReference;

  /**
   * 分贝值
   */
  private double mDecibel;
  private double mLatestMean = 0;

  public DecibelRecorder(DecibelUI decibelUI) {
    this.mDecibelUIWeakReference = new WeakReference<>(decibelUI);
  }

  /**
   * 关闭
   */
  public void off() {
    this.mRunning = false;
  }

  /**
   * 启动
   */
  public void on() {
    if (mRunning) {
      return;
    }

    mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
        AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);

    final DecibelUI decibelUI = mDecibelUIWeakReference.get();
    if (null != decibelUI) {
      decibelUI.onStarted();
    }

    mRunning = true;

    new Thread(new Runnable() {
      @Override public void run() {
        if (AudioRecord.STATE_INITIALIZED == mAudioRecord.getState()) {
          mAudioRecord.startRecording();
        } else {
          // TODO 请求麦克风权限
          return;
        }

        short[] buffer = new short[BUFFER_SIZE];
        final DecibelUI decibelUI = mDecibelUIWeakReference.get();
        while (mRunning) {
          int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
          long v = 0;
          for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
          }
          double mean = v / (double) r;
          // 平滑处理
          mLatestMean = (mLatestMean + mean) / 2;
          mDecibel = 10 * Math.log10(mLatestMean);
          Ln.e("db --> " + mDecibel);
          if (null != decibelUI) {
            decibelUI.onRunning(mDecibel, mLatestMean);
          }
          // 大概一秒十次
          synchronized (LOCK) {
            try {
              LOCK.wait(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;

        int count = 50;
        while (count-- > 0 && !mRunning) {
          // 停止监测后的阻尼停止效果
          mLatestMean = mLatestMean / 1.5;
          mDecibel = 10 * Math.log10(mLatestMean);
          Ln.e("db --> " + mDecibel);
          if (null != decibelUI) {
            decibelUI.onStopping(mDecibel, mLatestMean);
          }
          // 大概一秒十次
          synchronized (LOCK) {
            try {
              LOCK.wait(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }

        if (null != decibelUI && !mRunning) {
          decibelUI.onStopped();
        }
      }
    }).start();
  }

  /**
   * 开关
   */
  public void toggle() {
    if (mRunning) {
      off();
    } else {
      on();
    }
  }

  /**
   * 是否处于运行状态
   *
   * @return
   */
  public boolean isRunning() {
    return this.mRunning;
  }

  @Override public float getSpeedX() {
    return (float) mDecibel;
  }
}
