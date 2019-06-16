package com.ljphawk.arms.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.gyf.immersionbar.ImmersionBar;
import com.ljphawk.arms.application.ActivityManager;
import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.http.RequestUrlUtils;
import com.ljphawk.arms.utils.ToastUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Host-0 on 2017/1/16.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity implements BaseView {

    //    public static final String TAG = "BaseActivity";
    public static String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected RequestUrlUtils mRequestUrlUtils;
    protected CompositeDisposable disposables;
    protected P presenter;
    protected V mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resView());
        //添加activity
        ActivityManager.getInstance().addActivity(this);
        mContext = this;
        TAG = mContext.getClass().getSimpleName();
        //沉浸式
        initImmersionBar(true);
        //disposables
        disposables = new CompositeDisposable();
        //request
        mRequestUrlUtils = new RequestUrlUtils();
        //初始化presenter
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attach(mContext, (V) this);
            presenter.setRequestUrlUtils(mRequestUrlUtils);
        }
        //抽象 初始化findViewById
        initView(savedInstanceState);
        //抽象 初始化数据
        initData();
    }

    protected void initImmersionBar(boolean enable) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(enable) //解决软键盘与底部输入框冲突问题
                .statusBarDarkFont(true, 0.2f)//如果不能改变状态栏的颜色 就用后面0.2f的透明度
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
        ActivityManager.getInstance().removeActivity(this);
        cancelAllRequest();
    }


//    public void setToolbarTitleAndBack(BaseActivity activity, boolean backEnabled, String title) {
//        Toolbar toolbar = activity.findViewById(R.id.base_toolbar);
//        if (toolbar == null) {
//            return;
//        }
//        activity.setSupportActionBar(toolbar);//关联toolbar
//        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示title
//        activity.getSupportActionBar().setDisplayShowHomeEnabled(backEnabled); //启用back
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(backEnabled); //启用back
//        if (CommonUtils.StringHasValue(title)) {
//            TextView tvTitle = activity.findViewById(R.id.tv_toolbar_title);
//            tvTitle.setText(title);
//        }
//    }
//
//
//    public Toolbar getToolbar(BaseActivity activity) {
//        Toolbar toolbar = activity.findViewById(R.id.base_toolbar);
//        return toolbar;
//    }
//
//    public TextView getToolbarTextView(BaseActivity activity) {
//        TextView tvTitle = activity.findViewById(R.id.tv_toolbar_title);
//        return tvTitle;
//    }

@Override
    public void showToast(String content) {
        ToastUtils.showToast(content);
    }


    @Override
    public void startActivity(Class targetActivity) {
        mContext.startActivity(new Intent(mContext, targetActivity));
    }

    //toolbar返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activityFinish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //关闭activity前的操作  ->如果需要自己控制 返回的操作，重写该方法， 删除super
    protected void activityFinish() {
        finish();
    }

    public void addDisposables(Disposable disposable) {
        try {
            if (disposables != null) {
                disposables.add(disposable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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


//    protected void umOnEvent(String eventId) {
//        onEventParams(eventId, null);
//    }

//    protected void onEventParams(String eventId, Map<String, String> map) {
//        UmengEventTj.onEventParams(mContext, eventId, map);
//    }

//    public void postCatchedException(Throwable throwable) {
//        if (!MyApplication.isDebug) {
//            CrashReport.postCatchedException(throwable);
//        }
//    }

    public MyApplication getApp() {
        return (MyApplication) getApplication();
    }

}
