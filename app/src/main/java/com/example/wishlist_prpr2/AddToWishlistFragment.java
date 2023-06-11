package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.WishlistsAdapter;
import com.example.wishlist_prpr2.model.Gift;
import com.example.wishlist_prpr2.model.Product;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToWishlistFragment extends Fragment {
    private HomeActivity homeActivity;
    private Product product;
    private RecyclerView wishlistsRecyclerView;
    private List<Wishlist> wishlists = new ArrayList<>();
    private WishlistsAdapter wishlistsAdapter;
    private EditText priority;
    private Button save;
    private boolean hasSelected;
    private Wishlist selectedWishlist;

    public AddToWishlistFragment(HomeActivity homeActivity, Product product) {
        this.homeActivity = homeActivity;
        this.product = product;
        hasSelected = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_wishlist, container, false);

        priority = view.findViewById(R.id.add_to_wishlist_priority);
        wishlistsRecyclerView = view.findViewById(R.id.add_to_wishlist_recyclerview);
        save = view.findViewById(R.id.add_to_wishlist_save);

        displayMyWishlists();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = priority.getText().toString();
                if(!hasSelected){
                    Toast.makeText(homeActivity, "Choose a wishlists first", Toast.LENGTH_SHORT).show();
                }
                else if(p.isEmpty()){
                    Toast.makeText(homeActivity, "Priority is required", Toast.LENGTH_SHORT).show();
                    priority.requestFocus();
                }
                else{
                    Gift gift = new Gift(selectedWishlist.getId(), "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + product.getId(), Integer.parseInt(p));
                    ApiSocial.getInstance().createGift(CurrentUser.getInstance().getApiToken(), gift).enqueue(new Callback<Gift>(){
                        @Override
                        public void onResponse(@NonNull Call<Gift> call, @NonNull Response<Gift> response) {
                            if(response.isSuccessful()){
                                assert response.body() != null;
                                gift.update(response.body());

                                Toast.makeText(homeActivity, "Item successfully added to wishlist", Toast.LENGTH_SHORT).show();
                                homeActivity.replaceFragment(new HomeFragment(homeActivity));
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Gift> call, @NonNull Throwable t) {
                            Toast.makeText(homeActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void displayMyWishlists() {
        ApiSocial.getInstance().getAllWishlists(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Wishlist>>(){
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<Wishlist> responseWishlists = response.body();
                    for (int i = 0; i < responseWishlists.size(); i++) {
                        if(responseWishlists.get(i).getUser_id() == CurrentUser.getInstance().getUser().getId()){
                            wishlists.add(responseWishlists.get(i));
                        }
                    }
                    displayWishLists();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {
            }
        });
    }

    private void displayWishLists() {
        wishlistsAdapter = new WishlistsAdapter(wishlists);
        wishlistsAdapter.setOnItemClickListener(new WishlistsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                hasSelected = true;
                selectedWishlist = wishlists.get(position);
                wishlists.clear();
                wishlists.add(selectedWishlist);
                wishlistsAdapter.notifyDataSetChanged();
            }
        });
        wishlistsRecyclerView.setHasFixedSize(true);
        wishlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistsRecyclerView.setAdapter(wishlistsAdapter);
    }
}
