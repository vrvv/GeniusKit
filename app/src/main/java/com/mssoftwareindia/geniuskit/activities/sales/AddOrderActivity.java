package com.mssoftwareindia.geniuskit.activities.sales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.OtherVersionAdapter;
import com.mssoftwareindia.geniuskit.adapter.ProductListAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityAddOrderBinding;
import com.mssoftwareindia.geniuskit.model.PlaceOrder;
import com.mssoftwareindia.geniuskit.model.ProductResponse;
import com.mssoftwareindia.geniuskit.model.ShopListResponse;
import com.mssoftwareindia.geniuskit.model.SubmitOrder;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class AddOrderActivity extends AppCompatActivity {
    public AddOrderActivity context;
    public String TAG = "AddOrderActivity";
    View view;
    public ActivityAddOrderBinding binding;
    public ProductListAdapter adapter;
    public ArrayList<ProductResponse.Response> responseArrayList = new ArrayList<>();
    public SubmitOrder submitOrder;
    public ArrayList<SubmitOrder> submitOrderArrayList = new ArrayList<>();
    public String place_order_json = "";
    public ShopListResponse.Response shop_id;
    public BottomSheetDialog mBottomDelivery;
    public ArrayList<ProductResponse.Response> confirmProductArray = new ArrayList<>();
    public static List<ProductResponse.SubProduct> tempsubProductArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrderBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;

        shop_id = (ShopListResponse.Response) getIntent().getSerializableExtra("shop_obj");
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);

        MyApp.subProductArrayList.clear();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.subProductArrayList.clear();
                onBackPressed();
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitOrderArrayList.size() > 0 || MyApp.subProductArrayList.size() > 0) {
                    confirmProductArray.clear();
                    confirmProductArray.addAll((ArrayList) adapter.mArrayList);
                    Intent intent = new Intent(context, OrderConfirmActivity.class);
                    intent.putParcelableArrayListExtra("proObj", (ArrayList) adapter.mArrayList);
                    intent.putExtra("shop_obj", shop_id);
                    startActivity(intent);

                    //  hitOrderApi();
                } else {
                    ToastUtils.show(context, "Please add product");
                }

            }
        });

    }


    public void bottomDelivery(List<ProductResponse.SubProduct> otherVersions, ProductListAdapter.HolderHorizontal holder, View rel_product, int position) {
        View view = getLayoutInflater().inflate(R.layout.bottom_other_variant, null);
        mBottomDelivery = new BottomSheetDialog(context);
        mBottomDelivery.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomDelivery.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));


        ImageView img_close = view.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  MyApp.subProductArrayList.addAll(tempsubProductArray);
                mBottomDelivery.dismiss();
            }
        });
        RecyclerView recycler_category = view.findViewById(R.id.recycler_category);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_category.setLayoutManager(layoutManager);
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recycler_category.setLayoutAnimation(controller);
        OtherVersionAdapter otherVersionAdapter = new OtherVersionAdapter(context, otherVersions);
        recycler_category.setAdapter(otherVersionAdapter);
        otherVersionAdapter.setListner(new OtherVersionAdapter.onClickAdd() {
            @Override
            public void onUpdateQty(List<ProductResponse.SubProduct> mArrayList, int pos) {

                if (mArrayList.get(pos).getQty() > 0) {
                    if (MyApp.subProductArrayList.size() > 0) {
                        for (int i = 0; i < MyApp.subProductArrayList.size(); i++) {

                            if (MyApp.subProductArrayList.get(i).getProd_id().equals(mArrayList.get(pos).getProd_id())) {
                                MyApp.subProductArrayList.set(i, mArrayList.get(pos));
                            } else {
                                boolean notAvail = false;
                                for (int j = 0; j < MyApp.subProductArrayList.size(); j++) {
                                    if (MyApp.subProductArrayList.get(j).getProd_id().equals(mArrayList.get(pos).getProd_id())) {
                                        notAvail = true;
                                    }
                                }
                                if (!notAvail)
                                    MyApp.subProductArrayList.add(mArrayList.get(pos));
                            }
                        }
                    } else {
                        MyApp.subProductArrayList.add(mArrayList.get(pos));
                    }
                } else {
                    for (int i = 0; i < MyApp.subProductArrayList.size(); i++) {
                        if (MyApp.subProductArrayList.get(i).getQty() < 1 && MyApp.subProductArrayList.get(i).getProd_id().equals(mArrayList.get(pos).getProd_id())) {
                            MyApp.subProductArrayList.remove(i);
                        }
                    }
                }
            }
        });
        mBottomDelivery.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.subProductArrayList.clear();
        hitShopListApi();
    }

    private void hitOrderApi() {
        Gson gson = new Gson();
        place_order_json = gson.toJson(submitOrderArrayList);
        Call<ResponseBody> call = APIClient.getInstance().salesCreateOrder(
                MyApp.userModel.getData().getResponse().get(0).getRegcode(), place_order_json, shop_id.getShop_id());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                PlaceOrder commonRes = gson.fromJson(responseData, PlaceOrder.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    onBackPressed();
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void hitShopListApi() {
        Log.i("Shop_id", "==>" + shop_id.getShop_id());
        Log.i("Regcode", "==>" + MyApp.userModel.getData().getResponse().get(0).getRegcode());
        Call<ResponseBody> call = APIClient.getInstance().getProduct(shop_id.getShop_id(), MyApp.userModel.getData().getResponse().get(0).getRegcode());
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
                adapter = new ProductListAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);
                adapter.setListner(new ProductListAdapter.onClickAdd() {
                    @Override
                    public void onUpdateQty(List<ProductResponse.Response> mArrayList, int pos) {
                        submitOrderArrayList.clear();
                        int totalDp = 0;
                        int totalMrp = 0;

                        for (int i = 0; i < mArrayList.size(); i++) {
                            if (mArrayList.get(i).getQty() > 0) {
                                submitOrder = new SubmitOrder();
                                submitOrder.setItem_id(mArrayList.get(i).getProd_id());
                                submitOrder.setQty(mArrayList.get(i).getQty() + "");
                                submitOrderArrayList.add(submitOrder);
                                totalDp += Float.parseFloat(mArrayList.get(i).getProd_dp()) * mArrayList.get(i).getQty();
                                totalMrp += Float.parseFloat(mArrayList.get(i).getProd_pay()) * mArrayList.get(i).getQty();
                            }
                        }
                        binding.tvTotalDp.setText("Total Dp: " + totalDp);
                        binding.tvTotalMrp.setText("Total Dp: " + totalMrp);
                        Log.i(TAG, "Total Dp : " + totalDp);
                        Log.i(TAG, "Total MRP : " + totalMrp);
                    }
                });
                adapter.setOnItemApprochClickListener(new ProductListAdapter.OnItemClick() {
                    @Override
                    public void onOtherVersion(List<ProductResponse.SubProduct> otherVersions, ProductListAdapter.HolderHorizontal holder, View rel_product, int position) {
                        bottomDelivery(otherVersions, holder, rel_product, position);
                        mBottomDelivery.show();

                    }

                });
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }
}