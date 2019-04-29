package com.cqian.app;

import android.Manifest;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.cqian.baselibrary.ioc.OnClick;
import com.cqian.baselibrary.ioc.ViewById;
import com.cqian.framelibrary.base.BaseSkinActivity;
import com.cqian.framelibrary.skin.SkinManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseSkinActivity {
    @ViewById(R.id.btChangeSkin)
    Button mBtChangeSkin;
    @ViewById(R.id.btRestoreDefault)
    Button mBtRestoreDefault;


    @OnClick({R.id.btChangeSkin, R.id.btRestoreDefault})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btChangeSkin:
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skin-debug.apk";
                SkinManager.getInstance().loadSkin(path);
                break;
            case R.id.btRestoreDefault:
                SkinManager.getInstance().restoreDefault();
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
        final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(PERMISSIONS[1], PERMISSIONS[2])
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                    }
                });
    }
}
