package com.ljp.titlebar;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ljp.base.utils.ScreenUtil;
import com.ljp.titlebar.listener.OnLeftClickListener;
import com.ljp.titlebar.listener.OnRightClickListener;
import com.ljp.titlebar.listener.OnTitleClickListener;


/*
 *@创建者       L_jp
 *@创建时间     2019/6/14 17:37.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class TitleBar extends FrameLayout implements View.OnClickListener, Runnable {
    private static final String TAG = "TitleBar";
    private final Context mContext;
    private View mToolbarView;
    private TextView mTvToolbarRight;
    private TextView mTvToolbarLeft;
    private TextView mTvToolbarTitle;
    private View mViewToolbarLine;
    private OnLeftClickListener mOnLeftClickListener;
    private OnTitleClickListener mOnTitleClickListener;
    private OnRightClickListener mOnRightClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        initStyle(attrs);
    }

    private void init() {
        int id = getId();
        if (id > 0) {
            throw new RuntimeException("TitleBar的id已经设置为base_toolbar，请使用base_toolbar");
        }
        setId(R.id.base_toolbar);
        mToolbarView = LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, null);
        addView(mToolbarView);
        addLineView();
        initView();
        initListener();
    }

    private void addLineView() {
        mViewToolbarLine = new View(mContext);
        mViewToolbarLine.setId(R.id.view_toolbar_line);
        mViewToolbarLine.setBackgroundColor(Color.parseColor("#e9e9e9"));
        FrameLayout.LayoutParams lineParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(mContext, 1));
        lineParams.gravity = Gravity.BOTTOM;
        mViewToolbarLine.setLayoutParams(lineParams);
        addView(mViewToolbarLine);
    }


    private void initView() {
        mTvToolbarLeft = mToolbarView.findViewById(R.id.tv_toolbar_left);
        mTvToolbarTitle = mToolbarView.findViewById(R.id.tv_toolbar_title);
        mTvToolbarRight = mToolbarView.findViewById(R.id.tv_toolbar_right);
    }

    private void initListener() {
        mTvToolbarLeft.setOnClickListener(this);
        mTvToolbarTitle.setOnClickListener(this);
        mTvToolbarRight.setOnClickListener(this);
    }

    private void initStyle(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        //左边设置
        setLeftTitle(typedArray.getString(R.styleable.TitleBar_leftTitle));
        setLeftColor(typedArray.getColor(R.styleable.TitleBar_leftColor, mContext.getResources().getColor(R.color.c666)));
        //getDimensionPixelSize获取的是px
        setLeftSize(ScreenUtil.px2sp(mContext, typedArray.getDimensionPixelSize(R.styleable.TitleBar_leftSize, ScreenUtil.dip2px(mContext, 14))));

        boolean isShowLeft = typedArray.getBoolean(R.styleable.TitleBar_showLeft, true);
        setShowLeft(isShowLeft);
        if (isShowLeft) {
            setLeftIcon(typedArray.getResourceId(R.styleable.TitleBar_leftIcon, R.drawable.img_toolbar_back));
        }

        //标题设置
        setTitle(typedArray.getString(R.styleable.TitleBar_title));
        setTitleColor(typedArray.getColor(R.styleable.TitleBar_titleColor, mContext.getResources().getColor(R.color.c111)));
        setTitleSize(ScreenUtil.px2sp(mContext, typedArray.getDimensionPixelSize(R.styleable.TitleBar_titleSize, ScreenUtil.dip2px(mContext, 16))));

        //右边设置
        setRightTitle(typedArray.getString(R.styleable.TitleBar_rightTitle));
        setRightColor(typedArray.getColor(R.styleable.TitleBar_rightColor, mContext.getResources().getColor(R.color.c666)));
        setRightSize(ScreenUtil.px2sp(mContext, typedArray.getDimensionPixelSize(R.styleable.TitleBar_rightSize, ScreenUtil.dip2px(mContext, 14))));
        setRightIcon(typedArray.getResourceId(R.styleable.TitleBar_rightIcon, 0));

        //分割线设置
        setLineVisible(typedArray.getBoolean(R.styleable.TitleBar_lineVisible, true));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置TitleBar默认的宽度
        int contentWidth = getMeasuredWidth();

        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
            contentWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int contentMaxHeight = getMeasuredHeight();
        // 设置TitleBar默认的高度
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            //内容最大的高度
            contentMaxHeight = Math.max(contentMaxHeight, getActionBarHeight());
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                //如果view是gone掉的或者第0个 （第0个是toolbarde 主布局）
                if (childAt.getVisibility() == View.GONE || i == 0) {
                    childAt.getLayoutParams().height = getActionBarHeight();
                    continue;
                }
                //测量子view的宽高
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                contentMaxHeight = Math.max(contentMaxHeight, childAt.getMeasuredHeight());
            }
        }
        setMeasuredDimension(contentWidth, contentMaxHeight);
    }

    /**
     * 获取ActionBar的高度
     */
    private int getActionBarHeight() {
        TypedArray ta = mContext.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) ta.getDimension(0, 0);
        ta.recycle();
        return actionBarSize;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_toolbar_left) {
            if (null != mOnLeftClickListener) {
                mOnLeftClickListener.onLeftClick(v);
            }
        } else if (i == R.id.tv_toolbar_title) {
            if (null != mOnTitleClickListener) {
                mOnTitleClickListener.onTitleClick(v);
            }
        } else if (i == R.id.tv_toolbar_right) {
            if (null != mOnRightClickListener) {
                mOnRightClickListener.onRightClick(v);
            }
        }
    }

    @Override
    public void run() {
        // 更新中间标题的内边距，避免向左或者向右偏移
        final int leftSize = mTvToolbarLeft.getWidth();
        final int rightSize = mTvToolbarRight.getWidth();
        if (leftSize != rightSize) {
            if (leftSize > rightSize) {
                mTvToolbarTitle.setPadding(0, 0, leftSize - rightSize, 0);
            } else {
                mTvToolbarTitle.setPadding(rightSize - leftSize, 0, 0, 0);
            }
        }

        // 更新 View 状态
        mTvToolbarLeft.setEnabled(!"".equals(mTvToolbarLeft.getText().toString().trim()) || hasCompoundDrawables(mTvToolbarLeft));
        mTvToolbarTitle.setEnabled(!"".equals(mTvToolbarTitle.getText().toString().trim()) || hasCompoundDrawables(mTvToolbarTitle));
        mTvToolbarRight.setEnabled(!"".equals(mTvToolbarRight.getText().toString().trim()) || hasCompoundDrawables(mTvToolbarRight));
    }

    /**
     * 检查TextView的任意方向图标是否有不为空的
     */
    private boolean hasCompoundDrawables(TextView view) {
        Drawable[] drawables = view.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                return true;
            }
        }
        return false;
    }

    //设置左边的点击事件监听
    public void setLeftOnClickListener(OnLeftClickListener leftOnClickListener) {
        this.mOnLeftClickListener = leftOnClickListener;
    }

    //设置title的点击事件监听
    public void setTitleClickListener(OnTitleClickListener leftOnClickListener) {
        this.mOnTitleClickListener = leftOnClickListener;
    }

    //设置右边的点击事件监听
    public void setRightOnClickListener(OnRightClickListener rightOnClickListener) {
        this.mOnRightClickListener = rightOnClickListener;
    }

    /**
     * 设置标题
     */

    public void setTitle(int stringId) {
        if (stringId > 0) {
            setTitle(getResources().getString(stringId));
        }
    }


    public void setTitle(CharSequence title) {
        if (null == title) {
            title = "";
        }
        if (null != mTvToolbarTitle) {
            mTvToolbarTitle.setText(title);
        }
        post(this);
    }

    /**
     * 设置左边的标题
     */
    public void setLeftTitle(int stringId) {
        if (stringId > 0) {
            setLeftTitle(getResources().getString(stringId));
        }
    }

    public void setLeftTitle(CharSequence text) {
        if (null == text) {
            text = "";
        }
        if (null != mTvToolbarLeft) {
            mTvToolbarLeft.setText(text);
        }
        post(this);
    }

    /**
     * 设置右边的标题
     */
    public void setRightTitle(int stringId) {
        if (stringId > 0) {
            setRightTitle(getResources().getString(stringId));
        }
    }

    public void setRightTitle(CharSequence text) {
        if (null == text) {
            text = "";
        }
        if (null != mTvToolbarRight) {
            mTvToolbarRight.setText(text);
        }
        post(this);
    }

    /**
     * 设置左边的图标
     */
    public void setLeftIcon(int iconId) {
        if (iconId > 0) {
            setLeftIcon(getContext().getResources().getDrawable(iconId));
        }
    }

    public void setLeftIcon(Drawable drawable) {
        mTvToolbarLeft.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        post(this);
    }

    /**
     * 设置右边的图标
     */
    public void setRightIcon(int iconId) {
        if (iconId > 0) {
            setRightIcon(getContext().getResources().getDrawable(iconId));
        }
    }

    public void setRightIcon(Drawable drawable) {
        mTvToolbarRight.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        post(this);
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int color) {
        mTvToolbarTitle.setTextColor(color);
    }

    /**
     * 设置左标题颜色
     */
    public void setLeftColor(int color) {
        mTvToolbarLeft.setTextColor(color);
    }

    /**
     * 设置右标题颜色
     */
    public void setRightColor(int color) {
        mTvToolbarRight.setTextColor(color);
    }


    /**
     * 设置左标题的文本大小
     */
    public void setLeftSize(float size) {
        mTvToolbarLeft.setTextSize(size);
        post(this);
    }

    /**
     * 设置标题的文本大小
     */
    public void setTitleSize(float size) {
        mTvToolbarTitle.setTextSize(size);
        post(this);
    }

    /**
     * 设置右标题的文本大小
     */
    public void setRightSize(float size) {
        mTvToolbarRight.setTextSize(size);
        post(this);
    }

    /**
     * 设置分割线是否显示
     */
    public void setLineVisible(boolean visible) {
        mViewToolbarLine.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setShowLeft(boolean visible) {
        mTvToolbarLeft.setVisibility(visible ? VISIBLE : GONE);
        post(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        // 移除监听
        mTvToolbarTitle.setOnClickListener(null);
        mTvToolbarLeft.setOnClickListener(null);
        mTvToolbarRight.setOnClickListener(null);
        super.onDetachedFromWindow();
    }
}
