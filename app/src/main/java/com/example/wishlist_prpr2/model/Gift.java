package com.example.wishlist_prpr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gift {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("wishlist_id")
    private int wishlist_id;

    @Expose
    @SerializedName("product_url")
    private String product_url;

    @Expose
    @SerializedName("priority")
    private int priority;

    @Expose
    @SerializedName("booked")
    private int booked;

    public Gift(int id, int wishlist_id, String product_url, int priority, int booked) {
        this.id = id;
        this.wishlist_id = wishlist_id;
        this.product_url = product_url;
        this.priority = priority;
        this.booked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isBooked() {
        return booked == 1;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }
}
