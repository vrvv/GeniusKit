package com.mssoftwareindia.geniuskit.activities.sales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.ConfirmOrderAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityOrderConfirmBinding;
import com.mssoftwareindia.geniuskit.model.CheckOtp;
import com.mssoftwareindia.geniuskit.model.ConfirmOtp;
import com.mssoftwareindia.geniuskit.model.PlaceOrder;
import com.mssoftwareindia.geniuskit.model.ProductResponse;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;
import com.mssoftwareindia.geniuskit.model.SubmitOrder;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.ui.OtpView;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class OrderConfirmActivity extends AppCompatActivity {
    public OrderConfirmActivity context;
    public String TAG = "OrderConfirmActivity";
    View view;
    public ActivityOrderConfirmBinding binding;
    public ConfirmOrderAdapter adapter;
    public ArrayList<ProductResponse.Response> responseArrayList = new ArrayList<>();
    public ArrayList<ProductResponse.Response> tempResponseArrayList = new ArrayList<>();
    public SubmitOrder submitOrder;
    public ArrayList<SubmitOrder> submitOrderArrayList = new ArrayList<>();
    public String place_order_json = "";
    public String shop_id = "";
    float totalDp = 0;
    float totalMrp = 0;
    public ShopListResponse.Response shopObj;
    public BottomSheetDialog mBottomSheetVerifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderConfirmBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        tempResponseArrayList = (ArrayList) getIntent().getParcelableArrayListExtra("proObj");
        shopObj = (ShopListResponse.Response) getIntent().getSerializableExtra("shop_obj");
        binding.tvBranchName.setText(shopObj.getShop_name() + "(" + shopObj.getShop_mobile_number() + ")");
        binding.tvAddress.setText(shopObj.getShop_city() + "-" + shopObj.getShop_pincode() + "," + shopObj.getShop_state());

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
                ProductResponse.Response rObj = new ProductResponse.Response();
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
                    hitCheckOtp();

                } else {
                    ToastUtils.show(context, "Please add product");
                }

            }
        });

    }

    private void hitCheckOtp() {
        Call<ResponseBody> call = APIClient.getInstance().salesCheckOtp(
                MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                CheckOtp commonRes = gson.fromJson(responseData, CheckOtp.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    if (commonRes.getData().getResponse().get(0).getOtpstatus().equals("1")) {
                        hitCreateOrderOtp();
                    } else {
                        hitOrderApi();

                    }
                    //  _dialog("");

                } else {
                    _dialog1(commonRes.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void hitCreateOrderOtp() {
        Call<ResponseBody> call = APIClient.getInstance().crateSalesOrderOtp(
                shopObj.getShop_id(), MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                ConfirmOtp commonRes = gson.fromJson(responseData, ConfirmOtp.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    bottomOTP(commonRes.getData().getResponse().get(0).getOtp());
                    mBottomSheetVerifyOTP.show();

                    //  _dialog("");

                } else {
                    _dialog1(commonRes.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void bottomOTP(String otp) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sales_verify_otp, null);
        mBottomSheetVerifyOTP = new BottomSheetDialog(context);
        mBottomSheetVerifyOTP.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetVerifyOTP.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        OtpView mOtpView = view.findViewById(R.id.otp_view);
        TextView tvContinue = view.findViewById(R.id.tvContinue);
        TextView tvResend = view.findViewById(R.id.tvResend);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitCreateOrderOtp();
                mBottomSheetVerifyOTP.dismiss();
            }
        });
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpInput = mOtpView.getOTP().toString().trim();
                if (otpInput.isEmpty()) {
                    ToastUtils.show(context, "Please enter otp");
                    return;
                }
                if (!otpInput.equals(otp)) {
                    ToastUtils.show(context, "Otp is wrong.");
                    return;
                }
                mBottomSheetVerifyOTP.dismiss();
                hitOrderApi();
            }
        });
    }

    private void hitOrderApi() {
        Gson gson = new Gson();
        place_order_json = gson.toJson(submitOrderArrayList);
        Call<ResponseBody> call = APIClient.getInstance().salesCreateOrder(
                MyApp.userModel.getData().getResponse().get(0).getRegcode(), place_order_json, shopObj.getShop_id());
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
                        Intent intent = new Intent(context, DashBoardActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                }, null);
    }

}