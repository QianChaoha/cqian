package com.cqian.app.activity;

import android.content.Intent;
import android.view.View;

import com.cqian.app.R;
import com.cqian.baselibrary.ioc.OnClick;
import com.cqian.framelibrary.base.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {


    @OnClick({R.id.btChangeSkin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btChangeSkin:
                startActivity(new Intent(this, SkinActivity.class));
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }
}
