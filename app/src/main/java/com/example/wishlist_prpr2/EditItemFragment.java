package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.model.Product;

public class EditItemFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton, deleteButton;
    private Product product;
    private EditText nameEditText, descriptionEditText, priceEditText, priorityEditText, linkEditText, listEditText;

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
        priorityEditText = view.findViewById(R.id.editItem_priority);
        linkEditText = view.findViewById(R.id.editItem_link);
        listEditText = view.findViewById(R.id.editItem_list);
        saveButton = view.findViewById(R.id.editItem_save);
        deleteButton = view.findViewById(R.id.editItem_delete);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String link = linkEditText.getText().toString();
                String priority = priorityEditText.getText().toString();
                String list = listEditText.getText().toString();

                if(name.isEmpty() || description.isEmpty() || price.isEmpty() || link.isEmpty() || priority.isEmpty() || list.isEmpty()) {
                    Toast.makeText(homeActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO: update item in API
                    Toast.makeText(homeActivity, "Item successfully updated", Toast.LENGTH_SHORT).show();
                    // TODO: go back to previous fragment
                    homeActivity.replaceFragment(new CreateFragment(homeActivity));
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete item in API
                Toast.makeText(homeActivity, "Item successfully deleted", Toast.LENGTH_SHORT).show();
                // TODO: go back to previous fragment
                homeActivity.replaceFragment(new CreateFragment(homeActivity));
            }
        });
        return view;
    }
}
