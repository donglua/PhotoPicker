package me.iwf.photopicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import java.util.ArrayList;

import me.iwf.photopicker.utils.PermissionsUtils;

/**
 * Created by Donglua on 16/6/25.
 * Builder class to ease Intent setup.
 */
public class PhotoPicker {

  public static final int REQUEST_CODE             = 233;

  public final static int DEFAULT_MAX_COUNT        = 9;
  public final static int DEFAULT_COLUMN_NUMBER    = 3;

  public final static String KEY_SELECTED_PHOTOS   = "SELECTED_PHOTOS";

  public final static String EXTRA_MAX_COUNT       = "MAX_COUNT";
  public final static String EXTRA_SHOW_CAMERA     = "SHOW_CAMERA";
  public final static String EXTRA_SHOW_GIF        = "SHOW_GIF";
  public final static String EXTRA_GRID_COLUMN     = "column";
  public final static String EXTRA_ORIGINAL_PHOTOS = "ORIGINAL_PHOTOS";
  public final static String EXTRA_PREVIEW_ENABLED = "PREVIEW_ENABLED";

  public static PhotoPickerBuilder builder() {
    return new PhotoPickerBuilder();
  }

  public static class PhotoPickerBuilder {
    private Bundle mPickerOptionsBundle;
    private Intent mPickerIntent;

    public PhotoPickerBuilder() {
      mPickerOptionsBundle = new Bundle();
      mPickerIntent = new Intent();
    }

    /**
     * Send the Intent from an Activity with a custom request code
     *
     * @param activity    Activity to receive result
     * @param requestCode requestCode for result
     */
    public void start(@NonNull Activity activity, int requestCode) {
      if (PermissionsUtils.checkReadStoragePermission(activity)) {
        activity.startActivityForResult(getIntent(activity), requestCode);
      }
    }

    /**
     *
     * @param fragment    Fragment to receive result
     * @param requestCode requestCode for result
     */
    public void start(@NonNull Context context,
                      @NonNull android.support.v4.app.Fragment fragment, int requestCode) {
      if (PermissionsUtils.checkReadStoragePermission(fragment.getActivity())) {
        fragment.startActivityForResult(getIntent(context), requestCode);
      }
    }

    /**
     * Send the Intent with a custom request code
     *
     * @param fragment    Fragment to receive result
     */
    public void start(@NonNull Context context,
                      @NonNull android.support.v4.app.Fragment fragment) {
      if (PermissionsUtils.checkReadStoragePermission(fragment.getActivity())) {
        fragment.startActivityForResult(getIntent(context), REQUEST_CODE);
      }
    }

    /**
     * Get Intent to start {@link PhotoPickerActivity}
     *
     * @return Intent for {@link PhotoPickerActivity}
     */
    public Intent getIntent(@NonNull Context context) {
      mPickerIntent.setClass(context, PhotoPickerActivity.class);
      mPickerIntent.putExtras(mPickerOptionsBundle);
      return mPickerIntent;
    }

    /**
     * Send the crop Intent from an Activity
     *
     * @param activity Activity to receive result
     */
    public void start(@NonNull Activity activity) {
      start(activity, REQUEST_CODE);
    }

    public PhotoPickerBuilder setPhotoCount(int photoCount) {
      mPickerOptionsBundle.putInt(EXTRA_MAX_COUNT, photoCount);
      return this;
    }

    public PhotoPickerBuilder setGridColumnCount(int columnCount) {
      mPickerOptionsBundle.putInt(EXTRA_GRID_COLUMN, columnCount);
      return this;
    }

    public PhotoPickerBuilder setShowGif(boolean showGif) {
      mPickerOptionsBundle.putBoolean(EXTRA_SHOW_GIF, showGif);
      return this;
    }

    public PhotoPickerBuilder setShowCamera(boolean showCamera) {
      mPickerOptionsBundle.putBoolean(EXTRA_SHOW_CAMERA, showCamera);
      return this;
    }

    public PhotoPickerBuilder setSelected(ArrayList<String> imagesUri) {
      mPickerOptionsBundle.putStringArrayList(EXTRA_ORIGINAL_PHOTOS, imagesUri);
      return this;
    }

    public PhotoPickerBuilder setPreviewEnabled(boolean previewEnabled) {
      mPickerOptionsBundle.putBoolean(EXTRA_PREVIEW_ENABLED, previewEnabled);
      return this;
    }
  }
}
