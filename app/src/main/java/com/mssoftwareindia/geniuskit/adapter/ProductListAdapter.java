package com.mssoftwareindia.geniuskit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.model.ProductResponse;

import java.util.ArrayList;
import java.util.List;


public class ProductListAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public List<ProductResponse.Response> mArrayList = new ArrayList<>();
    private static final int VIEW_TYPE_HORIZONTAL = 0;
    private LayoutInflater mInflater;
    public onClickAdd onClickAddlisner;
    public OnItemClick onItemClick;

    public ProductListAdapter(Context mContext,
                              List<ProductResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList.addAll(mArrayList);
        mInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClick {
        void onOtherVersion(List<ProductResponse.SubProduct> otherVersions, HolderHorizontal holder, View rel_product, int position);
    }

    public void setOnItemApprochClickListener(OnItemClick onItemClickListener) {
        this.onItemClick = onItemClickListener;
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
        return new HolderHorizontal(mInflater.inflate(R.layout.view_product_raw, parent, false));

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

        holder.tv_price.setText("₹" + current.getProd_pay());
        holder.tv_offer_price.setText(Html.fromHtml("<s>" + current.getProd_dp() + "</s>"));
        // holder.tv_product_bv.setText(current.getProd_umo_id());
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
            holder.tv_add.setVisibility(View.GONE);
            holder.lin_added.setVisibility(View.VISIBLE);
            holder.tv_count.setText(mArrayList.get(position).getQty() + "");
            Log.i("QTY", "==>" + mArrayList.get(position).getQty());
        } else {
            holder.tv_add.setVisibility(View.VISIBLE);
            holder.lin_added.setVisibility(View.GONE);
        }
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // count++;
                mArrayList.get(position).setQty(current.getQty() + 1);
                holder.tv_count.setText(mArrayList.get(position).getQty() + "");
                double price = Double.parseDouble(current.getProd_pay());
                holder.tv_price.setText("\u20B9" + (mArrayList.get(position).getQty() * price) + "");
                onClickAddlisner.onUpdateQty(mArrayList, position);
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (mArrayList.get(position).getQty() < 2) {
                    holder.tv_add.setVisibility(View.VISIBLE);
                    holder.lin_added.setVisibility(View.GONE);
                    mArrayList.get(position).setQty(0);
                    holder.tv_count.setText("1");
                } else {
                    mArrayList.get(position).setQty(current.getQty() - 1);
                    holder.tv_count.setText(mArrayList.get(position).getQty() + "");
                    double price = Double.parseDouble(current.getProd_pay());
                    holder.tv_price.setText("\u20B9" + (mArrayList.get(position).getQty() * price) + "");
                }

                onClickAddlisner.onUpdateQty(mArrayList, position);
            }
        });


        if (current.getSubProductList().size() > 1) {
            holder.rel_product.setBackgroundResource(R.drawable.rounded_yellow_bg);
            holder.tv_product_bv.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.img_dropdown.setVisibility(View.VISIBLE);
            holder.lin_add_btn.setVisibility(View.GONE);
            holder.tv_product_bv.setText(current.getSubProductList().get(0).getProd_umo_id());
        } else {
            holder.tv_product_bv.setTextColor(Color.parseColor("#A4A4A4"));
            holder.rel_product.setBackgroundResource(R.drawable.rounded_orange_border);
          //  holder.lin_add_btn.setVisibility(View.VISIBLE);
            if (current.getOut_of_stock().equals("1")) {
                holder.tv_out_stock.setVisibility(View.VISIBLE);
                holder.lin_add_btn.setVisibility(View.GONE);
            } else {
                holder.tv_out_stock.setVisibility(View.GONE);
                holder.lin_add_btn.setVisibility(View.VISIBLE);
            }
            holder.img_dropdown.setVisibility(View.GONE);
            holder.tv_product_bv.setText(current.getProd_umo_id());
        }

        holder.rel_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onOtherVersion(current.getSubProductList(), holder, holder.rel_product, position);
            }
        });
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mArrayList.get(position).getQty() == 0) {
                    mArrayList.get(position).setQty(1);
                    holder.tv_add.setVisibility(View.GONE);
                    holder.lin_added.setVisibility(View.VISIBLE);
                }
                onClickAddlisner.onUpdateQty(mArrayList, position);

            }
        });

    }


    public class HolderHorizontal extends RecyclerView.ViewHolder {

        TextView tv_product_name, tv_product_bv, tv_price, tv_offer_price, tv_bv, tv_pv, tv_offer, tv_add, tv_count, tv_out_stock;
        public LinearLayout lin_added;
        public ImageButton imgMinus, imgPlus;
        public ImageView image, img_dropdown;
        public RelativeLayout rel_product;
        public LinearLayout lin_add_btn;

        public HolderHorizontal(View itemView) {
            super(itemView);
            rel_product = itemView.findViewById(R.id.rel_product);
            lin_added = itemView.findViewById(R.id.lin_added);
            tv_out_stock = itemView.findViewById(R.id.tv_out_stock);
            image = itemView.findViewById(R.id.image);
            img_dropdown = itemView.findViewById(R.id.img_dropdown);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_bv = itemView.findViewById(R.id.tv_product_bv);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_offer_price = itemView.findViewById(R.id.tv_offer_price);
            tv_bv = itemView.findViewById(R.id.tv_bv);
            tv_add = itemView.findViewById(R.id.tv_add);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_pv = itemView.findViewById(R.id.tv_pv);
            lin_add_btn = itemView.findViewById(R.id.lin_add_btn);
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

