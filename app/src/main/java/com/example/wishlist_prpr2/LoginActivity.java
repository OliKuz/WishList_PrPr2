package com.example.wishlist_prpr2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.APIs.ApiToken;
import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.UserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginLayout, signupLayout;
    private EditText emailEditText, emailLogEditText, passwordEditText, passwordLogEditText, nameEditText, confirmPasswordEditText, dobEditText, lastnameEditText;
    private Button loginButton, signupButton, selectPhotoButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String imagePath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginLayout = findViewById(R.id.login_layout);
        signupLayout = findViewById(R.id.signup_layout);

        emailLogEditText = findViewById(R.id.email_edit_text);
        emailEditText = findViewById(R.id.s_email_edit_text);
        lastnameEditText = findViewById(R.id.lastname_edit_text);
        passwordEditText = findViewById(R.id.s_password_edit_text);
        passwordLogEditText = findViewById(R.id.password_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);

        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        selectPhotoButton = findViewById(R.id.select_photo_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogEditText.getText().toString();
                String password = passwordLogEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    ApiSocial.getInstance().authenticationUser(new UserObject(email, password)).enqueue(new Callback<ApiToken>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiToken> call, @NonNull Response<ApiToken> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                getUser(response.body(), email, password);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<ApiToken> call, @NonNull Throwable t) {
                            Toast.makeText(LoginActivity.this, "Connection to API failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String lastname = lastnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String dob = dobEditText.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() /*|| dob.isEmpty()*/) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String datePattern = "\\d{2}/(0[1-9]|1[0-2])/\\d{4}";
                    if (!dob.matches(datePattern)) {
                        Toast.makeText(LoginActivity.this, "Please enter date of birth in the format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 9){
                        Toast.makeText(LoginActivity.this, "Please make sure your password is longer than 8 characters", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirmPassword)){
                        Toast.makeText(LoginActivity.this, "Please make sure your password confirmation matches your password", Toast.LENGTH_SHORT).show();
                    } else {
                        if (imagePath == null) {
                            imagePath = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
                        }
                        User user = new User(name, lastname, email, password, dob, imagePath);
                        ApiSocial.getInstance().registerUser(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                                if (response.isSuccessful()) {
                                    authenticationUser(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                                Toast.makeText(LoginActivity.this, "Connection to API failed", Toast.LENGTH_SHORT).show();
                            }
                        });
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
            imagePath = getPathFromUri(selectedImageUri);
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    private void getUser(ApiToken apiToken, String email, String password) {
        ApiSocial.getInstance().searchUser("Bearer " + apiToken.getApiToken(), email).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    User user = response.body().get(0);
                    user.setPassword(password);
                    CurrentUser.getInstance().updateUser(user);
                    CurrentUser.getInstance().setApiToken(apiToken);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection to API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void authenticationUser(User user) {
        UserObject userObject = new UserObject(user.getEmail(), user.getPassword());
        ApiSocial.getInstance().authenticationUser(userObject).enqueue(new Callback<ApiToken>() {
            @Override
            public void onResponse(@NonNull Call<ApiToken> call, @NonNull Response<ApiToken> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    CurrentUser.getInstance().setApiToken(response.body());
                    CurrentUser.getInstance().updateUser(user);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ApiToken> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection to API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}