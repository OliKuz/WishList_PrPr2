package com.example.wishlist_prpr2;

import com.example.wishlist_prpr2.APIs.ApiToken;
import com.example.wishlist_prpr2.model.User;

public class CurrentUser {
    private static CurrentUser instance;
    private ApiToken myApiToken;
    private User user;

    public CurrentUser(){
    }

    public static synchronized CurrentUser getInstance(){
        if(instance == null){
            instance = new CurrentUser();
        }
        return instance;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void setApiToken(ApiToken mApiToken) {
        mApiToken.extension();
        this.myApiToken = mApiToken;
    }

    public User getUser() {
        return user;
    }

    public String getApiToken() {
        return myApiToken.getApiToken();
    }

    public void forgetUser() {
        user = null;
    }
}
