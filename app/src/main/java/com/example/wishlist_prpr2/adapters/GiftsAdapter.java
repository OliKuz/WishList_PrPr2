package com.example.wishlist_prpr2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.R;
import com.example.wishlist_prpr2.model.Gift;
import com.example.wishlist_prpr2.model.Product;

import java.util.List;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.GiftsViewHolder>{

    private final List<Gift> gifts;
    private final List<Product> products;
    private OnItemClickListener listener;

    public GiftsAdapter(List<Gift> gifts, List<Product> products) {
        this.gifts = gifts;
        this.products = products;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GiftsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gifts_view, parent, false);
        return new GiftsViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftsViewHolder holder, int position) {
        Gift currGift = gifts.get(position);
        Product currProduct = products.get(position);

        if(currProduct.getName() != null) {
            holder.name.setText(currProduct.getName());
        }
        else{
            holder.name.setText("No Name");
        }
        if(currProduct.getDescription() != null) {
            holder.description.setText(currProduct.getDescription());
        }
        else{
            holder.description.setText("No description");
        }

        if(currGift.getPriority() != 0){
            holder.priority.setText("Priority: " + Integer.toString(currGift.getPriority()));
        }
        else {
            holder.priority.setText("No priority");
        }
        holder.price.setText(currProduct.getPrice() + "€");

        holder.giftsLayout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public static class GiftsViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, priority, price;
        ImageView image;
        ImageButton delete;
        Button reserve;
        LinearLayout giftsLayout;

        public GiftsViewHolder(View giftListView, final OnItemClickListener listener) {
            super(giftListView);
            giftsLayout = giftListView.findViewById(R.id.gift_layout);
            name = giftListView.findViewById(R.id.gift_name);
            description = giftListView.findViewById(R.id.gift_description);
            priority = giftListView.findViewById(R.id.gift_priority);
            price = giftListView.findViewById(R.id.gift_price);
            image = giftListView.findViewById(R.id.gift_image);
            delete = giftListView.findViewById(R.id.gift_delete);
            reserve = giftListView.findViewById(R.id.gift_reserve);


            giftsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
