package com.example.fbufinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.FavoritePlacesAdapter;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Favorite;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FavoritesFragment extends Fragment  {
    private static final String TAG = "FavoritesFragment";
    List<Favorite> favorites;
    FavoritePlacesAdapter favAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //favorites = new ArrayList<>();
        //favAdapter = new FavoritePlacesAdapter(getContext(), favorites);
        queryFavorites();
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_favorites, container, false);
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPlaces = view.findViewById(R.id.rvFavorites);

        favorites = new ArrayList<>();
        favAdapter = new FavoritePlacesAdapter(getContext(), favorites);
        rvPlaces.setAdapter(favAdapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));


        /*LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPlaces.setLayoutManager(horizontalLayoutManager);*/
    }
    // Create a handler which can run code periodically
    static final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(2);
    Handler myHandler = new android.os.Handler();
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            queryFavorites();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };




    private void queryFavorites() {
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        query.include(Favorite.KEY_USER);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        /*
        query.findInBackground((objects, e) -> {
            if(e == null){
                for (ParseObject result : objects) {
                    Log.d("Object found ",result.getObjectId());
                    favorites=objects;
                }
            }else{
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> favs, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                //Log.i(TAG, "Fav:" + favorite.getFavPlaceId() + ", username: " + favorite.getUser().getUsername());
                /*favorites.addAll(favs);
                favorites=favs;
                favAdapter.notifyDataSetChanged();*/
                favAdapter.clear();
                favAdapter.addAll(favs);
            }
        });

    }

}