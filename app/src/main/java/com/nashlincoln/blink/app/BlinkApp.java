package com.nashlincoln.blink.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.nashlincoln.blink.model.Database;
import com.nashlincoln.blink.network.BlinkApi;

/**
 * Created by nash on 10/17/14.
 */
public class BlinkApp extends Application {
    public static final String PREF_NAME = "blink";
    public static final String PREF_API_HOST = "api_host";
    private static BlinkApp sInstance;

    private SharedPreferences mPreferences;

    public static BlinkApp getApp() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        fetchData();
    }

    private void fetchData() {
        if (isConfigured()) {
            Database.getInstance().fetchAttributeTypes();
            Database.getInstance().fetchDeviceTypes();
            Database.getInstance().fetchDevices();
        }
    }

    public boolean isConfigured() {
        return !getHost().equals("");
    }

    public void setHost(String host) {
        mPreferences.edit().putString(PREF_API_HOST, host).apply();
        BlinkApi.createService(host);
        fetchData();
    }

    public String getHost() {
        return mPreferences.getString(PREF_API_HOST, "");
    }
}
