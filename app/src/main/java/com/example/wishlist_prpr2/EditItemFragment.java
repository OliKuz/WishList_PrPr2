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

import com.example.wishlist_prpr2.APIs.ApiProducts;
import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.model.Product;
import com.example.wishlist_prpr2.model.Wishlist;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditItemFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton, deleteButton;
    private Product product;
    private EditText nameEditText, descriptionEditText, priceEditText, linkEditText, imageEditText;

    public EditItemFragment(HomeActivity homeActivity, Product product) {
        this.homeActivity = homeActivity;
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        nameEditText = view.findViewById(R.id.editItem_name);
        descriptionEditText = view.findViewById(R.id.editItem_description);
        priceEditText = view.findViewById(R.id.editItem_price);
        linkEditText = view.findViewById(R.id.editItem_link);
        imageEditText = view.findViewById(R.id.editItem_image);
        saveButton = view.findViewById(R.id.editItem_save);
        deleteButton = view.findViewById(R.id.editItem_delete);

        nameEditText.setText(product.getName());
        descriptionEditText.setText(product.getDescription());
        priceEditText.setText(Float.toString(product.getPrice()));
        linkEditText.setText(product.getLink());
        imageEditText.setText(product.getImage());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                if(!name.equals(product.getName())){
                    product.setName(name);
                }
                String description = descriptionEditText.getText().toString();
                if(!description.equals(product.getDescription())){
                    product.setDescription(description);
                }
                String price = priceEditText.getText().toString();
                if(!price.equals(Float.toString(product.getPrice()))){
                    product.setPrice(Float.parseFloat(price));
                }
                String link = linkEditText.getText().toString();
                if(!link.equals(product.getLink())){
                    product.setLink(link);
                }

                ApiProducts.getInstance().updateProduct(product, product.getId()).enqueue(new Callback<Product>(){
                    @Override
                    public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            product.update(response.body());

                            Toast.makeText(homeActivity, "Item successfully updated", Toast.LENGTH_SHORT).show();
                            homeActivity.replaceFragment(new ItemFragment(homeActivity, product));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                        Toast.makeText(homeActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiProducts.getInstance().deleteProduct(product.getId()).enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(homeActivity, "Item successfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(homeActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}
