package com.aiprous.medicobox.activity;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Window;
import android.view.WindowManager;

import com.aiprous.medicobox.R;
import com.aiprous.medicobox.adapter.OrderDetailsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderPlacedActivity extends AppCompatActivity {

    @BindView(R.id.searchview_medicine)
    SearchView searchview_medicine;
    @BindView(R.id.rc_order_placed)
    RecyclerView rc_order_placed;
    private Context mContext=this;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<OrderDetailsActivity.ProductModel> mproductArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        searchview_medicine.setFocusable(false);
        //set status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mproductArrayList.add(new OrderDetailsActivity.ProductModel("Horicks Lite Badam Jar 450 gm","235","box of 450 gm powder","200"));
        mproductArrayList.add(new OrderDetailsActivity.ProductModel("Horicks Lite Badam Jar 450 gm","235","box of 450 gm powder","200"));
        mproductArrayList.add(new OrderDetailsActivity.ProductModel("Horicks Lite Badam Jar 450 gm","235","box of 450 gm powder","200"));


        layoutManager = new LinearLayoutManager(mContext);
        rc_order_placed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rc_order_placed.setHasFixedSize(true);
        rc_order_placed.setAdapter(new OrderDetailsAdapter(mContext, mproductArrayList));

    }
}