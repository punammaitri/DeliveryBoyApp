package com.aiprous.deliveryboy.login;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by v.jadhav on 6/3/2017.
 */
@SuppressWarnings("ALL")
public interface LoginActivityService {
    @POST("loginDriver")
    @FormUrlEncoded
    Call<LoginActivityModel> getLogin(@Field("UID") String UID,
                                      @Field("password") String password,
                                      @Field("device_token") String device_token);



}
