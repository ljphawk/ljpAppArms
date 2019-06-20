package com.ljp.bottomtab;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljp.base.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/19 13:53.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class MainBottomTab extends LinearLayout implements View.OnClickListener {

    private final Context mContext;
    private List<MainTabItemBean> tabList = new ArrayList();
    private BottomTabItemClickListener mBottomTabItemClickListener;

    public MainBottomTab(Context context) {
        this(context, null);
    }

    public MainBottomTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainBottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        addTabView();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        int dp_5 = ScreenUtil.dip2px(mContext, 5);
        setPadding(0, dp_5, 0, dp_5);
        tabList.add(new MainTabItemBean("首页", R.drawable.ic_selector_selectable_home_index, R.id.main_bottom_tab1));
        tabList.add(new MainTabItemBean("消息", R.drawable.ic_selector_selectable_home_message, R.id.main_bottom_tab2));
        tabList.add(new MainTabItemBean("发现", R.drawable.ic_selector_selectable_home_found, R.id.main_bottom_tab3));
        tabList.add(new MainTabItemBean("我的", R.drawable.ic_selector_selectable_home_me, R.id.main_bottom_tab4));

    }

    /*
     *添加textView  后期弃用TextView的话 setTabItemName的方法也要改
     */
    private void addTabView() {
        removeAllViews();
        for (int i = 0; i < tabList.size(); i++) {
            MainTabItemBean itemBean = tabList.get(i);
            TextView textView = new TextView(mContext);
            textView.setId(itemBean.getId());
            textView.setOnClickListener(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textView.setLayoutParams(layoutParams);
            textView.setClickable(true);
            textView.setFocusable(true);
            textView.setGravity(Gravity.CENTER);
            textView.setText(itemBean.getName());
            textView.setTextSize(11);
            textView.setTextColor(getResources().getColorStateList(R.color.main_bottom_tab_text_selector));
            Drawable drawable = mContext.getResources().getDrawable(itemBean.getDrawableTop());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, drawable, null, null);
            textView.setCompoundDrawablePadding(ScreenUtil.dip2px(mContext, 2));
            addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < tabList.size(); i++) {
            if (v.getId() == tabList.get(i).getId()) {
                if (mBottomTabItemClickListener != null) {
                    mBottomTabItemClickListener.tabItemClick(i, tabList.get(i).getName(), v);
                }
                return;
            }
        }
    }


    public void setBottomTabItemClickListener(BottomTabItemClickListener listener) {
        this.mBottomTabItemClickListener = listener;
    }


    public void setTabSelect(int position) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (null != childAt) {
                if (i == position) {
                    childAt.setActivated(true);
                } else {
                    childAt.setActivated(false);
                }
            }
        }
    }

    /*
    重置所有的tab
     */
    public void resetAllTab(List<MainTabItemBean> tabList) {
        this.tabList = tabList;
        addTabView();
    }

    /*
        获取指定的view
     */
    public View getTabItem(int position) {
        return getChildAt(position);
    }

    /*
    设置tab的文字
     */
    public void setTabItemName(int position, CharSequence name) {
        View tabItem = getTabItem(position);
        if (tabItem instanceof TextView && null != name) {
            ((TextView) tabItem).setText(name);
        }
    }

    /*
    设置tab的选择器
     */
    public void setTabItemDrawable(int position, int drawableRes) {
        View tabItem = getTabItem(position);
        if (tabItem instanceof TextView && drawableRes > 0) {
            Drawable drawable = mContext.getResources().getDrawable(drawableRes);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) tabItem).setCompoundDrawables(null, drawable, null, null);
        }
    }
}
