package com.cai.framework.web;

import android.net.Uri;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;


public class WebProtocolDO {
    private WebView webView;
    private Map<String, String> params;
    private String scheme;
    private String domain;//自己取的名称：authority+path
    private Uri uri;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
