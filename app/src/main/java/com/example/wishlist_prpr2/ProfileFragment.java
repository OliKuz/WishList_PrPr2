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

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.WishlistsAdapter;
import com.example.wishlist_prpr2.model.Gift;
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
    private Button friendsButton, wishlistsButton, reservedGiftsButton;
    private TextView userName;
    private ImageView profilePicture;
    private RecyclerView wishlistsRecyclerView;
    private HomeActivity homeActivity;
    private WishlistsAdapter wishlistsAdapter;
    private User user;
    private final List<User> friends = new ArrayList<>();
    private final List<Wishlist> wishlists = new ArrayList<>();
    private List<Gift> gifts = new ArrayList<>();

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
        reservedGiftsButton = view.findViewById(R.id.profile_reserved_gifts);
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
        countReservedGifts();

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

        String imagePath = user.getImage();
        if(imagePath == null || imagePath.isEmpty()) {
            imagePath = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
        }
        Picasso.get().load(imagePath).transform(transformation).into(profilePicture);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friends.size() > 0) {
                    homeActivity.replaceFragment(new FriendsFragment(homeActivity, friends));
                }
                else{
                    homeActivity.replaceFragment(new DiscoverFriendsFragment(homeActivity));
                }
            }
        });
        reservedGiftsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity.replaceFragment(new ReservedGiftsFragment(homeActivity, gifts));
            }
        });
        return view;
    }

    private void notCurrentUser() {
        friendsButton.setVisibility(View.GONE);
        reservedGiftsButton.setVisibility(View.GONE);
        wishlistsButton.setVisibility(View.GONE);
    }

    private void displayWishLists() {
        wishlistsAdapter = new WishlistsAdapter(wishlists);
        wishlistsAdapter.setOnItemClickListener(new WishlistsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                homeActivity.replaceFragment(new WishlistFragment(homeActivity, wishlists.get(position)));
            }
        });
        wishlistsRecyclerView.setHasFixedSize(true);
        wishlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistsRecyclerView.setAdapter(wishlistsAdapter);
    }

    private void countFriends(){
        ApiSocial.getInstance().getFriendsOfLoggedInUser(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<User> users = response.body();
                    friends.addAll(users);
                    friendsButton.setText("Friends\n(" + friends.size() + ")");
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
    }

    private void countWishlists(){
        ApiSocial.getInstance().getAllWishlists(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<Wishlist>>(){
            @Override
            public void onResponse(@NonNull Call<List<Wishlist>> call, @NonNull Response<List<Wishlist>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<Wishlist> responseWishlists = response.body();
                    for (int i = 0; i < responseWishlists.size(); i++) {
                        if(responseWishlists.get(i).getUser_id() == user.getId()){
                            wishlists.add(responseWishlists.get(i));
                        }
                    }
                    wishlistsButton.setText("Wishlists\n(" + wishlists.size() + ")");
                    displayWishLists();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Wishlist>> call, @NonNull Throwable t) {
            }
        });
    }

    private void countReservedGifts(){
        ApiSocial.getInstance().getGiftsReserved(CurrentUser.getInstance().getApiToken(), user.getId()).enqueue(new Callback<List<Gift>>(){
            @Override
            public void onResponse(@NonNull Call<List<Gift>> call, @NonNull Response<List<Gift>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    gifts = response.body();
                    reservedGiftsButton.setText("Reserved Gifts (" + gifts.size() + ")");
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Gift>> call, @NonNull Throwable t) {
                Toast.makeText(homeActivity, "Connection to API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
