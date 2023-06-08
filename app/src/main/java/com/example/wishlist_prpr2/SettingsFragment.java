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

public class SettingsFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton, photoButton, logOutButton;
    private EditText nameEditText, emailEditText, passwordEditText;

    public SettingsFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameEditText = view.findViewById(R.id.settings_name);
        emailEditText = view.findViewById(R.id.settings_email);
        passwordEditText = view.findViewById(R.id.settings_password);
        saveButton = view.findViewById(R.id.settings_save);
        photoButton = view.findViewById(R.id.settings_photo);
        logOutButton = view.findViewById(R.id.settings_logout);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(homeActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 9) {
                    Toast.makeText(homeActivity, "Please make sure your password is longer than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    // TODO: update user in API
                    Toast.makeText(homeActivity, "Information successfully updated", Toast.LENGTH_SHORT).show();
                    homeActivity.replaceFragment(new ProfileFragment(homeActivity));
                }
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: log out user
                Intent intent = new Intent(homeActivity, LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
