# PermissionLibrary

[![](https://jitpack.io/v/NastiaGusev/PermissionLibrary.svg)](https://jitpack.io/#NastiaGusev/PermissionLibrary)

Permission library for requesting permissions in Android Apps:

```java
    android.permission.READ_CONTACTS
    android.permission.CAMERA
    android.permission.RECORD_AUDIO
    android.permission.ACCESS_FINE_LOCATION
    android.permission.ACCESS_COARSE_LOCATION
    android.permission.ACCESS_BACKGROUND_LOCATION
```

## To add library:

Add it in your build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency:

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

Add wanted permissions in Manifest:

```html
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
```

Request Permissions in Activity:

```java
 public class MainActivity extends AppCompatActivity {

    MyPermissions myPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPermissions = new MyPermissions(this, multiplePermissionLauncher, manuallyPermissionLauncher);
        myPermissions.launchRequestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS});
    }
```



