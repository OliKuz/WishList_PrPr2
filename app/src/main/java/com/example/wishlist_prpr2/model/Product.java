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
    private float price;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("photo")
    private String image;
    @Expose
    @SerializedName("categoryIds")
    private int[] categoryIds;
    @Expose
    @SerializedName("is_active")
    private int is_active;

    public Product(String name, String description, String link,  String image, float price, int[] categoryID) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.image = image;
        this.categoryIds = categoryID;
        this.is_active = 1;
    }

    public int[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(int[] categoryID) {
        this.categoryIds = categoryID;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public int isActive() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public void update(Product newProduct) {
        this.id = newProduct.getId();
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.price = newProduct.getPrice();
        this.link = newProduct.getLink();
        this.image = newProduct.getImage();
        this.categoryIds = newProduct.getCategoryIds();
        this.is_active = newProduct.isActive();
    }
}
