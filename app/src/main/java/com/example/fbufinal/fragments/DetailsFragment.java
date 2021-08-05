package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextView tvAddress, tvPhone, tvRating, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;
    private String title, formatted_phone_number, formatted_address;
    private JSONArray opening_hours;
    private int rating;
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
        tvRating = view.findViewById(R.id.tvRating);
        tvMonday = view.findViewById(R.id.tvMonday);
        tvTuesday = view.findViewById(R.id.tvTuesday);
        tvWednesday = view.findViewById(R.id.tvWednesday);
        tvThursday = view.findViewById(R.id.tvThursday);
        tvFriday = view.findViewById(R.id.tvFriday);
        tvSaturday = view.findViewById(R.id.tvSaturday);
        tvSunday = view.findViewById(R.id.tvSunday);

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
                    rating = result.getInt("rating");
                    formatted_phone_number = result.getString("formatted_phone_number");
                    opening_hours = result.getJSONObject("opening_hours").getJSONArray("weekday_text");
                    formatted_address = result.getString("formatted_address");
                    //price_level = jsonObject.getString("price_level");
                    latitude = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    longitude = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    imagePath = result.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                    MapFragment.setLatLng(latitude, longitude, title);
                    //PlaceDetailsActivity.setImage2(imagePath);


                    Log.i(TAG, "hours: " + opening_hours);

                    //tvTitle.setText(title);
                    tvAddress.setText(formatted_address);
                    tvMonday.setText(opening_hours.getString(0));
                    tvTuesday.setText(opening_hours.getString(1));
                    tvWednesday.setText(opening_hours.getString(2));
                    tvThursday.setText(opening_hours.getString(3));
                    tvFriday.setText(opening_hours.getString(4));
                    tvSaturday.setText(opening_hours.getString(5));
                    tvSunday.setText(opening_hours.getString(6));
                    tvPhone.setText(formatted_phone_number);
                    tvRating.setText("" + rating);

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