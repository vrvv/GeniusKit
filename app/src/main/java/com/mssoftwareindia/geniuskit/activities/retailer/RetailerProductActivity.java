package com.mssoftwareindia.geniuskit.activities.retailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.activities.sales.AddOrderActivity;
import com.mssoftwareindia.geniuskit.activities.sales.OrderConfirmActivity;
import com.mssoftwareindia.geniuskit.adapter.OtherVersionAdapter;
import com.mssoftwareindia.geniuskit.adapter.ProductListAdapter;
import com.mssoftwareindia.geniuskit.adapter.RetailerProductListAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityRetailerProductBinding;
import com.mssoftwareindia.geniuskit.model.DistributorListResponse;
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

public class RetailerProductActivity extends AppCompatActivity {
    public RetailerProductActivity context;
    public String TAG = "RetailerProductActivity";
    View view;
    public ActivityRetailerProductBinding binding;
    public RetailerProductListAdapter adapter;
    public ArrayList<ProductResponse.Response> responseArrayList = new ArrayList<>();
    public SubmitOrder submitOrder;
    public ArrayList<SubmitOrder> submitOrderArrayList = new ArrayList<>();
    public String place_order_json = "";
    public DistributorListResponse.Response shop_id;
    public BottomSheetDialog mBottomDelivery;
    public ArrayList<ProductResponse.Response> confirmProductArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerProductBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        shop_id = (DistributorListResponse.Response) getIntent().getSerializableExtra("shop_obj");
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerShop.setLayoutAnimation(controller);
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
                if (submitOrderArrayList.size() > 0 || MyApp.subProductArrayList.size() >0) {
                    Intent intent = new Intent(context, RetailerOrderConfirmActivity.class);
                    intent.putParcelableArrayListExtra("proObj", (ArrayList) adapter.mArrayList);
                    intent.putExtra("shop_obj",shop_id);
                    startActivity(intent);

                    //  hitOrderApi();
                } else {
                    ToastUtils.show(context, "Please add product");
                }

            }
        });

    }

    public void bottomDelivery(List<ProductResponse.SubProduct> otherVersions, RetailerProductListAdapter.HolderHorizontal holder, View rel_product, int position) {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.subProductArrayList.clear();
        hitShopListApi();
    }
    private void hitShopListApi() {
        Call<ResponseBody> call = APIClient.getInstance().getProductRetailer(MyApp.retailerUserModel.getData().getResponse().get(0).getRetailer_id(),shop_id.getDistid());
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
                adapter = new RetailerProductListAdapter(context, responseArrayList);
                binding.recyclerShop.setAdapter(adapter);
                adapter.setListner(new RetailerProductListAdapter.onClickAdd() {
                    @Override
                    public void onUpdateQty(List<ProductResponse.Response> mArrayList, int pos) {
                        submitOrderArrayList.clear();
                        float totalDp = 0;
                        float totalMrp = 0;

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
                     }
                });
                adapter.setOnItemApprochClickListener(new RetailerProductListAdapter.OnItemClick() {
                    @Override
                    public void onOtherVersion(List<ProductResponse.SubProduct> otherVersions, RetailerProductListAdapter.HolderHorizontal holder, View rel_product, int position) {
                        bottomDelivery(otherVersions, holder, rel_product, position);
                        mBottomDelivery.show();

                    }

                });  }

            @Override
            public void failure(String responseData) {
            }
        });
    }
}