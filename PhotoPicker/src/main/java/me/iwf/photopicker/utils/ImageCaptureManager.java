package me.iwf.photopicker.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.iwf.photopicker.BuildConfig;

/**
 * Created by donglua on 15/6/23.
 *
 *
 * http://developer.android.com/training/camera/photobasics.html
 */
public class ImageCaptureManager {

  private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
  public static final int REQUEST_TAKE_PHOTO = 1;

  private String mCurrentPhotoPath;
  private Context mContext;

  public ImageCaptureManager(Context mContext) {
    this.mContext = mContext;
  }

  private File createImageFile() throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
    String imageFileName = "JPEG_" + timeStamp + ".jpg";
    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    if (!storageDir.exists()) {
      if (!storageDir.mkdir()) {
        Log.e("TAG", "Throwing Errors....");
        throw new IOException();
      }
    }

    File image = new File(storageDir, imageFileName);

    // Save a file: path for use with ACTION_VIEW intents
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
  }


  public Intent dispatchTakePictureIntent() throws IOException {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure that there's a camera activity to handle the intent
    if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
      // Create the File where the photo should go
      File file = createImageFile();
      Uri photoFile;
      if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        String authority = mContext.getApplicationInfo().packageName + ".provider";
        photoFile = FileProvider.getUriForFile(this.mContext.getApplicationContext(), authority, file);
      } else {
        photoFile = Uri.fromFile(file);
      }

      // Continue only if the File was successfully created
      if (photoFile != null) {
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
      }
    }
    return takePictureIntent;
  }


  public void galleryAddPic() {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

    if (TextUtils.isEmpty(mCurrentPhotoPath)) {
      return;
    }

    File f = new File(mCurrentPhotoPath);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    mContext.sendBroadcast(mediaScanIntent);
  }


  public String getCurrentPhotoPath() {
    return mCurrentPhotoPath;
  }


  public void onSaveInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState != null && mCurrentPhotoPath != null) {
      savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, mCurrentPhotoPath);
    }
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState != null && savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
      mCurrentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY);
    }
  }

}
