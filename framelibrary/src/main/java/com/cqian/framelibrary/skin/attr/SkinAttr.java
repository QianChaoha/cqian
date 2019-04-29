package com.cqian.framelibrary.skin.attr;

import android.view.View;

/**
 * Description:
 * Data: 2019/4/17
 *
 * @author: cqian
 */
public class SkinAttr {
    public SkinAttr(String resValue, SkinType skinType) {
        mResValue = resValue;
        mSkinType = skinType;
    }

    private String mResValue;
    private SkinType mSkinType;


    public void skin(View view) {
        mSkinType.skin(view, mResValue);
    }


}
