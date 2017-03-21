package me.iwf.PhotoPickerDemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class MainActivity extends AppCompatActivity {

  private PhotoAdapter photoAdapter;

  private ArrayList<String> selectedPhotos = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    photoAdapter = new PhotoAdapter(this, selectedPhotos);

    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
    recyclerView.setAdapter(photoAdapter);

    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setGridColumnCount(4)
                .start(MainActivity.this);
      }
    });

    findViewById(R.id.button_no_camera).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PhotoPicker.builder()
                .setPhotoCount(7)
                .setShowCamera(false)
                .setPreviewEnabled(false)
                .start(MainActivity.this);
      }
    });

    findViewById(R.id.button_one_photo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .start(MainActivity.this);
      }
    });

    findViewById(R.id.button_photo_gif).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PhotoPicker.builder()
                .setShowCamera(true)
                .setShowGif(true)
                .start(MainActivity.this);
      }
    });

    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
            new RecyclerItemClickListener.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
          PhotoPicker.builder()
                  .setPhotoCount(PhotoAdapter.MAX)
                  .setShowCamera(true)
                  .setPreviewEnabled(false)
                  .setSelected(selectedPhotos)
                  .start(MainActivity.this);
        } else {
          PhotoPreview.builder()
                  .setPhotos(selectedPhotos)
                  .setCurrentItem(position)
                  .start(MainActivity.this);
        }
      }
    }));
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

}
