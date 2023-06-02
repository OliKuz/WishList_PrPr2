package com.example.wishlist_prpr2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginLayout, signupLayout;
    private EditText emailEditText, passwordEditText, nameEditText, confirmPasswordEditText, dobEditText;
    private Button loginButton, signupButton, selectPhotoButton;
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginLayout = findViewById(R.id.login_layout);
        signupLayout = findViewById(R.id.signup_layout);

        emailEditText = findViewById(R.id.s_email_edit_text);
        passwordEditText = findViewById(R.id.s_password_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);

        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        selectPhotoButton = findViewById(R.id.select_photo_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //TODO check for the validity of the existence of the user
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String dob = dobEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String datePattern = "\\d{2}/(0[1-9]|1[0-2])/\\d{4}";
                    if (!dob.matches(datePattern)) {
                        Toast.makeText(LoginActivity.this, "Please enter date of birth in the format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (password.length() < 9){
                        Toast.makeText(LoginActivity.this, "Please make sure your password is longer than 8 characters", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!password.equals(confirmPassword)){
                        Toast.makeText(LoginActivity.this, "Please make sure your password confirmation matches your password", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //TODO add condition to check if the account already exists
                    }

                }
            }
        });

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the photo selection from the device's gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    public void onSelectionClicked(View view) {
        switch (view.getId()) {
            case R.id.login_selection:
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.GONE);
                break;
            case R.id.signup_selection:
                loginLayout.setVisibility(View.GONE);
                signupLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            //picture selected option
        }
    }
}