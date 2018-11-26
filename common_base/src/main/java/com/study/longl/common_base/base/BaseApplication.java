package com.study.longl.common_base.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by longl on 2018/11/26.
 * 基础Applicatioin,所有module的application都要继承这个application
 */

public class BaseApplication extends Application {

    public static BaseApplication getApplication() {
        return application;
    }

    private static BaseApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    private void initRouter() {
        ARouter.init(application);
    }


}
