package com.example.permissionlibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;

public class Utils {
    public Context context;
    private ActivityResultLauncher<Intent> requestManualPermissionLauncher;

    public Utils (Context context) {
        this.context = context;
    }

    public void setRequestManualPermissionLauncher(ActivityResultLauncher<Intent> manuallyPermissionLauncher) {
        this.requestManualPermissionLauncher = manuallyPermissionLauncher;
    }

    public void openPermissionSettingDialog(String message,String denyMessage) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton(context.getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                        intent.setData(uri);
                                        dialog.cancel();
                                        requestManualPermissionLauncher.launch(intent);
                                    }
                                }).setNegativeButton(context.getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //User refuses to give permission - show deny messages
                                myToast(denyMessage);
                                Log.d("TAG", denyMessage);
                            }
                        }
                ).show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    public void myToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
