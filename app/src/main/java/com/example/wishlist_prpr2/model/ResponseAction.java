package com.example.wishlist_prpr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAction {
    @Expose
    @SerializedName("success")
    private String success;
    @Expose
    @SerializedName("payload")
    private String[] payload;

    public ResponseAction(String success, String[] payload) {
        this.success = success;
        this.payload = payload;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String[] getPayload() {
        return payload;
    }

    public void setPayload(String[] payload) {
        this.payload = payload;
    }
}
