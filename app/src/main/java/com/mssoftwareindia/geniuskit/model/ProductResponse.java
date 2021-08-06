package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductResponse implements Serializable {
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
        @SerializedName("prod_id")
        public String prod_id = "";
        @Expose
        @SerializedName("prod_name")
        public String prod_name = "";
        @Expose
        @SerializedName("prod_umo_id")
        public String prod_umo_id = "";

        public String getProd_qty() {
            return prod_qty;
        }

        public void setProd_qty(String prod_qty) {
            this.prod_qty = prod_qty;
        }

        @Expose
        @SerializedName("prod_qty")
        public String prod_qty = "";
        @Expose
        @SerializedName("prod_dp")
        public String prod_dp = "";

        public String getOut_of_stock() {
            return out_of_stock;
        }

        public void setOut_of_stock(String out_of_stock) {
            this.out_of_stock = out_of_stock;
        }

        @Expose
        @SerializedName("out_of_stock")
        public String out_of_stock = "";

        public String getProd_total_pay() {
            return prod_total_pay;
        }

        public void setProd_total_pay(String prod_total_pay) {
            this.prod_total_pay = prod_total_pay;
        }

        @Expose
        @SerializedName("prod_total_pay")
        public String prod_total_pay = "";
        @Expose
        @SerializedName("prod_price")
        public String prod_price = "";
        @Expose
        @SerializedName("prod_per")
        public String prod_per = "";
        @Expose
        @SerializedName("prod_dis")
        public String prod_dis = "";
        @Expose
        @SerializedName("prod_pay")
        public String prod_pay = "";
        @Expose
        @SerializedName("qty")
        public int qty = 0;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getProd_id() {
            return prod_id;
        }

        public void setProd_id(String prod_id) {
            this.prod_id = prod_id;
        }

        public String getProd_name() {
            return prod_name;
        }

        public void setProd_name(String prod_name) {
            this.prod_name = prod_name;
        }

        public String getProd_umo_id() {
            return prod_umo_id;
        }

        public void setProd_umo_id(String prod_umo_id) {
            this.prod_umo_id = prod_umo_id;
        }

        public String getProd_dp() {
            return prod_dp;
        }

        public void setProd_dp(String prod_dp) {
            this.prod_dp = prod_dp;
        }

        public String getProd_price() {
            return prod_price;
        }

        public void setProd_price(String prod_price) {
            this.prod_price = prod_price;
        }

        public String getProd_per() {
            return prod_per;
        }

        public void setProd_per(String prod_per) {
            this.prod_per = prod_per;
        }

        public String getProd_dis() {
            return prod_dis;
        }

        public void setProd_dis(String prod_dis) {
            this.prod_dis = prod_dis;
        }

        public String getProd_pay() {
            return prod_pay;
        }

        public void setProd_pay(String prod_pay) {
            this.prod_pay = prod_pay;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }

        public String getProd_desc() {
            return prod_desc;
        }

        public void setProd_desc(String prod_desc) {
            this.prod_desc = prod_desc;
        }

        @Expose
        @SerializedName("main_image")
        public String main_image = "";
        @Expose
        @SerializedName("prod_desc")
        public String prod_desc = "";

        public List<SubProduct> getSubProductList() {
            return subProductList;
        }

        public void setSubProductList(List<SubProduct> subProductList) {
            this.subProductList = subProductList;
        }

        @Expose
        @SerializedName("subprod")
        private List<SubProduct> subProductList;


    }

    public static class SubProduct implements Serializable {
        @Expose
        @SerializedName("prod_id")
        public String prod_id = "";
        @Expose
        @SerializedName("prod_name")
        public String prod_name = "";
        @Expose
        @SerializedName("prod_umo_id")
        public String prod_umo_id = "";

        public String getProd_qty() {
            return prod_qty;
        }

        public void setProd_qty(String prod_qty) {
            this.prod_qty = prod_qty;
        }

        @Expose
        @SerializedName("prod_qty")
        public String prod_qty = "";
        @Expose
        @SerializedName("prod_dp")
        public String prod_dp = "";

        public String getProd_total_pay() {
            return prod_total_pay;
        }

        public void setProd_total_pay(String prod_total_pay) {
            this.prod_total_pay = prod_total_pay;
        }

        @Expose
        @SerializedName("prod_total_pay")
        public String prod_total_pay = "";
        @Expose
        @SerializedName("prod_price")
        public String prod_price = "";
        @Expose
        @SerializedName("prod_per")
        public String prod_per = "";
        @Expose
        @SerializedName("prod_dis")
        public String prod_dis = "";
        @Expose
        @SerializedName("prod_pay")
        public String prod_pay = "";
        @Expose
        @SerializedName("qty")
        public int qty = 0;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getProd_id() {
            return prod_id;
        }

        public void setProd_id(String prod_id) {
            this.prod_id = prod_id;
        }

        public String getProd_name() {
            return prod_name;
        }

        public void setProd_name(String prod_name) {
            this.prod_name = prod_name;
        }

        public String getProd_umo_id() {
            return prod_umo_id;
        }

        public void setProd_umo_id(String prod_umo_id) {
            this.prod_umo_id = prod_umo_id;
        }

        public String getProd_dp() {
            return prod_dp;
        }

        public void setProd_dp(String prod_dp) {
            this.prod_dp = prod_dp;
        }

        public String getProd_price() {
            return prod_price;
        }

        public void setProd_price(String prod_price) {
            this.prod_price = prod_price;
        }

        public String getProd_per() {
            return prod_per;
        }

        public void setProd_per(String prod_per) {
            this.prod_per = prod_per;
        }

        public String getProd_dis() {
            return prod_dis;
        }

        public void setProd_dis(String prod_dis) {
            this.prod_dis = prod_dis;
        }

        public String getProd_pay() {
            return prod_pay;
        }

        public void setProd_pay(String prod_pay) {
            this.prod_pay = prod_pay;
        }

        public String getMain_image() {
            return main_image;
        }

        public void setMain_image(String main_image) {
            this.main_image = main_image;
        }

        public String getProd_desc() {
            return prod_desc;
        }

        public void setProd_desc(String prod_desc) {
            this.prod_desc = prod_desc;
        }

        @Expose
        @SerializedName("main_image")
        public String main_image = "";
        @Expose
        @SerializedName("prod_desc")
        public String prod_desc = "";


    }
}
