package com.dongdian.jj.gorgeous;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.tools.jj.tools.http.Http;
import com.tools.jj.tools.utils.LogUtil;
import com.tools.jj.tools.utils.glide.GlideUtil;

/**
 * Created by Jiajun he on 2018/4/8.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;

        init();


    }

    private void init() {
        GlideUtil.init(this);
        //接口请求初始化
        Http.initHttp(this);
        //图片框架初始化
        GlideUtil.init(this);
        //初始化hawk
        Hawk.init(this).build();
        //开启debug
        LogUtil.OPEN_LOG=true;
        //友盟
        //UMConfigure.init(this, "5a94ca46f43e481d9700015d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

    }
}
