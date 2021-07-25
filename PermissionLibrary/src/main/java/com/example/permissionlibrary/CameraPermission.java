package com.example.permissionlibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class CameraPermission {
    private Context context;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    public final String GRANT_MESSAGE = "Camera permission granted!";
    public final String DENIED_MESSAGE = "Contacts permission denied. You can use the application but you will not have access to some features.";

    public CameraPermission (Context context) {
        this.context = context;
    }

    public void setRequestPermissionLauncher(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        this.requestPermissionLauncher = multiplePermissionLauncher;
    }

    /**
     */
    public void checkPermissionCamera(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", GRANT_MESSAGE);
        }else {
            Log.d("TAG", DENIED_MESSAGE);
        }
    }

    public void getPermissionCamera() {
        requestPermissionLauncher.launch(new String[]{Manifest.permission.CAMERA});
    }
}
