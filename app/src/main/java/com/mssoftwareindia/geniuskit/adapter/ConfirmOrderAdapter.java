package com.mssoftwareindia.geniuskit.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.model.ProductResponse;

import java.util.ArrayList;
import java.util.List;


public class ConfirmOrderAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ProductResponse.Response> mArrayList = new ArrayList<>();
    private static final int VIEW_TYPE_HORIZONTAL = 0;
    private LayoutInflater mInflater;
    public onClickAdd onClickAddlisner;

    public ConfirmOrderAdapter(Context mContext,
                               List<ProductResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList.addAll(mArrayList);
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListner(onClickAdd listner) {
        this.onClickAddlisner = listner;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_HORIZONTAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderHorizontal(mInflater.inflate(R.layout.view_confirm_order, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderHorizontal holderHome = (HolderHorizontal) holder;
        bindHolderHorizontal(holderHome, position);
    }

    private void bindHolderHorizontal(HolderHorizontal holder, int position) {
        final ProductResponse.Response current = mArrayList.get(position);
        //count = 1;
        holder.tv_pv.setText(current.getProd_per() + "%");
        holder.tv_product_name.setText((current.getProd_name()));
        try {
            float price = 0, offerPrice = 0;
            price = Float.parseFloat((current.getProd_pay())) * current.getQty();
            offerPrice = Float.parseFloat((current.getProd_dp())) * current.getQty();
            holder.tv_price.setText("₹" + price + "");
            holder.tv_offer_price.setText(Html.fromHtml("<s>" + offerPrice + "</s>"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_product_bv.setText(current.getQty() + " * " + current.getProd_pay());
        //  holder.tv_desc.setText((current.getDisc()));
        try {
            // Picasso.with(context).load(categoryDetails.getCategoryImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.img_categories);
            RequestOptions ro = new RequestOptions();
            ro.placeholder(R.drawable.genius_logo);
            ro.error(R.drawable.genius_logo);

            Glide.with(mContext)
                    .applyDefaultRequestOptions(ro)
                    .load(current.getMain_image())
                    .into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mArrayList.get(position).getQty() > 0) {
        } else {
        }


    }


    class HolderHorizontal extends RecyclerView.ViewHolder {

        TextView tv_product_name, tv_product_bv, tv_price, tv_offer_price, tv_bv, tv_pv, tv_offer;
        public ImageView image;

        public HolderHorizontal(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_bv = itemView.findViewById(R.id.tv_product_bv);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_offer_price = itemView.findViewById(R.id.tv_offer_price);
            tv_bv = itemView.findViewById(R.id.tv_bv);
            tv_pv = itemView.findViewById(R.id.tv_pv);
        }
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public interface onClickAdd {
        public void onUpdateQty(List<ProductResponse.Response> mArrayList, int pos);
    }


}

