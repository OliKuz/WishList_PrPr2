package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EditWishlistFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton, deleteButton;
    private EditText nameEditText, descriptionEditText, deadlineEditText;

    public EditWishlistFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
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
                    // TODO: update wishlist in API
                    Toast.makeText(homeActivity, "Wishlist successfully updated", Toast.LENGTH_SHORT).show();
                    // TODO: go back to previous fragment
                    homeActivity.replaceFragment(new CreateFragment(homeActivity));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete wishlist in API
                Toast.makeText(homeActivity, "Wishlist successfully deleted", Toast.LENGTH_SHORT).show();
                // TODO: go back to previous fragment
                homeActivity.replaceFragment(new CreateFragment(homeActivity));
            }
        });
        return view;
    }
}
