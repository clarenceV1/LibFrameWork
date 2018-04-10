package com.cai.framework.manager;

import com.example.clarence.utillibrary.log.ILog;
import com.example.clarence.utillibrary.log.LogFactory;

/**
 * Created by clarence on 2018/3/23.
 */

public class LogDock {

    public static ILog getLog() {
        return LogFactory.getInsatance().getiLog();
    }

}
