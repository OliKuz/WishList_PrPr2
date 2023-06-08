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

import androidx.fragment.app.Fragment;

public class NewItemFragment extends Fragment {

    private HomeActivity homeActivity;
    private Button saveButton, selectImageButton;
    private EditText nameEditText, descriptionEditText, priceEditText, linkEditText;

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
        saveButton = view.findViewById(R.id.newItem_saveButton);
        selectImageButton = view.findViewById(R.id.newItem_selectImage);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String link = linkEditText.getText().toString();

                if(name.isEmpty() || description.isEmpty() || price.isEmpty() || link.isEmpty()) {
                    Toast.makeText(homeActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO: save item to API
                    homeActivity.replaceFragment(new CreateFragment(homeActivity));
                    Toast.makeText(homeActivity, "Item successfully created", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
