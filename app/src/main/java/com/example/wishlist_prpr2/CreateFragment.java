package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class CreateFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button newItemButton, newWishlistButton;
    public CreateFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        newItemButton = view.findViewById(R.id.create_newItemButton);
        newWishlistButton = view.findViewById(R.id.create_newWhishlistsButton);

        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new NewItemFragment(homeActivity));
            }
        });

        newWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new NewWishlistFragment(homeActivity));
            }
        });
        return view;
    }
}
