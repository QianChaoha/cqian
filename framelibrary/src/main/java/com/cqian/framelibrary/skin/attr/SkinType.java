package com.cqian.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqian.framelibrary.skin.SkinManager;
import com.cqian.framelibrary.skin.SkinResource;

/**
 * Description:
 * Data: 2019/4/17
 *
 * @author: cqian
 */
public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resValue) {
            ColorStateList color = getResource().getColorByName(resValue);
            if (color != null) {
                TextView textView = (TextView) view;
                textView.setTextColor(color);
            }
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resValue) {
            Drawable drawable = getResource().getDrawableByName(resValue);
            if (drawable != null) {
                ImageView imageView= (ImageView) view;
                imageView.setBackground(drawable);
            }

            ColorStateList color = getResource().getColorByName(resValue);
            if (color != null) {
                ImageView imageView= (ImageView) view;
                imageView.setBackgroundColor(color.getDefaultColor());
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resValue) {
            Drawable drawable = getResource().getDrawableByName(resValue);
            if (drawable != null) {
                ImageView imageView= (ImageView) view;
                imageView.setImageDrawable(drawable);
            }

        }
    };

    private static SkinResource getResource() {
        return SkinManager.getInstance().getSkinResource();
    }

    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public String getResName() {
        return mResName;
    }

    public abstract void skin(View view, String resName);

}
