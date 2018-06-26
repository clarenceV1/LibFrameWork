package com.cai.framework.web;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.alibaba.fastjson.JSONObject;
import com.example.clarence.utillibrary.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class WebProtocolManager {

    // meetone://shareQQ?param={"type"="1"} //{"type"="1"}要加密
    private static final String PROTOCOL_SCHEME = "meetone://";
    private static final String PROTOCOL_PARAM = "param";
    private IWebProtocol iProtocol;

    private WebProtocolManager() {

    }

    public static final class Holder {
        public static final WebProtocolManager protocolManager = new WebProtocolManager();
    }

    public static WebProtocolManager getInstall() {
        return Holder.protocolManager;
    }

    public void init(IWebProtocol iProtocol) {
        this.iProtocol = iProtocol;
    }

    /**
     * @param uri
     * @return
     */
    public boolean isProtocol(WebView webView,  Uri uri) {
        if (PROTOCOL_SCHEME.equals(uri.getScheme())) {
            if (iProtocol != null) {
                String host = uri.getHost();
                if(TextUtils.isEmpty(host)){
                    return false;
                }
                String params = uri.getQueryParameter(PROTOCOL_PARAM);
                if (!TextUtils.isEmpty(params)) {
                    try {
                        params = Base64Utils.decodeToString(params);
                        params = URLDecoder.decode(params,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                IWebProtocolCallback callback = new IWebProtocolCallback() {
                    @Override
                    public void callBack(WebProtocolDO protocolDO,Map<String, String> result) {
                        handlerProtocolResult(protocolDO, result);
                    }
                };
                WebProtocolDO protocolDO = new WebProtocolDO();
                protocolDO.setHost(host);
                protocolDO.setParams(params);
                protocolDO.setWebView(webView);
                protocolDO.setUri(uri);
                iProtocol.handlerProtocol(protocolDO,callback);
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
    private void handlerProtocolResult(WebProtocolDO protocolDO, Map<String, String> param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", protocolDO.getUri().toString());
        if (param != null) {
            jsonObject.putAll(param);
        }
        javascriptCallBack(protocolDO.getWebView(), "jsCallBack", jsonObject.toJSONString());
    }


    /**
     * 安卓调用javascript方法
     *
     * @param methdName javascript的方法名
     * @param param     返回给javascript 的值
     */
    private void javascriptCallBack(WebView webView, String methdName, String param) {
        // Android版本变量
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:");
        builder.append(methdName);
        builder.append("(");
        builder.append(Base64Utils.encodeToString(param));
        builder.append(")");
        String jsUrl = builder.toString();
        if (Build.VERSION.SDK_INT < 19) {
            webView.loadUrl(jsUrl);//xxxxxjavascript方法名
        } else {
            webView.evaluateJavascript(jsUrl, new ValueCallback<String>() {//xxxxxjavascript方法名
                @Override
                public void onReceiveValue(String value) { //javascript 返回的结果
                    //此处为 js 返回的结果
                    Log.d("WebViewFragment", "javascript 返回的结果" + value);
                }
            });
        }
    }
}
