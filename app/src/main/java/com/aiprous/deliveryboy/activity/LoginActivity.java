package com.aiprous.deliveryboy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiprous.deliveryboy.MainActivity;
import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.application.DeliveryBoyApp;
import com.aiprous.deliveryboy.utils.APIConstant;
import com.aiprous.deliveryboy.utils.BaseActivity;
import com.aiprous.deliveryboy.utils.CustomProgressDialog;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aiprous.deliveryboy.utils.APIConstant.Authorization;
import static com.aiprous.deliveryboy.utils.APIConstant.BEARER;
import static com.aiprous.deliveryboy.utils.APIConstant.GETUSERINFO;
import static com.aiprous.deliveryboy.utils.APIConstant.LOGIN;
import static com.aiprous.deliveryboy.utils.BaseActivity.isNetworkAvailable;
import static com.aiprous.deliveryboy.utils.BaseActivity.isValidEmailId;
import static com.aiprous.deliveryboy.utils.BaseActivity.showToast;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_signup)
    Button btn_signup;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_sign_up_here)
    TextView tv_sign_up_here;
    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.linearLayoutMain)
    NestedScrollView linearLayoutMain;
    private String lEmailMobile;
    private String lPass;
    private Context mContext = this;
    private String getMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //Change status bar color
        BaseActivity baseActivity = new BaseActivity();
        baseActivity.changeStatusBarColor(this);
    }

    @OnClick(R.id.btn_signup)
    public void onClickSignUp() {

        lEmailMobile = edtEmail.getText().toString().trim();
        lPass = edtPassword.getText().toString().trim();

        if (lEmailMobile.length() == 0) {
            showToast(this, getResources().getString(R.string.error_email));
        } else if (lPass.length() == 0) {
            showToast(this, getResources().getString(R.string.error_pass));
        } else {
            if (lEmailMobile.toString().matches("[a-zA-Z ]+")) {
                if (!isValidEmailId(edtEmail)) {
                    showToast(this, "Please enter valid email id");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", lEmailMobile);
                        jsonObject.put("password", lPass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!isNetworkAvailable(this)) {
                        CustomProgressDialog.getInstance().showDialog(mContext, mContext.getResources().getString(R.string.check_your_network), APIConstant.ERROR_TYPE);
                    } else {
                        CustomProgressDialog.getInstance().showDialog(mContext, "", APIConstant.PROGRESS_TYPE);
                        AttemptLogin(jsonObject);
                    }
                }
            } else {
                if (edtEmail.length() <= 9) {
                    showToast(this, "Mobile number must be  10 digit");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", lEmailMobile);
                        jsonObject.put("password", lPass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!isNetworkAvailable(this)) {
                        CustomProgressDialog.getInstance().showDialog(mContext, mContext.getResources().getString(R.string.check_your_network), APIConstant.ERROR_TYPE);
                    } else {
                        CustomProgressDialog.getInstance().showDialog(mContext, "", APIConstant.PROGRESS_TYPE);
                        AttemptLogin(jsonObject);
                    }
                }
            }
        }
    }

    @OnClick(R.id.tv_forgot_password)
    public void onClickPassword() {
        //startActivity(new Intent(this, SetPasswordActivity.class));
        finish();
    }

    private void AttemptLogin(JSONObject jsonObject) {
        AndroidNetworking.post(LOGIN)
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            JsonObject getAllResponse = (JsonObject) new JsonParser().parse(response.toString());
                            JsonObject responseObject = getAllResponse.get("user_data").getAsJsonObject();
                            String status = getAllResponse.get("status").getAsString();

                            if (status.equals("success")) {
                                String id = responseObject.get("seller_id").getAsString();
                                String name = responseObject.get("name").getAsString();
                                String email = responseObject.get("email").getAsString();
                                String mobile = responseObject.get("mobile").getAsString();
                                String vehicle_type = responseObject.get("vehicle_type").getAsString();
                                String vehicle_number = responseObject.get("vehicle_number").getAsString();
                                String api_token = responseObject.get("api_token").getAsString();

                                //to save data
                                DeliveryBoyApp.onSaveLoginDetail(id, api_token, name, mobile, email,vehicle_type);

                                Toast.makeText(mContext, "Login successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .putExtra("email", "" + email));
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                finish();
                            } else {
                                String msg = getAllResponse.get("message").getAsString();
                                Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
                            }
                            CustomProgressDialog.getInstance().dismissDialog();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        CustomProgressDialog.getInstance().dismissDialog();
                        Toast.makeText(LoginActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "onError errorCode : " + error.getErrorCode());
                        Log.e("Error", "onError errorBody : " + error.getErrorBody());
                        Log.e("Error", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }
}
