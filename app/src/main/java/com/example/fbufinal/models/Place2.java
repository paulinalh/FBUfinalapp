package com.example.fbufinal.models;

import android.graphics.Movie;
import android.util.Log;

import com.example.fbufinal.BuildConfig;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Place2 {
    public static final String KEY = BuildConfig.API_KEY;
    String title;
    String description;
    String placeId;
    String imagePath;

    public Place2() {

    }

    public Place2(JSONObject jsonObject) throws JSONException {
        imagePath = jsonObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
        title = jsonObject.getString("name");
        description = jsonObject.getString("name");
        placeId=jsonObject.getString("place_id");

    }

    public static List<Place2> fromJsonArray(JSONArray placeJsonArray) throws JSONException {
        List<Place2> places= new ArrayList<>();
        for(int i =0; i<placeJsonArray.length(); i++){
            places.add(new Place2(placeJsonArray.getJSONObject(i)));
        }
        return places;
    }

    public String getImagePath() {
        //URL format
        //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=
        // CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4Qr
        // YAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU
        // _jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY
        String imageURL=String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s", imagePath +"&key="+ KEY);
        return imageURL;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getPlaceId() {
        return placeId;
    }


}
