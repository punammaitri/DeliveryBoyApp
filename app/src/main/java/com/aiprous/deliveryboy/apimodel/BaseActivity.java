package com.aiprous.deliveryboy.apimodel;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.aiprous.deliveryboy.R;

import java.util.regex.Pattern;


/**
 * Created by Mankesh71 on 20/6/2017.
 */
@SuppressWarnings("ALL")
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private static final boolean DEBUG_ENABLE = true;
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));
    }

    public static void printLog(String tag, String message) {
        if (DEBUG_ENABLE) {
            Log.d(tag, message);
        }
    }

    public void printError(String tag, String message, Exception e) {
        if (DEBUG_ENABLE) {
            Log.e(tag, message, e);
        }
    }

    /* show toast message to user */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //---Function to check network connection---//
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }


    public static void hideKeyboard(Context context, View view) {
        // Check if no view has focus:
        //View view = context.get
        if (view != null) {

            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    /* check email id is valid or not */
    public boolean isValidEmailId(EditText editText) {
        String text = editText.getText().toString().trim();
        if (!Pattern.matches(EMAIL_REGEX, text)) {
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    /*Change Status bar color*/
    public static void changeStatusBarColor(Activity mActivity) {
        //set status bar color
        Window window = mActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
        }
    }


}

