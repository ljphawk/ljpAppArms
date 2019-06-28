package com.ljp.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljp.dialog.listener.ViewClickWrapper;

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
        super(context, theme > 0 ? theme : R.style.BaseDialogStyle);
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

        protected static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        protected static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
        private Context mContext;
        private List<com.ljp.dialog.listener.OnShowListener> mOnShowListeners;
        private List<com.ljp.dialog.listener.OnCancelListener> mOnCancelListeners;
        private List<com.ljp.dialog.listener.OnDismissListener> mOnDismissListeners;
        private OnKeyListener mOnKeyListener;
        private BaseDialog mDialog;
        private View mContentView;
        // 位置
        private int mGravity = Gravity.CENTER;
        // 宽度和高度
        private int mWidth = WRAP_CONTENT;
        private int mHeight = WRAP_CONTENT;
        // 点击空白是否能够取消  默认点击阴影可以取消
        private boolean mCancelable = true;
        //id属性设置
        private SparseArray<CharSequence> mTextArray = new SparseArray<>();
        private SparseIntArray mVisibilityArray = new SparseIntArray();
        private SparseArray<Drawable> mBackgroundArray = new SparseArray<>();
        private SparseArray<Drawable> mImageArray = new SparseArray<>();
        private SparseArray<com.ljp.dialog.listener.OnClickListener> mClickArray = new SparseArray<>();

        public Builder(Context context) {
            mContext = context;
        }

        public B setContentView(@NonNull int layoutId) {
            return setContentView(LayoutInflater.from(mContext).inflate(layoutId, null));
        }

        public B setContentView(@NonNull View view) {
            mContentView = view;
            return (B) this;
        }

        /**
         * 显示
         */
        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }

        /**
         * 创建
         */
        public BaseDialog create() {
            // 判断布局是否为空
            if (mContentView == null) {
                throw new IllegalArgumentException("Dialog layout cannot be empty");
            }

            ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
            if (layoutParams != null) {
                if (mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mWidth = layoutParams.width;
                }
                if (mHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mHeight = layoutParams.height;
                }
            }

            mDialog = createDialog(mContext, -1);
            mDialog.setContentView(mContentView);

            mDialog.setCancelable(mCancelable);
            if (mCancelable) {
                mDialog.setCanceledOnTouchOutside(true);
            }

            if (null != mOnShowListeners) {
                mDialog.setOnShowListeners(mOnShowListeners);
            }
            if (null != mOnCancelListeners) {
                mDialog.setOnCancelListeners(mOnCancelListeners);
            }
            if (null != mOnDismissListeners) {
                mDialog.setOnDismissListeners(mOnDismissListeners);
            }
            if (mOnKeyListener != null) {
                mDialog.setOnKeyListener(mOnKeyListener);
            }
            // 设置参数
            WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            params.gravity = mGravity;
//            params.windowAnimations = mAnimations;
            mDialog.getWindow().setAttributes(params);

            // 设置文本
            for (int i = 0; i < mTextArray.size(); i++) {
                ((TextView) mContentView.findViewById(mTextArray.keyAt(i))).setText(mTextArray.valueAt(i));
            }

            // 设置可见状态
            for (int i = 0; i < mVisibilityArray.size(); i++) {
                mContentView.findViewById(mVisibilityArray.keyAt(i)).setVisibility(mVisibilityArray.valueAt(i));
            }

            // 设置背景
            for (int i = 0; i < mBackgroundArray.size(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mContentView.findViewById(mBackgroundArray.keyAt(i)).setBackground(mBackgroundArray.valueAt(i));
                }else {
                    mContentView.findViewById(mBackgroundArray.keyAt(i)).setBackgroundDrawable(mBackgroundArray.valueAt(i));
                }
            }

            // 设置图片
            for (int i = 0; i < mImageArray.size(); i++) {
                ((ImageView) mContentView.findViewById(mImageArray.keyAt(i))).setImageDrawable(mImageArray.valueAt(i));
            }

            // 设置点击事件
            for (int i = 0; i < mClickArray.size(); i++) {
                mContentView.findViewById(mClickArray.keyAt(i)).setOnClickListener(new ViewClickWrapper(mDialog, mClickArray.valueAt(i)));
            }

            return mDialog;
        }

        /**
         * 创建对话框对象（子类可以重写此方法来改变 Dialog 类型）
         */
        protected BaseDialog createDialog(Context context, int themeResId) {
            return new BaseDialog(context, themeResId);
        }

        /**
         * 设置宽度
         */
        public B setWidth(int width) {
            mWidth = width;
            return (B) this;
        }

        /**
         * 设置高度
         */
        public B setHeight(int height) {
            mHeight = height;
            return (B) this;
        }

        /**
         * 设置重心位置
         */
        public B setGravity(int gravity) {
            // 适配 Android 4.2 新特性，布局反方向（开发者选项 - 强制使用从右到左的布局方向）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gravity = Gravity.getAbsoluteGravity(gravity, mContext.getResources().getConfiguration().getLayoutDirection());
            }
            mGravity = gravity;
            return (B) this;
        }

        /**
         * 是否可以取消
         */
        public B setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return (B) this;
        }

        /**
         * 添加显示监听
         */
        public B addOnShowListener(@NonNull com.ljp.dialog.listener.OnShowListener listener) {
            if (mOnShowListeners == null) {
                mOnShowListeners = new ArrayList<>();
            }
            mOnShowListeners.add(listener);
            return (B) this;
        }

        /**
         * 添加取消监听
         */
        public B addOnCancelListener(@NonNull com.ljp.dialog.listener.OnCancelListener listener) {
            if (mOnCancelListeners == null) {
                mOnCancelListeners = new ArrayList<>();
            }
            mOnCancelListeners.add(listener);
            return (B) this;
        }

        /**
         * 添加销毁监听
         */
        public B addOnDismissListener(@NonNull com.ljp.dialog.listener.OnDismissListener listener) {
            if (mOnDismissListeners == null) {
                mOnDismissListeners = new ArrayList<>();
            }
            mOnDismissListeners.add(listener);
            return (B) this;
        }

        /**
         * 设置按键监听
         */
        public B setOnKeyListener(@NonNull OnKeyListener onKeyListener) {
            mOnKeyListener = onKeyListener;
            return (B) this;
        }
        /**
         * 设置文本
         */
        public B setText(@IdRes int id, @StringRes int resId) {
            return setText(id, mContext.getResources().getString(resId));
        }
        public B setText(@IdRes int id, CharSequence text) {
            mTextArray.put(id, text);
            return (B) this;
        }

        /**
         * 设置可见状态
         */
        public B setVisibility(@IdRes int id, int visibility) {
            mVisibilityArray.put(id, visibility);
            return (B) this;
        }

        /**
         * 设置背景
         */
        public B setBackground(@IdRes int id, @DrawableRes int resId) {
            return setBackground(id, mContext.getResources().getDrawable(resId));
        }
        public B setBackground(@IdRes int id, Drawable drawable) {
            mBackgroundArray.put(id, drawable);
            return (B) this;
        }

        /**
         * 设置图片
         */
        public B setImageDrawable(@IdRes int id, @DrawableRes int resId) {
            return setBackground(id, mContext.getResources().getDrawable(resId));
        }
        public B setImageDrawable(@IdRes int id, Drawable drawable) {
            mImageArray.put(id, drawable);
            return (B) this;
        }

        /**
         * 设置点击事件
         */
        public B setOnClickListener(@IdRes int id, @NonNull com.ljp.dialog.listener.OnClickListener listener) {
            mClickArray.put(id, listener);
            return (B) this;
        }

    }
}
