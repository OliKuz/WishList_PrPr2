package com.example.wishlist_prpr2.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProducts {
    private static final String URL = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/";
    private static APIServiceProducts apiInterface;

    public static APIServiceProducts getInstance() {
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
