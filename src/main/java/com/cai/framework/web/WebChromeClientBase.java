package com.cai.framework.web;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import com.cai.lib.logger.Logger;

public class WebChromeClientBase extends WebChromeClient {
    WebViewFragment fragment;
    Context context;

    public WebChromeClientBase(WebViewFragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
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
        fragment.setWebTitleByWebView(title);
//        super.onReceivedTitle(view, title);
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
//
//    @Override
//    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//        new AlertDialog.Builder(context)
//                .setTitle("Js调用警告对话框")
//                .setMessage(message)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                })
//                .setCancelable(false)
//                .show();
//        return true;
//    }

//    @Override
//    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//        new AlertDialog.Builder(context)
//                .setTitle("Js调用确认对话框")
//                .setMessage(message)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .show();
//        return true;
//    }
//
//    @Override
//    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//        final EditText et = new EditText(context);
//        et.setText(defaultValue);
//        new AlertDialog.Builder(context)
//                .setTitle(message)
//                .setView(et)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm(et.getText().toString());
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                })
//                .setCancelable(false)
//                .show();
//
//        return true;
//    }
}
