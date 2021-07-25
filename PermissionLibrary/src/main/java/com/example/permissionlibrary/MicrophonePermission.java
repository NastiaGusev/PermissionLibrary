package com.example.permissionlibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class MicrophonePermission {
    private Context context;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    public final String GRANT_MESSAGE = "Microphone permission granted!";
    public final String DENIED_MESSAGE = "Microphone permission denied. You can use the application but you will not have access to some features.";

    public MicrophonePermission(Context context) {
        this.context = context;
    }

    public void setRequestPermissionLauncher(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        this.requestPermissionLauncher = multiplePermissionLauncher;
    }

    /**
     * Check if RECORD_AUDIO permission is granted
     */
    public void checkPermissionMicrophone() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", GRANT_MESSAGE);
        }else {
            Log.d("TAG", DENIED_MESSAGE);
        }
    }

    public void getPermissionMicrophone() {
        requestPermissionLauncher.launch(new String[]{Manifest.permission.RECORD_AUDIO});
    }
}