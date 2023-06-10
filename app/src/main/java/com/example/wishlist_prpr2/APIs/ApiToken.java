package com.example.wishlist_prpr2.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiToken {
    @Expose
    @SerializedName("accessToken")
    private String apiToken;

    public ApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void extension() {
        if (apiToken != null && !apiToken.startsWith("Bearer")) {
            this.apiToken = "Bearer " + apiToken;
        }
    }


}
