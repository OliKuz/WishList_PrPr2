package com.example.wishlist_prpr2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.CurrentUser;
import com.example.wishlist_prpr2.R;
import com.example.wishlist_prpr2.model.Wishlist;

import java.util.List;

public class WishlistsAdapter extends RecyclerView.Adapter<WishlistsAdapter.WishlistViewHolder> {
    private final List<Wishlist> wishlists;
    private OnItemClickListener listener;

    public WishlistsAdapter(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wishlist_view, parent, false);
        return new WishlistViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        Wishlist wishlist = wishlists.get(position);
        if(wishlist.getName() != null) {
            holder.nameTextView.setText(wishlist.getName());
        }
        else{
            holder.nameTextView.setText("Wishlist Without Name");
        }
        if(wishlist.getDescription() != null) {
            holder.descriptionTextView.setText(wishlist.getDescription());
        }
        else{
            holder.descriptionTextView.setText("No description");
        }

        if(wishlist.getEnd_date() != null) {
            holder.deadlineTextView.setText(wishlist.getEnd_date().substring(0, 10));
        }
        else{
            holder.deadlineTextView.setText("No deadline");
        }

        holder.wishlistLayout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView deadlineTextView;
        LinearLayout wishlistLayout;

        public WishlistViewHolder(View wishlistView, final OnItemClickListener listener) {
            super(wishlistView);
            wishlistLayout = wishlistView.findViewById(R.id.list_wishlist_layout);
            nameTextView = wishlistView.findViewById(R.id.list_wishlist_name);
            descriptionTextView = wishlistView.findViewById(R.id.list_wishlist_description);
            deadlineTextView = wishlistView.findViewById(R.id.list_wishlist_deadline);

            wishlistLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
