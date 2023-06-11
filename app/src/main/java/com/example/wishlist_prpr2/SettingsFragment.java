package com.example.wishlist_prpr2;

import android.content.Intent;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    private HomeActivity homeActivity;
    private Button saveButton, deleteAccountButton, logoutButton;
    private EditText nameEditText, lastnameEditText, emailEditText, passwordEditText, imagePath;

    public SettingsFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameEditText = view.findViewById(R.id.settings_name);
        lastnameEditText = view.findViewById(R.id.settings_lastname);
        emailEditText = view.findViewById(R.id.settings_email);
        passwordEditText = view.findViewById(R.id.settings_password);
        imagePath = view.findViewById(R.id.settings_image);
        saveButton = view.findViewById(R.id.settings_save);
        logoutButton = view.findViewById(R.id.settings_logout);
        deleteAccountButton = view.findViewById(R.id.settings_deleteAccount);

        nameEditText.setText(CurrentUser.getInstance().getUser().getName());
        lastnameEditText.setText(CurrentUser.getInstance().getUser().getLast_name());
        emailEditText.setText(CurrentUser.getInstance().getUser().getEmail());
        System.out.println("PASSWORD IS: " + CurrentUser.getInstance().getUser().getPassword());
        passwordEditText.setText(CurrentUser.getInstance().getUser().getPassword());
        imagePath.setText(CurrentUser.getInstance().getUser().getImage());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();

                if (password.length() < 9) {
                    Toast.makeText(homeActivity, "Please make sure your password is longer than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    String name = nameEditText.getText().toString();
                    if (!name.equals(CurrentUser.getInstance().getUser().getName())){
                        CurrentUser.getInstance().getUser().setName(name);
                    }
                    String lastname = lastnameEditText.getText().toString();
                    if (!lastname.equals(CurrentUser.getInstance().getUser().getLast_name())){
                        CurrentUser.getInstance().getUser().setLastname(lastname);
                    }
                    String email = emailEditText.getText().toString();
                    if (!email.equals(CurrentUser.getInstance().getUser().getEmail())){
                        CurrentUser.getInstance().getUser().setEmail(email);
                    }
                    if (!password.equals(CurrentUser.getInstance().getUser().getPassword())){
                        CurrentUser.getInstance().getUser().setPassword(password);
                    }

                    ApiSocial.getInstance().updateUser(CurrentUser.getInstance().getUser(),
                            CurrentUser.getInstance().getApiToken()).enqueue(new Callback<User>(){
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if(response.isSuccessful()){
                                assert response.body() != null;
                                CurrentUser.getInstance().updateUser(response.body());
                                CurrentUser.getInstance().getUser().setPassword(password);
                                Toast.makeText(homeActivity, "Information successfully updated", Toast.LENGTH_SHORT).show();
                                homeActivity.replaceFragment(new HomeFragment(homeActivity));
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(homeActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.getInstance().setApiToken(null);
                CurrentUser.getInstance().forgetUser();
                Toast.makeText(homeActivity, "Successfully logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(homeActivity, LoginActivity.class);
                startActivity(intent);
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiSocial.getInstance().deleteUser(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(homeActivity, "Account successfully deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(homeActivity, LoginActivity.class);
                            startActivity(intent);
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
