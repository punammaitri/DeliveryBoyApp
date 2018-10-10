package com.aiprous.deliveryboy.login;

import android.content.Context;

import com.aiprous.deliveryboy.apimodel.APICallback;
import com.aiprous.deliveryboy.apimodel.APIServiceFactory;
import com.aiprous.deliveryboy.apimodel.BaseServiceResponseModel;
import com.aiprous.deliveryboy.apimodel.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class LoginActivityServiceProvider {
    private final LoginActivityService loginActivityService;

    public LoginActivityServiceProvider(Context context) {
        loginActivityService = APIServiceFactory.createService(LoginActivityService.class, context);
    }

    public void getLogin(String Uid, String Password,String devicetoken,final APICallback apiCallback) {
        Call<LoginActivityModel> call = null;
        call = loginActivityService.getLogin(Uid, Password,devicetoken);
        String url = call.request().url().toString();

        call.enqueue(new Callback<LoginActivityModel>() {
            @Override
            public void onResponse(Call<LoginActivityModel> call, Response<LoginActivityModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                    apiCallback.onSuccess(response.body());
                } else if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 404) {
                    apiCallback.onSuccess(response.body());
                } else {
                    BaseServiceResponseModel model = ErrorUtils.parseError(response);
                    apiCallback.onFailure(model, response.errorBody());
                    // apiCallback.onFailure(response.body(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LoginActivityModel> call, Throwable t) {
                apiCallback.onFailure(null, null);
            }
        });
    }
}
