package me.iwf.photopicker.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import me.iwf.photopicker.PhotoPickerActivity;

/**
 * Created by donglua on 15/7/2.
 */
public class PhotoPickerIntent extends Intent {

  public PhotoPickerIntent() {
  }

  public PhotoPickerIntent(Intent o) {
    super(o);
  }

  public PhotoPickerIntent(String action) {
    super(action);
  }

  public PhotoPickerIntent(String action, Uri uri) {
    super(action, uri);
  }

  public PhotoPickerIntent(Context packageContext, Class<?> cls) {
    super(packageContext, cls);
  }

  public void setPhotoCount(int photoCount) {
    this.putExtra(PhotoPickerActivity.EXTRA_MAX_COUNT, photoCount);
  }


  public void setShowCamera(boolean showCamera) {
    this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
  }


}
