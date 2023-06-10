package com.example.wishlist_prpr2.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.HomeActivity;
import com.example.wishlist_prpr2.ProfileFragment;
import com.example.wishlist_prpr2.R;
import com.example.wishlist_prpr2.model.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.UserViewHolder> {

    private List<User> usersList;
    //private OnItemClickListener listener;
    public static HomeActivity homeActivity;

    public FriendsAdapter(HomeActivity homeActivity, List<User> userList) {
        this.usersList = userList;
        FriendsAdapter.homeActivity = homeActivity;
        System.out.println("NUM ITEMS: " + getItemCount());
    }

    /*
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
     */


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_view, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User currentUser = usersList.get(position);
        holder.nameTextView.setText(currentUser.getName() + " " + currentUser.getLast_name());

        System.out.println("DID POSITION: " + position);
        //TODO: load image
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView profilePicture;

        public UserViewHolder(View userView /*, final OnItemClickListener listener*/) {
            super(userView);
            nameTextView = userView.findViewById(R.id.list_user_name);
            profilePicture = userView.findViewById(R.id.list_user_picture);

            /*
            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: go to user's view
                    homeActivity.replaceFragment(new ProfileFragment(homeActivity));
                }
            });
             */

        }
    }
}
