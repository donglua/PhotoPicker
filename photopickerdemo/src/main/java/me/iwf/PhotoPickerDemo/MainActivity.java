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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class MainActivity extends AppCompatActivity {

  enum RequestCode {
    Button(R.id.button),
    ButtonNoCamera(R.id.button_no_camera),
    ButtonOnePhoto(R.id.button_one_photo),
    ButtonPhotoGif(R.id.button_photo_gif)
    ;

    @IdRes final int mViewId;
    RequestCode(@IdRes int viewId) {
      mViewId = viewId;
    }
  }
  RecyclerView recyclerView;
  PhotoAdapter photoAdapter;

  ArrayList<String> selectedPhotos = new ArrayList<>();

  public final static int REQUEST_CODE = 1;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    photoAdapter = new PhotoAdapter(this, selectedPhotos);

    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
    recyclerView.setAdapter(photoAdapter);


    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkPermission(RequestCode.Button);
      }
    });


    findViewById(R.id.button_no_camera).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkPermission(RequestCode.ButtonNoCamera);
      }
    });

    findViewById(R.id.button_one_photo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkPermission(RequestCode.ButtonOnePhoto);
      }
    });

    findViewById(R.id.button_photo_gif).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkPermission(RequestCode.ButtonPhotoGif);
      }
    });

  }


  public void previewPhoto(Intent intent) {
    startActivityForResult(intent, REQUEST_CODE);
  }


  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    List<String> photos = null;
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
      if (data != null) {
        photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
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

      // permission was granted, yay!
      onClick(RequestCode.values()[requestCode].mViewId);

    } else {
      // permission denied, boo! Disable the
      // functionality that depends on this permission.
      Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
    switch (permission) {
      case Manifest.permission.READ_EXTERNAL_STORAGE:
        // No need to explain to user as it is obvious
        return false;

      default:
        return true;
    }
  }

  private void checkPermission(@NonNull RequestCode requestCode) {

    int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

    if (permissionState != PackageManager.PERMISSION_GRANTED) {

      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
              Manifest.permission.READ_EXTERNAL_STORAGE)) {

        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

      } else {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                requestCode.ordinal());

      }
    } else {
      // Permission granted
      onClick(requestCode.mViewId);
    }
  }

  private void onClick(@IdRes int viewId) {

    switch (viewId) {
      case R.id.button: {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(9);
        startActivityForResult(intent, REQUEST_CODE);
        break;
      }

      case R.id.button_no_camera: {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(7);
        intent.setShowCamera(false);
        startActivityForResult(intent, REQUEST_CODE);
        break;
      }

      case R.id.button_one_photo: {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_CODE);
        break;
      }

      case R.id.button_photo_gif : {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(4);
        intent.setShowCamera(true);
        intent.setShowGif(true);
        startActivityForResult(intent, REQUEST_CODE);
        break;
      }
    }
  }
}
