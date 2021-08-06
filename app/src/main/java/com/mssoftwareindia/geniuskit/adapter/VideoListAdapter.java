package com.mssoftwareindia.geniuskit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.model.VideoListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichLinkView;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;
import io.github.ponnamkarthik.richlinkpreview.ViewListener;


public class VideoListAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<VideoListResponse.Response> mArrayList;
    private LayoutInflater mInflater;

    public VideoListAdapter(Context mContext, ArrayList<VideoListResponse.Response> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderHorizontal(mInflater.inflate(R.layout.custom_video_raw, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderHorizontal holderHome = (HolderHorizontal) holder;
        bindHolderHorizontal(holderHome, position);
    }

    private void bindHolderHorizontal(HolderHorizontal holder, int position) {
        final VideoListResponse.Response current = mArrayList.get(position);
        holder.tv_link.setText(current.getVideo_name());
        holder.richLinkView.setLink(current.getVideo_url(), new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        RichPreview richPreview = new RichPreview(new ResponseListener() {
            @Override
            public void onData(MetaData metaData) {
                Picasso.get().load(metaData.getImageurl());
            }

            @Override
            public void onError(Exception e) {

            }
        });
        holder.richLinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getVideo_url()));
                mContext.startActivity(browserIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getVideo_url()));
                mContext.startActivity(browserIntent);

            }
        });

    }

    class HolderHorizontal extends RecyclerView.ViewHolder {
        TextView tv_link;
        RichLinkView richLinkView;

        public HolderHorizontal(View itemView) {
            super(itemView);
            tv_link = itemView.findViewById(R.id.tv_link);
            richLinkView = itemView.findViewById(R.id.richLinkView);
        }
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


}

