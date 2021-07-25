package com.example.permissionlibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import java.util.List;

public class MyPermissions {

    private Context context;
    public Utils utils;
    public CameraPermission cameraPermission;
    public ContactsPermission contactsPermission;
    public LocationPermission locationPermission;
    public MicrophonePermission microphonePermission;

    public String[] permissions;

    public ActivityResultLauncher<String[]> multiplePermissionsLauncher;
    public ActivityResultLauncher<Intent> requestManualPermissionLauncher;

    public MyPermissions(Context context, ActivityResultLauncher<String[]> multiplePermissionsLauncher,
                         ActivityResultLauncher<Intent> manuallyPermissionLauncher) {
        this.context = context;
        this.multiplePermissionsLauncher = multiplePermissionsLauncher;
        this.requestManualPermissionLauncher = manuallyPermissionLauncher;
        initUtils();
        initPermissions();
    }

    private void initUtils() {
        utils = new Utils(context);
        utils.setRequestManualPermissionLauncher(requestManualPermissionLauncher);
    }

    private void initPermissions() {
        cameraPermission = new CameraPermission(context);
        cameraPermission.setRequestPermissionLauncher(multiplePermissionsLauncher);
        contactsPermission = new ContactsPermission(context);
        contactsPermission.setRequestPermissionLauncher(multiplePermissionsLauncher);
        locationPermission = new LocationPermission(context);
        locationPermission.setLocationRequestPermissionLauncher(multiplePermissionsLauncher);
        microphonePermission = new MicrophonePermission(context);
        microphonePermission.setRequestPermissionLauncher(multiplePermissionsLauncher);
    }

    private String generateManualMessage(String[] permissions) {
        String neededPermissions = generatePermissionName(permissions);
        return "To use this app like we intended you have to give us permission to access your " + neededPermissions +
                ".\nTo do this go to Permissions and enable these permissions.";
    }

    private String generateDenyMessage(String[] permissions) {
        String neededPermissions = generatePermissionName(permissions);
        return neededPermissions + " permission denied. You can use" +
                " the application but you will not have access to some features.";
    }

    /**
     * Lanuch request multiple permissions
     *
     * @param perm String array of permissions to request
     */
    public void launchRequestPermissions(String[] perm) {
        this.permissions = perm;
        if (permissions.length > 0) {
            multiplePermissionsLauncher.launch(permissions);
        }
    }

    /**
     * Launch request for settings dialog
     * @param manualPermissions String array of permissions to request manually
     */
    public void launchManualSettingsDialog(List<String> manualPermissions) {
        if (manualPermissions.size() > 0) {
            String[] permissions = manualPermissions.toArray(new String[0]);
            utils.openPermissionSettingDialog(generateManualMessage(permissions), generateDenyMessage(permissions));
        }
    }

    public void printGrantedPermissions(List<String> grantedPermissions) {
        if (grantedPermissions.size() > 0) {
            Log.d("TAG", "Granted permissions: " + generatePermissionName(grantedPermissions.toArray(new String[0])));
            utils.myToast("Granted permissions: " + generatePermissionName(grantedPermissions.toArray(new String[0])));
        }
    }

    /**
     * Generates a string of the wanted permissions names
     *
     * @param permissions String[] of permissions
     * @return names of the permissions in one string
     */
    public String generatePermissionName(String[] permissions) {
        StringBuilder perms = new StringBuilder();
        int length = permissions.length;

        for (int i = 0; i < length; i++) {
            switch (permissions[i]) {
                case Manifest.permission.CAMERA:
                    perms.append("camera");
                    break;
                case Manifest.permission.READ_CONTACTS:
                    perms.append("contacts");
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    perms.append("location");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    perms.append("microphone");
                    break;
            }

            if (i < length - 2) {
                perms.append(", ");
            } else if (i == length - 2) {
                perms.append(" and ");
            }
        }
        return perms.toString();
    }

    /**
     * Request one permission
     *
     * @param permission String permission to request
     */
    public void requestPermission(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                cameraPermission.getPermissionCamera();
                break;
            case Manifest.permission.READ_CONTACTS:
                contactsPermission.getPermissionContacts();
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                locationPermission.getFirstPermission();
                break;
            case Manifest.permission.ACCESS_BACKGROUND_LOCATION:
                locationPermission.getSecondPermission();
                break;
            case Manifest.permission.RECORD_AUDIO:
                microphonePermission.getPermissionMicrophone();
                break;
        }
    }

    /**
     * Check which permissions where granted from list of wanted permissions
     */
    public void checkPermissions() {
        if (permissions.length > 0) {
            for (String perm : permissions) {
                switch (perm) {
                    case Manifest.permission.CAMERA:
                        cameraPermission.checkPermissionCamera();
                        break;
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                    case Manifest.permission.ACCESS_COARSE_LOCATION:
                    case Manifest.permission.ACCESS_BACKGROUND_LOCATION:
                        locationPermission.checkPermissionLocation();
                        break;
                    case Manifest.permission.READ_CONTACTS:
                        contactsPermission.checkPermissionContacts();
                        break;
                    case Manifest.permission.RECORD_AUDIO:
                        microphonePermission.checkPermissionMicrophone();
                        break;
                }
            }
        }
    }

}