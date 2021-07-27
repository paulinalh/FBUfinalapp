package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ServicesFragment extends Fragment {
    private static final String TAG = "Services fragment";
    static String placeId;
    protected List<PlaceServicesRating> allPlaces;
    String objectId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queryObject();
        if(objectId==null){
            createObject();
        }
        queryObject();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //queryPosts();

        return inflater.inflate(R.layout.fragment_services, container, false);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //RecyclerView rvPlaces = view.findViewById(R.id.rvPlaces);
        PlacesAdapter.IPlaceRecyclerView mListener = null;
        //queryPosts();
        Log.i("Services Fragment", placeId);


    }

    public static void setPlace(String id) {

        placeId=id;
    }

    public void queryObject() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceInclusionServices");

        // Finds only the comments that has "myPost" as a post
        query.whereEqualTo("placeId", placeId);

        query.findInBackground((objects, e) -> {
            if(e == null){
                for (ParseObject result : objects) {
                    Log.d("Object found ",result.getObjectId());
                    objectId=result.getObjectId();
                }
            }else{
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createObject() {
        ParseObject newObject = new ParseObject("PlaceInclusionServices");

        newObject.put("placeId", placeId);
        newObject.put("wheelchairRatings", new JSONArray());
        newObject.put("rampRatings", new JSONArray());
        newObject.put("parkingRatings", new JSONArray());
        newObject.put("elevatorRatings", new JSONArray());
        newObject.put("dogRatings", new JSONArray());
        newObject.put("brailleRatings", new JSONArray());
        newObject.put("lightsRatings", new JSONArray());
        newObject.put("soundRatings", new JSONArray());
        newObject.put("signlanguageRatings", new JSONArray());

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        newObject.saveInBackground(e -> {
            if (e==null){
                //Save was done
            }else{
                //Something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}