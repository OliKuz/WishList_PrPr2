package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.model.Product;
import com.squareup.picasso.Picasso;

public class ItemFragment extends Fragment {
    private HomeActivity homeActivity;
    private Product product;
    private TextView name, description, price, link;
    private ImageView image;
    private ImageButton addToWishlist;
    private Button edit;

    public ItemFragment(HomeActivity homeActivity, Product product) {
        this.homeActivity = homeActivity;
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);


        name = view.findViewById(R.id.item_name);
        description = view.findViewById(R.id.item_description);
        price = view.findViewById(R.id.item_price);
        link = view.findViewById(R.id.item_link);
        image = view.findViewById(R.id.item_image);
        addToWishlist = view.findViewById(R.id.item_add_to_wishlist);
        edit = view.findViewById(R.id.item_edit);

        if(product.getName() != null) {
            name.setText(product.getName());
        }
        else{
            name.setText("No Name");
        }
        if(product.getDescription() != null) {
            description.setText(product.getDescription());
        }
        else{
            description.setText("No description");
        }
        price.setText(Float.toString(product.getPrice()) + "€");
        link.setText(product.getLink());

        String imagePath = product.getImage();
        if(imagePath == null || imagePath.isEmpty() || imagePath.equals("null")) {
            Picasso.get().load(imagePath).into(image);
        }
        else {
            image.setVisibility(View.GONE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.replaceFragment(new EditItemFragment(homeActivity, product));
            }
        });

        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.replaceFragment(new AddToWishlistFragment(homeActivity, product));
            }
        });

        return view;
    }
}
