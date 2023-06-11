package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.Wishlist;

public class WishlistFragment extends Fragment {
    private HomeActivity homeActivity;
    private Wishlist wishlist;
    private TextView wishlistName, wishlistDescription, wishlistDeadline;

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

        return view;
    }

}
