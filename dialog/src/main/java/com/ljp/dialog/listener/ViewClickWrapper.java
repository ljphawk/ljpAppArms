package com.ljp.dialog.listener;


import android.view.View;

import com.ljp.dialog.BaseDialog;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/28 17:18.
 *@描述         点击事件的包装类，为了把dialog回调出去
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class ViewClickWrapper implements View.OnClickListener {
    private final BaseDialog mDialog;
    private final OnClickListener mListener;

    public ViewClickWrapper(BaseDialog dialog, OnClickListener listener) {
        mDialog = dialog;
        mListener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onClick(View v) {
        mListener.onClick(mDialog, v);
    }
}
