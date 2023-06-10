package com.example.wishlist_prpr2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.APIs.ApiProducts;
import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.model.Product;
import com.example.wishlist_prpr2.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewItemFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton;
    private EditText nameEditText, descriptionEditText, priceEditText, linkEditText, selectImageURL;

    public NewItemFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        nameEditText = view.findViewById(R.id.newItem_name);
        descriptionEditText = view.findViewById(R.id.newItem_description);
        priceEditText = view.findViewById(R.id.newItem_price);
        linkEditText = view.findViewById(R.id.newItem_link);
        saveButton = view.findViewById(R.id.newItem_save);
        selectImageURL = view.findViewById(R.id.newItem_imageURL);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String link = linkEditText.getText().toString();
                String imagePath = selectImageURL.getText().toString();

                if(name.isEmpty() || description.isEmpty() || price.isEmpty() || link.isEmpty()) {
                    Toast.makeText(homeActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Product product = new Product(name, description, link, imagePath, Integer.parseInt(price), 0);
                    ApiProducts.getInstance().createProduct(product).enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                            if (response.isSuccessful()) {
                                homeActivity.replaceFragment(new CreateFragment(homeActivity));
                                Toast.makeText(homeActivity, "Item successfully created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(homeActivity, "Error creating item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                            Toast.makeText(homeActivity, "Connection to API failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }
}
