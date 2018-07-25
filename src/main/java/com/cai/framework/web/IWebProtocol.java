package com.cai.framework.web;

import android.net.Uri;

import com.tencent.smtt.sdk.WebView;

public interface IWebProtocol {
    void handlerProtocol(WebView webView, Uri uri, IWebProtocolCallback callback);

    void jumpNewActivity(String url);
}
