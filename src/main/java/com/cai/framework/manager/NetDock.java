package com.cai.framework.manager;

import android.content.Context;

import com.cai.framework.base.GodBaseApplication;
import com.example.clarence.netlibrary.Net1Build;
import com.example.clarence.netlibrary.NetBaseBuild;
import com.example.clarence.netlibrary.NetFactory;

import retrofit2.Retrofit;

/**
 * Created by clarence on 2018/3/23.
 */

public class NetDock {

    /**
     * 初始化一次就够了
     */
    public static void initNet(Context context) {
        NetBaseBuild netBaseBuild = new Net1Build(context)
                .baseUrl("http://www.sojson.com");
        NetFactory.getInsatance().init(netBaseBuild);
    }

    public static Retrofit request() {
        return NetFactory.getInsatance().request();
    }
}
