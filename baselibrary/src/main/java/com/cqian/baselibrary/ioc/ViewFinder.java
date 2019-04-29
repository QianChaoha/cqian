package com.cqian.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Description:
 * Data: 2019/3/20
 *
 * @author: cqian
 */
public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        mActivity = activity;
    }

    public ViewFinder(View view) {
        mView = view;
    }

    public View findViewById(int id) {
        return mActivity != null ? mActivity.findViewById(id) : mView.findViewById(id);
    }
}
