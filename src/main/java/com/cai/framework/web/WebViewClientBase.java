package com.cai.framework.web;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cai.lib.logger.Logger;

public class WebViewClientBase extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d("shouldOverrideUrlLoading: " + url);
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
}
