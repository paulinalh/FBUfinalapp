package com.example.fbufinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.FavoritePlacesAdapter;
import com.example.fbufinal.adapters.SpecificSearchAdapter;
import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecificSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecificSearchFragment extends Fragment {

    private static final String TAG = "SpecificSearchFragment";
    static String KEY;
    List<PlaceServicesRating> specificSearchList;
    SpecificSearchAdapter searchAdapter;
    List<Integer> emptyList= new ArrayList<>();

    public SpecificSearchFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setKey(String key) {
        KEY=key;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        specificSearchList = new ArrayList<>();
        searchAdapter = new SpecificSearchAdapter(getContext(), specificSearchList);
        querySearch();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_specific_search, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvSearch = view.findViewById(R.id.rvSpSearch);

        specificSearchList = new ArrayList<>();
        searchAdapter = new SpecificSearchAdapter(getContext(), specificSearchList);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));


        /*LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPlaces.setLayoutManager(horizontalLayoutManager);*/
    }

    private void querySearch() {
        ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);
        query.include(Favorite.KEY_USER);
        emptyList.add(0,0);
        query.whereNotContainedIn(KEY, emptyList);


        query.findInBackground(new FindCallback<PlaceServicesRating>() {
            @Override
            public void done(List<PlaceServicesRating> list, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                //Log.i(TAG, "Fav:" + favorite.getFavPlaceId() + ", username: " + favorite.getUser().getUsername());
                specificSearchList.addAll(list);
                searchAdapter.notifyDataSetChanged();
            }
        });

    }
}