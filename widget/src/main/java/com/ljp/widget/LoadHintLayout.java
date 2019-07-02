package com.ljp.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;


/*
 *@创建者       L_jp
 *@创建时间     2019/7/1 18:05.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class LoadHintLayout extends FrameLayout {

    //提示布局
    private ViewGroup mHintView;
    //提示图标
    private ImageView mImageView;
    //提示文本
    private TextView mTextView;
    private View mContentView;

    public static final int completeType = 0;
    public static final int errorType = 1;
    public static final int emptyType = 2;

    public LoadHintLayout(@NonNull Context context) {
        super(context);
    }

    public LoadHintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadHintLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 显示
     */
    public void show(int type) {
        if (null == mHintView) {
            //初始化布局
            initLayout();
        }

        switch (type) {
            case LoadHintLayout.completeType:
                break;
            case LoadHintLayout.errorType:
                // 判断当前网络是否可用
                if (isNetworkAvailable(getContext())) {
                    setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_hint_request));
                    setHint("网络请求错误");
                } else {
                    setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_hint_nerwork));
                    setHint("没有网络了");
                }
                break;
            case LoadHintLayout.emptyType:
                setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_hint_empty));
                setHint("暂无数据");
                break;
            default:
                throw new IllegalArgumentException("loading type non existent");
        }

        if (type == LoadHintLayout.completeType) {
            mContentView.setVisibility(VISIBLE);
            mHintView.setVisibility(GONE);
        } else {
            mHintView.setVisibility(VISIBLE);
            mContentView.setVisibility(GONE);
        }
    }


    /**
     * 设置提示图标，请在show方法之后调用
     */
    public void setIcon(Drawable drawable) {
        if (null != mImageView) {
            mImageView.setImageDrawable(drawable);
        }
    }

    /**
     * 设置提示文本，请在show方法之后调用
     */
    public void setHint(CharSequence text) {
        if (null != mTextView && null != text) {
            mTextView.setText(text);
        }
    }

    /**
     * 初始化提示的布局
     */
    private void initLayout() {
        mHintView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.widget_hint_layout, null);
        mImageView = mHintView.findViewById(R.id.iv_hint_icon);
        mTextView = mHintView.findViewById(R.id.iv_hint_text);

        addView(mHintView);
    }

    public void setContentView(View oldContent) {
        removeView(mContentView);
        mContentView = oldContent;
        addView(mContentView);
        /*
            重新设置layoutParams是由于有些view可能用了weight（权重），
            就会导致宽高会为0，本来的宽高大小已经给了LoadHintLayout，
            所以这里就直接Mach_parent就可以;
            还有一点就是如果设置了margin，margin其实已经给LoadHintLayout了，contentView自身的margin需要取消
         */
        ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((MarginLayoutParams) layoutParams).setMargins(0, 0, 0, 0);
        }
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mContentView.setLayoutParams(layoutParams);
    }

    /**
     * 判断网络功能是否可用
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public View getContentView() {
        return mContentView;
    }
}