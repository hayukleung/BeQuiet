package com.hayukleung.bequiet.eventbus;

public class NotifyType {
  // 系统
  public static final int TYPE_AIRPLANE_ALIVE = 1;//飞行模式状态开启
  public static final int TYPE_AIRPLANE_DEAD = 2;//飞行模式状态关闭
  public static final int TYPE_NETWORK_ALIVE = 3;//网络状态可用
  public static final int TYPE_NETWORK_DEAD = 4;//网络状态不可用

  // 用户

  // 推送

  private int mType;
  private Object mExtra;

  public NotifyType(int type) {
    this(type, null);
  }

  public NotifyType(int type, Object extra) {
    this.mType = type;
    this.mExtra = extra;
  }

  public int getType() {
    return mType;
  }

  public Object getExtra() {
    return mExtra;
  }

  public interface INotify {
    void onNotify(NotifyType type);
  }
}
