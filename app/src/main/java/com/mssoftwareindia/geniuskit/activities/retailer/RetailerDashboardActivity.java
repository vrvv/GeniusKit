package com.mssoftwareindia.geniuskit.activities.retailer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.activities.sales.DashBoardActivity;
import com.mssoftwareindia.geniuskit.activities.sales.EditProfileActivity;
import com.mssoftwareindia.geniuskit.activities.sales.OrderHistoryActivity;
import com.mssoftwareindia.geniuskit.activities.sales.SalesLoginActivity;
import com.mssoftwareindia.geniuskit.databinding.ActivityDashBoardBinding;
import com.mssoftwareindia.geniuskit.databinding.ActivityRetailerDashboardBinding;
import com.mssoftwareindia.geniuskit.model.SalesDashboardResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class RetailerDashboardActivity extends AppCompatActivity {
    public RetailerDashboardActivity context;
    public String TAG = "RetailerDashboardActivity";
    View view;
    public ActivityRetailerDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerDashboardBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.tvRegCode.setText("Contact Person:"+MyApp.retailerUserModel.getData().getResponse().get(0).getCperson());
        binding.tvName.setText("Name:"+MyApp.retailerUserModel.getData().getResponse().get(0).getShop_name());
        binding.tvEmail.setText("Email:"+MyApp.retailerUserModel.getData().getResponse().get(0).getEmail());
        binding.tvMobile.setText("Mobile:"+MyApp.retailerUserModel.getData().getResponse().get(0).getMobilenumber());

        hitShopDashboardApi();
        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomTooltip();
            }
        });
        binding.linListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopOrderHistoryActivity.class);
                startActivity(intent);
            }
        });
        binding.linAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DistributorListActivity.class);
                startActivity(intent);
            }
        });

        binding.linListDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DistributorListActivity.class);
                startActivity(intent);
            }
        });
        binding.linVideoGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoGallaryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        // Otherwise, ask user if he wants to leave :)
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        moveTaskToBack(true);
                    }
                }).create().show();
    }

    private void hitShopDashboardApi() {
        Call<ResponseBody> call = APIClient.getInstance().shopDashBoard(MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                SalesDashboardResponse aboutus = gson.fromJson(responseData, SalesDashboardResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    binding.tvTodayAmount.setText("\u20B9 " + aboutus.getData().getResponse().get(0).getToday_amount());
                    binding.tvTodayOrder.setText(aboutus.getData().getResponse().get(0).getToday_order());
                    binding.tvTotalAmount.setText("\u20B9 " + aboutus.getData().getResponse().get(0).getTotal_amount());
                    binding.tvTotalOrder.setText(aboutus.getData().getResponse().get(0).getTotal_order());

                }

            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void showCustomTooltip() {
        Context wrapper = new ContextThemeWrapper(context, R.style.popupMenuStyle);
        PopupMenu popup = new PopupMenu(wrapper, binding.imgProfile);
        popup.inflate(R.menu.reatiler_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_logout:
                        MyApp.mySharedPref.clearApp();
                        MyApp.mySharedPref.setIsLoggedIn(false);
                        MyApp.mySharedPref.setReatailerModel("");
                        MyApp.mySharedPref.setIsReatailer(false);
                        startActivity(new Intent(context, RetailerLoginActivity.class));
                        finish();
                        break;
                    case R.id.share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "GeniusKit\n" + "http://play.google.com/store/apps/details?id=" + getPackageName());
                        startActivity(Intent.createChooser(shareIntent, "Share link using"));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

}