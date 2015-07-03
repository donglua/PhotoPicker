package me.iwf.photopicker.event;

import me.iwf.photopicker.entity.Photo;

/**
 * Created by donglua on 15/6/20.
 */
public interface OnItemCheckListener {

  /***
   *
   * @param position
   * @param path
   *@param isCheck
   * @param selectedItemCount   @return enable check
   */
  boolean OnItemCheck(int position, Photo path, boolean isCheck, int selectedItemCount);

}
