package me.iwf.photopicker.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.adapter.PhotoAdapter;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.event.OnPhotoClickListener;
import me.iwf.photopicker.utils.ImageCaptureManager;
import me.iwf.photopicker.utils.MediaStoreHelper;

import static android.app.Activity.RESULT_OK;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoPickerFragment extends Fragment {

  private PhotoAdapter photoAdapter;
  private List<String> photoPaths = new ArrayList<>();

  private MediaStoreHelper mediaHelper;

  private ImageCaptureManager captureManager;




  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    photoAdapter = new PhotoAdapter(getActivity(), photoPaths);

    mediaHelper = new MediaStoreHelper(getActivity());

    captureManager = new ImageCaptureManager(getActivity());

    AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, List<String>>() {
      @Override protected List<String> doInBackground(Void... voids) {
        return mediaHelper.getAllPhotoPath();
      }

      @Override protected void onPostExecute(List<String> images) {
        photoPaths.addAll(images);
        photoAdapter.notifyDataSetChanged();
      }
    });
  }


  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_photo_picker, container, false);

    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_photos);
    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
    layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(photoAdapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    photoAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
      @Override public void onClick(View v, int position) {
        final int index = position - 1;

        int [] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);
        ImagePagerFragment imagePagerFragment =
            ImagePagerFragment.newInstance(photoPaths, index, screenLocation,
                v.getWidth(), v.getHeight());

        ((PhotoPickerActivity) getActivity()).addImagePagerFragment(imagePagerFragment);
      }
    });


    photoAdapter.setOnCameraClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = null;
        try {
          intent = captureManager.dispatchTakePictureIntent();
          startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    return layout;
  }


  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
      captureManager.galleryAddPic();
      photoPaths.add(0, captureManager.getCurrentPhotoPath());
      photoAdapter.notifyDataSetChanged();
    }
  }


  public PhotoAdapter getPhotoAdapter() {
    return photoAdapter;
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    captureManager.onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }


  @Override public void onViewStateRestored(Bundle savedInstanceState) {
    captureManager.onRestoreInstanceState(savedInstanceState);
    super.onViewStateRestored(savedInstanceState);
  }

}
