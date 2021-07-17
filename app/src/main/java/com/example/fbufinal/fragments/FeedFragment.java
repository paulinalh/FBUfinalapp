package com.example.fbufinal.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Movie;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.MainActivity;
import com.example.fbufinal.adapters.Places2Adapter;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place2;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place.Field;
import com.example.fbufinal.models.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class FeedFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_AND_LOCATION = 345;
    public static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    public static final String API_KEY = "AIzaSyDdVbIsNC0NVYooAS-NazR92E6pH5IBtzw";
    public static final String RADIUS = "1500";
    protected PlacesAdapter adapter;
    protected Places2Adapter places2Adapter;
    protected List<Place> allPlaces;
    protected List<Place2> places;
    protected View place;
    public String TAG = "FeedFragment";
    public String currentLatitude;
    public String currentLongitude;
    //Initialize location client
    FusedLocationProviderClient client;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
            Toast.makeText(getContext(), "Permissions already accepted", Toast.LENGTH_SHORT).show();
            getCurrentLocation();
        }else{
            //when permission is denied, request permission

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            // requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }


        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.activity_places);

        RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);

        // rvPlaces.setLayoutManager(layout);
/*
        allPlaces = new ArrayList<>();
        adapter = new PlacesAdapter(getContext(), allPlaces);
        rvPlaces.setAdapter(adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));*/
        places = new ArrayList<>();
        places2Adapter = new Places2Adapter(getContext(), places);
        rvPlaces.setAdapter(places2Adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        //Initialize location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Check coditions
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null) {
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        currentLatitude = String.valueOf(longitude);
                        currentLongitude = String.valueOf(latitude);
                        Toast.makeText(getContext(), currentLongitude, Toast.LENGTH_SHORT).show();
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

        }else{
            //when location service is not enabled
            //Open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void getJson() {
        //places = new ArrayList<>();


        //create the adapter
        //final Places2Adapter pAdapter = new Places2Adapter(getContext(), places);
        //places2Adapter= new Places2Adapter(getContext(), places);


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
                    places.addAll(Place2.fromJsonArray(results));
                    //pAdapter.notifyDataSetChanged();
                    places2Adapter.notifyDataSetChanged();
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

    private void queryPlaces() {
        ParseQuery<Place> query = ParseQuery.getQuery(Place.class);
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
                    Log.i(TAG, "Place: " + place.getDescription());
                }

                // save received posts to list and notify adapter of new data
                allPlaces.addAll(places);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode==100 && (grantResults.length>0)&&(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)){
            //when permission are granted call method
            Toast.makeText(getContext(), "Permissions accepted", Toast.LENGTH_SHORT).show();
            getCurrentLocation();

        }else{
            //when permissions are denied, display toast
            Toast.makeText(getActivity(), "PermissionDenied", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }*/

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull @NotNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull @NotNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}