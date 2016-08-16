package me.iwf.PhotoPickerDemo;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public interface PicChosenListInterface {



    void onActivityResult(int requestCode, int resultCode, Intent data);

    ArrayList<String> getPhotos();

}
