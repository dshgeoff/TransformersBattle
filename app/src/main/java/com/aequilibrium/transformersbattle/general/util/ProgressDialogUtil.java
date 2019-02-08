package com.aequilibrium.transformersbattle.general.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.aequilibrium.transformersbattle.R;

public class ProgressDialogUtil {

    public static ProgressDialog createProgressDialog(Context context) {

        ProgressDialog dialog = new ProgressDialog(context);

        try {
            dialog.show();
        } catch (Exception e) {

        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_custom_progress_dialog);

        return dialog;
    }

}
