package com.ljp.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;

import java.util.ArrayList;
import java.util.List;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/21 14:51.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class BaseDialog extends AppCompatDialog implements DialogInterface.OnShowListener, DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    private List<com.ljp.dialog.listener.OnShowListener> mOnShowListener;
    private List<com.ljp.dialog.listener.OnCancelListener> mOnCancelListener;
    private List<com.ljp.dialog.listener.OnDismissListener> mOnDismissListener;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public BaseDialog(Context context) {
        this(context, R.style.BaseDialogStyle);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 设置显示监听器集合
     */
    private void setOnShowListeners(@Nullable List<com.ljp.dialog.listener.OnShowListener> listeners) {
        super.setOnShowListener(this);
        mOnShowListener = listeners;
    }

    /**
     * 设置取消监听器集合
     */
    private void setOnCancelListeners(@Nullable List<com.ljp.dialog.listener.OnCancelListener> listeners) {
        super.setOnCancelListener(this);
        mOnCancelListener = listeners;
    }

    /**
     * 设置销毁监听器集合
     */
    private void setOnDismissListeners(@Nullable List<com.ljp.dialog.listener.OnDismissListener> listeners) {
        super.setOnDismissListener(this);
        mOnDismissListener = listeners;
    }

    public void addOnShowListener(com.ljp.dialog.listener.OnShowListener onShowListener) {
        if (null == mOnShowListener) {
            super.setOnShowListener(this);
            mOnShowListener = new ArrayList<>();
        }
        this.mOnShowListener.add(onShowListener);
    }

    public void addOnCancelListener(com.ljp.dialog.listener.OnCancelListener onCancelListener) {
        if (null == mOnCancelListener) {
            super.setOnCancelListener(this);
            mOnCancelListener = new ArrayList<>();
        }
        this.mOnCancelListener.add(onCancelListener);
    }

    public void addOnDismissListener(com.ljp.dialog.listener.OnDismissListener onDismissListener) {
        if (null == mOnDismissListener) {
            super.setOnDismissListener(this);
            mOnDismissListener = new ArrayList<>();
        }
        this.mOnDismissListener.add(onDismissListener);
    }


    @Override
    public void onShow(DialogInterface dialog) {
        if (null != mOnShowListener) {
//            mOnShowListener.onShow(this);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (null != mOnCancelListener) {
//            mOnCancelListener.onCancel(this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mHandler.removeCallbacksAndMessages(this);
        if (null != mOnDismissListener) {
//            mOnDismissListener.onDismiss(this);
        }
    }


    /**
     * 执行
     */
    public final boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    public final boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        return mHandler.postAtTime(r, this, uptimeMillis);
    }

    public static class Builder<B extends Builder> {

        private Context mContext;
        private com.ljp.dialog.listener.OnShowListener mOnShowListener;
        private com.ljp.dialog.listener.OnCancelListener mOnCancelListener;
        private com.ljp.dialog.listener.OnDismissListener mOnDismissListener;

        public Builder(Context context) {
            mContext = context;
        }

    }
}
