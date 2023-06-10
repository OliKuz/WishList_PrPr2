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

import com.example.wishlist_prpr2.APIs.ApiSocial;
import com.example.wishlist_prpr2.adapters.FriendsAdapter;
import com.example.wishlist_prpr2.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFriendsFragment extends Fragment {
    private HomeActivity homeActivity;
    private SearchView searchBar;
    private RecyclerView recyclerFriendsView;
    private FriendsAdapter friendsAdapter;
    private List<User> userList;
    private List<User> allUsers;

    public DiscoverFriendsFragment(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        userList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_friends, container, false);

        searchBar = view.findViewById(R.id.discover_friends_search);
        recyclerFriendsView = view.findViewById(R.id.discover_friends_recyclerview);

        getAllUsers();

        friendsAdapter = new FriendsAdapter(userList);
        friendsAdapter.setOnItemClickListener(new FriendsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                homeActivity.replaceFragment(new ProfileFragment(homeActivity, userList.get(position)));
            }
        });
        recyclerFriendsView.setAdapter(friendsAdapter);
        recyclerFriendsView.setHasFixedSize(true);
        recyclerFriendsView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateUsers(newText);
                return true;
            }
        });

        return view;
    }

    private void updateUsers(String searchQuery){
        userList.clear();
        for(User user : allUsers){
            if((user.getName().toLowerCase().contains(searchQuery.toLowerCase())) ||
                    (user.getLast_name().toLowerCase().contains(searchQuery.toLowerCase()))){
                userList.add(user);
            }
        }
        friendsAdapter.notifyDataSetChanged();
    }
    private void getAllUsers() {
        ApiSocial.getInstance().getAllUsers(CurrentUser.getInstance().getApiToken()).enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    allUsers = response.body();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
    }

}
