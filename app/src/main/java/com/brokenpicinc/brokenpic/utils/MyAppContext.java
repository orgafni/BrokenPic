package com.brokenpicinc.brokenpic.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by orgaf on 15/03/2017.
 */

public class MyAppContext extends Application {

    private static Context context;
    public void onCreate() {
        super.onCreate();
        MyAppContext.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MyAppContext.context;
    }

}
