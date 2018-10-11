package com.aiprous.deliveryboy.activity;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.adapter.OrderAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.rc_medicine_list)
    RecyclerView rc_medicine_list;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    ArrayList<OrderActivity.ListModel> mlistModelsArray = new ArrayList<>();
    private Context mContext = this;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        txtTitle.setText("Orders");

        //set status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        //add static data into array list
        mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));
        mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));
        mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));

        layoutManager = new LinearLayoutManager(mContext);
        rc_medicine_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc_medicine_list.setHasFixedSize(true);
        rc_medicine_list.setAdapter(new OrderAdapter(mContext, mlistModelsArray));
    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressList() {
        finish();
    }

    public class ListModel {
        int image;
        String orderId;
        String value;

        public ListModel(int image, String orderId, String value) {
            this.image = image;
            this.orderId = orderId;
            this.value = value;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
