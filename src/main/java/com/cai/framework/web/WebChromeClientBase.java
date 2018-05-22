package com.cai.framework.web;

import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cai.lib.logger.Logger;

public class WebChromeClientBase extends WebChromeClient {
    WebViewFragment fragment;

    public WebChromeClientBase(WebViewFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Logger.d("onProgressChanged: " + newProgress);
        fragment.mViewBinding.progressBar.setProgress(newProgress);
        if (newProgress == 100) {
            fragment.mViewBinding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        Logger.d("onReceivedTitle: " + title);
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        Logger.d("onReceivedTouchIconUrl: " + url);
        super.onReceivedTouchIconUrl(view, url, precomposed);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog,
                                  boolean isUserGesture, Message resultMsg) {
        Logger.d("onCreateWindow: " + isDialog);
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }
}
