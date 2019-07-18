package com.ljphawk.arms.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljphawk.arms.R;


/*
 *@创建者       L_jp
 *@创建时间     2019/7/1 18:05.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class LoadHintLayout extends RelativeLayout {

    //提示布局
    private View mHintView;
    //提示图标
    private ImageView mImageView;
    //提示文本
    private TextView mTextView;

    private PageRetryClickListener mPageRetryClickListener;

    private boolean isCanClick = false;

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
    public void show(boolean isCanClick) {
        this.isCanClick = isCanClick;
        if (null == mHintView) {
            //初始化布局
            initLayout();
        }

        if (!hintIsShow()) {
            mHintView.setVisibility(VISIBLE);
        }
    }

    public void hide() {
        if (null != mHintView && hintIsShow()) {
            mHintView.setVisibility(GONE);
        }
    }

    public boolean hintIsShow() {
        return null != mHintView && mHintView.getVisibility() == VISIBLE;
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
        mHintView = LayoutInflater.from(getContext()).inflate(R.layout.widget_hint_layout, this, false);
        mImageView = mHintView.findViewById(R.id.iv_hint_icon);
        mTextView = mHintView.findViewById(R.id.iv_hint_text);
        LinearLayout llHintContent = mHintView.findViewById(R.id.ll_hint_content);
        llHintContent.setOnClickListener(v -> {
            if (mPageRetryClickListener != null && isCanClick) {
                mPageRetryClickListener.pageRetryClick(v);
            }
        });

        if (getBackground() == null) {
            // 默认使用 windowBackground 作为背景
            TypedArray ta = getContext().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
            setBackground(ta.getDrawable(0));
            ta.recycle();
        }

        addView(mHintView,getLayoutParams());
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackground(background);
            mHintView.setBackground(background);
        } else {
            setBackgroundDrawable(background);
            mHintView.setBackgroundDrawable(background);
        }
    }


    public void setPageRetryClick(PageRetryClickListener pageRetryClickListener) {
        this.mPageRetryClickListener = pageRetryClickListener;
    }

    public interface PageRetryClickListener {
        void pageRetryClick(View view);
    }
}