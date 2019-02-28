package com.aiprous.deliveryboy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.application.DeliveryBoyApp;
import com.aiprous.deliveryboy.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.txtMobileNumber)
    TextView txtMobileNumber;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtDob)
    TextView txtDob;
    @BindView(R.id.txtVehicleType)
    TextView txtVehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        txtTitle.setText("Profile");

        //Change status bar color
        BaseActivity baseActivity = new BaseActivity();
        baseActivity.changeStatusBarColor(this);

        txtUsername.setText(DeliveryBoyApp.onGetName());
        txtEmail.setText(DeliveryBoyApp.onGetEmail());
        txtMobileNumber.setText(DeliveryBoyApp.onGetMobileNo());
        txtUsername.setText(DeliveryBoyApp.onGetName());
        txtVehicleType.setText(DeliveryBoyApp.onGetVehicleType());
    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressDetail() {
        finish();
    }

}
