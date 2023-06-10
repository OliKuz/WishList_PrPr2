package com.example.wishlist_prpr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
    @Expose
    @SerializedName("success")
    private String success;
    @Expose
    @SerializedName("error")
    private String[] error;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("instance")
    private String instance;
    @Expose
    @SerializedName("detail")
    private String detail;

    public Error(String success, String[] error, String type, String title, int status, String instance, String detail) {
        this.success = success;
        this.error = error;
        this.type = type;
        this.title = title;
        this.status = status;
        this.instance = instance;
        this.detail = detail;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String[] getError() {
        return error;
    }

    public void setError(String[] error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
