package com.cai.framework.web;

import android.view.View;
import android.webkit.WebSettings;

import com.cai.framework.R;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.base.GodBasePresenterFragment;
import com.cai.framework.databinding.WebVewFragmentBinding;

import java.util.List;

public class WebViewFragment extends GodBasePresenterFragment<WebVewFragmentBinding> {

    public static final String KEY_RUL = "URL";
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.web_vew_fragment;
    }

    @Override
    public void initView(View view) {
        initData();
        initWebView(view);
        initProgressBar(view);
    }

    private void initData() {
        url = getArguments().getString(KEY_RUL);
    }

    private void initProgressBar(View view) {
        mViewBinding.progressBar.setMax(100);
    }

    private void initWebView(View view) {
        initWebSetting();

        WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase(this);
        WebViewClientBase mWebViewClientBase = new WebViewClientBase();
        mViewBinding.webViewLayout.setWebViewClient(mWebViewClientBase);
        mViewBinding.webViewLayout.setWebChromeClient(mWebChromeClientBase);
        mViewBinding.webViewLayout.loadUrl(url);
    }

    private void initWebSetting() {
        WebSettings webSettings = mViewBinding.webViewLayout.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initDagger() {

    }
}
