package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DistributorListResponse implements Serializable {
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
        @SerializedName("distid")
        public String distid = "";
        @Expose
        @SerializedName("name")
        public String name = "";
        @Expose
        @SerializedName("number")
        public String number = "";
        @Expose
        @SerializedName("dist_state")
        public String dist_state = "";

        public String getDistid() {
            return distid;
        }

        public void setDistid(String distid) {
            this.distid = distid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDist_state() {
            return dist_state;
        }

        public void setDist_state(String dist_state) {
            this.dist_state = dist_state;
        }

        public String getDist_city() {
            return dist_city;
        }

        public void setDist_city(String dist_city) {
            this.dist_city = dist_city;
        }

        public String getDist_address() {
            return dist_address;
        }

        public void setDist_address(String dist_address) {
            this.dist_address = dist_address;
        }

        public String getComp_name() {
            return comp_name;
        }

        public void setComp_name(String comp_name) {
            this.comp_name = comp_name;
        }

        @Expose
        @SerializedName("dist_city")
        public String dist_city = "";
        @Expose
        @SerializedName("dist_address")
        public String dist_address = "";
        @Expose
        @SerializedName("comp_name")
        public String comp_name = "";


    }
}
