package com.cai.framework.web;

import java.util.Map;

public interface IWebProtocol {
    Map<String, String> handlerProtocol(String protocol, String params);
}
