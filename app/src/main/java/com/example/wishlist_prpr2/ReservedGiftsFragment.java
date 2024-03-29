package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.ApiProducts;
import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.GiftsAdapter;
import com.example.wishlist_prpr2.model.Gift;
import com.example.wishlist_prpr2.model.Product;
import com.example.wishlist_prpr2.model.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedGiftsFragment extends Fragment {
    private HomeActivity homeActivity;
    private List<Gift> gifts;
    private final List<Product> products = new ArrayList<>();
    private RecyclerView giftsRecyclerView;
    private GiftsAdapter giftsAdapter;

    public ReservedGiftsFragment(HomeActivity homeActivity, List<Gift> gifts) {
        this.homeActivity = homeActivity;
        this.gifts = gifts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserved_gifts, container, false);

        giftsRecyclerView = view.findViewById(R.id.reserved_gifts_recyclerView);

        getProducts(gifts);

        return view;
    }

    private void getProducts(List<Gift> gifts){
        for (int i = 0; i < gifts.size(); i++) {
            int id = Integer.parseInt(gifts.get(i).getProduct_url().substring(66));
            ApiProducts.getInstance().getProductByID(id).enqueue(new Callback<Product>(){
                @Override
                public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        products.add(response.body());

                        if(products.size() == gifts.size()) {
                            displayGifts();
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                    Toast.makeText(homeActivity, "Connection to API Products failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayGifts(){
        giftsAdapter = new GiftsAdapter(gifts, products, false);
        giftsAdapter.setOnItemClickListener(new GiftsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                homeActivity.replaceFragment(new ItemFragment(homeActivity, products.get(position)));
            }
            @Override
            public void onItemDeleted(int position) {}

            @Override
            public void onItemReserved(int position) {}
        });
        giftsRecyclerView.setHasFixedSize(true);
        giftsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        giftsRecyclerView.setAdapter(giftsAdapter);
    }
}
