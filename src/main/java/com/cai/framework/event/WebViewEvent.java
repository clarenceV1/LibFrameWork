package com.cai.framework.event;

public class WebViewEvent {
    public boolean hideWebClseBtn;
    public static final int TYPE_HIDE_BTN = 1;
    public static final int TYPE_LOAD_FINISH = 2;
    public int type;

    public WebViewEvent(int type, boolean hideWebClseBtn) {
        this.type = type;
        this.hideWebClseBtn = hideWebClseBtn;
    }

    public WebViewEvent(int type) {
        this.type = type;
    }
}
