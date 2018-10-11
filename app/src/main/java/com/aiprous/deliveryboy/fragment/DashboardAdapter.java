package com.aiprous.deliveryboy.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private ArrayList<DashboardFragment.ListModel> mSubListModelArray;
    private Context mContext;
    private PopupWindow window;

    public DashboardAdapter(Context mContext, ArrayList<DashboardFragment.ListModel> mSubListModel) {
        this.mContext = mContext;
        this.mSubListModelArray = mSubListModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dashboard_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.img_pending_order.setImageResource(mSubListModelArray.get(position).getImage());
        holder.txtOrder.setText(mSubListModelArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return (mSubListModelArray == null) ? 0 : mSubListModelArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_pending_order)
        ImageView img_pending_order;
        @BindView(R.id.txtOrder)
        TextView txtOrder;

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
