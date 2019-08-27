package com.ljphawk.arms.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;

import com.ljp.base.utils.CommonUtils;
import com.ljphawk.arms.listener.WebViewJavaScriptFunction;
import com.ljphawk.arms.utils.ThreadUtil;
import com.ljphawk.arms.utils.ToastUtils;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView {
    private Activity mActivity;
    private Context mContext;
    private boolean isCleanHistory;

    public X5WebView(Context context) {
        this(context, null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.mContext = context;

        this.getView().setClickable(true);
        initWebViewSettings();
        this.setWebChromeClient(chromeClient);
        this.setWebViewClient(new SelfWebViewClient());
        // WebStorage webStorage = WebStorage.getInstance();

        this.setDownloadListener((s, s1, s2, s3, l) -> {
            Uri uri = Uri.parse(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        });
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
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

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (mOnReceivedTitleListener != null) {
                mOnReceivedTitleListener.onReceivedTitle(s);
            }

        }

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                   JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                 JsResult arg3) {
            /**
             * 这里写入你自定义的window alert
             */
            return super.onJsAlert(null, arg1, arg2, arg3);
        }
    };


    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSetting.setUserAgentString(webSetting.getUserAgentString() + "liujunpeng");
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计

        this.addJavascriptInterface(mWebViewJavaScriptFunction, "liujunpeng");
    }


    private class SelfWebViewClient extends com.tencent.smtt.sdk.WebViewClient {

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
    }

    private WebViewJavaScriptFunction mWebViewJavaScriptFunction = new WebViewJavaScriptFunction() {
        @Override
        public void onJsFunctionCalled(String tag) {

        }

        @JavascriptInterface
        public String getNativeUserInfo() {//返回用户信息
            return "";
        }

        @JavascriptInterface
        public void closeNativeWebView() {//关闭浏览器
            ThreadUtil.runOnUiThread(() -> {
                if (null != mActivity && !mActivity.isFinishing()) {
                    mActivity.finish();
                }
            });
        }

        @JavascriptInterface
        public void setWebViewTitle(String title) {//修改标题
            ThreadUtil.runOnUiThread(() -> {
                if (null != mOnReceivedTitleListener) {
                    mOnReceivedTitleListener.onReceivedTitle(title);
                }
            });
        }

        @JavascriptInterface
        public void openNativeView(boolean isClose, String classPath) {
            ThreadUtil.runOnUiThread(() -> {
                if (null != mActivity) {
                    try {
                        Intent intent = new Intent();
                        ComponentName com = new ComponentName(mContext.getPackageName(), classPath);
                        intent.setComponent(com);
                        mContext.startActivity(intent);
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
    };

    private onReceivedTitleListener mOnReceivedTitleListener;

    public interface onReceivedTitleListener {
        void onReceivedTitle(String title);
    }

    public void setOnReceivedTitleListener(onReceivedTitleListener onReceivedTitleListener) {
        this.mOnReceivedTitleListener = onReceivedTitleListener;
    }
}
