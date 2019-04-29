package com.cqian.app;

import android.app.Application;

import com.cqian.framelibrary.skin.SkinManager;

/**
 * Description:
 * Data: 2019/4/24
 *
 * @author: cqian
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(getApplicationContext());
    }
}
