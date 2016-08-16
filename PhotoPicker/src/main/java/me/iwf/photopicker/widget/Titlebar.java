package me.iwf.photopicker.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.iwf.photopicker.R;

/**
 * Created by Administrator on 2016/8/5.
 */
public class Titlebar extends FrameLayout {
    private RelativeLayout rootView;
    private TextView tvLeft;
    private ImageView ivLeft;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    @Override
    public RelativeLayout getRootView() {
        return rootView;
    }

    private TextView tvTitle;
    private TextView tvRight;
    private ImageView ivRight;

    private OnClickListener leftOnclickListener;
    private OnClickListener rightOnclickListener;

    private Activity mActivity;

    public Titlebar(Context context) {
        this(context,null);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData(context, attrs,defStyleAttr);
        initEvent(context,attrs,defStyleAttr);
    }

    public void init(Activity activity){
        mActivity = activity;
        leftOnclickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        };
        ivLeft.setOnClickListener(leftOnclickListener);

    }

    private void initEvent(Context context, AttributeSet attrs, int defStyleAttr) {

        if (context instanceof Activity){
            final Activity activity = (Activity) context;
            leftOnclickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            };
        }


    }

    public void setLeftOnclickListener(OnClickListener listener){
        if (listener != null){
            leftOnclickListener = listener;
            ivLeft.setOnClickListener(leftOnclickListener);
            tvLeft.setOnClickListener(leftOnclickListener);
        }

    }

    public void setRightOnclickListener(OnClickListener listener){
        if (listener != null){
            rightOnclickListener = listener;
            ivRight.setOnClickListener(rightOnclickListener);
            tvRight.setOnClickListener(rightOnclickListener);
        }
    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
            tvTitle.setVisibility(VISIBLE);
        }
    }

    public void setLeft(Drawable leftDrawable,String leftTxt,OnClickListener listener){
        if (leftDrawable != null){
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(leftDrawable);
            tvLeft.setVisibility(GONE);
        }else if (!TextUtils.isEmpty(leftTxt)){
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(leftTxt);
            ivLeft.setVisibility(GONE);
        }else {//all not set,default

        }

        if (listener != null){
            leftOnclickListener = listener;
        }




    }

    public void setRitht(Drawable rightDrawable,String rightTxt,OnClickListener listener){
        if (!TextUtils.isEmpty(rightTxt)){
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightTxt);
            ivRight.setVisibility(GONE);
            if (listener != null){
                rightOnclickListener = listener;
                tvRight.setOnClickListener(rightOnclickListener);
            }
        }else if (rightDrawable != null){
            ivRight.setVisibility(VISIBLE);
            tvRight.setVisibility(GONE);
            ivRight.setImageDrawable(rightDrawable);
            if (listener != null){
                rightOnclickListener = listener;
                ivRight.setOnClickListener(rightOnclickListener);
            }
        }else {

        }

        if (listener != null){
            rightOnclickListener = listener;
            ivRight.setOnClickListener(rightOnclickListener);
        }
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTitlebar);
          String  leftTxt = typedArray.getString(R.styleable.MyTitlebar_mtb_leftTxt);
            String  title = typedArray.getString(R.styleable.MyTitlebar_mtb_title);
            String  rightTxt = typedArray.getString(R.styleable.MyTitlebar_mtb_rightTxt);

            Drawable leftDrawable = typedArray.getDrawable(R.styleable.MyTitlebar_mtb_left_icon);
            Drawable rightDrawable = typedArray.getDrawable(R.styleable.MyTitlebar_mtb_right_icon);

           //left:drawable first
           setLeft(leftDrawable,leftTxt,null);

            //center
            setTitle(title);


            //right: text first
            setRitht(rightDrawable,rightTxt,null);


        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

    }

    private void initView(Context context) {
        rootView = (RelativeLayout) View.inflate(context,R.layout.view_titlebar,null);
        ivLeft = (ImageView) rootView.findViewById(R.id.iv_left);
        tvLeft = (TextView) rootView.findViewById(R.id.tv_left);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);

        ivRight = (ImageView) rootView.findViewById(R.id.iv_right);
        tvRight = (TextView) rootView.findViewById(R.id.tv_right);
        this.addView(rootView);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }
}
