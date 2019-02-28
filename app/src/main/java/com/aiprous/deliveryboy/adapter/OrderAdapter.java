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
import com.aiprous.deliveryboy.model.AllOrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<AllOrderModel.Data> mDataArrayList;
    private Context mContext;

    public OrderAdapter(Context mContext, ArrayList<AllOrderModel.Data> mDataArrayList) {
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

        holder.txt_order_id.setText(mDataArrayList.get(position).getEntity_id());
        holder.txtProcessing.setText(mDataArrayList.get(position).getStatus());
        holder.tv_content.setText(mDataArrayList.get(position).getAddress());

        //for date conversion
        StringTokenizer mDate = new StringTokenizer(mDataArrayList.get(position).getCreated_at(), " ");
        String date = mDate.nextToken();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String newFormat = formatter.format(testDate);
        holder.txtDate.setText("" + newFormat);

        if (holder.txtProcessing.getText().equals("pending")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_orange));
            holder.img_view.setImageResource(R.drawable.pending);
        } else if (holder.txtProcessing.getText().equals("processing")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_sky_blue));
            holder.img_view.setImageResource(R.drawable.processing);
        } else if (holder.txtProcessing.getText().equals("completed")) {
            holder.txtProcessing.setTextColor(mContext.getResources().getColor(R.color.color_green));
            holder.img_view.setImageResource(R.drawable.checked);
        }

        holder.llayout_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetails.class)
                        .putExtra("getOrderStatus", "" + holder.txtProcessing.getText()));
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
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.tv_content)
        TextView tv_content;

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
