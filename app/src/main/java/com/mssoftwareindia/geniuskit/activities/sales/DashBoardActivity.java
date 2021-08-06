package com.mssoftwareindia.geniuskit.activities.sales;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityDashBoardBinding;
import com.mssoftwareindia.geniuskit.model.SalesDashboardResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DashBoardActivity extends AppCompatActivity {
    public DashBoardActivity context;
    public String TAG = "DashBoardActivity";
    View view;
    public ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.tvRegCode.setText("Reg Code:" + MyApp.userModel.getData().getResponse().get(0).getRegcode());
        binding.tvName.setText("Name:" + MyApp.userModel.getData().getResponse().get(0).getUsername());
        binding.tvEmail.setText("Email:" + MyApp.userModel.getData().getResponse().get(0).getEmail());
        binding.tvMobile.setText("Mobile:" + MyApp.userModel.getData().getResponse().get(0).getMobile());


        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomTooltip();
            }
        });

        binding.linAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopListActivity.class);
                startActivity(intent);

            }
        });
        binding.linAddRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddShopActivity.class);
                startActivity(intent);
            }
        });
        binding.linListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
        binding.linListReatiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopListActivity.class);
                startActivity(intent);
            }
        });
        binding.linListSearchShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchRetailerActivity.class);
                startActivity(intent);
            }
        });
        hitSalesDashboardApi();
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

    private void hitSalesDashboardApi() {
        Call<ResponseBody> call = APIClient.getInstance().salesDashBoard(MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                SalesDashboardResponse aboutus = gson.fromJson(responseData, SalesDashboardResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {

                    try {
                        binding.tvTodayAmount.setText("\u20B9 " + aboutus.getData().getResponse().get(0).getToday_amount());
                        binding.tvTodayOrder.setText(aboutus.getData().getResponse().get(0).getToday_order());
                        binding.tvTotalAmount.setText("\u20B9 " + aboutus.getData().getResponse().get(0).getTotal_amount());
                        binding.tvTotalOrder.setText(aboutus.getData().getResponse().get(0).getTotal_order());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        //inflating menu from xml resource
        popup.inflate(R.menu.post_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        startActivity(new Intent(context, EditProfileActivity.class));
                        break;
                    case R.id.edit:
                        MyApp.mySharedPref.clearApp();
                        MyApp.mySharedPref.setIsLoggedIn(false);
                        MyApp.mySharedPref.setUserModel("");
                        MyApp.mySharedPref.setUserModel("");
                        startActivity(new Intent(context, SalesLoginActivity.class));
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
        //displaying the pop
        popup.show();


    }

}