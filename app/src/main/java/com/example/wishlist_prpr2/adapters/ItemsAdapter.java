package com.example.wishlist_prpr2.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.R;
import com.example.wishlist_prpr2.model.Product;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private final List<Product> itemList;
    private ItemsAdapter.OnItemClickListener listener;

    public ItemsAdapter(List<Product> itemList) {
        this.itemList = itemList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemsAdapter.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ItemsAdapter.ItemsViewHolder(v, listener);
    }


    @Override
    public void onBindViewHolder(ItemsAdapter.ItemsViewHolder holder, int position) {
        Product currentProduct = itemList.get(position);
        holder.nameTextView.setText(currentProduct.getName() + " ");
        holder.nameTextView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

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

        String imagePath = currentProduct.getImage();
        if(imagePath == null || imagePath.isEmpty()) {
            imagePath = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
        }
        Picasso.get().load(imagePath).transform(transformation).into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView profilePicture;

        public ItemsViewHolder(View itemView, final ItemsAdapter.OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.list_item_name);
            profilePicture = itemView.findViewById(R.id.list_item_picture);

            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
