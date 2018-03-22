package com.cai.framework.log;

/**
 * Created by clarence on 2018/3/21.
 */

public interface ILog {
    //for error log
    void error(String tag, String... msg);

    //for warming log
    void warn(String tag, String... msg);

    //for info log
    void info(String tag, String... msg);

    //for debug log
    void debug(String tag, String... msg);

    //for verbose log
    void verbose(String tag, String... msg);

    //for error log show position
    void showLogPosition(String tag, String... msg);
}
