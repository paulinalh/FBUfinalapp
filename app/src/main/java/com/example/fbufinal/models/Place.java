package com.example.fbufinal.models;

import com.example.fbufinal.BuildConfig;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Place {
    public static final String KEY = BuildConfig.API_KEY;
    String title;
    String description;
    String placeId;
    String imagePath;

    public Place() {

    }

    public Place(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("photos")) {
            imagePath = jsonObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
        }
        title = jsonObject.getString("name");
        description = jsonObject.getString("name");
        placeId = jsonObject.getString("place_id");


    }


    public static List<Place> fromJsonArray(JSONArray placeJsonArray) throws JSONException {
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < placeJsonArray.length(); i++) {
            places.add(new Place(placeJsonArray.getJSONObject(i)));
        }
        return places;
    }


    public String getImagePath() {
        //URL format
        //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=
        // CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4Qr
        // YAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU
        // _jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY
        String imageURL = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s", imagePath + "&key=" + KEY);
        return imageURL;

    }

    public String getTitle() {
        return title;
    }

    public String getPlaceId() {
        return placeId;
    }

}
