package com.demo.inject2;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.demo.inject2.EntryClass.TAG;

/**
 * Context��ȡ
 *
 * @author boyliang
 */
public final class ContexHunter {
    private static Context sContext = null;
    public static Context getContext() {

        Log.i(TAG, "getContext1");
        if (sContext == null) {
            synchronized (ContexHunter.class) {
                if (sContext == null) {
                    Log.i(TAG, "getContext2");
                    sContext = searchContextForSystemServer();
                }
            }
        }
        Log.i(TAG, "getContext4");
        return sContext;
    }

    private static Context searchContextForSystemServer() {
        Context result = null;
        try {
            result = (Context) ActivityThread.getInitialApplication();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log.i(TAG, errors.toString());
            return null;
        }
        return result;

    }

    private static Context searchContextForZygoteProcess() {
        Context result = null;

        try {
            Object obj = RuntimeInit.getApplicationObject();
            if (obj != null) {
                obj = ApplicationThread.getActivityThreadObj(obj);
                if (obj != null) {
                    //result = (Application) ActivityThread.getInitialApplication(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
