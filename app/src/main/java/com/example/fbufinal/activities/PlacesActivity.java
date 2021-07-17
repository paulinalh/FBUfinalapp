package com.example.fbufinal.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.User;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlacesActivity extends AppCompatActivity  {
    //private RecyclerView rvPlaces;
    protected PlacesAdapter adapter;
    protected List<Place> allPlaces;
    protected String TAG="Places Activity";
    protected View place;
    protected String API_KEY="AIzaSyDdVbIsNC0NVYooAS-NazR92E6pH5IBtzw";
    //Place place;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_places);

        RecyclerView rvPlaces=(RecyclerView) findViewById(R.id.rvPlaces);

       // rvPlaces.setLayoutManager(layout);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), API_KEY);

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        discoverPlaces();
        /*
        allPlaces=new ArrayList<>();
        adapter= new PlacesAdapter(this, allPlaces);
        rvPlaces.setAdapter(adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        queryPlaces();*/



    }

    private void discoverPlaces() {

    }

    private void queryPlaces() {
        ParseQuery<Place> query=ParseQuery.getQuery(Place.class);
        //query.include(Place.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Place>() {
            @Override
            public void done(List<Place> places, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Place place : places) {
                    Log.i(TAG, "Place: " + place.getDescription() );
                }

                // save received posts to list and notify adapter of new data
                allPlaces.addAll(places);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
