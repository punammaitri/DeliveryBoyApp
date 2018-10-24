package com.aiprous.deliveryboy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.apimodel.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.edt_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_mobile_no)
    EditText edtMobileNo;
    @BindView(R.id.edt_email_id)
    EditText edtEmailId;
    @BindView(R.id.edt_gender)
    EditText edtGender;
    @BindView(R.id.edt_vehicle)
    EditText edtVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        txtTitle.setText("Edit Profile");

        //Change status bar color
        BaseActivity baseActivity = new BaseActivity();
        baseActivity.changeStatusBarColor(this);

    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressDetail() {
        finish();
    }

    @OnClick(R.id.btn_save_up)
    public void onSave() {

        String emailPattern = "[A-Za-z0-9._-]+@[a-z]+\\.+[a-z]+";

        String lFirst_name=edtFirstName.getText().toString();
        String lLast_name=edtLastName.getText().toString();
        String lMobile_number=edtMobileNo.getText().toString();
        String lEmail_id=edtEmailId.getText().toString();
        String lGender=edtGender.getText().toString();
        String lVehicle=edtVehicle.getText().toString();

        if(lFirst_name.length()==0&&lLast_name.length()==0&&lMobile_number.length()==0&&lEmail_id.length()==0&&lGender.length()==0&&lVehicle.length()==0)
        {
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();
        }
        else if(lFirst_name.length()==0)
        {
            edtFirstName.setError("Please enter first name");
        }else if(lLast_name.length()==0){
            edtLastName.setError("Please enter last name");
        }else if(lMobile_number.length()==0){
            edtMobileNo.setError("Please enter mobile number");
        }else if(lEmail_id.length()==0){
            edtEmailId.setError("Please enter email id");
        } else if(lGender.length()==0){
            edtGender.setError("Please enter gender");
        }else if(lVehicle.length()==0){
            edtVehicle.setError("Please enter vehicle");
        }else if(lFirst_name.length()>3)
        {
            edtFirstName.setError("First name should be grater than 3 character");
        }else if(lFirst_name.length()>3){
            edtLastName.setError("Last name should be grater than 3 character");
        }else if(lMobile_number.length()<=9){
            edtMobileNo.setError("Mobile number must be 10 digit");
        }else if (!lEmail_id.matches(emailPattern)) {
            edtEmailId.setError("Please enter valid email address");
        }else {
            //call API
        }

    }
}
