package me.iwf.photopicker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.adapter.PhotoGridAdapter;
import me.iwf.photopicker.adapter.PopupDirectoryListAdapter;
import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.entity.PhotoDirectory;
import me.iwf.photopicker.event.OnPhotoClickListener;
import me.iwf.photopicker.utils.ImageCaptureManager;
import me.iwf.photopicker.utils.MediaStoreHelper;

import static android.app.Activity.RESULT_OK;
import static me.iwf.photopicker.PhotoPickerActivity.EXTRA_SHOW_GIF;
import static me.iwf.photopicker.utils.MediaStoreHelper.INDEX_ALL_PHOTOS;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoPickerFragment extends Fragment {

  private ImageCaptureManager captureManager;
  private PhotoGridAdapter photoGridAdapter;

  private PopupDirectoryListAdapter listAdapter;
  private List<PhotoDirectory> directories;

  private List<String> selected_photos;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    directories = new ArrayList<>();

    captureManager = new ImageCaptureManager(getActivity());

    Bundle mediaStoreArgs = new Bundle();
    if (getActivity() instanceof PhotoPickerActivity) {
      mediaStoreArgs.putBoolean(EXTRA_SHOW_GIF, ((PhotoPickerActivity) getActivity()).isShowGif());
    }

    MediaStoreHelper.getPhotoDirs(getActivity(), mediaStoreArgs,
        new MediaStoreHelper.PhotosResultCallback() {
          @Override public void onResultCallback(List<PhotoDirectory> dirs) {
            directories.clear();
            directories.addAll(dirs);
            photoGridAdapter.setSelectedPhotoPaths(selected_photos);
            photoGridAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetChanged();
          }
        });
  }


  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    setRetainInstance(true);

    final View rootView = inflater.inflate(R.layout.fragment_photo_picker, container, false);

    photoGridAdapter = new PhotoGridAdapter(getActivity(), directories);
    listAdapter  = new PopupDirectoryListAdapter(getActivity(), directories);

    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photos);
    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
    layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(photoGridAdapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    photoGridAdapter.setOnCameraClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Intent intent = captureManager.dispatchTakePictureIntent();
          startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    return rootView;
  }


  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
      captureManager.galleryAddPic();
      if (directories.size() > 0) {
        String path = captureManager.getCurrentPhotoPath();
        PhotoDirectory directory = directories.get(INDEX_ALL_PHOTOS);
        directory.getPhotos().add(INDEX_ALL_PHOTOS, new Photo(path.hashCode(), path));
        directory.setCoverPath(path);
        photoGridAdapter.notifyDataSetChanged();
      }
    }
  }


  public PhotoGridAdapter getPhotoGridAdapter() {
    return photoGridAdapter;
  }

  public void setSelectedPhotos(List<String> selectedPhotos) {
    selected_photos = selectedPhotos;
  }


  @Override public void onSaveInstanceState(Bundle outState) {
    captureManager.onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }


  @Override public void onViewStateRestored(Bundle savedInstanceState) {
    captureManager.onRestoreInstanceState(savedInstanceState);
    super.onViewStateRestored(savedInstanceState);
  }

  public ArrayList<String> getSelectedPhotoPaths() {
    return photoGridAdapter.getSelectedPhotoPaths();
  }

  public void initMenuWithPreselectedPhotos() {

    if (selected_photos != null && selected_photos.size() > 0) {
      int maxCount = ((PhotoPickerActivity) getActivity()).getMaxCount();
      ((PhotoPickerActivity) getActivity()).getMenuDoneItem().setEnabled(true);
      ((PhotoPickerActivity) getActivity()).getMenuDoneItem()
              .setTitle(getString(R.string.done_with_count, selected_photos.size(), maxCount));
    }
  }
}
