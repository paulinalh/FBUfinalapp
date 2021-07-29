package com.example.fbufinal.activities;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.fragments.DetailsFragment;
import com.example.fbufinal.fragments.FeedFragment;
import com.example.fbufinal.fragments.MapFragment;
import com.example.fbufinal.fragments.ReviewsFragment;
import com.example.fbufinal.fragments.SectionsFragment;
import com.example.fbufinal.fragments.ViewPagerFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.fbufinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class PlaceDetailsActivity extends AppCompatActivity {
    private static String imagePath;
    String placeName;
    String placeId;
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,photos,opening_hours,formatted_address,price_level,geometry";
    private static final String TAG = "PlaceDetailsActivity";
    String imageURL="";


    final FragmentManager fragmentManager = getSupportFragmentManager();

    public static void setImage2(String imagePath) {
        //imageURL = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s", imagePath + "&key=" + KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        this.placeId = i.getStringExtra("searchPlaceId");
        this.placeName = i.getStringExtra("searchPlaceName");

        getImagePath();


        setTitle(placeName);
        imageURL = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s", imagePath + "&key=" + KEY);


        setContentView(R.layout.activity_place_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ImageView ivSearchPlace = (ImageView) findViewById(R.id.ivSearchPlace);
        if (imageURL != "") {
            Glide.with(this).load(imageURL).into(ivSearchPlace);
        }


        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Fragment currentFragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new ViewPagerFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit();

        DetailsFragment.setId(placeId);
        ReviewsFragment.setId(placeId);

    }

    private void getImagePath() {

        AsyncHttpClient client = new AsyncHttpClient();
        //Format of the API URL
        //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
        String FINAL_URL = DETAILS_API_URL + this.placeId + FIELDS_FOR_URL + "&key=" + KEY;
        client.get(FINAL_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JsonHttpResponseHandler.JSON json) {
                Log.d(TAG, FINAL_URL);
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject result = jsonObject.getJSONObject("result");

                    imagePath = result.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                    //PlaceDetailsActivity.setImage2(imagePath);


                } catch (JSONException e) {
                    Log.e(TAG, "Hit Json exception", e);
                    e.printStackTrace();
                }
            }@Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                Log.d(TAG, "onFailure");
            }
        });

        }
    }