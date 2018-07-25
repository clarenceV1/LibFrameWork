package com.cai.framework.event;

import java.util.Map;

public class WebViewJsEvent {
   public Map<String,String> params;

    public WebViewJsEvent(Map<String,String> params) {
        this.params = params;
    }
}
