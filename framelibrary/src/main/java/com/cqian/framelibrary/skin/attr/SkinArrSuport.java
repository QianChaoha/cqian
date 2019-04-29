package com.cqian.framelibrary.skin.attr;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data: 2019/4/17
 *
 * @author: cqian
 */
public class SkinArrSuport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrList = new ArrayList<SkinAttr>();
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            SkinType skinType = getSkinType(attributeName);


            if (skinType != null) {

                String resValue = getResValue(context, attributeValue);
                System.out.println("attributeName " + attributeName + "  attributeValue " + attributeValue + " resValue " + resValue);
                //attributeName textColor   attributeValue @2131034150   resValue  colorAccent
                //attributeName background  attributeValue @2131427328   resValue  ic_launcher

                if (TextUtils.isEmpty(resValue)) {
                    continue;
                }

                //resValue --> skinType(mResName,name)
                //colorAccent --> skinType(textColor,TEXT_COLOR)
                SkinAttr skinAttr = new SkinAttr(resValue, skinType);
                skinAttrList.add(skinAttr);
            }
        }
        return skinAttrList;
    }

    /**
     * 获取资源名称对应的值,资源名称只会是SkinType中申明的其中之一
     *
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResValue(Context context, String attributeValue) {
        if (attributeValue != null && attributeValue.startsWith("@")) {
            attributeValue = attributeValue.replace("@", "");
            return context.getResources().getResourceEntryName(Integer.valueOf(attributeValue));
        }
        return null;
    }

    private static SkinType getSkinType(String attributeName) {
        SkinType[] skinTypes = SkinType.values();
        if (skinTypes != null && skinTypes.length > 0) {
            for (SkinType skinType : skinTypes) {
                if (!TextUtils.isEmpty(attributeName) && attributeName.equals(skinType.getResName())) {
                    return skinType;
                }
            }
        }
        return null;
    }
}
