package com.aiprous.deliveryboy.apimodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aiprous.deliveryboy.application.DeliveryBoyApp;

public class NetworkConnectivity {

    private static Context mContext = DeliveryBoyApp.getContext();
    private static ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

    public static boolean isOnline() {

        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}