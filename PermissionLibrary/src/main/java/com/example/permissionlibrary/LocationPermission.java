package com.example.permissionlibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationPermission {

    private Context context;
    public final String FR_GRANT_MESSAGE = "Foreground Location permission granted!";
    public final String BG_GRANT_MESSAGE = "Background Location permission granted!";
    public final String DENIED_MESSAGE = "Location permission denied. You can use the application but you will not have access to some features.";

    private boolean fine;
    private boolean coarse;
    private boolean background;
    private ActivityResultLauncher<String[]> locationRequestPermissionLauncher;

    public LocationPermission(Context context) {
        this.context = context;
    }

    public void setLocationRequestPermissionLauncher(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        this.locationRequestPermissionLauncher = multiplePermissionLauncher;
    }

    public void getFirstPermission() {
        locationRequestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public void getSecondPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            locationRequestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION});
        }
    }

    private String checkSDKVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        } else {
            return "";
        }
    }

    private void checkAllPermissionLocation() {
        fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
        coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;

        //Check if the Android version is 9 and below or if we have the background permission
        background = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    public List<String> requestPermissionLocation() {
        List<String> permissions = new ArrayList<>();
        checkAllPermissionLocation();
        if (!fine || !coarse) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!background) {
            permissions.add(checkSDKVersion());
            permissions.removeAll(Collections.singletonList(""));
        }

        if (fine && background || coarse && background) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        return permissions;
    }

    public void checkPermissionLocation() {
        checkAllPermissionLocation();
        if (fine && background || coarse && background) {
            Log.d("TAG", BG_GRANT_MESSAGE);
        } else if (fine || coarse) {
            Log.d("TAG", FR_GRANT_MESSAGE);
        } else {
            Log.d("TAG", DENIED_MESSAGE);
        }
    }
}