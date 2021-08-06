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
import com.mssoftwareindia.geniuskit.activities.retailer.RetailerProductActivity;
import com.mssoftwareindia.geniuskit.model.DistributorListResponse;

import java.util.ArrayList;


public class DistributorAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DistributorListResponse.Response> mArrayList;
    private LayoutInflater mInflater;

    public DistributorAdapter(Context mContext, ArrayList<DistributorListResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderHorizontal(mInflater.inflate(R.layout.view_distributor, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderHorizontal holderHome = (HolderHorizontal) holder;
        bindHolderHorizontal(holderHome, position);
    }

    private void bindHolderHorizontal(HolderHorizontal holder, int position) {
        final DistributorListResponse.Response current = mArrayList.get(position);
        holder.tv_branch_name.setText(current.getName());
        holder.tv_number.setText(current.getNumber());
        holder.tv_address.setText(current.getDist_address()+"-"+current.getDist_city()+","+current.getDist_state());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RetailerProductActivity.class);
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

