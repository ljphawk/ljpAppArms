package com.ljp.bottomtab;



/*
 *@创建者       L_jp
 *@创建时间     2019/6/19 13:57.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class MainTabItemBean  {

    private String name;
    private int drawableTop;
    private int id;

    public MainTabItemBean(String name, int drawableTop,int id) {
        this.name = name;
        this.drawableTop = drawableTop;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableTop() {
        return drawableTop;
    }

    public void setDrawableTop(int drawableTop) {
        this.drawableTop = drawableTop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
