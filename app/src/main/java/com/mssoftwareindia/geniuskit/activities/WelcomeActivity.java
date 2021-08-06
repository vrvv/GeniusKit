package com.mssoftwareindia.geniuskit.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.activities.retailer.RetailerLoginActivity;
import com.mssoftwareindia.geniuskit.activities.sales.SalesLoginActivity;
import com.mssoftwareindia.geniuskit.adapter.SliderAdapterExample;
import com.mssoftwareindia.geniuskit.databinding.ActivityWelcomeBinding;
import com.mssoftwareindia.geniuskit.model.MainBannerResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.NetworkUtil;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class WelcomeActivity extends AppCompatActivity {
    public WelcomeActivity context;
    public String TAG = "WelcomeActivity";
    View view;
    public ActivityWelcomeBinding binding;
    SliderAdapterExample adapter;
    public ArrayList<MainBannerResponse.Response> dashboardImageArrayList = new ArrayList<>();
    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
       /* if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        }*/
        getMainBanner();
        binding.cardSaleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SalesLoginActivity.class);
                startActivity(intent);
            }
        });
        binding.cardRetailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RetailerLoginActivity.class);
                startActivity(intent);
            }
        });
        binding.cardAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutusActivity.class);
                startActivity(intent);
            }
        });
        binding.cardContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactusActivity.class);
                startActivity(intent);
            }
        });
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void getMainBanner() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseBody> call = APIClient.getInstance().homeBanner();
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                MainBannerResponse mainBannerResponse = gson.fromJson(responseData, MainBannerResponse.class);
                if (mainBannerResponse.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    dashboardImageArrayList.clear();
                    dashboardImageArrayList.addAll(mainBannerResponse.getData().getResponse());
                }
                adapter = new SliderAdapterExample(context, dashboardImageArrayList);
                binding.imageSlider.setSliderAdapter(adapter);
                binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                binding.imageSlider.setIndicatorSelectedColor(Color.RED);
                binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
                binding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
                binding.imageSlider.startAutoCycle();

            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

}