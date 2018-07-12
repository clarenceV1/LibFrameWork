package com.cai.framework.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cai.framework.event.WebViewEvent;
import com.cai.lib.logger.Logger;

import org.greenrobot.eventbus.EventBus;

public class WebViewClientBase extends WebViewClient {

    WebViewFragment webViewFragment;

    public WebViewClientBase(WebViewFragment webViewFragment) {
        this.webViewFragment = webViewFragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (webViewFragment.isLoadNewActivity) {
            WebProtocolManager.getInstall().jumpNewActivity(url);
            return true;
        }
        Logger.d("shouldOverrideUrlLoading: " + url);
        // 步骤2：根据协议的参数，判断是否是所需要的url
        // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
        //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
            EventBus.getDefault().post(new WebViewEvent(WebViewEvent.TYPE_BTN, false));
            view.loadUrl(url);
            return true;
        } else if (WebProtocolManager.getInstall().isProtocol(webViewFragment.mWebView, uri)) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Logger.d("onPageStarted: " + url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Logger.d("onPageFinished: " + url);
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Logger.d("onReceivedError: " + failingUrl);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        Logger.d("doUpdateVisitedHistory: " + url);
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();    //表示等待证书响应
        // handler.cancel();      //表示挂起连接，为默认方式
        // handler.handleMessage(null);    //可做其他处理
    }
}
