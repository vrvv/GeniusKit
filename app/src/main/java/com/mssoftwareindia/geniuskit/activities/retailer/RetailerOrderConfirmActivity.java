package com.mssoftwareindia.geniuskit.activities.retailer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.ConfirmOrderAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityRetailerOrderConfirmBinding;
import com.mssoftwareindia.geniuskit.model.DistributorListResponse;
import com.mssoftwareindia.geniuskit.model.PlaceOrder;
import com.mssoftwareindia.geniuskit.model.ProductResponse;
import com.mssoftwareindia.geniuskit.model.SubmitOrder;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class RetailerOrderConfirmActivity extends AppCompatActivity {
    public RetailerOrderConfirmActivity context;
    public String TAG = "RetailerOrderConfirmActivity";
    View view;
    public ActivityRetailerOrderConfirmBinding binding;
    public ConfirmOrderAdapter adapter;
    public ArrayList<ProductResponse.Response> responseArrayList = new ArrayList<>();
    public ArrayList<ProductResponse.Response> tempResponseArrayList = new ArrayList<>();
    public SubmitOrder submitOrder;
    public ArrayList<SubmitOrder> submitOrderArrayList = new ArrayList<>();
    public String place_order_json = "";
    public String shop_id = "";
    float totalDp = 0;
    float totalMrp = 0;
    public DistributorListResponse.Response shopObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerOrderConfirmBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        tempResponseArrayList = (ArrayList) getIntent().getParcelableArrayListExtra("proObj");
        shopObj = (DistributorListResponse.Response) getIntent().getSerializableExtra("shop_obj");
        binding.tvBranchName.setText(shopObj.getName() + "(" + shopObj.getNumber() + ")");
        binding.tvAddress.setText(shopObj.getDist_address() + "-" + shopObj.getDist_city() + "," + shopObj.getDist_state());

        for (ProductResponse.Response obj : tempResponseArrayList) {
            if (obj.getQty() > 0) {
                responseArrayList.add(obj);
                submitOrder = new SubmitOrder();
                submitOrder.setItem_id(obj.getProd_id());
                submitOrder.setQty(obj.getQty() + "");
                submitOrderArrayList.add(submitOrder);
                totalDp += Float.parseFloat(obj.getProd_dp()) * obj.getQty();
                totalMrp += Float.parseFloat(obj.getProd_pay()) * obj.getQty();
            }
        }
        for (ProductResponse.SubProduct obj : MyApp.subProductArrayList) {
            if (obj.getQty() > 0) {

                ProductResponse.Response rObj= new ProductResponse.Response();
                rObj.setProd_id(obj.getProd_id());
                rObj.setProd_name(obj.getProd_name());
                rObj.setProd_umo_id(obj.getProd_umo_id());
                rObj.setProd_dp(obj.getProd_dp());
                rObj.setProd_price(obj.getProd_price());
                rObj.setProd_per(obj.getProd_per());
                rObj.setProd_dis(obj.getProd_dis());
                rObj.setProd_pay(obj.getProd_pay());
                rObj.setMain_image(obj.getMain_image());
                rObj.setProd_desc(obj.getProd_desc());
                rObj.setQty(obj.getQty());

                responseArrayList.add(rObj);
                submitOrder = new SubmitOrder();
                submitOrder.setItem_id(obj.getProd_id());
                submitOrder.setQty(obj.getQty() + "");
                submitOrderArrayList.add(submitOrder);
                totalDp += Float.parseFloat(obj.getProd_dp()) * obj.getQty();
                totalMrp += Float.parseFloat(obj.getProd_pay()) * obj.getQty();
            }
        }
        binding.tvTotalDp.setText("Total Dp: " + totalDp);
        binding.tvTotalMrp.setText("Total Pay: " + totalMrp);

        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);
        adapter = new ConfirmOrderAdapter(context, responseArrayList);
        binding.recyclerShop.setAdapter(adapter);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitOrderArrayList.size() > 0) {
                    hitOrderApi();
                } else {
                    ToastUtils.show(context, "Please add product");
                }

            }
        });

    }

    private void hitOrderApi() {
        Gson gson = new Gson();
        place_order_json = gson.toJson(submitOrderArrayList);
        Call<ResponseBody> call = APIClient.getInstance().salesCreateOrderRetailer(
                shopObj.getDistid(), place_order_json, MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                PlaceOrder commonRes = gson.fromJson(responseData, PlaceOrder.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    MyApp.subProductArrayList.clear();
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
        customDialog.showDialogTwoButton(context, "Order", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                    }
                }, null);
    }

    private void _dialog(String msg) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Order", "Confirm Order Sucessfully.",
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                        Intent intent = new Intent(context, RetailerDashboardActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                }, null);
    }

}