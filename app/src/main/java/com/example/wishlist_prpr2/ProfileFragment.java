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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.API;
import com.example.wishlist_prpr2.adapters.WishlistsAdapter;
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
    private RecyclerView wishlistsRecyclerView;
    private HomeActivity homeActivity;
    private WishlistsAdapter wishlistsAdapter;
    private User user;
    private final List<User> friends = new ArrayList<>();
    private final List<Wishlist> wishlists = new ArrayList<>();

    public ProfileFragment(HomeActivity homeActivity, User user) {
        this.homeActivity = homeActivity;
        this.user = user;
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
        wishlistsRecyclerView = view.findViewById(R.id.profile_wishlists_recyclerview);

        userName.setText(user.getName());
        if(user.equals(CurrentUser.getInstance().getUser())) {
            countFriends();
        }
        else{
            notCurrentUser();
        }
        countWishlists();
        displayWishLists();
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

        Picasso.get().load(user.getImage()).transform(transformation).into(profilePicture);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friends.size() > 0) {
                    homeActivity.replaceFragment(new FriendsFragment(homeActivity, friends));
                }
                else{
                    Toast.makeText(homeActivity, "You have no friends yet", Toast.LENGTH_SHORT).show();
                }
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

    private void notCurrentUser() {
        friendsButton.setVisibility(View.GONE);
        friendRequestsButton.setVisibility(View.GONE);
        wishlistsButton.setVisibility(View.GONE);
    }

    private void displayWishLists() {
        /*
        wishlistsAdapter = new WishlistsAdapter(homeActivity, wishlists);
        wishlistsRecyclerView.setAdapter(wishlistsAdapter);
        wishlistsRecyclerView.setHasFixedSize(true);
        wishlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         */
    }

    private void countFriends(){
        API.getInstance().getFriendsOfLoggedInUser(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
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
        friends.add(friend);
        friendsButton.setText("Friends\n(" + friends.size() + ")");
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
                        if(wishlists.get(i).getUser_id() == user.getId()){
                            increaseNumWishlists(wishlists.get(i));
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {

            }
        });
    }

    private void increaseNumWishlists(Wishlist wishlist){
        wishlists.add(wishlist);
        wishlistsButton.setText("Wishlists\n(" + wishlists.size() + ")");
    }
}
