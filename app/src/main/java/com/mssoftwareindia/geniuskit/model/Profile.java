package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Profile  implements Serializable {
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
        @SerializedName("regcode")
        public String regcode = "";
        @Expose
        @SerializedName("name")
        public String name = "";
        @Expose
        @SerializedName("mobile")
        public String mobile = "";
        @Expose
        @SerializedName("email")
        public String email = "";
        @Expose
        @SerializedName("mobile2")
        public String mobile2 = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getCity() {
            return city;
        }

        public String getRegcode() {
            return regcode;
        }

        public void setRegcode(String regcode) {
            this.regcode = regcode;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProof_number() {
            return proof_number;
        }

        public void setProof_number(String proof_number) {
            this.proof_number = proof_number;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }


        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        @Expose
        @SerializedName("city")
        public String city = "";
        @Expose
        @SerializedName("state")
        public String state = "";
        @Expose
        @SerializedName("address")
        public String address = "";
        @Expose
        @SerializedName("proof_number")
        public String proof_number = "";
        @Expose
        @SerializedName("pincode")
        public String pincode = "";
    }
}
