package com.aiprous.deliveryboy.utils;


public class APIConstant {

    //API BASE URL
    public static String LOGIN = "http://user8.itsindev.com/medibox/API/delivery-boy/login.php";
    public static String GETUSERINFO = "http://user8.itsindev.com/medibox/API/customer_self.php";
    public static String GETALLORDERDETAIL = "http://user8.itsindev.com/medibox/API/delivery-boy/delivery-boy-orders.php";
    public static String DELIVERYBOY_TRACKING = "http://user8.itsindev.com/medibox/API/deliveryboy_tracking.php?order_id=";
    /*public static String GETUSERINFO = "http://user8.itsindev.com/medibox/API/delivery-boy-orders.php";
    public static String GETUSERINFO = "http://user8.itsindev.com/medibox/API/customer_self.php";*/

    //Response Status Code
    public static final int SUCCESS_CODE = 200;
    /*For Sweet alert*/
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int PROGRESS_TYPE = 4;
    public static final String SOME_THING_WENT_WRONG = "Oops! Something went wrong!";
    public static String Authorization = "Authorization";
    public static String BEARER = "Bearer ";
}

