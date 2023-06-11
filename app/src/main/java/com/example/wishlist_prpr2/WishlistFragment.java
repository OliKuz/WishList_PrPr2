package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.ApiProducts;
import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.GiftsAdapter;
import com.example.wishlist_prpr2.model.Gift;
import com.example.wishlist_prpr2.model.Product;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {
    private final HomeActivity homeActivity;
    private final Wishlist wishlist;
    private final List<Gift> gifts = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private GiftsAdapter giftsAdapter;
    private TextView wishlistName, wishlistDescription, wishlistDeadline, edit;
    private RecyclerView giftsRecyclerView;


    public WishlistFragment(HomeActivity homeActivity, Wishlist wishlist) {
        this.homeActivity = homeActivity;
        this.wishlist = wishlist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        wishlistName = view.findViewById(R.id.wishlist_name);
        wishlistDescription = view.findViewById(R.id.wishlist_description);
        wishlistDeadline = view.findViewById(R.id.wishlist_deadline);
        giftsRecyclerView = view.findViewById(R.id.wishlist_gifts_recycler_view);
        edit = view.findViewById(R.id.wishlist_edit);

        if(wishlist.getName() != null) {
            wishlistName.setText(wishlist.getName());
        }
        else{
            wishlistName.setText("No wishlists name");
        }
        if(wishlist.getDescription() != null) {
            wishlistDescription.setText(wishlist.getDescription());
        }
        else{
            wishlistDescription.setText("No description");
        }
        if(wishlist.getEnd_date() != null) {
            wishlistDeadline.setText(wishlist.getEnd_date().substring(0, 10));
        }
        else{
            wishlistDeadline.setText("No deadline");
        }

        if(wishlist.getUser_id() == CurrentUser.getInstance().getUser().getId()){
            edit.setVisibility(View.VISIBLE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new EditWishlistFragment(homeActivity, wishlist));
            }
        });

        getGifts();

        return view;
    }

    private void getGifts(){
        ApiSocial.getInstance().getAllGifts(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Gift>>(){
            @Override
            public void onResponse(@NonNull Call<List<Gift>> call, @NonNull Response<List<Gift>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<Gift> allGifts = response.body();

                    for (int i = 0; i < allGifts.size(); i++) {
                        if(allGifts.get(i).getWishlist_id() == wishlist.getId()){
                            gifts.add(allGifts.get(i));
                        }
                    }
                    getProducts(gifts);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Gift>> call, @NonNull Throwable t) {
                Toast.makeText(homeActivity, "Connection to API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProducts(List<Gift> gifts){
        for (int i = 0; i < gifts.size(); i++) {
            int id = Integer.parseInt(gifts.get(i).getProduct_url().substring(66));
            ApiProducts.getInstance().getProductByID(CurrentUser.getInstance().getApiToken(), id).enqueue(new Callback<Product>(){
                @Override
                public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        products.add(response.body());

                        if(products.size() == gifts.size()) {
                            displayGifts();
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                    Toast.makeText(homeActivity, "Connection to API Products failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayGifts(){
        boolean ownWishlists = wishlist.getUser_id() == CurrentUser.getInstance().getUser().getId();
        giftsAdapter = new GiftsAdapter(gifts, products, ownWishlists);
        giftsAdapter.setOnItemClickListener(new GiftsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: link with gift fragment
                //homeActivity.replaceFragment(new WishlistFragment(homeActivity, gifts.get(position)));
            }
            @Override
            public void onItemDeleted(int position) {
                ApiSocial.getInstance().deleteGift(CurrentUser.getInstance().getApiToken(), gifts.get(position).getId()).enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            gifts.remove(position);
                            products.remove(position);
                            giftsAdapter.notifyItemRemoved(position);
                            Toast.makeText(homeActivity, "Deleted gift from wishlists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(homeActivity, "Connection to API failed. Couldn't remove gift", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onItemReserved(int position) {
                ApiSocial.getInstance().reserveGift(CurrentUser.getInstance().getApiToken(), gifts.get(position).getId()).enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            gifts.get(position).setBooked(1);
                            giftsAdapter.notifyDataSetChanged();
                            Toast.makeText(homeActivity, "Gift successfully reserved", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(homeActivity, "Connection to API failed. Couldn't remove gift", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        giftsRecyclerView.setHasFixedSize(true);
        giftsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        giftsRecyclerView.setAdapter(giftsAdapter);
    }
}
