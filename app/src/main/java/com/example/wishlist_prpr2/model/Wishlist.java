package com.example.wishlist_prpr2.model;

import java.util.List;

public class Wishlist {
    private int id;
    private String name;
    private String description;
    private int user_id;
    private List<Gift> gifts;
    private String creation_date;
    private String end_date;

    public Wishlist(int id, String name, String description, int user_id, List<Gift> gifts, String creation_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user_id = user_id;
        this.gifts = gifts;
        this.creation_date = creation_date;
        this.end_date = end_date;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
