package com.example.wishlist_prpr2.APIs;

import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.UserObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("users")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<ApiToken> authenticationUser(@Body UserObject userObject);

    @GET("users/search/")
    Call<List<User>> searchUser(@Header("Authorization") String apiToken, @Query("s") String name);

    @GET("users/{ID}")
    Call<List<User>> getUser(@Header("Authorization") String token, @Path("ID") long userId);

    @DELETE("users")
    Call<ResponseBody> deleteUser(@Header("Authorization") String token);

    @PUT("users")
    Call<User> updateUser(@Body User user, @Header("Authorization") String token);

    // FRIENDS
    @GET("friends")
    Call<List<User>> getFriends(@Header("Authorization") String token);

    // WISHLISTS

    // RESERVATIONS

    // GIFTS




}
