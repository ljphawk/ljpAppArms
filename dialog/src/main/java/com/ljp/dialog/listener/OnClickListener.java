package com.ljp.dialog.listener;


import android.view.View;

import com.ljp.dialog.BaseDialog;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/28 17:25.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public interface OnClickListener<V extends View> {
    void onClick(BaseDialog dialog, V view);
}
