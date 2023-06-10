package com.example.wishlist_prpr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("categoryID")
    private int categoryID;
    @Expose
    @SerializedName("is_active")
    private boolean is_active;

    public Product(int id, String name, String description, int price, String link, String image, int categoryID, boolean is_active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.image = image;
        this.categoryID = categoryID;
        this.is_active = is_active;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
