package com.hayukleung.bequiet.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import com.hayukleung.bequiet.R;

/**
 * chargerlink_v2
 * com.hayukleung.bequiet.ui.dialog
 * Dialogs.java
 *
 * by hayukleung
 * at 2016-08-13 17:56
 */
public class Dialogs {
  /**
   * 请求权限（Android6.0+系统有效）
   */
  public static Dialog requestPermission(final Activity activity, String permission) {
    final Dialog dialog = new Dialog(activity, R.style.dialog);
    dialog.setContentView(R.layout.dialog_request_permission);
    ((TextView) dialog.findViewById(R.id.permissions)).setText(
        String.format("%s请求%s", activity.getString(R.string.app_name), permission));
    dialog.findViewById(R.id.negative).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dialog.dismiss();
        activity.finish();
      }
    });
    dialog.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(String.format("package:%s", activity.getPackageName())));
        activity.startActivity(intent);
        dialog.dismiss();
      }
    });
    dialog.setCanceledOnTouchOutside(false);
    dialog.setCancelable(false);
    dialog.show();
    return dialog;
  }
}
