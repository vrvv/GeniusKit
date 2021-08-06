package com.mssoftwareindia.geniuskit.activities.retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.activities.sales.ShopListActivity;
import com.mssoftwareindia.geniuskit.adapter.DistributorAdapter;
import com.mssoftwareindia.geniuskit.adapter.ShopAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityDistributorListBinding;
import com.mssoftwareindia.geniuskit.model.DistributorListResponse;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DistributorListActivity extends AppCompatActivity {
    public DistributorListActivity context;
    public String TAG = "DistributorListActivity";
    View view;
    public ActivityDistributorListBinding binding;
    public DistributorAdapter adapter;
    public ArrayList<DistributorListResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDistributorListBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);
        hitDistributorListApi();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitDistributorListApi() {
        Call<ResponseBody> call = APIClient.getInstance().getDistributor(MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                DistributorListResponse aboutus = gson.fromJson(responseData, DistributorListResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new DistributorAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);

            }

            @Override
            public void failure(String responseData) {
            }
        });

    }
}