package com.cai.framework.web;

import android.content.Context;

public interface IWebProtocolCallback {
    void callBack(Context context,WebProtocolDO protocolDO, int code, String error, String data);
}
