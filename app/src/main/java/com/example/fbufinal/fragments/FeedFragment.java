package com.example.fbufinal.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.MainActivity;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

//Queries places from the Google Places API Json fila and show them in a recycler view
public class FeedFragment extends Fragment implements PlacesAdapter.IPlaceRecyclerView {

    private static final int RC_CAMERA_AND_LOCATION = 345;
    public static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    public static final String API_KEY = "AIzaSyDdVbIsNC0NVYooAS-NazR92E6pH5IBtzw";
    public static final String RADIUS = "1500";
    public Fragment lastFragment = new Fragment();
    Fragment currentFragment = new Fragment();
    Fragment quickDetails = new QuickDetailsFragment();
    protected PlacesAdapter placesAdapter;
    protected List<Place> places;
    protected View place;
    public String TAG = "FeedFragment";
    public String currentLatitude;
    public String currentLongitude;
    public double longitude, latitude;
    //Initialize location client
    FusedLocationProviderClient client;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        places = new ArrayList<>();
        placesAdapter = new PlacesAdapter(getContext(), places, this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission is granted call method
            getCurrentLocation();
        } else {
            //when permission is denied, request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);

        places = new ArrayList<>();
        placesAdapter = new PlacesAdapter(getContext(), places, this);
        rvPlaces.setAdapter(placesAdapter);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPlaces.setLayoutManager(horizontalLayoutManager);


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        //Initialize location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Check conditions
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        currentLatitude = String.valueOf(longitude);
                        currentLongitude = String.valueOf(latitude);
                        Log.i(TAG, "Longitude: " + currentLongitude);
                        getJson();
                    } else {
                        //when location is null
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        //initialize location call back
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                //Initialize location
                                Location location1 = locationResult.getLastLocation();
                                //set latitude and longitude
                                currentLatitude = String.valueOf(location1.getLatitude());
                                currentLongitude = String.valueOf(location1.getLongitude());
                                getJson();
                            }
                        };
                        //request location updates
                        client.requestLocationUpdates(locationRequest
                                , locationCallback, Looper.myLooper());
                    }
                }
            });

        } else {
            //when location service is not enabled
            //Open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void getJson() {

        AsyncHttpClient client = new AsyncHttpClient();
        //Format of the API URL
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?
        // location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
        String FINAL_URL = PLACES_API_URL + currentLongitude + "," + currentLatitude + "&radius=" + RADIUS + "&opennow&key=" + API_KEY;
        client.get(FINAL_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, FINAL_URL);
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    places.addAll(Place.fromJsonArray(results));
                    placesAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Places: " + places.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit Json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                Log.d(TAG, "onFailure");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            //when permission are granted call method
            Toast.makeText(getContext(), "Permissions accepted", Toast.LENGTH_SHORT).show();
            getCurrentLocation();

        } else {
            //when permissions are denied, display toast
            Toast.makeText(getActivity(), "PermissionDenied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void goToPlaceDetails(Place place) {

        lastFragment = currentFragment;


        String placeId = place.getPlaceId();
        String imagePath = place.getImagePath();
        String placeName = place.getTitle();


        QuickDetailsFragment.setDetails(placeId, imagePath);
        ReviewsFragment.setDetails(placeId, imagePath);
        ServicesFragment.setPlace(placeId, placeName);
        MapFragment.setLatLng(latitude, longitude, placeName);

        getFragmentManager().beginTransaction().replace(R.id.child_fragment_container, quickDetails).addToBackStack("details").commit();
        //getChildFragmentManager().beginTransaction().replace(R.id.child_fragment_container, quickDetails).commit();


    }

}

