package com.cqian.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Description:
 * Data: 2019/4/17
 *
 * @author: cqian
 */
public class SkinView {
    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrList) {
        mView = view;
        mAttrs = skinAttrList;
    }

    public void skin() {
        for (SkinAttr skinAttr : mAttrs ) {
            skinAttr.skin(mView);
        }
    }
}
