package com.aiprous.deliveryboy.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.aiprous.deliveryboy.R;

public class DeliveryBoyApp extends MultiDexApplication {

    private static Context mContext;
    private static SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

    }

    public static Context getContext() {
        return mContext;
    }

    public static void onSaveLoginDetail(String id, String authToken, String name,
                                         String mobile_number, String email,String vehicle_type) {

        SharedPreferences.Editor edt = mSharedPreferences.edit();
        edt.putString("ID", id);
        edt.putString("AUTHTOKEN", authToken);
        edt.putString("NAME", name);
        edt.putString("MOBILENO", mobile_number);
        edt.putString("EMAIL", email);
        edt.putString("VEHICLE_TYPE", vehicle_type);
        edt.commit();
    }

    public static String onGetId() {
        return mSharedPreferences.getString("ID", "");
    }

    public static String onGetAuthToken() {
        return mSharedPreferences.getString("AUTHTOKEN", "");
    }

    public static String onGetName() {
        return mSharedPreferences.getString("NAME", "");
    }

    public static String onGetMobileNo() {
        return mSharedPreferences.getString("MOBILENO", "");
    }

    public static String onGetEmail() {
        return mSharedPreferences.getString("EMAIL", "");
    }

    public static String onGetVehicleType() {
        return mSharedPreferences.getString("VEHICLE_TYPE", "");
    }

    public static void onSaveLatiLong(String lattitude,String longitude) {
        SharedPreferences.Editor lEditor = mSharedPreferences.edit();
        lEditor.putString("LAT", lattitude);
        lEditor.putString("LONG", longitude);
        lEditor.commit();
    }

    @Nullable
    public static String getLattitude() {
        return mSharedPreferences.getString("LAT", "");
    }

    public static String getLongitude() {
        return mSharedPreferences.getString("LONG", "");
    }

}