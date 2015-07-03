package me.iwf.PhotoPickerDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class MainActivity extends ActionBarActivity {


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
      @Override public void onClick(View view) {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(9);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });


    findViewById(R.id.button_no_camera).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(7);
        intent.setShowCamera(false);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });


    findViewById(R.id.button_one_photo).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_CODE);
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


}
