package com.ljphawk.arms.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.ljp.dialog.BaseDialog;
import com.ljp.dialog.LoadingDialog;
import com.ljp.widget.LoadHintLayout;

import java.util.HashMap;
import java.util.Map;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/1 17:07.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class StatusManager {

    private BaseDialog mDialog;
    private String loadMessage = "加载中...";
    private Context mContext;
    private Map<Object, LoadHintLayout> mLoadHintLayoutMap = new HashMap<>();

    /**
     * 显示加载中
     */
    public void showLoading(FragmentActivity activity) {
        showLoading(activity, loadMessage);
    }

    /**
     * 显示加载中
     */
    public void showLoading(FragmentActivity activity, String message) {
        if (null == activity) {
            return;
        }
        if (null == mDialog || !loadMessage.equals(message)) {
            mDialog = new LoadingDialog.Builder(activity).setMessage(message).create();
        }
        this.loadMessage = message;

        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     *取消dialog
     */
    public void hideLoading() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 显示加载完成
     */
    public void showComplete() {
        hideLoading();
        if (mLoadHintLayoutMap.size() == 0) {
            return;
        }
        showComplete(mLoadHintLayoutMap.keySet().iterator().next());
    }

    /**
     * 显示加载完成
     */
    public void showComplete(Object activityOrFragmentOrView) {
        hideLoading();
        showLayout(activityOrFragmentOrView, LoadHintLayout.completeType);
    }

    /**
     * 显示空提示
     */
    public void showEmpty(Object activityOrFragmentOrView) {
        showLayout(activityOrFragmentOrView, LoadHintLayout.emptyType);
    }

    /**
     * 显示错误提示
     */
    public void showError(Object activityOrFragmentOrView) {
        showLayout(activityOrFragmentOrView, LoadHintLayout.errorType);
    }

    /**
     * 显示布局更改
     */
    public void showLayout(Object activityOrFragmentOrView, int type) {

        boolean isContain = mLoadHintLayoutMap.containsKey(activityOrFragmentOrView);
        if (isContain) {
            LoadHintLayout loadHintLayout = mLoadHintLayoutMap.get(activityOrFragmentOrView);
            if (null != loadHintLayout) {
                loadHintLayout.show(type);
                return;
            }
        } else {
            //如果不包含并且类型还是完成状态不进行下面的操作
            if (type == LoadHintLayout.completeType) {
                return;
            }
        }

        View contentParent;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            mContext = activity;
            contentParent = activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            mContext = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
            if (contentParent == null) {
                throw new IllegalArgumentException("the fragment must already has a parent ,please do not invoke this in onCreateView,you should use this method in onActivityCreated() or onStart");
            }
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            mContext = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)");
        }

        int childCount = ((ViewGroup) contentParent).getChildCount();
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (((ViewGroup) contentParent).getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = ((ViewGroup) contentParent).getChildAt(0);
        }
        ((ViewGroup) contentParent).removeView(oldContent);
        LoadHintLayout loadHintLayout = new LoadHintLayout(mContext);
        ((ViewGroup) contentParent).addView(loadHintLayout, index, oldContent.getLayoutParams());
        loadHintLayout.setContentView(oldContent);
        loadHintLayout.show(type);

        mLoadHintLayoutMap.put(activityOrFragmentOrView, loadHintLayout);
    }


    /**
     * 设置提示图标
     */
    public void setIcon(Drawable drawable) {
        if (mLoadHintLayoutMap.size() == 0) {
            return;
        }
        setIcon(mLoadHintLayoutMap.keySet().iterator().next(), drawable);
    }

    public void setIcon(Object activityOrFragmentOrView, Drawable drawable) {
        LoadHintLayout loadHintLayout = mLoadHintLayoutMap.get(activityOrFragmentOrView);
        if (null != loadHintLayout) {
            loadHintLayout.setIcon(drawable);
        }
    }

    /**
     * 设置提示文本
     */

    public void setHint(CharSequence text) {
        if (mLoadHintLayoutMap.size() == 0) {
            return;
        }
        setHint(mLoadHintLayoutMap.keySet().iterator().next(), text);
    }

    public void setHint(Object activityOrFragmentOrView, CharSequence text) {
        LoadHintLayout loadHintLayout = mLoadHintLayoutMap.get(activityOrFragmentOrView);
        if (null != loadHintLayout) {
            loadHintLayout.setHint(text);
        }
    }


    public void setPageRetryClickListener(Object activityOrFragmentOrView, LoadHintLayout.PageRetryClickListener pageRetryClickListener) {
        LoadHintLayout loadHintLayout = mLoadHintLayoutMap.get(activityOrFragmentOrView);
        if (null != loadHintLayout) {
            loadHintLayout.setPageRetryClick(pageRetryClickListener);
        }
    }

    public void setPageRetryClickListener(LoadHintLayout.PageRetryClickListener pageRetryClickListener) {
        if (mLoadHintLayoutMap.size() == 0) {
            return;
        }
        setPageRetryClickListener(mLoadHintLayoutMap.keySet().iterator().next(), pageRetryClickListener);
    }

}
