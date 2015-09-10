package me.iwf.photopicker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.fragment.ImagePagerFragment;
import me.iwf.photopicker.fragment.PhotoPickerFragment;

import static android.widget.Toast.LENGTH_LONG;

public class PhotoPickerActivity extends AppCompatActivity {

  private PhotoPickerFragment pickerFragment;
  private ImagePagerFragment imagePagerFragment;

  public final static String EXTRA_MAX_COUNT     = "MAX_COUNT";
  public final static String EXTRA_SHOW_CAMERA   = "SHOW_CAMERA";
  public final static String EXTRA_SHOW_GIF      = "SHOW_GIF";
  public final static String KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS";
  public final static String EXTRA_ACTIVITY_TITLE = "ACTIVITY_TITLE";

  private MenuItem menuDoneItem;

  public final static int DEFAULT_MAX_COUNT = 9;

  private int maxCount = DEFAULT_MAX_COUNT;

  /** to prevent multiple calls to inflate menu */
  private boolean menuIsInflated = false;

  private boolean showGif = false;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    boolean showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
    boolean showGif    = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
    ArrayList<String> selectedPhotos = getIntent().getStringArrayListExtra(KEY_SELECTED_PHOTOS);
    String activityTitle = getIntent().getStringExtra(EXTRA_ACTIVITY_TITLE);

    setShowGif(showGif);

    setContentView(R.layout.activity_photo_picker);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle(activityTitle);
    setSupportActionBar(mToolbar);


    ActionBar actionBar = getSupportActionBar();

    setNavigationStatusBar();

    assert actionBar != null;
    actionBar.setDisplayHomeAsUpEnabled(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      actionBar.setElevation(25);
    }

    maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);

    pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPickerFragment);

    pickerFragment.getPhotoGridAdapter().setShowCamera(showCamera);
    pickerFragment.setSelectedPhotos(selectedPhotos);

    pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
      @Override public boolean OnItemCheck(int position, Photo photo, final boolean isCheck, int selectedItemCount) {

        int total = selectedItemCount + (isCheck ? -1 : 1);

        menuDoneItem.setEnabled(total > 0);

        if (maxCount <= 1) {
          List<Photo> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
          if (!photos.contains(photo)) {
            photos.clear();
            pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
          }
          return true;
        }

        if (total > maxCount) {
          Toast.makeText(getActivity(), getString(R.string.over_max_count_tips, maxCount),
              LENGTH_LONG).show();
          return false;
        }

        menuDoneItem.setTitle(getString(R.string.done_with_count, total, maxCount));
        return true;
      }
    });

  }


  /**
   * Overriding this method allows us to run our exit animation first, then exiting
   * the activity when it complete.
   */
  @Override public void onBackPressed() {
    if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
      imagePagerFragment.runExitAnimation(new Runnable() {
        public void run() {
          if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
          }
        }
      });
    } else {
      super.onBackPressed();
    }
  }


  public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
    this.imagePagerFragment = imagePagerFragment;
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, this.imagePagerFragment)
        .addToBackStack(null)
        .commit();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (!menuIsInflated) {
      getMenuInflater().inflate(R.menu.menu_picker, menu);
      menuDoneItem = menu.findItem(R.id.done);
      menuDoneItem.setEnabled(false);
      menuIsInflated = true;
      pickerFragment.initMenuWithPreselectedPhotos();
      return true;
    }
    return false;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      super.onBackPressed();
      return true;
    }

    if (item.getItemId() == R.id.done) {
      Intent intent = new Intent();
      ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
      intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
      setResult(RESULT_OK, intent);
      finish();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public MenuItem getMenuDoneItem() {
    return menuDoneItem;
  }

  public PhotoPickerActivity getActivity() {
    return this;
  }

  public boolean isShowGif() {
    return showGif;
  }

  public void setShowGif(boolean showGif) {
    this.showGif = showGif;
  }

  public int getMaxCount() {
    return maxCount;
  }

  public void setNavigationStatusBar() {
    // Fix portrait issues
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      // Fix issues for KitKat setting Status Bar color primary
      if (Build.VERSION.SDK_INT >= 19) {
        TypedValue typedValue19 = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
        final int color = typedValue19.data;
        FrameLayout statusBar = (FrameLayout) findViewById(R.id.status_bar);
        statusBar.setBackgroundColor(color);
      }

      // Fix issues for Lollipop, setting Status Bar color primary dark
      if (Build.VERSION.SDK_INT >= 21) {
        TypedValue typedValue21 = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
        final int color = typedValue21.data;
        FrameLayout statusBar = (FrameLayout) findViewById(R.id.status_bar);
        statusBar.setBackgroundColor(color);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
      }
    }

    // Fix landscape issues (only Lollipop)
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      if (Build.VERSION.SDK_INT >= 19) {
        TypedValue typedValue19 = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
        final int color = typedValue19.data;
        FrameLayout statusBar = (FrameLayout) findViewById(R.id.status_bar);
        statusBar.setBackgroundColor(color);
      }
      if (Build.VERSION.SDK_INT >= 21) {
        TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        final int color = typedValue.data;
      }
    }
  }
}
