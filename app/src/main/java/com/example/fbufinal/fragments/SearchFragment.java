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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {


    private static final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        return fragment;
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

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
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


                // TODO: Get info about the selected place.
                //Toast.makeText(MainActivity.this,autocompleteFragment.getHint(), Toast.LENGTH_SHORT ).show();
                Log.i(TAG, "Place: " + searchPlace.getName() + ", " + searchPlace.getId());
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId",searchPlace.getId());
                i.putExtra("searchPlaceName",searchPlace.getName());

                startActivity(i);
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