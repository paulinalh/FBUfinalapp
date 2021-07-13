package com.example.fbufinal.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PlacesActivity extends AppCompatActivity {
    //private RecyclerView rvPlaces;
    protected PlacesAdapter adapter;
    protected List<Place> allPlaces;
    protected String TAG="Places Activity";
    protected View post;
    Place place;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_places);

        RecyclerView rvPlaces=(RecyclerView) findViewById(R.id.rvPlaces);

       // rvPlaces.setLayoutManager(layout);

        allPlaces=new ArrayList<>();
        adapter= new PlacesAdapter(this, allPlaces);
        rvPlaces.setAdapter(adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        queryPlaces();


    }

    private void queryPlaces() {
        ParseQuery<Place> query=ParseQuery.getQuery(Place.class);
        //query.include()
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Place>() {
            @Override
            public void done(List<Place> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Place place : allPlaces) {
                    Log.i(TAG, "Place: " + place.getDescription() );
                }

                // save received posts to list and notify adapter of new data
                allPlaces.addAll(allPlaces);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
