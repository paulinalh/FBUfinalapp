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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import java.util.Collections;
import java.util.List;

//Receives a key of what the user wants to search and show the corresponding places in a vertical recycler view
public class SpecificSearchFragment extends Fragment {

    private static final String TAG = "SpecificSearchFragment";
    static String KEY;
    List<PlaceServicesRating> specificSearchList;
    SpecificSearchAdapter searchAdapter;
    List<Integer> emptyList = new ArrayList<>();

    public SpecificSearchFragment() {
        // Required empty public constructor
    }

    public static void setKey(String key) {
        KEY = key;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        specificSearchList = new ArrayList<>();
        searchAdapter = new SpecificSearchAdapter(getContext(), specificSearchList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specific_search, container, false);


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

        ImageView ivSSearch = view.findViewById(R.id.ivSSearch);
        TextView tvSSearch = view.findViewById(R.id.tvSSearch);

        specificSearchList = new ArrayList<>();
        searchAdapter = new SpecificSearchAdapter(getContext(), specificSearchList);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        if (KEY.equals("wheelchairRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wheelchair_icon_2));
            tvSSearch.setText(getString(R.string.Wheelchair));
            querySearch();

        } else if (KEY.equals("rampRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ramp_icon_2));
            tvSSearch.setText(getString(R.string.Ramp));
            querySearch();

        } else if (KEY.equals("parkingRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.parking_icon_2));
            tvSSearch.setText(getString(R.string.Parking));
            querySearch();

        } else if (KEY.equals("elevatorRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.elevator_icon_2));
            tvSSearch.setText(getString(R.string.Elevator));
            querySearch();

        } else if (KEY.equals("dogRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.dog_icon_2));
            tvSSearch.setText(getString(R.string.Dog));
            querySearch();

        } else if (KEY.equals("brailleRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.braille_icon_2));
            tvSSearch.setText(getString(R.string.Braille));
            querySearch();

        } else if (KEY.equals("lightsRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.light_icon_2));
            tvSSearch.setText(getString(R.string.Lights));
            querySearch();

        } else if (KEY.equals("soundRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sound_icon_2));
            tvSSearch.setText(getString(R.string.Sound));
            querySearch();

        } else if (KEY.equals("signlanguageRatings")) {
            ivSSearch.setImageDrawable(getContext().getResources().getDrawable(R.drawable.signlanguage_icon_2));
            tvSSearch.setText(getString(R.string.Sign_Language));
            querySearch();

        } else if (KEY.equals("recommendedPlaces")) {
            Glide.with(getContext())
                    .load((ParseUser.getCurrentUser().getParseFile("picture")).getUrl())
                    .circleCrop() // create an effect of a round profile picture
                    .into(ivSSearch);
            tvSSearch.setText("all your needs");
            queryRecommended();

        }

    }

    private void querySearch() {
        ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);
        query.include(Favorite.KEY_USER);
        emptyList.add(0, 0);
        query.whereNotContainedIn(KEY, emptyList);


        query.findInBackground(new FindCallback<PlaceServicesRating>() {
            @Override
            public void done(List<PlaceServicesRating> list, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting places", e);
                    return;
                }
                specificSearchList.addAll(list);
                searchAdapter.notifyDataSetChanged();
            }
        });

    }

    private void queryRecommended() {
        ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);
        List<Integer> userNeeds = ParseUser.getCurrentUser().getList("needs");
        List<PlaceServicesRating> places = new ArrayList<>();
        query.include(Favorite.KEY_USER);
        //emptyList.add(0, 0);
        query.whereContainedIn("allServices", userNeeds);
        query.findInBackground(new FindCallback<PlaceServicesRating>() {
            @Override
            public void done(List<PlaceServicesRating> list, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting places", e);
                    return;
                }
                for (PlaceServicesRating place : list) {
                    List<Integer> placeServices = place.getAllServices();
                    if (placeServices.equals(userNeeds)) {
                        places.add(place);
                    }
                }
                //Log.i(TAG, "Fav:" + favorite.getFavPlaceId() + ", username: " + favorite.getUser().getUsername());
                specificSearchList.addAll(places);
                searchAdapter.notifyDataSetChanged();
            }
        });

    }
}