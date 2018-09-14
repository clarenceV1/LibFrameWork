package com.cai.framework.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cai.framework.R;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.GodBasePresenterFragment;
import com.cai.framework.databinding.WebVewFragmentBinding;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebViewFragment extends GodBasePresenterFragment<WebVewFragmentBinding> {

    public static final String KEY_RUL = "URL";

    public static final String HTML_DATA = "htmlData";


    private String url;
    private String htmlData;
    WebView mWebView;
    WebSettings webSettings;
    boolean isLoadNewActivity;//二级页面是否要重新开启新的页面
    Map<String, String> extraHeaders = new HashMap<>();

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

    public void setExtraHeaders(Map<String, String> extraHeaders) {
        this.extraHeaders = extraHeaders;
    }

    public void setExtraHeaders(String key, String value) {
        extraHeaders.put(key, value);
    }

    public boolean isLoadNewActivity() {
        return isLoadNewActivity;
    }

    public void setLoadNewActivity(boolean loadNewActivity) {
        isLoadNewActivity = loadNewActivity;
    }

    private void initData() {
        url = getArguments().getString(KEY_RUL);
        htmlData = getArguments().getString(HTML_DATA);
    }

    private void initProgressBar() {
        mViewBinding.progressBar.setMax(100);
    }

    private void initWebView() {
        //创建Webview
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        try {
            //部分机型在创建X5 Webview时会异常 eg.金立 M5
            mWebView = new WebView(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mWebView != null) {
            mWebView.setLayoutParams(params);
//          mWebView.setBackgroundColor(getResources().getColor(R.color.ys_30_30_30));
            mViewBinding.rootView.addView(mWebView);
            initWebSetting();
            WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase(this);
            WebViewClientBase mWebViewClientBase = new WebViewClientBase(this);
            mWebView.setWebViewClient(mWebViewClientBase);
            mWebView.setWebChromeClient(mWebChromeClientBase);
            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
            if (!StringUtils.isEmpty(url)) {
                if (extraHeaders != null && extraHeaders.size() > 0) {
                    mWebView.loadUrl(url, extraHeaders);
                } else {
                    mWebView.loadUrl(url);
                }
            } else if (!StringUtils.isEmpty(htmlData)) {
                mWebView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
            }

        }
    }


    private void initWebSetting() {
        if (mWebView == null) {
            return;
        }
        webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        }

        //缩放操作
        //webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        //webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        }




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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webSettings.setAllowFileAccess(true); //设置可以访问文件
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//x5里面已经有判断
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (webSettings != null) {
//            webSettings.setJavaScriptEnabled(true);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (webSettings != null) {
//            webSettings.setJavaScriptEnabled(false);
//        }
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }

    public WebView getWebView() {
        return mWebView;
    }

    public boolean canGoBack() {
        if (mWebView != null) {
            return mWebView.canGoBack();
        }
        return false;
    }

    public void goBack() {
        if (mWebView != null) {
            mWebView.goBack();
        }
    }

    public void reload() {
        if (mWebView != null) {
            mWebView.reload();
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
            if (extraHeaders != null && extraHeaders.size() > 0) {
                mWebView.loadUrl(url, extraHeaders);
            } else {
                mWebView.loadUrl(url);
            }
        }
    }

    public void jsCallBack(Map<String, String> params) {
        if (mWebView != null) {
            WebProtocolDO webProtocolDO = new WebProtocolDO();
            webProtocolDO.setWebView(mWebView);
            WebProtocolManager.getInstall().handlerProtocolResult(getContext(), webProtocolDO, params);
        }
    }
}
