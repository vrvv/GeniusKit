package com.mssoftwareindia.geniuskit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.activities.sales.OrderProductListActivity;
import com.mssoftwareindia.geniuskit.model.OrderHistoryResponse;

import java.util.ArrayList;


public class OrderHistoryAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<OrderHistoryResponse.Response> mArrayList;
    private LayoutInflater mInflater;

    public OrderHistoryAdapter(Context mContext, ArrayList<OrderHistoryResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderHorizontal(mInflater.inflate(R.layout.view_order, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderHorizontal holderHome = (HolderHorizontal) holder;
        bindHolderHorizontal(holderHome, position);
    }

    private void bindHolderHorizontal(HolderHorizontal holder, int position) {
        final OrderHistoryResponse.Response current = mArrayList.get(position);
        holder.tv_orderid.setText(current.getOrders_code());
        holder.tv_status.setText(current.getOrder_status());
        holder.tv_total_amount.setText(current.getOrder_total_payment());
        holder.tv_date.setText(current.getOrders_date());
        holder.tvShopName.setText(current.getShop_name()+"("+current.getShop_mobile()+")");
        holder.tvCity.setText(current.getShop_city() + "-" + current.getShop_pincode());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderProductListActivity.class);
                intent.putExtra("order_id", mArrayList.get(position).getOrders_id());
                mContext.startActivity(intent);
            }
        });

    }

    class HolderHorizontal extends RecyclerView.ViewHolder {
        public TextView tv_orderid, tv_status, tv_total_amount, tv_date, tvShopName, tvCity;


        public HolderHorizontal(View itemView) {
            super(itemView);
            tv_orderid = itemView.findViewById(R.id.tv_orderid);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_total_amount = itemView.findViewById(R.id.tv_total_amount);
            tv_date = itemView.findViewById(R.id.tv_date);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


}

