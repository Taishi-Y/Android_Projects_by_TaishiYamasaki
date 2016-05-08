package me.taishi_y.geolocationsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by yamasakitaishi on 2016/05/02.
 */

public class MyApplication extends Application {
    private static Context mContext;

    public void onCreate(){
        super.onCreate();
        this.mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    public static Context getAppContext() {
        return MyApplication.mContext;
    }
}