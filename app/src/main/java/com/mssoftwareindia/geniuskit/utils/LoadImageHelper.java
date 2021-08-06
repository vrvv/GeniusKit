package com.mssoftwareindia.geniuskit.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.imageview.ShapeableImageView;
import com.mssoftwareindia.geniuskit.BuildConfig;
import com.mssoftwareindia.geniuskit.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadImageHelper {

    public static void LoadImage(Context mContext, String imageUrl, ImageView view) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.drawable.ic_launcher_background);

                Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+imageUrl).
                        into(view);
            } catch (Exception ex) {
              //  ex.printStackTrace();
            }
        }
    }

    public static void LoadImage(Context mContext, String imageUrl, ImageView view,
                                         int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                RequestOptions requestOptions = new RequestOptions();

                requestOptions.placeholder(loadingImageResourceId);
                requestOptions.error(failedImageResourceId);
                requestOptions.centerCrop();

                Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+imageUrl)
                        .into(view);
            } catch (Exception ex) {
               // ex.printStackTrace();
            }
        }
    }

    public static void LoadShapeableImageView(Context mContext, String imageUrl, ShapeableImageView view,
                                 int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                RequestOptions requestOptions = new RequestOptions();

                requestOptions.placeholder(loadingImageResourceId);
                requestOptions.error(failedImageResourceId);
                requestOptions.centerCrop();

                Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+imageUrl)
                        .into(view);
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }
    }

    public static void LoadImageCircle(Context mContext, String imageUrl, CircleImageView view,
                                 int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                RequestOptions requestOptions = new RequestOptions();

                requestOptions.placeholder(loadingImageResourceId);
                requestOptions.error(failedImageResourceId);
                requestOptions.centerCrop();

                Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+imageUrl)
                        .into(view);
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }
    }

    public static void LoadFailedImage(Context mContext, String imageUrl, ImageView view,
                                       int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(loadingImageResourceId);
                requestOptions.error(failedImageResourceId);

                Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+"public/storage/"+imageUrl)
                        .into(view);
            } catch (Exception ex) {
              //  ex.printStackTrace();
            }

        }
    }

    public static void LoadImageAsCircle(Context mContext, String imageUrl, ImageView view,
                                         int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(failedImageResourceId);
            requestOptions.placeholder(loadingImageResourceId);
            requestOptions.circleCrop();

            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+"public/storage/"+imageUrl).
                    into(view);

        }
    }

    public static void LoadGroupImageAsWithoutPlaceholderCircle(Context mContext, String imageUrl, ImageView view) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.ic_launcher);
            requestOptions.circleCrop();

            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(imageUrl).
                    into(view);

        }
    }

    public static void WithoutCacheLoadImageAsCircle(Context mContext, String imageUrl, ImageView view,
                                                     int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {


            Glide.with(mContext).load(BuildConfig.API_URL+imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(failedImageResourceId))
                    .into(view);


        }
    }
    public static void WithoutCacheLoadImage(Context mContext, String imageUrl, ImageView view,
                                                     int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {


            Glide.with(mContext).load(BuildConfig.API_URL+imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(failedImageResourceId))
                    .into(view);


        }
    }
    //When URL doesn't change, however, image changes
    public static void LoadFirstTimeProfileImage(Context mContext, String imageUrl, ImageView view,
                                                 int failedImageResourceId, int loadingImageResourceId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.ic_launcher);
            requestOptions.circleCrop();
            requestOptions.signature(new ObjectKey(System.currentTimeMillis()));

            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(BuildConfig.API_URL+imageUrl).
                    into(view);
        }
    }

}