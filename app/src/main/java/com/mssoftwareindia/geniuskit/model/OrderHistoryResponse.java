package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderHistoryResponse implements Serializable {
    @Expose
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @Expose
        @SerializedName("response")
        private List<Response> response;
        @Expose
        @SerializedName("result")
        private String result;
        @Expose
        @SerializedName("status")
        private int status;

        public List<Response> getResponse() {
            return response;
        }

        public void setResponse(List<Response> response) {
            this.response = response;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class Response implements Serializable {
        @Expose
        @SerializedName("shop_name")
        public String shop_name="";
        @Expose
        @SerializedName("shop_mobile")
        public String shop_mobile="";
        @Expose
        @SerializedName("shop_city")
        public String shop_city="";
        @Expose
        @SerializedName("shop_pincode")
        public String shop_pincode="";

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_mobile() {
            return shop_mobile;
        }

        public void setShop_mobile(String shop_mobile) {
            this.shop_mobile = shop_mobile;
        }

        public String getShop_city() {
            return shop_city;
        }

        public void setShop_city(String shop_city) {
            this.shop_city = shop_city;
        }

        public String getShop_pincode() {
            return shop_pincode;
        }

        public void setShop_pincode(String shop_pincode) {
            this.shop_pincode = shop_pincode;
        }

        @Expose
        @SerializedName("orders_id")
        public String orders_id="";

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        @Expose
        @SerializedName("orders_code")
        public String orders_code = "";
        @Expose
        @SerializedName("orders_date")
        public String orders_date = "";
        @Expose
        @SerializedName("orders_total_item")
        public String orders_total_item = "";
        @Expose
        @SerializedName("orders_total_mrp")
        public String orders_total_mrp = "";

        public String getOrders_code() {
            return orders_code;
        }

        public void setOrders_code(String orders_code) {
            this.orders_code = orders_code;
        }

        public String getOrders_date() {
            return orders_date;
        }

        public void setOrders_date(String orders_date) {
            this.orders_date = orders_date;
        }

        public String getOrders_total_item() {
            return orders_total_item;
        }

        public void setOrders_total_item(String orders_total_item) {
            this.orders_total_item = orders_total_item;
        }

        public String getOrders_total_mrp() {
            return orders_total_mrp;
        }

        public void setOrders_total_mrp(String orders_total_mrp) {
            this.orders_total_mrp = orders_total_mrp;
        }

        public String getOrders_total_dp() {
            return orders_total_dp;
        }

        public void setOrders_total_dp(String orders_total_dp) {
            this.orders_total_dp = orders_total_dp;
        }

        public String getOrder_total_payment() {
            return order_total_payment;
        }

        public void setOrder_total_payment(String order_total_payment) {
            this.order_total_payment = order_total_payment;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        @Expose
        @SerializedName("orders_total_dp")
        public String orders_total_dp = "";
        @Expose
        @SerializedName("order_total_payment")
        public String order_total_payment = "";
        @Expose
        @SerializedName("order_status")
        public String order_status = "";


    }
}
