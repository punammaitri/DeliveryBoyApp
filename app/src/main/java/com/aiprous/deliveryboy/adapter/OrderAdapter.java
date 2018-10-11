package com.aiprous.deliveryboy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.activity.OrderActivity;
import com.aiprous.deliveryboy.activity.OrderDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<OrderActivity.ListModel> mDataArrayList;
    private Context mContext;

    public OrderAdapter(Context mContext, ArrayList<OrderActivity.ListModel> mDataArrayList) {
        this.mContext = mContext;
        this.mDataArrayList = mDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.img_view.setImageResource(mDataArrayList.get(position).getImage());
        holder.txt_order_id.setText(mDataArrayList.get(position).getOrderId());
        holder.txtProcessing.setText(mDataArrayList.get(position).getValue());

        if (holder.txtProcessing.getText().equals("Pending")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_orange));
        } else if (holder.txtProcessing.getText().equals("Processing")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_sky_blue));
        } else if (holder.txtProcessing.getText().equals("Completed")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_green));
        }

        holder.llayout_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetails.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return (mDataArrayList == null) ? 0 : mDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_view)
        ImageView img_view;
        @BindView(R.id.txt_order_id)
        TextView txt_order_id;
        @BindView(R.id.txtProcessing)
        TextView txtProcessing;
        @BindView(R.id.llayout_listing)
        LinearLayout llayout_listing;

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
