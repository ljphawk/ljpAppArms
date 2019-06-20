package com.ljp.bottomtab;


import android.view.View;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/19 14:50.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public interface BottomTabItemClickListener {
    void tabItemClick(int position, String name, View view);
}
