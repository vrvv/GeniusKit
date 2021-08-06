package com.mssoftwareindia.geniuskit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Aboutus implements Serializable {
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
        @SerializedName("status")
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public About getAbout() {
            return about;
        }

        public void setAbout(About about) {
            this.about = about;
        }

        @Expose
        @SerializedName("about")
        public About about;


    }

    public static class About implements Serializable {
        @Expose
        @SerializedName("heading")
        public String heading = "";
        @Expose
        @SerializedName("content")
        public String content = "";

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
