package com.brokenpicinc.brokenpic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * Created by orgaf_000 on 2/15/2017.
 */

public class DialogInterrupter {
    public final static void showNeturalDialog(String msg, Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

//    final static void showNeturalDialog(String msg, Activity activity) {
//        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//        alertDialog.setTitle("Alert");
//        alertDialog.setMessage(msg);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();
//    }
