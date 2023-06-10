package com.example.wishlist_prpr2;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wishlist_prpr2.APIs.API;
import com.example.wishlist_prpr2.model.User;
import com.example.wishlist_prpr2.model.Wishlist;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private Button friendsButton, wishlistsButton, friendRequestsButton;
    private TextView userName;
    private ImageView profilePicture;
    private HomeActivity homeActivity;
    private int numFriends;
    private int numWishlists;
    private final List<User> friends = new ArrayList<>();

    public ProfileFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        friendsButton = view.findViewById(R.id.profile_friends);
        wishlistsButton = view.findViewById(R.id.profile_wishlists);
        friendRequestsButton = view.findViewById(R.id.profile_friend_requests);
        userName = view.findViewById(R.id.profile_name);
        profilePicture = view.findViewById(R.id.profile_picture);

        userName.setText(CurrentUser.getInstance().getUser().getName());
        countFriends();
        countWishlists();

        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());

                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                if (squaredBitmap != source) {
                    source.recycle();
                }

                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader = new BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                paint.setShader(shader);
                paint.setAntiAlias(true);

                float radius = size / 2f;
                canvas.drawCircle(radius, radius, radius, paint);

                squaredBitmap.recycle();
                return bitmap;
            }

            @Override
            public String key() {
                return "circle";
            }
        };

        Picasso.get().load(CurrentUser.getInstance().getUser().getImage()).transform(transformation).into(profilePicture);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new FriendsFragment(homeActivity, friends));
            }
        });
        friendRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: link with friends request fragment
                //homeActivity.replaceFragment(new FriendsRequestFragment(homeActivity));
            }
        });
        return view;
    }

    private void countFriends(){
        API.getInstance().getFriendsOfLoggedInUser(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    System.out.println("CURRENT API: " + CurrentUser.getInstance().getApiToken());
                    friendsButton.setText("Friends\n(0)");
                    List<User> friends = response.body();
                    for (int i = 0; i < friends.size(); i++) {
                        increaseNumFriends(friends.get(i));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
    }

    private void increaseNumFriends(User friend){
        numFriends++;
        friends.add(friend);
        friendsButton.setText("Friends\n(" + numFriends + ")");
    }

    private void countWishlists(){
        API.getInstance().getAllWishlists(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Wishlist>>(){
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    wishlistsButton.setText("Wishlists\n(0)");
                    List<Wishlist> wishlists = response.body();
                    for (int i = 0; i < wishlists.size(); i++) {
                        if(wishlists.get(i).getUser_id() == CurrentUser.getInstance().getUser().getId()){
                            increaseNumWishlists();
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {

            }
        });
    }

    private void increaseNumWishlists(){
        numWishlists++;
        wishlistsButton.setText("Wishlists\n(" + numWishlists + ")");
    }
}
