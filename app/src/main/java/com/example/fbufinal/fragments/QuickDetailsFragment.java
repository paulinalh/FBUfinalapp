package com.example.fbufinal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.PlaceDetailsActivity;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

//Reads Json File and shows quick details
public class QuickDetailsFragment extends Fragment {
    private static final String KEY = BuildConfig.API_KEY;
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,opening_hours,formatted_address,price_level";
    private static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String TAG = "detailsFragment";
    private int fragment = 0;
    private static String placeId, imagePath;
    private ImageView ivDetailsImage, ivClose;
    private TextView tvTitle;
    private String title;
    private RelativeLayout ivReviewOption, ivMapOption, ivDetailsOption;
    private int rating;
    private RatingBar rbQuickPlace;
    protected List<Place> placeDetailsList;

    public QuickDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeDetailsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Need to define the child fragment layout
        View view = inflater.inflate(R.layout.fragment_quick_view, container, false);


        //Views of details fragment
        tvTitle = view.findViewById(R.id.tvQuickName);
        ivDetailsImage = view.findViewById(R.id.ivQuickPlace);
        rbQuickPlace = view.findViewById(R.id.rbQuickPlaceRating);
        ivReviewOption = view.findViewById(R.id.lottieComment);
        ivMapOption = view.findViewById(R.id.lottieMap);
        ivDetailsOption = view.findViewById(R.id.lottieDetails);
        ivClose = view.findViewById(R.id.ivClose);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PlacesAdapter.IPlaceRecyclerView mListener = null;

        getJson();


        ivReviewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                fragment = 3;
                i.putExtra("searchPlaceId", placeId);
                i.putExtra("searchPlaceName", title);
                i.putExtra("fragment", fragment);
                startActivity(i);

            }
        });

        ivMapOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = 2;
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId", placeId);
                i.putExtra("searchPlaceName", title);
                i.putExtra("fragment", fragment);

                startActivity(i);

            }
        });

        ivDetailsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = 0;
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId", placeId);
                i.putExtra("searchPlaceName", title);
                i.putExtra("fragment", fragment);
                startActivity(i);

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(QuickDetailsFragment.this).commit();

            }
        });


    }

    public void getJson() {

        AsyncHttpClient client = new AsyncHttpClient();
        //Format of the API URL
        //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
        String FINAL_URL = DETAILS_API_URL + this.placeId + FIELDS_FOR_URL + "&key=" + KEY;
        client.get(FINAL_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, FINAL_URL);
                JSONObject jsonObject = json.jsonObject;
                try {

                    JSONObject result = jsonObject.getJSONObject("result");
                    Log.i(TAG, "Result: " + result.toString());
                    title = result.getString("name");
                    rating = result.getInt("rating");
                    float rateFl = (float) (rating);
                    tvTitle.setText(title);
                    rbQuickPlace.setRating(rateFl);


                    if (imagePath != "") {
                        Glide.with(getContext()).load(imagePath).into(ivDetailsImage);
                    }

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

    public static void setDetails(String id, String path) {
        placeId = id;
        imagePath = path;

    }


}

