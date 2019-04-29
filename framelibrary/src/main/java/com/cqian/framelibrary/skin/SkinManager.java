package com.cqian.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;

import com.cqian.framelibrary.OnSkinChangeListener;
import com.cqian.framelibrary.base.BaseSkinActivity;
import com.cqian.framelibrary.skin.attr.SkinView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Data: 2019/4/23
 *
 * @author: cqian
 */
public class SkinManager {
    private static volatile SkinManager skinManagerInstance;
    private static Context mContext;
    private Map<OnSkinChangeListener, List<SkinView>> mSkinViews = new ArrayMap<>();

    private SkinResource mSkinResource;

    public static SkinManager getInstance() {
        if (skinManagerInstance == null) {
            synchronized (SkinManager.class) {
                if (skinManagerInstance == null) {
                    skinManagerInstance = new SkinManager();
                }
            }
        }
        return skinManagerInstance;
    }

    private SkinManager() {
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 加载skinPath路径下的资源,得到SkinResource --> 拿到当前Activity下的所有SkinView -->执行skin方法,最终执行到范型
     * 中skin方法,通过SkinResource将指定路径下的资源对当前控件的属性进行替换
     *
     * @param skinPath
     */
    public void loadSkin(String skinPath) {
        //初始化资源管理  范型SkinType中换肤方法skin(中使用)
        mSkinResource = new SkinResource(mContext, skinPath);
        //改变皮肤
        changeSkin();
    }

    private void changeSkin() {
        Set<OnSkinChangeListener> activitySet = mSkinViews.keySet();
        for (OnSkinChangeListener onSkinChangeListener : activitySet) {
            List<SkinView> skinViews = mSkinViews.get(onSkinChangeListener);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
            onSkinChangeListener.onSkinChanged(mSkinResource);
        }
    }

    /**
     * 恢复默认
     */
    public void restoreDefault() {
        mSkinResource = new SkinResource(mContext, mContext.getPackageResourcePath());
        //改变皮肤
        changeSkin();
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    public void register(OnSkinChangeListener onSkinChangeListener, List<SkinView> skinViewList) {
        mSkinViews.put(onSkinChangeListener, skinViewList);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    public void unRegister(OnSkinChangeListener onSkinChangeListener) {
        mSkinViews.remove(onSkinChangeListener);
    }
}
