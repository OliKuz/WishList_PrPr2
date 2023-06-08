package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private Button friendsButton, wishlistsButton, reservedItemsButton;
    private TextView userName;
    private HomeActivity homeActivity;

    public ProfileFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        friendsButton = view.findViewById(R.id.profile_friends);
        wishlistsButton = view.findViewById(R.id.profile_wishlists);
        reservedItemsButton = view.findViewById(R.id.profile_items);
        userName = view.findViewById(R.id.profile_name);

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //homeActivity.replaceFragment(new FriendsFragment(homeActivity));
            }
        });


        return view;
    }
}
