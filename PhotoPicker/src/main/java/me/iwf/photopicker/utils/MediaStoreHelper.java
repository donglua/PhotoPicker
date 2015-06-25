package me.iwf.photopicker.utils;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_MODIFIED;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * Created by donglua on 15/5/31.
 */
public class MediaStoreHelper {

  private Context mContext;

  public MediaStoreHelper(Context mContext) {
    this.mContext = mContext;
  }

  public List<String> getAllPhotoPath() {
    List<String> photoPaths = new ArrayList<>();
    String[] columns = {
        _ID, DATA, MIME_TYPE, DATE_MODIFIED
    };
    Cursor cursor = mContext.getContentResolver()
        .query(EXTERNAL_CONTENT_URI,
            columns,
            MIME_TYPE + "=? or " + MIME_TYPE + "=?",
            new String[] { "image/jpeg", "image/png" },
            DATE_MODIFIED + " DESC");

    int dataColumnIndex = cursor.getColumnIndex(DATA);
    while (cursor.moveToNext()) {
      String path = cursor.getString(dataColumnIndex);
      if (path != null) {
        photoPaths.add(path);
      }
    }
    cursor.close();
    return photoPaths;
  }
}
