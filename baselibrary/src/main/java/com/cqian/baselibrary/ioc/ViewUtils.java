package com.cqian.baselibrary.ioc;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description:
 * Data: 2019/3/20
 *
 * @author: cqian
 */
public class ViewUtils {
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);

    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);

    }

    public static void inject(ViewFinder finder, Object object) {
        injectField(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object 谁的成员变量需要通过注解设置值
     */
    private static void injectField(ViewFinder finder, Object object) {
        //====================1.获取类里的所有的属性================
        Class<?> clazz = object.getClass();
        //getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
        //getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] fields = clazz.getDeclaredFields();
        //====================2.找到ViewById注解,并获取注解的值====================
        for (int i = 0; i < fields.length; i++) {
            ViewById viewById = fields[i].getAnnotation(ViewById.class);
            if (viewById != null) {
                //拿到注解ViewById里id的值
                int value = viewById.value();
                //====================3.findViewById找到View====================
                View view = finder.findViewById(value);
                //能够注入搜索修饰符(默认private不能被注入)
                fields[i].setAccessible(true);
                //====================4.动态注入找到的View====================
                try {
                    fields[i].set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注入单击事件
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //====================1.获取类里的所有的方法================
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //====================2.找到OnClick注解,并获取注解的值====================
        for (int i = 0; i < methods.length; i++) {
            OnClick onClick = methods[i].getAnnotation(OnClick.class);
            if (onClick != null) {
                //拿到注解ViewById里id的值
                int[] value = onClick.value();
                if (value == null) {
                    throw new RuntimeException("OnClick注解未设置value");
                }
                //====================3.findViewById找到View后设置点击事件====================
                for (int j = 0; j < value.length; j++) {
                    View view = finder.findViewById(value[j]);
                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(methods[i], object));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;

        public DeclaredOnClickListener(Method method, Object object) {

            mMethod = method;
            mObject = object;
        }

        @Override
        public void onClick(View v) {
            try {
                mMethod.invoke(mObject, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

}
