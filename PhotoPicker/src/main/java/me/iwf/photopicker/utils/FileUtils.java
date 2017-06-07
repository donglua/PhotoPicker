package me.iwf.photopicker.utils;

import android.util.Log;

import java.io.File;

/**
 * Created by myc on 2016/12/14.
 * More Code on 1101255053@qq.com
 * Description:
 */
public class FileUtils {
    public static boolean fileIsExists(String path) {
        if (path == null || path.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
