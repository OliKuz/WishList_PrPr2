package com.example.wishlist_prpr2.model;

import com.example.wishlist_prpr2.APIs.ApiToken;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    private static User user;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("last_name")
    private String last_name;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("image")
    private String image;
    private ApiToken myApiToken;

    public User(){
    }
    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
        user = new User();
    }

    public void updateUser(User user) {
        this.id = user.id;
        this.myApiToken = user.myApiToken;
        this.name = user.name;
        this.last_name = user.last_name;
        this.email = user.email;
        this.password = user.password;
        this.image = user.image;
    }

    public static User getUser(){
        if(user == null){
            return new User();
        }
        return user;
    }

    public void setApiToken(ApiToken mApiToken) {
        System.out.println("ApiToken: " + mApiToken.getApiToken());
        mApiToken.extension();
        this.myApiToken = mApiToken;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ApiToken getMyApiToken() {
        return myApiToken;
    }

    public void setMyApiToken(ApiToken myApiToken) {
        this.myApiToken = myApiToken;
    }
}
