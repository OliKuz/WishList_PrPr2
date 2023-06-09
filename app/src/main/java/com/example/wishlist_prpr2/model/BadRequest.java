package com.example.wishlist_prpr2.model;

public class BadRequest {

    private String title;
    private int status;
    private String instance;
    private String detail;

    public BadRequest(String title, int status, String instance, String detail) {
        this.title = title;
        this.status = status;
        this.instance = instance;
        this.detail = detail;
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
