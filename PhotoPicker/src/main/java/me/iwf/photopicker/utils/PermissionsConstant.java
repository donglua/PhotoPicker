package me.iwf.photopicker.utils;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by donglua on 2016/10/19.
 */

public class PermissionsConstant {

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_EXTERNAL_READ = 2;
    public static final int REQUEST_EXTERNAL_WRITE = 3;

    public static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String[] PERMISSIONS_EXTERNAL_WRITE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static final String[] PERMISSIONS_EXTERNAL_READ = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
