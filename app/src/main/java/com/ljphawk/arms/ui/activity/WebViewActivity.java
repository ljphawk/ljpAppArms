package com.ljphawk.arms.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.presenter.WebViewPresenter;
import com.ljphawk.arms.ui.view.WebViewView;
import com.ljphawk.arms.widget.X5WebView;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/18 13:48.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewView {


    private static String loadUrlExtra = "loadUrlExtra";
    private X5WebView mWebView;
    private String mLoadUrl;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(loadUrlExtra, url);
        context.startActivity(intent);
    }

    @Override
    protected int resView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        return R.layout.activity_web_view;
    }

    @Override
    public WebViewPresenter initPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWebView = findViewById(R.id.web_view);

    }

    @Override
    protected void initData() {
        mWebView.setActivity(this);
        Intent intent = getIntent();
        mLoadUrl = intent.getStringExtra(loadUrlExtra);
        mWebView.setOnReceivedTitleListener(this::setTitleBarTitle);
        mWebView.loadUrl(mLoadUrl);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
