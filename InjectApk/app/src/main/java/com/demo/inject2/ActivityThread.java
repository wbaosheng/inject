package com.demo.inject2;

import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ActivityThread {
    private static final String CLASS_NAME = "android.app.ActivityThread";
    private static Field sInitialApplication_field;
    private static Field sSystemContext_field;

    /**
     * get Application object form ActivityThread object.
     */
    public static Application getInitialApplication() throws Exception {

        Class<?> ActivityThread_class = Class.forName("android.app.ActivityThread");
        Method currentActivityThread_method = ActivityThread_class.getDeclaredMethod("currentActivityThread");
        currentActivityThread_method.setAccessible(true);
        Object object = currentActivityThread_method.invoke(null);

        Method method = ActivityThread_class.getDeclaredMethod("getApplication");
        method.setAccessible(true);
        return (Application) method.invoke(object);
    }

    public static Object getSystemContext() throws Exception {
        Object context = null;

        if (sSystemContext_field == null) {
            Class<?> ActivityThread_class = Class.forName("android.app.ActivityThread");
            sSystemContext_field = ActivityThread_class.getDeclaredField("mSystemContext");
            sSystemContext_field.setAccessible(true);
        }

        context = sSystemContext_field.get(null);

        return context;
    }

}
