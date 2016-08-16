package me.iwf.photopicker.utils;

import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class ExifUtils {

    public static void clearSensitiveInfo(String filePath){
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(filePath);
            exifInterface.setAttribute(ExifInterface.TAG_DATETIME ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_MAKE ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_MODEL ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_APERTURE ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_ISO ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIG  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_DIG  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_DATESTAMP   ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_WHITE_BALANCE  ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_FOCAL_LENGTH   ,"0");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD    ,"0");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
