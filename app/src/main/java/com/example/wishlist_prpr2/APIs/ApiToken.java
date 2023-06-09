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

    private static final String URL = "https://balandrau.salle.url.edu/i3/socialgift/api-docs/v1/";
    private static APIService apiInterface;

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

    public static APIService getInstance() {
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

            apiInterface = retrofit.create(APIService.class);
        }

        return apiInterface;
    }
}
