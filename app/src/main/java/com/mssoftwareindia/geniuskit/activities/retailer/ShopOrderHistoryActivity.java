package com.mssoftwareindia.geniuskit.activities.retailer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.ShopOrderHistoryAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityShopOrderHistoryBinding;
import com.mssoftwareindia.geniuskit.model.PlaceOrder;
import com.mssoftwareindia.geniuskit.model.ShopOrderHistoryResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.ui.NoChangingBackgroundTextInputLayout;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopOrderHistoryActivity extends AppCompatActivity {
    public ShopOrderHistoryActivity context;
    public String TAG = "ShopOrderHistoryActivity";
    View view;
    public ActivityShopOrderHistoryBinding binding;
    public ShopOrderHistoryAdapter adapter;
    public ArrayList<ShopOrderHistoryResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopOrderHistoryBinding.inflate(getLayoutInflater());
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

    private void openOfferDailog(int pos) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_address);

        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            lp.width = (int) (metrics.widthPixels * 0.90);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        NoChangingBackgroundTextInputLayout ip_challan = dialog.findViewById(R.id.ip_challan);
        EditText edtChallan = dialog.findViewById(R.id.edtChallan);
        TextView txtAdd = dialog.findViewById(R.id.txtAdd);

        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtChallan.getText().toString().isEmpty()) {
                    ip_challan.setError("please enter challan");
                } else {
                    ip_challan.setError(null);
                    hitAssignShopApi(edtChallan.getText().toString(), pos);
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    private void hitAssignShopApi(String string, int pos) {
        Call<ResponseBody> call = APIClient.getInstance().updateChallan(responseArrayList.get(pos).getOrders_id(),
                MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id(), string);
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
        customDialog.showDialogTwoButton(context, "Challan", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                    }
                }, null);
    }

    private void _dialog(String msg) {

        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Challan", "Update challan success.",
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                        responseArrayList.clear();
                        hitOrderHistoryApi();

                    }
                }, null);
    }

    private void hitOrderHistoryApi() {
        /*MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id()*/
        Call<ResponseBody> call = APIClient.getInstance().shopOrderHistory(MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                ShopOrderHistoryResponse aboutus = gson.fromJson(responseData, ShopOrderHistoryResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new ShopOrderHistoryAdapter(context, responseArrayList);
                adapter.setListner(new ShopOrderHistoryAdapter.onClickAdd() {
                    @Override
                    public void onAddShop(int pos) {
                        openOfferDailog(pos);
                    }
                });
                binding.recyclerOrder.setAdapter(adapter);

            }

            @Override
            public void failure(String responseData) {
            }
        });
    }
}