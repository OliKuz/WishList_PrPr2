package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.WishlistsAdapter;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HomeActivity homeActivity;
    private ImageButton settingsButton;
    private TextView usernameTextView;
    private RecyclerView wishlistsRecyclerView;
    private WishlistsAdapter wishlistsAdapter;
    private List<Wishlist> wishlists = new ArrayList<>();

    public HomeFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        settingsButton = view.findViewById(R.id.home_settings);
        usernameTextView = view.findViewById(R.id.home_username);
        wishlistsRecyclerView = view.findViewById(R.id.home_wishlists_recycler_view);

        usernameTextView.setText("Hi, " + CurrentUser.getInstance().getUser().getName() + "!");
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new SettingsFragment(homeActivity));
            }
        });

        getWishlists();

        return view;
    }

    private void getWishlists(){
        ApiSocial.getInstance().getAllWishlists(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Wishlist>>(){
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    wishlists = response.body();
                    displayWishLists();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {
            }
        });
    }

    private void displayWishLists(){
        wishlistsAdapter = new WishlistsAdapter(wishlists);
        wishlistsAdapter.setOnItemClickListener(new WishlistsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO: link with wishlist fragment
                //homeActivity.replaceFragment(new WishlistFragment(homeActivity, userList.get(position)));
            }
        });
        wishlistsRecyclerView.setHasFixedSize(true);
        wishlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistsRecyclerView.setAdapter(wishlistsAdapter);
    }
}
