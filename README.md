#base 
为公用libs，存放工具类，colors，style

#bottomTab
为底部切换的tab；
可以动态设置名字，图标选择器，和添加自定义的item；
有点击事件回调；

#titleBar
通用的标题栏（toolbar）
可配置：标题文字，左边返回图标是否显示，左边设置文字；右边设置文字和图标；
点击事件的监听；
可自定义布局，把titleBar当作一个fragment使用

#dialog
通用的dialog，采用build模式；
增加通用的dialog请参考LoadingDialog；
自定义dialog使用方式
new BaseDialogFragment.Builder(this)
.setContentView(R.layout.dialog_custom)
.setAnimStyle(BaseDialog.AnimStyle.SCALE)
.show();

#widget
存放通用的自定义View


#LoadHintLayout
设置页面的空状态或者加载错误的状态；
封装在BaseActivity和BaseFragment中；
需要操作的布局必须用LoadHintLayout嵌套；
调用onLoadComplete、onLoadEmpty、onLoadError
