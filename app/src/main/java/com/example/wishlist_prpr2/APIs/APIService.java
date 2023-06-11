package com.example.wishlist_prpr2.APIs;

import com.example.wishlist_prpr2.model.Gift;
import com.example.wishlist_prpr2.model.Reservation;
import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.UserObject;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @GET("users")
    Call<List<User>> getAllUsers(@Header("Authorization") String apiToken);
    @GET("users/{ID}")
    Call<List<User>> getUser(@Header("Authorization") String apiToken, @Path("ID") long userId);
    @DELETE("users")
    Call<ResponseBody> deleteUser(@Header("Authorization") String apiToken);
    @PUT("users")
    Call<User> updateUser(@Body User user, @Header("Authorization") String apiToken);
    @GET("users/{ID}/friends")
    Call<List<User>> getFriends(@Header("Authorization") String apiToken, @Path("ID") long userId);
    @GET("users/{ID}/gifts/reserved")
    Call<List<Gift>> getGiftsReserved(@Header("Authorization") String apiToken, @Path("ID") long userId);
    @GET("users/{ID}/wishlists")
    Call<List<Wishlist>> getWishlists(@Header("Authorization") String apiToken, @Path("ID") long userId);


    // WISHLISTS
    @POST("wishlists")
    Call<Wishlist> createWishlist(@Header("Authorization") String apiToken, @Body Wishlist wishlist);
    @GET("wishlists")
    Call<List<Wishlist>> getAllWishlists(@Header("Authorization") String apiToken);
    @GET("wishlists/{ID}")
    Call<Wishlist> getWishlist(@Header("Authorization") String apiToken, @Path("ID") long wishlistId);
    @PUT("wishlists/{ID}")
    Call<Wishlist> updateWishlist(@Body Wishlist wishlist, @Header("Authorization") String apiToken, @Path("ID") long wishlistId);
    @DELETE("wishlists/{ID}")
    Call<ResponseBody> deleteWishlist(@Header("Authorization") String apiToken, @Path("ID") long wishlistId);

    // RESERVATIONS
    @POST("reservations/{gift_ID}/{user_ID}")
    Call<Reservation> createReservation(@Header("Authorization") String apiToken,@Body Reservation reservation, @Path("gift_ID") long giftId, @Path("user_ID") long userId);

    @DELETE("reservations/{gift_ID}/{user_ID}")
    Call<ResponseBody> deleteReservation(@Header("Authorization") String apiToken, @Path("gift_ID") long giftId, @Path("user_ID") long userId);

    // GIFTS
    @POST("gifts")
    Call<Gift> createGift(@Header("Authorization") String apiToken, @Body Gift gift);
    @GET("gifts")
    Call<List<Gift>> getAllGifts(@Header("Authorization") String apiToken);
    @GET("gifts/{ID}/user")
    Call<User> getUserByGiftID(@Header("Authorization") String apiToken, @Path("ID") long giftId);
    @GET("gifts/{ID}")
    Call<Gift> getGiftByID(@Header("Authorization") String apiToken, @Path("ID") long giftId);
    @PUT("gifts/{ID}")
    Call<Gift> updateGift(@Body Gift gift, @Header("Authorization") String apiToken, @Path("ID") long giftId);
    @DELETE("gifts/{ID}")
    Call<ResponseBody> deleteGift(@Header("Authorization") String apiToken, @Path("ID") long giftId);
    @POST("gifts/{ID}/book")
    Call<ResponseBody> reserveGift(@Header("Authorization") String apiToken, @Path("ID") long giftId);

    //FRIENDS
    @GET("friends")
    Call<List<User>> getFriendsOfLoggedInUser(@Header("Authorization") String apiToken);

}
