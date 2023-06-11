package com.example.wishlist_prpr2.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.GiftsViewHolder>{

    private final List<Gift> gifts;
    private final List<Product> products;
    private OnItemClickListener listener;
    private boolean ownWishlists;

    public GiftsAdapter(List<Gift> gifts, List<Product> products, boolean ownWishlists) {
        this.gifts = gifts;
        this.products = products;
        this.ownWishlists = ownWishlists;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemDeleted(int position);
        void onItemReserved(int position);
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

        displayPicture(holder, currProduct);

        if(ownWishlists){
            holder.delete.setVisibility(View.VISIBLE);
        }

        if(currGift.isBooked()){
            holder.reserve.setVisibility(View.GONE);
            holder.reserved.setVisibility(View.VISIBLE);
        }

        holder.giftsLayout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    private void displayPicture(GiftsViewHolder holder, Product product){
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

        String imagePath = product.getImage();
        if(imagePath == null || imagePath.isEmpty()) {
            imagePath = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
        }
        Picasso.get().load(imagePath).transform(transformation).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public static class GiftsViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, priority, price;
        ImageView image;
        ImageButton delete;
        Button reserve, reserved;
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
            reserved = giftListView.findViewById(R.id.gift_reserved);

            giftsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDeleted(getAdapterPosition());
                }
            });

            reserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemReserved(getAdapterPosition());
                }
            });
        }
    }
}
