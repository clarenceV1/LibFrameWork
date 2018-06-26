package com.cai.framework.web;

import java.util.Map;

public interface IWebProtocolCallback {
    void callBack(WebProtocolDO protocolDO, Map<String, String> result);

}
