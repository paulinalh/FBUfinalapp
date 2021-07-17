package com.example.fbufinal.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbufinal.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class PlacesActivity extends AppCompatActivity {
    //private RecyclerView rvPlaces;
    protected List<Place> allPlaces;
    protected String TAG = "Places Activity";
    protected View place;
    protected String API_KEY = "AIzaSyDdVbIsNC0NVYooAS-NazR92E6pH5IBtzw";
    //Place place;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_places);

        RecyclerView rvPlaces = (RecyclerView) findViewById(R.id.rvPlaces);

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

}
