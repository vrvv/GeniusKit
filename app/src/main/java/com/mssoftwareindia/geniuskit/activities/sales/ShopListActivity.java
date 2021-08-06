package com.mssoftwareindia.geniuskit.activities.sales;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.ShopAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityShopListBinding;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopListActivity extends AppCompatActivity {
    public ShopListActivity context;
    public String TAG = "ShopListActivity";
    View view;
    public ActivityShopListBinding binding;
    public ShopAdapter adapter;
    public ArrayList<ShopListResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopListBinding.inflate(getLayoutInflater());
    view = binding.getRoot();
    setContentView(view);
    context = this;
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
    final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);
    hitShopListApi();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
}

    private void hitShopListApi() {
        Call<ResponseBody> call = APIClient.getInstance().getShop(MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                ShopListResponse aboutus = gson.fromJson(responseData, ShopListResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new ShopAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);

            }

            @Override
            public void failure(String responseData) {
            }
        });

    }
}