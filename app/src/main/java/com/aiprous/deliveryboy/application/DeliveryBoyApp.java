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

    public static void onSaveLoginDetail(String id, String authToken, String firstname, String lastname,
                                         String mobile_number, String email, String storeId) {

        SharedPreferences.Editor edt = mSharedPreferences.edit();
        edt.putString("ID", id);
        edt.putString("AUTHTOKEN", authToken);
        edt.putString("FIRSTNAME", firstname);
        edt.putString("LASTNAME", lastname);
        edt.putString("MOBILENO", mobile_number);
        edt.putString("EMAIL", email);
        edt.putString("STOREID", storeId);
        edt.commit();
    }

    public static String onGetId() {
        return mSharedPreferences.getString("ID", "");
    }

    public static String onGetStoreId() {
        return mSharedPreferences.getString("STOREID", "");
    }

    public static String onGetAuthToken() {
        return mSharedPreferences.getString("AUTHTOKEN", "");
    }

    public static String onGetFirstName() {
        return mSharedPreferences.getString("FIRSTNAME", "");
    }

    public static String onGetLastName() {
        return mSharedPreferences.getString("LASTNAME", "");
    }

    public static String onGetMobileNo() {
        return mSharedPreferences.getString("MOBILENO", "");
    }

    public static String onGetEmail() {
        return mSharedPreferences.getString("EMAIL", "");
    }

    public static void onSetDriverStatus(String  driverstatusflag) {

        SharedPreferences.Editor edt = mSharedPreferences.edit();

        edt.putString("DRIVERSATUSFLAG", driverstatusflag);
        edt.commit();
    }
    public static String onGetDriverStatusFlag() {
        return mSharedPreferences.getString("DRIVERSATUSFLAG", "");
    }

    public static void onSaveAutoStart(String  autoStart) {

        SharedPreferences.Editor edt = mSharedPreferences.edit();

        edt.putString("autoStart", autoStart);
        edt.commit();
    }
    public static String onGetAutoStart() {
        return mSharedPreferences.getString("autoStart", "");
    }


    public static void onSaveLatiLong(String latlong) {
        SharedPreferences.Editor lEditor = mSharedPreferences.edit();
        lEditor.putString("LATILONG", latlong);
        lEditor.commit();
    }

    @Nullable
    public static String getLatiLong() {
        return mSharedPreferences.getString("LATILONG", "");
    }

}