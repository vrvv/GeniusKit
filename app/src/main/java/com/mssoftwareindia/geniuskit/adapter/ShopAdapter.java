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
import com.mssoftwareindia.geniuskit.activities.sales.AddOrderActivity;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;

import java.util.ArrayList;


public class ShopAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ShopListResponse.Response> mArrayList;
    private LayoutInflater mInflater;

    public ShopAdapter(Context mContext, ArrayList<ShopListResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderHorizontal(mInflater.inflate(R.layout.view_shop, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderHorizontal holderHome = (HolderHorizontal) holder;
        bindHolderHorizontal(holderHome, position);
    }

    private void bindHolderHorizontal(HolderHorizontal holder, int position) {
        final ShopListResponse.Response current = mArrayList.get(position);
        holder.tv_branch_name.setText(current.getShop_name());
        holder.tv_contact_person.setText("Contact Person :"+current.getShop_contact_person());
        holder.tv_number.setText(current.getShop_mobile_number());
        holder.tv_address.setText(current.getShop_city()+"-"+current.getShop_pincode()+","+current.getShop_state());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOrderActivity.class);
                intent.putExtra("shop_obj", mArrayList.get(position));
                mContext.startActivity(intent);
            }
        });


    }

    class HolderHorizontal extends RecyclerView.ViewHolder {
        TextView tv_branch_name, tv_number, tv_address,tv_contact_person;

        public HolderHorizontal(View itemView) {
            super(itemView);
            tv_contact_person = itemView.findViewById(R.id.tv_contact_person);
            tv_branch_name = itemView.findViewById(R.id.tv_branch_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


}

