package com.aiprous.deliveryboy.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.aiprous.deliveryboy.MainActivity;
import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.apimodel.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_signup)
    Button btn_signup;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_sign_up_here)
    TextView tv_sign_up_here;

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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.tv_forgot_password)
    public void onClickPassword() {
        //startActivity(new Intent(this, SetPasswordActivity.class));
        finish();
    }
}
