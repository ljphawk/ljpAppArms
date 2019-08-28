package com.ljphawk.arms.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.ljphawk.arms.base.BaseActivity;
import com.tencent.smtt.sdk.WebView;
/*
 *@创建者       L_jp
 *@创建时间     2018/08/07 10:12.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 *
 * @JavascriptInterface注解的方法名一定是public
 */

public class WebViewJavaScriptFunction {

    private BaseActivity mActivity;
    private WebView mWebView;

    public WebViewJavaScriptFunction(BaseActivity activity, WebView webView) {
        mActivity = activity;
        mWebView = webView;
    }

    /**
     *
     * @return 返回用户的信息
     */
    @JavascriptInterface
    public String getNativeUserInfo() {//返回用户信息
        return "";
    }

    /**
     * toast
     */
    @JavascriptInterface
    public void showToast(String message) {
        ToastUtils.showToast(message);
    }

    /**
     * 关闭浏览器
     */
    @JavascriptInterface
    public void closeNativeWebView() {
        ThreadUtil.runOnUiThread(() -> {
            if (null != mActivity && !mActivity.isFinishing()) {
                mActivity.finish();
            }
        });
    }

    /**
     * 修改标题
     * @param title 标题
     */
    @JavascriptInterface
    public void setWebViewTitle(String title) {
        ThreadUtil.runOnUiThread(() -> {
            if (null != mActivity) {
                mActivity.setTitleBarTitle(title);
            }
        });
    }

    /**
     * 打开本地页面 无参数的情况
     * @param isClose 是否关闭当前页面
     * @param classPath 页面路径
     */
    @JavascriptInterface
    public void openNativeView(boolean isClose, String classPath) {
        ThreadUtil.runOnUiThread(() -> {
            if (null != mActivity) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(mActivity.getPackageName(), classPath));
                    mActivity.startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showToast("请升级至最新版本~");
                    return;
                }

                if (isClose && !mActivity.isFinishing()) {
                    mActivity.finish();
                }
            }
        });
    }
}
