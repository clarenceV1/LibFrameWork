package com.cai.framework.web;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.cai.framework.R;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.GodBasePresenterFragment;
import com.cai.framework.databinding.WebVewFragmentBinding;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WebViewFragment extends GodBasePresenterFragment<WebVewFragmentBinding> {

    public static final String KEY_RUL = "URL";
    private static final String APP_CACAHE_DIRNAME = "web_cache";
    private String url;
    WebView mWebView;
    WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.web_vew_fragment;
    }

    @Override
    public void initView(View view) {
        initData();
        initWebView();
        initProgressBar();
    }

    private void initData() {
        url = getArguments().getString(KEY_RUL);
    }

    private void initProgressBar() {
        mViewBinding.progressBar.setMax(100);
    }

    private void initWebView() {
        //创建Webview
        int screenHeight = DeviceUtils.getScreenHeight(getContext()) - DimensUtils.dip2px(getContext(), 50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(GodBaseApplication.getAppContext());
        mWebView.setLayoutParams(params);
        mViewBinding.rootView.addView(mWebView);

        initWebSetting();

        WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase(this);
        WebViewClientBase mWebViewClientBase = new WebViewClientBase(this);
        mWebView.setWebViewClient(mWebViewClientBase);
        mWebView.setWebChromeClient(mWebChromeClientBase);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebView.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象

        mWebView.loadUrl(url);
    }


    private void initWebSetting() {
        webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        //webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        //webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置  Application Caches 缓存目录
        //String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        // webSettings.setAppCachePath(cacheDirPath);

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 处理javascript调用客户端
     *
     * @param uri
     * @return
     */
    public boolean handlerJavascript(Uri uri) {
        // 如果url的协议 = 预先约定的 js 协议
        // 就解析往下解析参数
        if (uri.getScheme().equals("js")) {
            // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
            // 所以拦截url,下面JS开始调用Android需要的方法
            if (uri.getAuthority().equals("webview")) {
                //  步骤3：
                // 执行JS所需要调用的逻辑
                System.out.println("js调用了Android的方法");
                // 可以在协议上带有参数并传递到Android上
                HashMap<String, String> params = new HashMap<>();
                Set<String> collection = uri.getQueryParameterNames();

                //只能通过一下方法返回结果给服务端
                String result = "这是处理后的结果";
                loadJavascript("returnResult", result);
                mWebView.loadUrl("javascript:returnResult(" + result + ")");//returnResult协商返回给javascript的方法
            }
            return true;
        }
        return false;
    }

    /**
     * 安卓调用javascript方法
     *
     * @param methdName javascript的方法名
     * @param param     返回给javascript 的值
     */
    public void loadJavascript(String methdName, String param) {
        // Android版本变量
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        String jsUrl = StringUtils.buildString("javascript:", methdName, "(", TextUtils.isEmpty(param) ? "" : param, ")");
        if (Build.VERSION.SDK_INT < 19) {
            mWebView.loadUrl(jsUrl);//xxxxxjavascript方法名
        } else {
            mWebView.evaluateJavascript(jsUrl, new ValueCallback<String>() {//xxxxxjavascript方法名
                @Override
                public void onReceiveValue(String value) { //javascript 返回的结果
                    //此处为 js 返回的结果
                    Log.d("WebViewFragment", "javascript 返回的结果" + value);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(false);
        }
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }

    public boolean canGoBack() {
        if (mWebView != null) {
            return mWebView.canGoBack();
        }
        return false;
    }

    public void goBack() {
        if (mWebView != null) {
            mWebView.canGoBack();
        }
    }

    /**
     * 从webview里获取头部标题---webview获取头部后回调此方法
     */
    public void setWebTitleByWebView(String title) {

    }

    @Override
    public void onDestroyView() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroyView();
    }

    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }
}
