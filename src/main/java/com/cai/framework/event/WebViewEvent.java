package com.cai.framework.event;

public class WebViewEvent {
    public boolean hideWebClseBtn;
    public static final String TYPE_BTN = "hideWebCloseBtn";
    public String type;

    public WebViewEvent(String type, boolean hideWebClseBtn) {
        this.type = type;
        this.hideWebClseBtn = hideWebClseBtn;
    }
}
