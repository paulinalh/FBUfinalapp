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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.PlaceDetailsActivity;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.adapters.ServicesAdapter;
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class QuickDetailsFragment extends Fragment {
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String TAG = "detailsFragment";
    String objectId;
    ServicesAdapter servicesAdapter;
    PlaceServicesRating placeToRate;
    int fragment=0;
    static String placeId;
    static String imagePath;
    ImageView ivDetailsImage, ivClose;
    TextView tvTitle, tvAddress, tvPhone, tvPrice, tvRating, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;
    Place place;
    String title, description, formatted_phone_number, formatted_address, price_level;
    JSONArray opening_hours;
    RelativeLayout lottieLike, lottieComment, lottieMap, lottieDetails;
    int rating;
    RatingBar rbQuickPlace;
    List<Integer> availableServicesList = new ArrayList<>();
    protected List<Place> placeDetailsList;
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,opening_hours,formatted_address,price_level";

    public QuickDetailsFragment() {
        // Required empty public constructor
    }

    public static QuickDetailsFragment newInstance(String placeId, String imagePath) {
        QuickDetailsFragment fragment = new QuickDetailsFragment();
        Bundle args = new Bundle();
        args.putString("GOOGLE_PLACE_ID", placeId);
        args.putString("GOOGLE_IMAGE_URL", imagePath);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeDetailsList = new ArrayList<>();

        //queryObject();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Need to define the child fragment layout
        View view = inflater.inflate(R.layout.fragment_quick_view, container, false);


        //Views of details fragment
        tvTitle = (TextView) view.findViewById(R.id.tvQuickName);
        ivDetailsImage = (ImageView) view.findViewById(R.id.ivQuickPlace);
        rbQuickPlace = (RatingBar) view.findViewById(R.id.rbQuickPlaceRating);
        lottieComment = (RelativeLayout) view.findViewById(R.id.lottieComment);
        lottieMap = (RelativeLayout) view.findViewById(R.id.lottieMap);
        lottieDetails = (RelativeLayout) view.findViewById(R.id.lottieDetails);
        ivClose = view.findViewById(R.id.ivClose);


        return view;


    }
    //RecyclerView rvServices;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);
        PlacesAdapter.IPlaceRecyclerView mListener = null;

        getJson();



        lottieComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                fragment =3;
                i.putExtra("searchPlaceId", placeId);
                i.putExtra("searchPlaceName", title);
                i.putExtra("fragment", fragment);
                startActivity(i);

            }
        });

        lottieMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=2;
                Intent i = new Intent(getContext(), PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId", placeId);
                i.putExtra("searchPlaceName", title);
                i.putExtra("fragment", fragment);

                startActivity(i);

            }
        });

        lottieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=0;
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
                    //placeId = jsonObject.getString("place_id");
                    rating = result.getInt("rating");
                    float rateFl = (float) (rating);
                    formatted_phone_number = result.getString("formatted_phone_number");
                    opening_hours = result.getJSONObject("opening_hours").getJSONArray("weekday_text");
                    formatted_address = result.getString("formatted_address");
                    //price_level = jsonObject.getString("price_level");

                    Log.i(TAG, "hours: " + opening_hours);

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

