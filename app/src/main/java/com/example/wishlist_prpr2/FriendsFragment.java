package com.example.wishlist_prpr2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wishlist_prpr2.adapters.FriendsAdapter;
import com.example.wishlist_prpr2.model.User;
import java.util.List;

public class FriendsFragment extends Fragment {
    private HomeActivity homeActivity;
    private SearchView searchBar;
    private RecyclerView recyclerFriendsView;
    private FriendsAdapter friendsAdapter;
    private List<User> userList;

    public FriendsFragment(HomeActivity homeActivity, List<User> friends) {
        this.homeActivity = homeActivity;
        userList = friends;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        searchBar = view.findViewById(R.id.friends_search);
        recyclerFriendsView = view.findViewById(R.id.friends_recyclerview);

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

        return view;
    }

}
