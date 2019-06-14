package com.ljphawk.arms.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ljphawk.arms.R;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/14 17:37.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class BaseToolbar extends RelativeLayout {
    private final Context mContext;

    public BaseToolbar(Context context) {
        this(context,null);
    }

    public BaseToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, this,false);
        addView(view);
    }
}
