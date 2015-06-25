package me.iwf.photopicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.R;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.event.OnPhotoClickListener;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends SelectableAdapter<PhotoAdapter.PhotoViewHolder> {

  private List<String> photoPaths = new ArrayList<>();
  private LayoutInflater inflater;

  private Context mContext;

  private OnItemCheckListener onItemCheckListener    = null;
  private OnPhotoClickListener onPhotoClickListener  = null;
  private View.OnClickListener onCameraClickListener = null;

  public final static int ITEM_TYPE_CAMERA = 100;
  public final static int ITEM_TYPE_PHOTO  = 101;

  public PhotoAdapter(Context mContext, List<String> photoPaths) {
    this.photoPaths = photoPaths;
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);

  }


  @Override public int getItemViewType(int position) {
    return position == 0 ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
  }


  @Override public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.item_photo, parent, false);
    PhotoViewHolder holder = new PhotoViewHolder(itemView);
    if (viewType == ITEM_TYPE_CAMERA) {
      holder.vSelected.setVisibility(View.GONE);
      holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER);
      holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onCameraClickListener != null) {
            onCameraClickListener.onClick(view);
          }
        }
      });
    }
    return holder;
  }


  @Override public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
    if (getItemViewType(position) == ITEM_TYPE_PHOTO) {

      Uri uri = Uri.fromFile(new File(photoPaths.get(position - 1)));

      Glide.with(mContext)
          .load(uri)
          .centerCrop()
          .thumbnail(0.1f)
          .placeholder(R.drawable.ic_photo_black_48dp)
          .error(R.drawable.ic_broken_image_black_48dp)
          .into(holder.ivPhoto);

      final boolean isChecked = isSelected(position);

      holder.vSelected.setSelected(isChecked);
      holder.ivPhoto.setSelected(isChecked);

      holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onPhotoClickListener != null) {
            onPhotoClickListener.onClick(view, position);
          }
        }
      });
      holder.vSelected.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {

          boolean isEnable = true;

          if (onItemCheckListener != null) {
            isEnable = onItemCheckListener.OnItemCheck(position, isChecked, getSelectedItemCount());
          }
          if (isEnable) {
            toggleSelection(position);
          }
        }
      });

    } else {
      holder.ivPhoto.setImageResource(R.drawable.camera);
    }
  }


  @Override public int getItemCount() {
    return photoPaths.size() + 1;
  }


  public static class PhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private View vSelected;
    public PhotoViewHolder(View itemView) {
      super(itemView);
      ivPhoto   = (ImageView) itemView.findViewById(R.id.iv_photo);
      vSelected = itemView.findViewById(R.id.v_selected);
    }
  }


  public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
    this.onItemCheckListener = onItemCheckListener;
  }


  public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
    this.onPhotoClickListener = onPhotoClickListener;
  }


  public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
    this.onCameraClickListener = onCameraClickListener;
  }


  public ArrayList<String> getSelectedPhotos() {
    ArrayList<String> selectedPhotos = new ArrayList<>(getSelectedItemCount());

    for (int position : getSelectedItems()) {
      selectedPhotos.add(photoPaths.get(position - 1));
    }

    return selectedPhotos;
  }

}
