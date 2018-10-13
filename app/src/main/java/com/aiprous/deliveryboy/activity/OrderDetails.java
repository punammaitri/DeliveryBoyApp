package com.aiprous.deliveryboy.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.adapter.OrderDetailsAdapter;
import com.aiprous.deliveryboy.apimodel.BaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetails extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.rec_sellerInvoice)
    RecyclerView rec_sellerInvoice;
    @BindView(R.id.txtCompleted)
    TextView txtCompleted;
    @BindView(R.id.btnDelivered)
    Button btnDelivered;
    @BindView(R.id.imgview)
    ImageView imgview;


    ArrayList<OrderDetails.ListModel> mlistModelsArray = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    private String getOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        //Change status bar color
        BaseActivity baseActivity = new BaseActivity();
        baseActivity.changeStatusBarColor(this);

        txtTitle.setText("Order Details");

        if (getIntent() != null && getIntent().hasExtra("getOrderStatus")) {
            getOrderStatus = getIntent().getStringExtra("getOrderStatus");
            if (getOrderStatus.equals("Pending")) {
                txtCompleted.setText("Pending");
                txtCompleted.setTextColor(getResources().getColor(R.color.color_orange));
                imgview.setImageResource(R.drawable.pending);
                btnDelivered.setVisibility(View.VISIBLE);

            } else if (getOrderStatus.equals("Processing")) {
                txtCompleted.setText("Processing");
                txtCompleted.setTextColor(getResources().getColor(R.color.color_sky_blue));
                imgview.setImageResource(R.drawable.processing);
                btnDelivered.setVisibility(View.VISIBLE);
            } else if (getOrderStatus.equals("Completed")) {
                txtCompleted.setText("Completed");
                txtCompleted.setTextColor(getResources().getColor(R.color.color_green));
                imgview.setImageResource(R.drawable.checked);
                btnDelivered.setVisibility(View.GONE);
            }
        }

        //add static data into List array list
        mlistModelsArray.add(new OrderDetails.ListModel(R.drawable.ic_menu_manage, "Horlicks Lite Badam Jar 450 gm"));
        mlistModelsArray.add(new OrderDetails.ListModel(R.drawable.ic_menu_manage, "Horlicks Lite Badam Jar 450 gm"));

        layoutManager = new LinearLayoutManager(this);
        rec_sellerInvoice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_sellerInvoice.setHasFixedSize(true);
        rec_sellerInvoice.setAdapter(new OrderDetailsAdapter(this, mlistModelsArray));
    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressSDescription() {
        finish();
    }

    public class ListModel {
        int image;
        String orderId;

        public ListModel(int image, String orderId) {
            this.image = image;
            this.orderId = orderId;
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

    }
}