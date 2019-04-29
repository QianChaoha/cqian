package com.cqian.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.cqian.baselibrary.ioc.ViewUtils;

/**
 * Description:
 * Data: 2019/3/27
 *
 * @author: cqian
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ViewUtils.inject(this);
        initTitle();
        initView();
        initData();
    }

    public void start(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected abstract int getLayoutId();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

}
