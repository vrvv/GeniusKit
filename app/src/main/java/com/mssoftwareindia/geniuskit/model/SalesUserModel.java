package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesUserModel implements Serializable {
    @Expose
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable{
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
        @SerializedName("regcode")
        public String regcode = "";
        @Expose
        @SerializedName("company")
        public String company = "";
        @Expose
        @SerializedName("sales")
        public String sales="";

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getRegcode() {
            return regcode;
        }

        public void setRegcode(String regcode) {
            this.regcode = regcode;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public String getLoginpassword() {
            return loginpassword;
        }

        public void setLoginpassword(String loginpassword) {
            this.loginpassword = loginpassword;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        @Expose
        @SerializedName("distributor")
        public String distributor = "";
        @Expose
        @SerializedName("loginpassword")
        public String loginpassword = "";
        @Expose
        @SerializedName("username")
        public String username = "";
        @Expose
        @SerializedName("email")
        public String email = "";
        @Expose
        @SerializedName("mobile")
        public String mobile = "";
        @Expose
        @SerializedName("mobile2")
        public String mobile2 = "";
        @Expose
        @SerializedName("regdate")
        public String regdate = "";
    }
}
