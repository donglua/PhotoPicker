package me.iwf.photopicker.utils;

import android.content.Intent;
import java.util.ArrayList;

import static me.iwf.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static me.iwf.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_GIF;

/**
 * Created by donglua on 15/7/2.
 */
@Deprecated
public class PhotoPickerIntent {
  public static void setPhotoCount(Intent intent, int photoCount) {
    intent.putExtra(EXTRA_MAX_COUNT, photoCount);
  }

  public static void setShowCamera(Intent intent, boolean showCamera) {
    intent.putExtra(EXTRA_SHOW_CAMERA, showCamera);
  }

  public static void setShowGif(Intent intent, boolean showGif) {
    intent.putExtra(EXTRA_SHOW_GIF, showGif);
  }

  public static void setColumn(Intent intent, int column) {
    intent.putExtra(EXTRA_GRID_COLUMN, column);
  }

  /**
   * To set some photos that have been selected before
   * @param intent
   * @param imagesUri Selected photos
     */
  public static void setSelected(Intent intent, ArrayList<String> imagesUri) {
    intent.putExtra(EXTRA_ORIGINAL_PHOTOS, imagesUri);
  }
}
