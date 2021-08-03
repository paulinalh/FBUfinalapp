package com.example.fbufinal.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.MainActivity;
import com.example.fbufinal.activities.PlaceDetailsActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

//Show Google autocomplete widget
public class SearchFragment extends Fragment {


    private static final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        String apiKey = getString(R.string.google_android_key);

        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), apiKey);
        }

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull @NotNull com.google.android.libraries.places.api.model. Place searchPlace) {


                // Get info about the selected place.
                Log.i(TAG, "Place: " + searchPlace.getName() + ", " + searchPlace.getId());
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId",searchPlace.getId());
                i.putExtra("searchPlaceName",searchPlace.getName());
                startActivity(i);
                ServicesFragment.setPlace(searchPlace.getId(), searchPlace.getName());

            }

            @Override
            public void onError(@NonNull @NotNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        return view;

    }
}