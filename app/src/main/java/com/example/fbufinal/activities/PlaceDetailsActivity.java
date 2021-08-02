package com.example.fbufinal.activities;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.fragments.DetailsFragment;
import com.example.fbufinal.fragments.ReviewsFragment;
import com.example.fbufinal.fragments.ViewPagerFragment;
import com.example.fbufinal.models.Favorite;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class PlaceDetailsActivity extends AppCompatActivity {
    private String imagePath;
    String placeName;
    String placeId;
    ParseUser currentUser = ParseUser.getCurrentUser();
    int fragment;
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,photos,opening_hours,formatted_address,price_level,geometry";
    private static final String TAG = "PlaceDetailsActivity";
    String imageURL;
    List<String> favList;
    boolean alreadyFav;
    String favPlaceId;
    Button btnBack;
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
        this.fragment=i.getIntExtra("fragment",0);

        ViewPagerFragment.setFragment(fragment);
        Fragment currentFragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new ViewPagerFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit();



        ParseUser user = ParseUser.getCurrentUser();

        getImagePath();


        setTitle(placeName);
        setContentView(R.layout.activity_place_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        toolBarLayout.setTitle(getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        isAlreadyFavorite(fab);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alreadyFav == false) {
                    alreadyFav = true;
                    fab.setImageDrawable(getDrawable(R.drawable.ufi_heart_active));
                    saveFavorite();
                } else if (alreadyFav == true) {
                    alreadyFav = false;
                    fab.setImageDrawable(getDrawable(R.drawable.ufi_heart));
                    deleteFavorite();
                }


            }
        });





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
                    showImage();
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

    private void saveFavorite() {
        Favorite favorite = new Favorite();
        favorite.setFavPlaceId(placeId);
        favorite.setUser(currentUser);
        favorite.setFavName(placeName);
        favorite.setFavImageURL(imageURL);
        favorite.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(PlaceDetailsActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Favorite save was successful");
                //etDescription.setText("");
                //ivPostImage.setImageResource(0);
            }
        });

    }

    public void deleteFavorite() {
// Retrieve the object by id
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        query.getInBackground(favPlaceId, (object, e) -> {
            if (e == null) {
                // Deletes the fetched ParseObject from the database
                object.deleteInBackground(e2 -> {
                    if (e2 == null) {
                        Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        //Something went wrong while deleting the Object
                        Toast.makeText(this, "Error: " + e2.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //Something went wrong while retrieving the Object
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isAlreadyFavorite(FloatingActionButton fab) {
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        query.include(Favorite.KEY_USER);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> favs, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    alreadyFav = false;
                    return;
                }
                for (Favorite favorite : favs) {
                    //Log.i(TAG, "Fav:" + favorite.getFavPlaceId() + ", username: " + favorite.getUser().getUsername());
                    if (favorite.getFavPlaceId().equals(placeId)) {
                        alreadyFav = true;
                        favPlaceId = favorite.getObjectId();
                        fab.setImageDrawable(getDrawable(R.drawable.ufi_heart_active));
                    }
                }

            }
        });

    }

    private void showImage() {
        imageURL = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s", imagePath + "&key=" + KEY);
        ImageView ivSearchPlace = (ImageView) findViewById(R.id.ivSearchPlace);
        if (imageURL != "") {
            Glide.with(this).load(imageURL).into(ivSearchPlace);
        }
    }

}
