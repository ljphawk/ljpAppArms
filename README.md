#mvpArms使用介绍
最快初始化一个mvp新项目，封装好会使用的一些第三方库和工具类

----------

###base
为公用libs，存放工具类，colors，style

###bottomTab
为底部切换的tab；
可以动态设置名字，图标选择器，和添加自定义的item；
有点击事件回调；

###titleBar
通用的标题栏（toolbar）
可配置：标题文字，左边返回图标是否显示，左边设置文字；右边设置文字和图标；
点击事件的监听；
可自定义布局，把titleBar当作一个fragment使用
 <com.ljp.titlebar.TitleBar
        app:title="标题"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"/>

###dialog
通用的dialog，采用build模式；
增加通用的dialog请参考LoadingDialog；
自定义dialog使用方式
new BaseDialogFragment.Builder(this)
.setContentView(R.layout.dialog_custom)
.setAnimStyle(BaseDialog.AnimStyle.SCALE)
.show();

###LoadHintLayout
设置页面的空状态或者加载错误的状态；
封装在BaseActivity和BaseFragment中；
需要操作的布局必须用LoadHintLayout嵌套；
调用onLoadComplete、onLoadEmpty、onLoadError


###base基类的介绍
暂时只使用一个Base就可以，不需要增加其他的Activity继承BaseActivity
目前总体来看
BaseActivity、BasePresenter、BaseView 为一套
BaseRefreshActivity、BasePresenter、BaseRefreshView 为一套

BaseActivity中可操作toast、显示或隐藏loading
BasePresenter中可通过getView操作toast、显示或隐藏loading
BaseRefreshActivity的presenter的getView增加了完成刷新和完成加载的方法

BaseActivity：
1，正常使用mvp，TestActivity extends BaseActivity<TestPresenter> implements TestView{...} (具体可看MainActivity)
2，如果一个页面逻辑比较少，觉得没有必要增加presenter的话 直接TestActivity extends BaseActivity{...}

BaseRefreshActivity:
大致同BaseActivity用法无区别，只是要继承BaseRefreshActivity，实现BaseRefreshView
TestActivity extends BaseRefreshActivity<TestPresenter> implements BaseRefreshView{...}
或
TestActivity extends BaseRefreshActivity{...}