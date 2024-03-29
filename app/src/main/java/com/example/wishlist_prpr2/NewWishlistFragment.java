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

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.model.Wishlist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewWishlistFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton;
    private EditText nameEditText, descriptionEditText, deadlineEditText;

    public NewWishlistFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_wishlist, container, false);

        nameEditText = view.findViewById(R.id.newWishlist_name);
        descriptionEditText = view.findViewById(R.id.newWishlist_description);
        deadlineEditText = view.findViewById(R.id.newWishlist_deadline);
        saveButton = view.findViewById(R.id.newWishlist_saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String deadline = deadlineEditText.getText().toString();

                String datePattern = "\\d{2}/(0[1-9]|1[0-2])/\\d{4}";

                if(name.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                    Toast.makeText(homeActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!deadline.matches(datePattern)){
                    Toast.makeText(homeActivity, "Please enter a date in the format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                }
                else {
                    Wishlist wishlist = new Wishlist(name, description, deadline);
                    ApiSocial.getInstance().createWishlist(CurrentUser.getInstance().getApiToken(), wishlist).enqueue(new Callback<Wishlist>() {
                        @Override
                        public void onResponse(@NonNull Call<Wishlist> call, @NonNull Response<Wishlist> response) {
                            if (response.isSuccessful()) {
                                homeActivity.replaceFragment(new CreateFragment(homeActivity));
                                Toast.makeText(homeActivity, "Wishlist successfully created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(homeActivity, "Failed to create wishlist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Wishlist> call, @NonNull Throwable t) {
                            Toast.makeText(homeActivity, "Connection to API failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }
}
