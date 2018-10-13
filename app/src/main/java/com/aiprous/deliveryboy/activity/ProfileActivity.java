package com.aiprous.deliveryboy.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.apimodel.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtEdit)
    TextView txtEdit;

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

    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressDetail() {
        finish();
    }

    @OnClick(R.id.txtEdit)
    public void onViewClicked() {
        startActivity(new Intent(this, EditProfileActivity.class));
    }
}
