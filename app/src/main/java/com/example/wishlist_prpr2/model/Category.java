package com.example.wishlist_prpr2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    private int id;

    private String name;

    private String description;

    private String photo;

    private int categoryParentID;


    public Category(int id, String name, String description, String photo, int categoryParentID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.categoryParentID = categoryParentID;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCategoryParentID() {
        return categoryParentID;
    }

    public void setCategoryParentID(int categoryParentID) {
        this.categoryParentID = categoryParentID;
    }
}
