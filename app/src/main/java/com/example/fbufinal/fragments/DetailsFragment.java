package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainer;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

//ViewPager and fragment inside Place Details Activity where the place details are shown
public class DetailsFragment extends Fragment {

    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String TAG = "detailsFragment";
    static String placeId;
    static String imagePath;
    private TextView tvAddress, tvPhone, tvPrice, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;
    private RatingBar rbRating;
    private String title, formatted_phone_number, formatted_address;
    private JSONArray opening_hours;
    private int price_level = 0;
    private float rating;
    public double latitude, longitude;
    protected List<Place> placeDetailsList;
    static List<Integer> availableServicesList;
    private static final String FIELDS_FOR_URL = "&fields=name,rating,formatted_phone_number,photos,opening_hours,formatted_address,price_level,geometry";

    public DetailsFragment() {
        // Required empty public constructor
    }


    public static void setId(String id) {
        placeId = id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeDetailsList = new ArrayList<>();

        //queryObject2();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Need to define the child fragment layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        //Views of details fragment
        tvAddress = view.findViewById(R.id.tvAddress);
        tvPhone = view.findViewById(R.id.tvPhone);
        rbRating = view.findViewById(R.id.tvRating);
        tvMonday = view.findViewById(R.id.tvMonday);
        tvTuesday = view.findViewById(R.id.tvTuesday);
        tvWednesday = view.findViewById(R.id.tvWednesday);
        tvThursday = view.findViewById(R.id.tvThursday);
        tvFriday = view.findViewById(R.id.tvFriday);
        tvSaturday = view.findViewById(R.id.tvSaturday);
        tvSunday = view.findViewById(R.id.tvSunday);
        tvPrice = view.findViewById(R.id.tvPrice);

        return view;


    }

    public static void setServices(List<Integer> list) {
        availableServicesList = list;


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PlacesAdapter.IPlaceRecyclerView mListener = null;

        getJson();


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
                    try {
                        double tempRating = result.getDouble("rating");
                        rating = (float) tempRating;
                        rbRating.setRating(rating);

                    } catch (Exception ignored) {

                    }

                    try {
                        formatted_phone_number = result.getString("formatted_phone_number");
                        tvPhone.setText(formatted_phone_number);
                    } catch (Exception ignored) {

                    }

                    try {
                        formatted_address = result.getString("formatted_address");
                        tvAddress.setText(formatted_address);


                    } catch (Exception ignored) {

                    }

                    latitude = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    longitude = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    imagePath = result.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                    MapFragment.setLatLng(latitude, longitude, title);
                    //PlaceDetailsActivity.setImage2(imagePath);

                    Log.i(TAG, "hours: " + opening_hours);


                    try {
                        opening_hours = result.getJSONObject("opening_hours").getJSONArray("weekday_text");
                        tvMonday.setText(opening_hours.getString(0));
                        tvTuesday.setText(opening_hours.getString(1));
                        tvWednesday.setText(opening_hours.getString(2));
                        tvThursday.setText(opening_hours.getString(3));
                        tvFriday.setText(opening_hours.getString(4));
                        tvSaturday.setText(opening_hours.getString(5));
                        tvSunday.setText(opening_hours.getString(6));
                    } catch (Exception ignored) {

                    }


                    price_level = result.getInt("price_level");
                    //Google places price level
                    String priceInWords = null;
                    if (price_level == 0) {
                        priceInWords = "Free";
                    } else if (price_level == 1) {
                        priceInWords = "Inexpensive";

                    } else if (price_level == 2) {
                        priceInWords = "Moderate";

                    } else if (price_level == 3) {
                        priceInWords = "Expensive";

                    } else if (price_level == 4) {
                        priceInWords = "Very expensive";

                    }
                    tvPrice.setText("" + price_level + "/5" + "  -   " + priceInWords);

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