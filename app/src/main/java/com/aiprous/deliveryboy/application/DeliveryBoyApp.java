package com.aiprous.deliveryboy.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
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

    public static void onSaveLoginDetail(String driver_id, String firstname, String lastname,  String mobile_number) {

        SharedPreferences.Editor edt = mSharedPreferences.edit();

        edt.putString("DRIVERID", driver_id);
        edt.putString("FIRSTNAME", firstname);
        edt.putString("LASTNAME", lastname);
        edt.putString("MOBILENO", mobile_number);
        edt.commit();
    }

    public static String onGetDriverId() {
        return mSharedPreferences.getString("DRIVERID", "");
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

}