package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RetailerUserModel implements Serializable {
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
        @SerializedName("retailer_id")
        public String retailer_id = "";
        @Expose
        @SerializedName("shop_name")
        public String shop_name = "";
        @Expose
        @SerializedName("cperson")
        public String cperson = "";
        @Expose
        @SerializedName("mobilenumber")
        public String mobilenumber = "";

        public String getRetailer_id() {
            return retailer_id;
        }

        public void setRetailer_id(String retailer_id) {
            this.retailer_id = retailer_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getCperson() {
            return cperson;
        }

        public void setCperson(String cperson) {
            this.cperson = cperson;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public void setMobilenumber(String mobilenumber) {
            this.mobilenumber = mobilenumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getShop_city() {
            return shop_city;
        }

        public void setShop_city(String shop_city) {
            this.shop_city = shop_city;
        }

        public String getShop_state() {
            return shop_state;
        }

        public void setShop_state(String shop_state) {
            this.shop_state = shop_state;
        }

        public String getShop_pincode() {
            return shop_pincode;
        }

        public void setShop_pincode(String shop_pincode) {
            this.shop_pincode = shop_pincode;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getShop_aadhar_number() {
            return shop_aadhar_number;
        }

        public void setShop_aadhar_number(String shop_aadhar_number) {
            this.shop_aadhar_number = shop_aadhar_number;
        }

        public String getShop_joining_date() {
            return shop_joining_date;
        }

        public void setShop_joining_date(String shop_joining_date) {
            this.shop_joining_date = shop_joining_date;
        }

        public String getShop_sales_person_id() {
            return shop_sales_person_id;
        }

        public void setShop_sales_person_id(String shop_sales_person_id) {
            this.shop_sales_person_id = shop_sales_person_id;
        }

        public String getShop_distributor_id() {
            return shop_distributor_id;
        }

        public void setShop_distributor_id(String shop_distributor_id) {
            this.shop_distributor_id = shop_distributor_id;
        }

        public String getShop_company_id() {
            return shop_company_id;
        }

        public void setShop_company_id(String shop_company_id) {
            this.shop_company_id = shop_company_id;
        }

        public String getShop_super_stockist_id() {
            return shop_super_stockist_id;
        }

        public void setShop_super_stockist_id(String shop_super_stockist_id) {
            this.shop_super_stockist_id = shop_super_stockist_id;
        }

        @Expose
        @SerializedName("email")
        public String email = "";
        @Expose
        @SerializedName("shop_city")
        public String shop_city = "";
        @Expose
        @SerializedName("shop_state")
        public String shop_state = "";
        @Expose
        @SerializedName("shop_pincode")
        public String shop_pincode = "";
        @Expose
        @SerializedName("shop_address")
        public String shop_address = "";
        @Expose
        @SerializedName("shop_aadhar_number")
        public String shop_aadhar_number = "";
        @Expose
        @SerializedName("shop_joining_date")
        public String shop_joining_date = "";
        @Expose
        @SerializedName("shop_sales_person_id")
        public String shop_sales_person_id = "";
        @Expose
        @SerializedName("shop_distributor_id")
        public String shop_distributor_id = "";
        @Expose
        @SerializedName("shop_company_id")
        public String shop_company_id = "";
        @Expose
        @SerializedName("shop_super_stockist_id")
        public String shop_super_stockist_id = "";
    }
}
