package com.example.permissionlibraryexample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.example.permissionlibrary.MyPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MyPermissions myPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPermissions = new MyPermissions(this, multiplePermissionLauncher, manuallyPermissionLauncher);
        myPermissions.launchRequestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS});
    }

    /**
     * Launch request for a list of permissions
     */
    public ActivityResultLauncher<String[]> multiplePermissionLauncher = registerForActivityResult
            (new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
                //Iterate over the permissions - and check if they are granted
                List<String> grantedPermissions = new ArrayList<>();
                List<String> manualPermissions = new ArrayList<>();
                for (Map.Entry<String, Boolean> permissionEntry : isGranted.entrySet()) {
                    if (permissionEntry.getValue()) {
                        grantedPermissions.add(permissionEntry.getKey());
                    } else {
                        //Check if the user denied asking again - if not ask again for permission
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissionEntry.getKey())) {
                            myPermissions.requestPermission(permissionEntry.getKey());
                        } else {
                            //If the user chose not to ask again, we add the permission to list of manual permissions
                            manualPermissions.add(permissionEntry.getKey());
                        }
                    }
                }
                myPermissions.printGrantedPermissions(grantedPermissions);
                myPermissions.launchManualSettingsDialog(manualPermissions.toArray(new String[0]));
            });

    /**
     * Launch request for settings dialog for requesting manual permission
     */
    public ActivityResultLauncher<Intent> manuallyPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    //After we return from the settings page - check each permission if it was granted
                    myPermissions.checkPermissions();
                }
            });
}