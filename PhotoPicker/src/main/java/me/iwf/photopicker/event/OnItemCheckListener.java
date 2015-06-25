package me.iwf.photopicker.event;

/**
 * Created by donglua on 15/6/20.
 */
public interface OnItemCheckListener {

  /***
   *
   * @param position
   * @param isCheck
   * @param selectedItemCount
   * @return enable check
   */
  boolean OnItemCheck(int position, boolean isCheck, int selectedItemCount);

}
