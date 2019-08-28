package com.ljphawk.arms.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;

import com.ljp.base.utils.CommonUtils;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.utils.WebViewJavaScriptFunction;
import com.ljphawk.arms.utils.ToastUtils;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/*
 *@创建者       L_jp
 *@创建时间     2018/08/07 10:12.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */


public class X5WebView extends WebView {
    private BaseActivity mActivity;
    private Context mContext;
    private boolean isCleanHistory;

    //最好通过new的方式然后addView进布局
    public X5WebView(Context context, BaseActivity activity) {
        super(context);
        this.mContext = context;
        this.mActivity = activity;
        initWebView();
        addJavascriptInterface(new WebViewJavaScriptFunction(mActivity,this) , "wndc");
    }

    //通过xml方式引入的话，一定要调用setActivity()
    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initWebView();
    }

    private void initWebView() {
        initWebViewSettings();
        setWebChromeClient(chromeClient);
        setWebViewClient(new SelfWebViewClient());
        setDownloadListener((s, s1, s2, s3, l) -> {
            Uri uri = Uri.parse(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        });
    }

    /**
     * 加载url
     * @param url
     */
    public void webViewLoadUrl(String url) {
        url = null == url ? "" : url;
        loadUrl(url);
    }

    public void setCleanHistory(boolean cleanHistory) {
        isCleanHistory = cleanHistory;
    }

    public void setActivity(BaseActivity activity) {
        this.mActivity = activity;
        addJavascriptInterface(new WebViewJavaScriptFunction(mActivity,this) , "wndc");
    }

    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView webView, String title) {
            super.onReceivedTitle(webView, title);
            if (mActivity != null) {
                mActivity.setTitleBarTitle(title);
            }
        }

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
            //这里写入你自定义的window alert
            return super.onJsAlert(null, arg1, arg2, arg3);
        }

    };


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置ua
        webSetting.setUserAgentString(webSetting.getUserAgentString() + "woniudanci");
    }


    private class SelfWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!CommonUtils.StringHasValue(url)) {
                return true;
            }

            if (!(url.startsWith("http") || url.startsWith("https"))) {//不是http  和https开头的
                try {
                    if (url.startsWith("phone://")) {//如果是打电话
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + url.substring(8)));
                        mContext.startActivity(intent);
                    } else if (url.startsWith("email://")) {//发邮件
                        try {
                            Intent data = new Intent(Intent.ACTION_SENDTO);
                            data.setData(Uri.parse("mailto:" + url.substring(8)));
                            mContext.startActivity(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (e instanceof ActivityNotFoundException) {
                                ToastUtils.showToast("手机中没有安装邮件app");
                            }
                        }
                    } else if (url.startsWith("copy://")) { //拷贝
                        String substring = url.substring(7);
                        ClipboardManager cmb = (ClipboardManager) mContext
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                        if (null != cmb) {
                            cmb.setText(substring);
                            ToastUtils.showToast("已复制到剪贴板");
                        }
                    } else {
                        Uri parse = Uri.parse(url);
                        String scheme = parse.getScheme();
                        if (CommonUtils.StringHasValue(scheme)) {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, parse));
                        }
                    }
                    return true;
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (isCleanHistory) {
                clearHistory();
                isCleanHistory = false;
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("SSL认证失败，是否继续访问？")
                    .setPositiveButton("确定", (dialog, which) -> handler.proceed())
                    .setNegativeButton("取消", (dialog, which) -> handler.cancel())
                    .create()
                    .show();
        }
    }

}
