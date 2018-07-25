package com.cai.framework.web;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.clarence.utillibrary.Base64Utils;
import com.example.clarence.utillibrary.PackageUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebProtocolManager {

    private IWebProtocol iProtocol;
    List<String> schemeList = new ArrayList<>();

    public static final String JS_CALLBACK_METHOD = "jsCallBack";

    private WebProtocolManager() {

    }

    public static final class Holder {
        public static final WebProtocolManager protocolManager = new WebProtocolManager();
    }

    public static WebProtocolManager getInstall() {
        return Holder.protocolManager;
    }

    public void init(List<String> schemes, IWebProtocol iProtocol) {
        if (iProtocol != null) {
            this.iProtocol = iProtocol;
        }
        if (schemes != null && schemes.size() > 0) {
            schemeList = schemes;
        }
    }

    public static Map<String, String> getResultMap(int code, String error, String data) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code + "");
        map.put("error", error);
        map.put("data", data);
        return map;
    }

    public void jumpNewActivity(String url) {
        if (iProtocol != null) {
            iProtocol.jumpNewActivity(url);
        }
    }

    /**
     * @param uri
     * @return
     */
    public boolean isProtocol(WebView webView, Uri uri) {
        String scheme = uri.getScheme();
        if (schemeList.contains(scheme)) {
            if (iProtocol != null) {
                IWebProtocolCallback callback = new IWebProtocolCallback() {
                    @Override
                    public void callBack(Context context, WebProtocolDO protocolDO, int code, String error, String data) {
                        Map<String, String> result = getResultMap(code, error, data);
                        handlerProtocolResult(context, protocolDO, result);
                    }
                };
                iProtocol.handlerProtocol(webView, uri, callback);
            }
            return true;
        }
        return false;
    }

    /**
     * 拼接返回的参数
     *
     * @param param
     */
    public void handlerProtocolResult(Context context, WebProtocolDO protocolDO, Map<String, String> param) {
        JSONObject jsonObject = new JSONObject();
        if (protocolDO.getUri() != null) {
            jsonObject.put("url", protocolDO.getUri().toString());
        }
        jsonObject.put("version", PackageUtils.getVersionName(context));
        if (param != null) {
            jsonObject.putAll(param);
        }
        javascriptCallBack(protocolDO.getWebView(), JS_CALLBACK_METHOD, jsonObject.toJSONString());
    }


    /**
     * 安卓调用javascript方法
     *
     * @param methdName javascript的方法名
     * @param param     返回给javascript 的值
     */
    public void javascriptCallBack(WebView webView, String methdName, String param) {
        // Android版本变量
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:");
        builder.append(methdName);
        builder.append("('");
        builder.append(Base64Utils.encodeToString(param));
        builder.append("')");
        String jsUrl = builder.toString();
        if (Build.VERSION.SDK_INT < 19) {
            webView.loadUrl(jsUrl);//xxxxxjavascript方法名
        } else {
            webView.evaluateJavascript(jsUrl, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Log.d("WebViewFragment", "javascript 返回的结果" + value);
                }
            });
        }
    }
}
