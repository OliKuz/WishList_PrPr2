package com.example.wishlist_prpr2.APIs;

import com.example.wishlist_prpr2.model.Category;
import com.example.wishlist_prpr2.model.Product;
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

public interface APIServiceProducts {

    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/search")
    Call<List<Product>> searchProducts(@Header("Authorization") String apiToken, @Query("s") String name);

    @GET("products/{ID}")
    Call<Product> getProductByID(@Header("Authorization") String token, @Path("ID") long productId);

    @DELETE("products/{ID}")
    Call<ResponseBody> deleteProduct(@Header("Authorization") String token, @Path("ID") long productId);

    @PUT("products/{ID}")
    Call<Product> updateProduct(@Body Product product, @Header("Authorization") String token, @Path("ID") long productId);

    //Categories
    @POST("categories")
    Call<APITokenProducts> createCategory(@Body Category category);

    @GET("categories")
    Call<List<Category>> getAllCategories(@Header("Authorization") String token);

    @GET("categories/{ID}")
    Call<Category> getCategoryByID(@Header("Authorization") String token, @Path("ID") long categoryId);

    @DELETE("category/{ID}")
    Call<ResponseBody> deleteCategory(@Header("Authorization") String token, @Path("ID") long categoryId);

    @PUT("category/{ID}")
    Call<Category> updateCategory(@Body Product product, @Header("Authorization") String token, @Path("ID") long categoryId);


}
