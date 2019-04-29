package com.cqian.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Binder;

import java.lang.reflect.Method;

/**
 * Description:
 * Data: 2019/4/24
 *
 * @author: cqian
 */
public class SkinResource {

    private Resources mSkinResources;
    private String mPackageName;

    /**
     * 加载皮肤
     *
     * @param skinPath
     */
    public SkinResource(Context context, String skinPath) {
        try {
            //通过反射实例化AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();
            //通过反射拿到AssetManager中addAssetPath方法
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //指定zip文件路径
            method.invoke(assetManager, skinPath);

            mSkinResources = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
            //获取skinPath包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
            //从指定路径中的drawable拿到pic文件,相当于R.drawable.pic  最后一个参数是存在SD卡上apk的包名
            //将拿到的资源设置到ImageView
            //mImageView.setImageDrawable(getDrawable(drawableId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据名称获取drawable
     *
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        int drawableId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
        if (drawableId == 0) {
            drawableId = mSkinResources.getIdentifier(resName, "mipmap", mPackageName);
        }
        return mSkinResources.getDrawable(drawableId);
    }

    public ColorStateList getColorByName(String colorName) {
        int colorId = mSkinResources.getIdentifier(colorName, "color", mPackageName);
        if (colorId != 0) {
            ColorStateList colorStateList = mSkinResources.getColorStateList(colorId);
            return colorStateList;
        }
        return null;
    }
}
