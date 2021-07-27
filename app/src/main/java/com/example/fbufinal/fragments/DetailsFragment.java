package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbufinal.BuildConfig;
import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.adapters.ProfileNeedsAdapter;
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

public class DetailsFragment extends Fragment {
    public static final String KEY = BuildConfig.API_KEY;
    public static final String DETAILS_API_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
    private static final String TAG = "detailsFragment";
    String objectId;
    ServicesAdapter servicesAdapter;
    PlaceServicesRating placeToRate;
    static String placeId;
    static String imagePath;
    ImageView ivDetailsImage;
    TextView tvTitle, tvAddress, tvPhone, tvPrice, tvRating, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;
    Place place;
    String title, description, formatted_phone_number, formatted_address, price_level;
    JSONArray opening_hours;
    int rating;
    List <Integer>availableServicesList = new ArrayList<>();
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

        placeDetailsList = new ArrayList<>();

        //queryObject();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Need to define the child fragment layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        //Views of details fragment
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvRating = (TextView) view.findViewById(R.id.tvRating);
        //ivDetailsImage = (ImageView) view.findViewById(R.id.ivDetailsImage);
        tvMonday = (TextView) view.findViewById(R.id.tvMonday);
        tvTuesday = (TextView) view.findViewById(R.id.tvTuesday);
        tvWednesday = (TextView) view.findViewById(R.id.tvWednesday);
        tvThursday = (TextView) view.findViewById(R.id.tvThursday);
        tvFriday = (TextView) view.findViewById(R.id.tvFriday);
        tvSaturday = (TextView) view.findViewById(R.id.tvSaturday);
        tvSunday = (TextView) view.findViewById(R.id.tvSunday);
        queryObject();


        return view;


    }
    RecyclerView rvServices;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);
        PlacesAdapter.IPlaceRecyclerView mListener = null;

        getJson();

        rvServices = view.findViewById(R.id.rvServices);
/*
        availableServicesList=checkAvailableServices();

        if(availableServicesList!=null){
            servicesAdapter = new ServicesAdapter(getContext(), availableServicesList);
            rvServices.setAdapter(servicesAdapter);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvServices.setLayoutManager(horizontalLayoutManager);
        }*/

        //queryObject();
        //RecyclerView rvServices = getView().findViewById(R.id.rvServices);





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

                    Log.i(TAG, "hours: " + opening_hours);

                    tvTitle.setText(title);
                    tvAddress.setText(formatted_address);
                    tvMonday.setText(opening_hours.getString(0));
                    tvTuesday.setText(opening_hours.getString(1));
                    tvWednesday.setText(opening_hours.getString(2));
                    tvThursday.setText(opening_hours.getString(3));
                    tvFriday.setText(opening_hours.getString(4));
                    tvSaturday.setText(opening_hours.getString(5));
                    tvSunday.setText(opening_hours.getString(6));
                    tvPhone.setText(formatted_phone_number);
                    //tvPrice.setText(price_level);
                    tvRating.setText("" + rating);


                  /*  if (imagePath != "") {
                        Glide.with(getContext()).load(imagePath).into(ivDetailsImage);
                    }*/

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

    public void queryObject() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceInclusionServices");
        //ParseQuery<PlaceServicesRating> query= ParseQuery.getQuery(PlaceServicesRating.class);

        // Finds only the comments that has placeId
        query.whereEqualTo("placeId", placeId);

        query.findInBackground((objects, e) -> {
            if(e == null){
                for (ParseObject result : objects) {
                    Log.d("Object found Details ",result.getObjectId());
                    this.objectId=result.getObjectId();
                    placeToRate= (PlaceServicesRating) result;
                    //objectId=placeToRate.getObjectId();

                    rvServices.setAdapter(servicesAdapter);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvServices.setLayoutManager(horizontalLayoutManager);
                    availableServicesList=checkAvailableServices();
                    servicesAdapter = new ServicesAdapter(getContext(), availableServicesList, placeToRate);
                }
            }else{
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            if(this.objectId==null){
                createObject();
            }

        });
    }

    private List<Integer> checkAvailableServices() {
        List<Integer> listServices = new ArrayList<>();
        int WEELCHAIR_CODE= 0;
        int RAMP_CODE= 1;
        int PARKING_CODE= 2;
        int ELEVATOR_CODE= 3;
        int DOG_CODE= 4;
        int BRAILLE_CODE= 5;
        int LIGHT_CODE= 6;
        int SOUND_CODE= 7;
        int SIGNLANGUAGE_CODE= 8;

        if(placeToRate.getWheelchairRatings().get(0)!=0){
            listServices.add(WEELCHAIR_CODE);
        } if(placeToRate.getRampRatings().get(0)!=0){
            listServices.add(RAMP_CODE);
        } if(placeToRate.getParkingRatings().get(0)!=0){
            listServices.add(PARKING_CODE);
        } if(placeToRate.getElevatorRatings().get(0)!=0){
            listServices.add(ELEVATOR_CODE);
        } if(placeToRate.getDogRatings().get(0)!=0){
            listServices.add(DOG_CODE);
        } if(placeToRate.getBrailleRatings().get(0)!=0){
            listServices.add(BRAILLE_CODE);
        } if(placeToRate.getLightsRatings().get(0)!=0){
            listServices.add(LIGHT_CODE);
        } if(placeToRate.getSoundRatings().get(0)!=0){
            listServices.add(SOUND_CODE);
        } if(placeToRate.getSignlanguageRatings().get(0)!=0){
            listServices.add(SIGNLANGUAGE_CODE);
        }

        return listServices;
    }

    public void createObject() {
        /*ParseObject newObject = new ParseObject("PlaceInclusionServices");


        newObject.put("placeId", placeId);
        newObject.put("wheelchairRatings", new JSONArray());
        newObject.put("rampRatings", new JSONArray());
        newObject.put("parkingRatings", new JSONArray());
        newObject.put("elevatorRatings", new JSONArray());
        newObject.put("dogRatings", new JSONArray());
        newObject.put("brailleRatings", new JSONArray());
        newObject.put("lightsRatings", new JSONArray());
        newObject.put("soundRatings", new JSONArray());
        newObject.put("signlanguageRatings", new JSONArray());*/

        List <Integer>emptyList=new ArrayList<>();
        emptyList.add(0);

        PlaceServicesRating newObject = new PlaceServicesRating();
        newObject.setRatingPlaceId(placeId);
        newObject.setWheelchairRatings(emptyList);
        newObject.setRampRatings(emptyList);
        newObject.setParkingRatings(emptyList);
        newObject.setElevatorRatings(emptyList);
        newObject.setDogRatings(emptyList);
        newObject.setBrailleRatings(emptyList);
        newObject.setLightsRatings(emptyList);
        newObject.setSoundRatings(emptyList);
        newObject.setSignlanguageRatings(emptyList);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        newObject.saveInBackground(e -> {
            if (e==null){
                //Save was done
                queryObject();

            }else{
                //Something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}

