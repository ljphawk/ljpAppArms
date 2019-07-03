package com.ljphawk.arms.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ljp.dialog.BaseDialog;
import com.ljp.dialog.LoadingDialog;
import com.ljp.widget.LoadHintLayout;
import com.ljphawk.arms.R;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

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

    private static final String TAG = "StatusManager";
    private BaseDialog mDialog;
    private String loadMessage = "加载中...";
    private LoadHintLayout mHintLayout;

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
        if (null != mHintLayout) {
            mHintLayout.hide();
        }
    }

    /**
     * 显示空提示
     */
    public void showEmpty(View view) {
        showLayout(view, R.drawable.icon_hint_empty, "暂无数据", false);
    }

    /**
     * 显示错误提示
     */
    public void showError(View view) {
        String hint;
        int dRes;
        // 判断当前网络是否可用
        if (isNetworkAvailable(view.getContext())) {
            dRes = R.drawable.icon_hint_request;
            hint = "网络请求出错了,点击可重试";
        } else {
            dRes = R.drawable.icon_hint_nerwork;
            hint = "可能没有网络了,点击可重试";
        }
        showLayout(view, dRes, hint, true);
    }

    /**
     * 显示布局更改
     */
    public void showLayout(View view, @DrawableRes int iconId, CharSequence hint, boolean isCanClick) {
        hideLoading();
        if (view == null) {
            Log.e(TAG, "showLayout: view is null!!!!!!!!!");
            return;
        }

        if (mHintLayout == null) {
            if (view instanceof LoadHintLayout) {
                mHintLayout = (LoadHintLayout) view;
            } else if (view instanceof ViewGroup) {
                mHintLayout = findHintLayout((ViewGroup) view);
            }

            if (mHintLayout == null) {
                throw new IllegalStateException("You didn't add this HintLayout to your Activity layout");
            }
        }

        mHintLayout.show(isCanClick);
        mHintLayout.setHint(hint);
        mHintLayout.setIcon(ContextCompat.getDrawable(view.getContext(), iconId));
    }

    /**
     * 判断网络功能是否可用
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     *重试的点击事件回调
     */
    public void setPageRetryClickListener(LoadHintLayout.PageRetryClickListener pageRetryClickListener) {
        if (null != mHintLayout) {
            mHintLayout.setPageRetryClick(pageRetryClickListener);
        }
    }


    /**
     * 智能获取布局中的 HintLayout 对象
     */
    private static LoadHintLayout findHintLayout(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if ((view instanceof LoadHintLayout)) {
                return (LoadHintLayout) view;
            } else if (view instanceof ViewGroup) {
                LoadHintLayout layout = findHintLayout((ViewGroup) view);
                if (layout != null) return layout;
            }
        }
        return null;
    }
}
