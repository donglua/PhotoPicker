package me.iwf.PhotoPickerDemo;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class PhotoPickUtils {

    public static void onActivityResult(int requestCode, int resultCode, Intent data,PickHandler pickHandler ) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {//第一次，选择图片后返回

                if (data != null) {
                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photos != null){
                        if (photos.size() >0){
                            pickHandler.onSuccess(photos);
                        }else {
                            pickHandler.onFail("未选择图片1");
                        }
                    }else {
                        pickHandler.onFail("未选择图片2");
                    }
                } else {
                    pickHandler.onFail("选择图片失败3");
                }
            }
        }else {
            if (requestCode == PhotoPicker.REQUEST_CODE){
                pickHandler.onCancle();
            }
        }


    }

    public static void startPick(Activity context,int count){
        PhotoPicker.builder()
                .setPhotoCount(count)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(true)
                .start(context, PhotoPicker.REQUEST_CODE);
    }

    public interface  PickHandler{
        void onSuccess(ArrayList<String> photos);
        void onFail(String error);
        void onCancle();
    }
}
