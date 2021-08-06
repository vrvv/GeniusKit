package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesDashboardResponse implements Serializable {
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
        @SerializedName("total_order")
        public String total_order = "";
        @Expose
        @SerializedName("total_amount")
        public String total_amount = "";
        @Expose
        @SerializedName("today_order")
        public String today_order = "";
        @Expose
        @SerializedName("today_amount")
        public String today_amount = "";


        public String getTotal_order() {
            return total_order;
        }

        public void setTotal_order(String total_order) {
            this.total_order = total_order;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getToday_order() {
            return today_order;
        }

        public void setToday_order(String today_order) {
            this.today_order = today_order;
        }

        public String getToday_amount() {
            return today_amount;
        }

        public void setToday_amount(String today_amount) {
            this.today_amount = today_amount;
        }
    }
}
