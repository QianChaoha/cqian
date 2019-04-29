package com.cqian.framelibrary.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.support.v7.appcompat.R;
import android.widget.Toast;

import com.cqian.baselibrary.base.BaseActivity;
import com.cqian.framelibrary.OnSkinChangeListener;
import com.cqian.framelibrary.skin.SkinManager;
import com.cqian.framelibrary.skin.SkinResource;
import com.cqian.framelibrary.skin.attr.SkinArrSuport;
import com.cqian.framelibrary.skin.attr.SkinAttr;
import com.cqian.framelibrary.skin.attr.SkinView;
import com.cqian.framelibrary.skin.support.SkinAppCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 1.拦截view的创建，自己创建view
 * 2.拿到attrs,解析生成SkinArr集合。每个SkinArr包含属性值resValue和范型SkinType
 * 3.通过创建的view和SkinArr集合创建SkinView
 * 4.通过SkinManager管理每个Activity的所有SkinView,通过Map缓存着activity-->List<SkinView>
 * 注意:1.所有替换的资源必须通过@string或者@color这种引用方式。
 * 2.不支持自定义View(没有拦截自定义View的创建)
 * Data: 2019/3/27
 *
 * @author: cqian
 */
public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory, OnSkinChangeListener {
    private SkinAppCompatViewInflater mAppCompatViewInflater;
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //1.创建View
        View view = createView(parent, name, context, attrs);
        //2.解析属性
        if (view != null) {
            // colorAccent --> skinType(textColor,TEXT_COLOR)
            // ic_launcher --> skinType(background,BACKGROUND)
            List<SkinAttr> skinAttrList = SkinArrSuport.getSkinAttrs(context, attrs);
            if (skinAttrList != null && skinAttrList.size() > 0) {
                SkinView skinView = new SkinView(view, skinAttrList);
                //3.统一交给SkinManager管理，形成一个Map.  activity --> List<SkinView>
                managerSkinView(skinView);
            }
        }
        return view;

    }

    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViewList = SkinManager.getInstance().getSkinViews(this);
        if (skinViewList == null) {
            skinViewList = new ArrayList<>();
            SkinManager.getInstance().register(this, skinViewList);
        }
        skinViewList.add(skinView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegister(this);
    }

    @Override
    public void onSkinChanged(SkinResource skinResource) {

    }

    /**
     * 参考系统源码创建一个View
     *
     * @param parent
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createView(View parent, String name, Context context, AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            TypedArray a = obtainStyledAttributes(R.styleable.AppCompatTheme);
            String viewInflaterClassName =
                    a.getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if ((viewInflaterClassName == null)
                    || AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                // Either default class name or set explicitly to null. In both cases
                // create the base inflater (no reflection)
                mAppCompatViewInflater = new SkinAppCompatViewInflater();
            } else {
                try {
                    Class viewInflaterClass = Class.forName(viewInflaterClassName);
                    mAppCompatViewInflater =
                            (SkinAppCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                    .newInstance();
                } catch (Throwable t) {
                    mAppCompatViewInflater = new SkinAppCompatViewInflater();
                }
            }
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                false /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

}
