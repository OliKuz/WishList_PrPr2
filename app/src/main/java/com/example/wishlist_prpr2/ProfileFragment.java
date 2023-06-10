package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.APIs.API;
import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private Button friendsButton, wishlistsButton, friendRequestsButton;
    private TextView userName;
    private HomeActivity homeActivity;
    private int numFriends;
    private int numWishlists;

    public ProfileFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        friendsButton = view.findViewById(R.id.profile_friends);
        wishlistsButton = view.findViewById(R.id.profile_wishlists);
        friendRequestsButton = view.findViewById(R.id.profile_friend_requests);
        userName = view.findViewById(R.id.profile_name);

        userName.setText(CurrentUser.getInstance().getUser().getName());
        countFriends();
        countWishlists();
        // TODO: load photo
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new FriendsFragment(homeActivity));
            }
        });
        friendRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: link with friends request fragment
                //homeActivity.replaceFragment(new FriendsRequestFragment(homeActivity));
            }
        });
        return view;
    }

    private void countFriends(){
        API.getInstance().getFriendsOfLoggedInUser(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    increaseNumFriends();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
    }

    private void increaseNumFriends(){
        numFriends++;
        friendsButton.setText("Friends (" + numFriends + ")");
    }

    private void countWishlists(){
        API.getInstance().getAllWishlists(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Wishlist>>(){
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<Wishlist> wishlists = response.body();
                    for (int i = 0; i < wishlists.size(); i++) {
                        if(wishlists.get(i).getUser_id() == CurrentUser.getInstance().getUser().getId()){
                            increaseNumWishlists();
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {

            }
        });
    }

    private void increaseNumWishlists(){
        numWishlists++;
        wishlistsButton.setText("Wishlists (" + numWishlists + ")");
    }
}
