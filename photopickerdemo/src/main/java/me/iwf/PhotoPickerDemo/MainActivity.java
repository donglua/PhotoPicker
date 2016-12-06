package me.iwf.PhotoPickerDemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class MainActivity extends AppCompatActivity {

  private PhotoAdapter photoAdapter;

  private ArrayList<String> selectedPhotos = new ArrayList<>();

  private int currentClickId = -1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    photoAdapter = new PhotoAdapter(this, selectedPhotos);

    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
    recyclerView.setAdapter(photoAdapter);

    findViewById(R.id.button).setOnClickListener(v -> onClick(v.getId()));
    findViewById(R.id.button_no_camera).setOnClickListener(v -> onClick(v.getId()));
    findViewById(R.id.button_one_photo).setOnClickListener(v -> onClick(v.getId()));
    findViewById(R.id.button_photo_gif).setOnClickListener(v -> onClick(v.getId()));
    findViewById(R.id.button_multiple_picked).setOnClickListener(v -> onClick(v.getId()));

    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
        (view, position) ->
          PhotoPreview.builder()
                  .setPhotos(selectedPhotos)
                  .setCurrentItem(position)
                  .start(MainActivity.this)
    ));
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK &&
        (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

      List<String> photos = null;
      if (data != null) {
        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
      }
      selectedPhotos.clear();

      if (photos != null) {

        selectedPhotos.addAll(photos);
      }
      photoAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    // If request is cancelled, the result arrays are empty.
    if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      if (currentClickId != -1) onClick(currentClickId);
    } else {
      // permission denied, boo! Disable the
      // functionality that depends on this permission.
      Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
    // No need to explain to user as it is obvious
    return false;
  }

  private void onClick(@IdRes int viewId) {
    switch (viewId) {
      case R.id.button: {
        PhotoPicker.builder()
            .setPhotoCount(9)
            .setGridColumnCount(4)
            .start(this);
        break;
      }

      case R.id.button_no_camera: {
        PhotoPicker.builder()
            .setPhotoCount(7)
            .setShowCamera(false)
            .setPreviewEnabled(false)
            .start(this);
        break;
      }

      case R.id.button_one_photo: {
        PhotoPicker.builder()
            .setPhotoCount(1)
            .start(this);
        break;
      }

      case R.id.button_photo_gif : {
        PhotoPicker.builder()
            .setShowCamera(true)
            .setShowGif(true)
            .start(this);
        break;
      }

      case R.id.button_multiple_picked:{
        PhotoPicker.builder()
            .setPhotoCount(4)
            .setShowCamera(true)
            .setSelected(selectedPhotos)
            .start(this);
        break;
      }
    }

    currentClickId = viewId;
  }
}
