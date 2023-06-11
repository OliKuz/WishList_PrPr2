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
import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.Wishlist;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWishlistFragment extends Fragment {
    private final HomeActivity homeActivity;
    private Button saveButton, deleteButton;
    private EditText nameEditText, descriptionEditText, deadlineEditText;
    private final Wishlist wishlist;

    public EditWishlistFragment(HomeActivity homeActivity, Wishlist wishlist) {
        this.homeActivity = homeActivity;
        this.wishlist = wishlist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_wishlist, container, false);

        nameEditText = view.findViewById(R.id.editWishlist_name);
        descriptionEditText = view.findViewById(R.id.editWishlist_description);
        deadlineEditText = view.findViewById(R.id.editWishlist_deadline);
        saveButton = view.findViewById(R.id.editWishlist_saveButton);
        deleteButton = view.findViewById(R.id.editWishlist_deleteButton);

        nameEditText.setText(wishlist.getName());
        descriptionEditText.setText(wishlist.getDescription());
        if(wishlist.getEnd_date() != null) {
            deadlineEditText.setText(wishlist.getEnd_date().substring(0, 10));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deadline = deadlineEditText.getText().toString();
                String datePattern = "\\d{4}-(0[1-9]|1[0-2])-\\d{2}";

                if (!deadline.matches(datePattern)){
                    Toast.makeText(homeActivity, "Please enter a date in the format yyyy-mm-dd", Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = nameEditText.getText().toString();
                    if(!wishlist.getName().equals(name)){
                        wishlist.setName(name);
                    }
                    String description = descriptionEditText.getText().toString();
                    if(!wishlist.getDescription().equals(description)){
                        wishlist.setDescription(description);
                    }
                    if(!wishlist.getEnd_date().equals(deadline)){
                        wishlist.setEnd_date(deadline);
                    }

                    ApiSocial.getInstance().updateWishlist(wishlist, CurrentUser.getInstance().getApiToken(), wishlist.getId()).enqueue(new Callback<Wishlist>(){
                        @Override
                        public void onResponse(@NonNull Call<Wishlist> call, @NonNull Response<Wishlist> response) {
                            if(response.isSuccessful()){
                                assert response.body() != null;
                                wishlist.update(response.body());

                                Toast.makeText(homeActivity, "Information successfully updated", Toast.LENGTH_SHORT).show();
                                homeActivity.replaceFragment(new WishlistFragment(homeActivity, wishlist));
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Wishlist> call, @NonNull Throwable t) {
                            Toast.makeText(homeActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiSocial.getInstance().deleteWishlist(CurrentUser.getInstance().getApiToken(), wishlist.getId()).enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(homeActivity, "Wishlist successfully deleted", Toast.LENGTH_SHORT).show();
                            homeActivity.replaceFragment(new ProfileFragment(homeActivity, CurrentUser.getInstance().getUser()));
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
