package com.example.wishlist_prpr2.model;

public class User {
    private int id;
    private String name;
    private String last_name;
    private String password;
    private String email;
    private String image;

    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }
}
