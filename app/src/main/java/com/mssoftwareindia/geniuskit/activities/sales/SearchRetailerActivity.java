package com.mssoftwareindia.geniuskit.activities.sales;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.SearchShopAdapter;
import com.mssoftwareindia.geniuskit.adapter.SearchShopAdapter.onClickAdd;
import com.mssoftwareindia.geniuskit.databinding.ActivitySearchRetailerBinding;
import com.mssoftwareindia.geniuskit.model.PlaceOrder;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SearchRetailerActivity extends AppCompatActivity {
    public SearchRetailerActivity context;
    public String TAG = "SearchRetailerActivity";
    View view;
    public ActivitySearchRetailerBinding binding;
    public SearchShopAdapter adapter;
    public ArrayList<ShopListResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRetailerBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitShopListApi(binding.edtSearch.getText().toString());
            }
        });
    }

    private void hitShopListApi(String name) {
        Call<ResponseBody> call = APIClient.getInstance().searchShop(MyApp.userModel.getData().getResponse().get(0).getRegcode(), name);
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
                adapter = new SearchShopAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);
                adapter.setListner(new onClickAdd() {
                    @Override
                    public void onAddShop(int pos) {
                        hitAssignShopApi(pos);
                    }
                });

            }

            @Override
            public void failure(String responseData) {
            }
        });

    }

    private void hitAssignShopApi(int pos) {
        Call<ResponseBody> call = APIClient.getInstance().assignShop(responseArrayList.get(pos).getShop_id(),
                MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                PlaceOrder commonRes = gson.fromJson(responseData, PlaceOrder.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    _dialog("");

                } else {
                    _dialog1(commonRes.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void _dialog1(String msg) {

        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Assign Shop", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                    }
                }, null);
    }

    private void _dialog(String msg) {

        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Order", "Assign shop success.",
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                        responseArrayList.clear();
                        adapter.notifyDataSetChanged();
                        binding.edtSearch.setText("");

                    }
                }, null);
    }


}