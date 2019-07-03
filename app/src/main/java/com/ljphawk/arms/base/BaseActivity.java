package com.ljphawk.arms.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.gyf.immersionbar.ImmersionBar;
import com.ljp.base.utils.CommonUtils;
import com.ljp.titlebar.TitleBar;
import com.ljp.titlebar.listener.OnLeftClickListener;
import com.ljp.widget.LoadHintLayout;
import com.ljphawk.arms.R;
import com.ljphawk.arms.application.AppManager;
import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.http.RequestUrlUtils;
import com.ljphawk.arms.utils.StatusManager;
import com.ljphawk.arms.utils.ToastUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Host-0 on 2017/1/16.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView, OnLeftClickListener {

    public static String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected CompositeDisposable disposables;
    protected P presenter;
    private TitleBar mTitleBar;
    private final StatusManager mStatusManager = new StatusManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resView());
        //添加activity
        AppManager.getInstance().addActivity(this);
        mContext = this;
        TAG = mContext.getClass().getSimpleName();
        //沉浸式
        initImmersionBar();
        //disposables
        disposables = new CompositeDisposable();
        //初始化presenter
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attach(this);
        }
        //抽象 初始化findViewById
        initView(savedInstanceState);
        //抽象 初始化数据
        initData();
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mTitleBar = findViewById(R.id.base_toolbar);
        if (null != mTitleBar) {
            mImmersionBar.titleBar(mTitleBar);
            mTitleBar.setLeftOnClickListener(this);
        }
        mImmersionBar.keyboardEnable(true) //解决软键盘与底部输入框冲突问题
                .statusBarDarkFont(true, 0.2f)//true是深色，false是白色 如果不能改变状态栏的颜色 就用后面0.2f的透明度
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                .init();   //所有子类都将继承这些相同的属性
    }

    protected abstract int resView();

    //初始化presenter
    public abstract P initPresenter();


    protected P getPresenter() {
        return presenter;
    }

    protected abstract void initView(Bundle savedInstanceState);

    /**
     *  //做数据初始化
     */
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
        cancelAllRequest();
    }


    public void setTitleBarTitle(CharSequence title) {
        if (CommonUtils.StringHasValue(title)) {
            if (null != mTitleBar) {
                mTitleBar.setTitle(title);

            }
        }
    }

    public void setShowTitleBarLeft(boolean backEnabled) {
        if (null != mTitleBar) {
            mTitleBar.setShowLeft(backEnabled);
        }
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void showToast(String content) {
        ToastUtils.showToast(content);
    }

    @Override
    public void showLoading() {
        mStatusManager.showLoading(this);
    }

    @Override
    public void showLoading(String content) {
        mStatusManager.showLoading(this, content);
    }

    @Override
    public void hideLoading() {
        mStatusManager.hideLoading();
    }

    /**
     * 加载完成，如果不传object的话，
     * 默认显示回当时第一个设置empty或者error的object
     */
    @Override
    public void onLoadComplete() {
        mStatusManager.showComplete();
    }

    /**
     *
     * @param activityOrFragmentOrView 针对其他显示的view视图
     */
    public void onLoadComplete(Object activityOrFragmentOrView) {
        mStatusManager.showComplete(activityOrFragmentOrView);
    }

    /**
     *设置指定对象view为空状态
     */
    public void onLoadEmpty(Object activityOrFragmentOrView) {
        mStatusManager.showEmpty(activityOrFragmentOrView);
    }

    /**
     *设置指定对象view为加载错误的状态
     */
    public void onLoadError(Object activityOrFragmentOrView) {
        mStatusManager.showError(activityOrFragmentOrView);
    }

    /**
     *设置指定对象view为加载错误的状态
     * 带点击重试的回调
     */
    public void onLoadError(Object activityOrFragmentOrView, LoadHintLayout.PageRetryClickListener pageRetryClickListener) {
        mStatusManager.showError(activityOrFragmentOrView);
        mStatusManager.setPageRetryClickListener(activityOrFragmentOrView, pageRetryClickListener);
    }

    @Override
    public void startActivity(Class<? extends Activity> targetActivity) {
        mContext.startActivity(new Intent(mContext, targetActivity));
    }

    /*
    titleBar左边的点击 默认操作关闭activity
     */
    @Override
    public void onLeftClick(View v) {
        activityFinish();
    }

    /*
    关闭activity前的操作  ->
    如果需要自己控制 返回的操作，重写该方法， 删除super
     */
    protected void activityFinish() {
        finish();
    }

    /*
    //添加网络请求 如果有的网络请求在页面销毁后不进行取消，
    在请求的时候可以不调用这个方法
     */
    public void addDisposables(Disposable disposable) {
        try {
            if (disposables != null) {
                disposables.add(disposable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    取消网络请求
     */
    public void cancelAllRequest() {
        try {
            if (disposables != null) {
                disposables.clear();
            }
            if (presenter != null) {
                presenter.detach();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    获取application
     */
    public MyApplication getApp() {
        return (MyApplication) getApplication();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public RequestUrlUtils getRequest() {
        return RequestUrlUtils.getInstance();
    }
}
