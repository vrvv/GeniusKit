package com.mssoftwareindia.geniuskit.activities.sales;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.OrderProductAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityOrderProductListBinding;
import com.mssoftwareindia.geniuskit.model.ProductResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class OrderProductListActivity extends AppCompatActivity {
    public OrderProductListActivity context;
    public String TAG = "OrderProductListActivity";
    View view;
    public ActivityOrderProductListBinding binding;
    public OrderProductAdapter adapter;
    public ArrayList<ProductResponse.Response> responseArrayList = new ArrayList<>();
    public String order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderProductListBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        order_id = getIntent().getStringExtra("order_id");
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);
        hitOrderListApi();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitOrderListApi() {
        Call<ResponseBody> call = APIClient.getInstance().getOrderProduct(order_id);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                ProductResponse aboutus = gson.fromJson(responseData, ProductResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new OrderProductAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }
}