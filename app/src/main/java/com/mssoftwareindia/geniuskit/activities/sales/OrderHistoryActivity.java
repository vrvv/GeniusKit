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
import com.mssoftwareindia.geniuskit.adapter.OrderHistoryAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityOrderHistoryBinding;
import com.mssoftwareindia.geniuskit.model.OrderHistoryResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class OrderHistoryActivity extends AppCompatActivity {
    public OrderHistoryActivity context;
    public String TAG = "OrderHistoryActivity";
    View view;
    public ActivityOrderHistoryBinding binding;
    public OrderHistoryAdapter adapter;
    public ArrayList<OrderHistoryResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.recyclerOrder.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerOrder.setLayoutAnimation(controller);
        hitOrderHistoryApi();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitOrderHistoryApi() {
        Call<ResponseBody> call = APIClient.getInstance().orderHistory(MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                OrderHistoryResponse aboutus = gson.fromJson(responseData, OrderHistoryResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new OrderHistoryAdapter(context, responseArrayList);
                binding.recyclerOrder.setAdapter(adapter);

            }

            @Override
            public void failure(String responseData) {
            }
        });
    }
}