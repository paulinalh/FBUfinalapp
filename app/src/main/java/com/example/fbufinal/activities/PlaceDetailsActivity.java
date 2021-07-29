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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class PlaceDetailsActivity extends AppCompatActivity {
    private static String imagePath;
    String placeName;
    String placeId;
    int favorite = 0;
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,photos,opening_hours,formatted_address,price_level,geometry";
    private static final String TAG = "PlaceDetailsActivity";
    String imageURL = "";
    List<String> favList;
    boolean alreadyFav = false;


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

        ParseUser user = ParseUser.getCurrentUser();


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

        favList = new ArrayList<>();
        favList.clear();

        favList = user.getList("favorites");


        for (int j = 0; j < favList.size(); j++) {

            if (favList.get(j) == placeId) {
                alreadyFav = true;
                fab.setImageDrawable(getDrawable(R.drawable.ufi_heart_active));
                break;
            }
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alreadyFav == false) {
                    alreadyFav = true;
                    favList.add(placeId);
                    updateObject(favList);
                    fab.setImageDrawable(getDrawable(R.drawable.ufi_heart_active));
                } else if (alreadyFav == true) {
                    alreadyFav = false;
                    fab.setImageDrawable(getDrawable(R.drawable.ufi_heart));
                    for (int i = 0; i < favList.size(); i++) {
                        if (favList.get(i) == placeId) {
                            favList.remove(i);
                            updateObject(favList);
                            break;
                        }
                    }
                }

                Snackbar.make(view, favList.get(0), Snackbar.LENGTH_LONG)
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
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                Log.d(TAG, "onFailure");
            }
        });

    }

    public void updateObject(List<String> favList) {

        //ParseQuery<ParseUser> query = ParseQuery.getQuery("User");
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("favorites", favList);

        // Saves the object.
        currentUser.saveInBackground(e -> {
            if (e == null) {
                //Save successfull
                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
            } else {
                // Something went wrong while saving
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
