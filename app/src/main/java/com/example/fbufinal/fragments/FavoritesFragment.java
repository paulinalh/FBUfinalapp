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

//Shows all favorite places saved by the user
public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";
    private FavoritePlacesAdapter favAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryFavorites();
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        //Handler that automatically updates the favorite places every 2 seconds
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPlaces = view.findViewById(R.id.rvFavorites);

        List<Favorite> favorites = new ArrayList<>();
        favAdapter = new FavoritePlacesAdapter(getContext(), favorites);
        rvPlaces.setAdapter(favAdapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    // handler to update favorites every 2 seconds
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
        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> favs, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                favAdapter.clear();
                favAdapter.addAll(favs);
            }
        });

    }

}