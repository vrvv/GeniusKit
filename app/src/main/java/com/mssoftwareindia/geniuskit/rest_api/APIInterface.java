package com.mssoftwareindia.geniuskit.rest_api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET(AppConstants.Url.ABOUT_US)
    Call<ResponseBody> aboutUs();

    @GET(AppConstants.Url.CONTACT_US)
    Call<ResponseBody> contactUs();

    @GET(AppConstants.Url.HOME_BANNER)
    Call<ResponseBody> homeBanner();

    @GET(AppConstants.Url.STATE)
    Call<ResponseBody> getState();

    @GET(AppConstants.Url.VIDEO_GALLRY)
    Call<ResponseBody> getVideoGallry();

    @FormUrlEncoded
    @POST(AppConstants.Url.CITY)
    Call<ResponseBody> getCity(@Field("name") String stateid);

    @FormUrlEncoded
    @POST(AppConstants.Url.LOGIN_SALES)
    Call<ResponseBody> salesLogin(
            @Field("regcode") String email,
            @Field("password") String password,
            @Field("token") String token

    );

    @FormUrlEncoded
    @POST(AppConstants.Url.ORDER_HISTORY)
    Call<ResponseBody> orderHistory(
            @Field("sales") String sales
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SHOP_ORDER_HISTORY)
    Call<ResponseBody> shopOrderHistory(
            @Field("shop") String shop
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SHOP)
    Call<ResponseBody> getShop(
            @Field("regcode") String distributor
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.GET_DISTRIBUTOR)
    Call<ResponseBody> getDistributor(
            @Field("shop") String shop
    );
    @FormUrlEncoded
    @POST(AppConstants.Url.ORDER_PRODUCT)
    Call<ResponseBody> getOrderProduct(
            @Field("order") String order
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.PRODUCT)
    Call<ResponseBody> getProduct(
            @Field("shop") String sales,
            @Field("regcode") String regcode
    );
    @FormUrlEncoded
    @POST(AppConstants.Url.PRODUCT_RETAILER)
    Call<ResponseBody> getProductRetailer(
            @Field("shop") String sales,
            @Field("distributor") String distributor
    );
    @FormUrlEncoded
    @POST(AppConstants.Url.ASSIGN_SHOP)
    Call<ResponseBody> assignShop(
            @Field("shopid") String shopid,
            @Field("regcode") String regcode
    );
    @FormUrlEncoded
    @POST(AppConstants.Url.UPDATE_CHALLAN)
    Call<ResponseBody> updateChallan(
            @Field("orderid") String orderid,
            @Field("shop_id") String shop_id,
            @Field("challan") String challan
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SEARCH_SHOP)
    Call<ResponseBody> searchShop(
            @Field("regcode") String distributor,
            @Field("name") String name

    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SALES_DASHBOARD)
    Call<ResponseBody> salesDashBoard(
            @Field("sales") String sales
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SHOP_DASHBOARD)
    Call<ResponseBody> shopDashBoard(
            @Field("shop") String shop
    );
    @FormUrlEncoded
    @POST(AppConstants.Url.ADD_SHOP)
    Call<ResponseBody> addShop(
            @Field("name") String name,
            @Field("person") String person,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("state") String state,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("address") String address,
            @Field("sales") String sales,
            @Field("aadhar") String aadhar,
            @Field("lang") String lang,
            @Field("lat") String lat

    );
    @FormUrlEncoded
    @POST(AppConstants.Url.ADD_SHOP_RETAILER)
    Call<ResponseBody> addReatailer(
            @Field("name") String name,
            @Field("person") String person,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("state") String state,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("address") String address,
             @Field("aadhar") String aadhar,
            @Field("lang") String lang,
            @Field("lat") String lat,
            @Field("token") String token


    );

    @FormUrlEncoded
    @POST(AppConstants.Url.EDIT_PROFILE)
    Call<ResponseBody> editProfile(
            @Field("regcode") String regcode,
            @Field("state") String state,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("address") String address

    );

    @FormUrlEncoded
    @POST(AppConstants.Url.GET_PROFILE)
    Call<ResponseBody> getProfile(
            @Field("regcode") String regcode
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SALES_CREATE_ORDER)
    Call<ResponseBody> salesCreateOrder(
            @Field("username") String username,
            @Field("data") String data, @Field("retailer_id") String retailer_id

    );

    @FormUrlEncoded
    @POST(AppConstants.Url.CREATE_SALES_ORDER_OTP)
    Call<ResponseBody> crateSalesOrderOtp(
            @Field("shop") String shopid,
            @Field("salesperson") String regcode
    );

    @FormUrlEncoded
    @POST(AppConstants.Url.SALES_CHECK_OTP)
    Call<ResponseBody> salesCheckOtp(
            @Field("salesperson") String regcode
    );
    /*   retailer
     */

    @FormUrlEncoded
    @POST(AppConstants.Url.LOGIN_SHOP_OTP)
    Call<ResponseBody> getOtp(
            @Field("mobile") String username    );

    @FormUrlEncoded
    @POST(AppConstants.Url.LOGIN_SHOP)
    Call<ResponseBody> loginShop(
            @Field("mobile") String mobile,
            @Field("otp") String otp,
            @Field("token") String token);
    @FormUrlEncoded
    @POST(AppConstants.Url.SALES_CREATE_ORDER_REATAILER)
    Call<ResponseBody> salesCreateOrderRetailer(
            @Field("distributorid") String username,
            @Field("data") String data, @Field("retailer_id") String retailer_id

    );
}