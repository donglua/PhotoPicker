package me.iwf.photopicker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.adapter.PhotoPagerAdapter;

/**
 * Image Pager Fragment
 * Created by donglua on 15/6/21.
 */
public class ImagePagerFragment extends Fragment {

  public final static String ARG_PATH = "PATHS";
  public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";

  private ArrayList<String> paths;
  private ViewPager mViewPager;
  private PhotoPagerAdapter mPagerAdapter;

  private int currentItem = 0;


  public static ImagePagerFragment newInstance(List<String> paths, int currentItem) {

    ImagePagerFragment f = new ImagePagerFragment();

    Bundle args = new Bundle();
    args.putStringArray(ARG_PATH, paths.toArray(new String[paths.size()]));
    args.putInt(ARG_CURRENT_ITEM, currentItem);

    f.setArguments(args);

    return f;
  }

  @Override
  public void onResume() {
    super.onResume();
    if(getActivity() instanceof PhotoPickerActivity){
      PhotoPickerActivity photoPickerActivity = (PhotoPickerActivity) getActivity();
      photoPickerActivity.updateTitleDoneItem();
    }
  }

  public void setPhotos(List<String> paths, int currentItem) {
    this.paths.clear();
    this.paths.addAll(paths);
    this.currentItem = currentItem;

    mViewPager.setCurrentItem(currentItem);
    mViewPager.getAdapter().notifyDataSetChanged();
  }


  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    paths = new ArrayList<>();

    Bundle bundle = getArguments();

    if (bundle != null) {
      String[] pathArr = bundle.getStringArray(ARG_PATH);
      paths.clear();
      if (pathArr != null) {

        paths = new ArrayList<>(Arrays.asList(pathArr));
      }

      currentItem     = bundle.getInt(ARG_CURRENT_ITEM);
    }

    mPagerAdapter = new PhotoPagerAdapter(Glide.with(this), paths);
  }


  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.__picker_picker_fragment_image_pager, container, false);

    mViewPager = rootView.findViewById(R.id.vp_photos);
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setCurrentItem(currentItem);
    mViewPager.setOffscreenPageLimit(5);

    return rootView;
  }


  public ViewPager getViewPager() {
    return mViewPager;
  }


  public ArrayList<String> getPaths() {
    return paths;
  }


  public ArrayList<String> getCurrentPath(){
    ArrayList<String> list = new ArrayList<>();
    int position = mViewPager.getCurrentItem();
    if(paths != null && paths.size() > position){
      list.add(paths.get(position));
    }
    return list;
  }


  public int getCurrentItem() {
    return mViewPager.getCurrentItem();
  }

  @Override public void onDestroy() {
    super.onDestroy();

    paths.clear();
    paths = null;

    if (mViewPager != null) {
      mViewPager.setAdapter(null);
    }
  }
}
