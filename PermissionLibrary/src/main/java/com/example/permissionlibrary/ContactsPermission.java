package com.example.permissionlibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class ContactsPermission {
    private Context context;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    public final String GRANT_MESSAGE = "Contacts permission granted!";
    public final String DENIED_MESSAGE = "Contacts permission denied. You can use the application but you will not have access to some features.";

    public ContactsPermission(Context context) {
        this.context = context;
    }

    public void setRequestPermissionLauncher(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        this.requestPermissionLauncher = multiplePermissionLauncher;
    }

    /**
     * Check if READ_CONTACTS permission is granted
     */
    public void checkPermissionContacts() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", GRANT_MESSAGE);
        }else {
            Log.d("TAG", DENIED_MESSAGE);
        }
    }

    public void getPermissionContacts() {
        requestPermissionLauncher.launch(new String[]{Manifest.permission.READ_CONTACTS});
    }

}
