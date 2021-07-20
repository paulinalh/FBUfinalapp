package com.example.fbufinal.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailsFragment extends Fragment {
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL="https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String TAG ="detailsFragment" ;
    PlacesAdapter adapter;
    String placeId;
    String imagePath;
    ImageView ivDetailsImage;
    TextView tvTitle,tvAddress, tvHours, tvPhone, tvPrice, tvRating ;
    Place place;
    String title, description,formatted_phone_number,opening_hours,formatted_address,price_level;
    int rating;
    protected List<Place> placeDetailsList;
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,opening_hours,formatted_address,price_level";

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String placeId, String imagePath) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("GOOGLE_PLACE_ID", placeId);
        args.putString("GOOGLE_IMAGE_URL", imagePath);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.placeId = getArguments().getString("GOOGLE_PLACE_ID", "");
        this.imagePath= getArguments().getString("GOOGLE_IMAGE_URL", "");
        //place = new Place();
        placeDetailsList= new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Need to define the child fragment layout
        return inflater.inflate(R.layout.fragment_details, container, false);


    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);

        getJson();

        tvTitle=view.findViewById(R.id.tvTitle);
        tvAddress =view.findViewById(R.id.tvAddress);
        tvHours=view.findViewById(R.id.tvHours);
        tvPhone=view.findViewById(R.id.tvPhone);
        tvPrice=view.findViewById(R.id.tvPrice);
        tvRating=view.findViewById(R.id.tvRating);
        ivDetailsImage=view.findViewById(R.id.ivDetailsImage);

        tvTitle.setText(title);
        tvAddress.setText(formatted_address);
        //tvHours.setText(opening_hours);
        tvPhone.setText(formatted_phone_number);
        //tvPrice.setText(price_level);
        tvRating.setText(""+rating);

        //adapter.notifyDataSetChanged();
        if (imagePath != "") {
            Glide.with(getContext()).load(imagePath).into(ivDetailsImage);
        }
        //setPlaceDetails();
    }

    private void setPlaceDetails() {
        getJson();
        tvTitle.setText(title);
        tvAddress.setText(formatted_address);
        //tvHours.setText(opening_hours);
        tvPhone.setText(formatted_phone_number);
        //tvPrice.setText(price_level);
        tvRating.setText(""+rating);

        //adapter.notifyDataSetChanged();
        if (imagePath != "") {
            Glide.with(getContext()).load(imagePath).into(ivDetailsImage);
        }
    }

    private void getJson() {

        AsyncHttpClient client = new AsyncHttpClient();
        //Format of the API URL
        //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
        String FINAL_URL = DETAILS_API_URL + placeId + FIELDS_FOR_URL + "&key=" + KEY;
        client.get(FINAL_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, FINAL_URL);
                JSONObject jsonObject = json.jsonObject;
                try {
                    //JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject result = jsonObject.getJSONObject("result");
                    Log.i(TAG, "Result: " + result.toString());
                    //placeDetailsList.addAll(Place.fromJsonArray(result));
                    title = result.getString("name");
                    //description = jsonObject.getString("name");
                    //placeId = jsonObject.getString("place_id");
                    rating = result.getInt("rating");
                    formatted_phone_number = result.getString("formatted_phone_number");
                    //opening_hours = jsonObject.getJSONArray("opening_hours").getJSONObject(0).getJSONArray("weekday_text").getJSONObject(0).getString("weekday_text");
                    formatted_address = result.getString("formatted_address");
                    //price_level = jsonObject.getString("price_level");

                    Log.i(TAG, "Place name: " + title);
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


}

