package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlist_prpr2.APIs.ApiProducts;
import com.example.wishlist_prpr2.adapters.ItemsAdapter;
import com.example.wishlist_prpr2.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItemsFragment extends Fragment {
    private HomeActivity homeActivity;
    private SearchView searchBar;
    private RecyclerView recyclerItemsView;
    private ItemsAdapter itemsAdapter;
    private List<Product> itemList;
    private List<Product> allItems;

    public SearchItemsFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        itemList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_items, container, false);

        searchBar = view.findViewById(R.id.discover_items_search);
        recyclerItemsView = view.findViewById(R.id.discover_items_recyclerview);

        getAllItems();

        itemsAdapter = new ItemsAdapter(itemList);
        itemsAdapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                homeActivity.replaceFragment(new ItemFragment(homeActivity, itemList.get(position)));
            }
        });
        recyclerItemsView.setAdapter(itemsAdapter);
        recyclerItemsView.setHasFixedSize(true);
        recyclerItemsView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void updateItems(String searchQuery){
        itemList.clear();
        for(Product item : allItems){
            if((item.getName().toLowerCase().contains(searchQuery.toLowerCase()))) {
                itemList.add(item);
            }
        }
        itemsAdapter.notifyDataSetChanged();
    }
    private void getAllItems() {
        ApiProducts.getInstance().getAllProducts().enqueue(new Callback<List<Product>>(){
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    allItems = response.body();
                    displayItems();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
            }
        });
    }

    private void displayItems(){
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateItems(newText);
                return true;
            }
        });
    }

}
