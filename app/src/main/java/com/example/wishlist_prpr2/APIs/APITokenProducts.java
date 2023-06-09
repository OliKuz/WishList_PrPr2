package com.example.wishlist_prpr2.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APITokenProducts {

    private static final String URL = "https://balandrau.salle.url.edu/i3/mercadoexpress/api-docs/v1/";
    private static APIServiceProducts apiInterface;

    @Expose
    @SerializedName("accessTokenProducts")
    private String apiTokenProducts;

    public APITokenProducts(String apiTokenProducts) {
        this.apiTokenProducts = apiTokenProducts;
    }

    public String getApiProductsToken() {
        return apiTokenProducts;
    }

    public void extensionProducts() {
        if (apiTokenProducts != null && !apiTokenProducts.startsWith("Bearer")) {
            this.apiTokenProducts = "Bearer " + apiTokenProducts;
        }
    }

    public static APIServiceProducts getInstanceProduct() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                    .build();

            apiInterface = retrofit.create(APIServiceProducts.class);
        }

        return apiInterface;
    }
}
