# PermissionLibrary

[![](https://jitpack.io/v/NastiaGusev/PermissionLibrary.svg)](https://jitpack.io/#NastiaGusev/PermissionLibrary)

Permission library for requesting permissions.

## To add library:

Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

and:

```gradle
dependencies {
    implementation 'com.github.NastiaGusev:PermissionLibrary:{latest version}'
}
```

## Implementation in Activity:

Implement multiplePermissionLauncher

```java
public ActivityResultLauncher<String[]> multiplePermissionLauncher = registerForActivityResult
            (new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
                for (Map.Entry<String, Boolean> permissionEntry : isGranted.entrySet()) {
                    if (permissionEntry.getValue()) {
                        //Permission granted
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissionEntry.getKey())) {
                            //Request permission second time
                            myPermissions.requestPermission(permissionEntry.getKey());
                        } else {
                            //The user chose to deny and not ask again - request permission manually
                        }
                    }
                }
            });
```

Implement manualPermissionLauncher

```java
 public ActivityResultLauncher<Intent> manuallyPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Check each permission if it was granted
                }
            });
```


