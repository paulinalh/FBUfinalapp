package com.example.fbufinal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.PostsActivity;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.adapters.ServicesAdapter;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServicesFragment extends Fragment {
    private static final String TAG = "Services fragment";
    static String placeId,placeName,image;
    protected List<PlaceServicesRating> allPlaces;
    PlaceServicesRating placeToRate;
    String objectId;
    TextView tvWheelchair, tvRamp, tvParking, tvElevator, tvDog, tvBraille, tvLights, tvSound, tvSignlanguage;
    ImageView ivWheelchair, ivRamp, ivParking, ivElevator, ivDog, ivBraille, ivLights, ivSound, ivSignlanguage, ivTextNeeds;
    RatingBar rbWheelchair, rbRamp, rbParking, rbElevator, rbDog, rbBraille, rbLights, rbSound, rbSignlanguage;
    LinearLayout lyWheelchair, lyRamp, lyParking, lyElevator, lyDog, lyBraille, lyLights, lySound, lySignlanguage;
    List<Integer> availableServicesList = new ArrayList<>();
    int WEELCHAIR_CODE = 0;
    int RAMP_CODE = 1;
    int PARKING_CODE = 2;
    int ELEVATOR_CODE = 3;
    int DOG_CODE = 4;
    int BRAILLE_CODE = 5;
    int LIGHT_CODE = 6;
    int SOUND_CODE = 7;
    int SIGNLANGUAGE_CODE = 8;
    Fragment ratingFragment = new RatingFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //queryObject();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPlaceData();
        queryObject();

    }

    private void refreshPlaceData() {

        ParseQuery<PlaceServicesRating> query = new ParseQuery<PlaceServicesRating>(PlaceServicesRating.class);
        if(placeToRate!=null){
            query.getInBackground(placeToRate.getObjectId(), (object, e) -> {
                if (e == null) {
                    placeToRate=object;
                } else {
                    // something went wrong
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_services, container, false);

        //All text views
        tvWheelchair = (TextView) view.findViewById(R.id.tvWheelchair);
        tvRamp = (TextView) view.findViewById(R.id.tvRamp);
        tvParking = (TextView) view.findViewById(R.id.tvParking);
        tvElevator = (TextView) view.findViewById(R.id.tvElevator);
        tvDog = (TextView) view.findViewById(R.id.tvDog);
        tvBraille = (TextView) view.findViewById(R.id.tvBraille);
        tvLights = (TextView) view.findViewById(R.id.tvLights);
        tvSound = (TextView) view.findViewById(R.id.tvSound);
        tvSignlanguage = (TextView) view.findViewById(R.id.tvSignLanguage);

        //All image views
        ivWheelchair = (ImageView) view.findViewById(R.id.ivWheelchair);
        ivRamp = (ImageView) view.findViewById(R.id.ivRamp);
        ivParking = (ImageView) view.findViewById(R.id.ivParking);
        ivElevator = (ImageView) view.findViewById(R.id.ivElevator);
        ivDog = (ImageView) view.findViewById(R.id.ivDog);
        ivBraille = (ImageView) view.findViewById(R.id.ivBraille);
        ivLights = (ImageView) view.findViewById(R.id.ivLights);
        ivSound = (ImageView) view.findViewById(R.id.ivSound);
        ivSignlanguage = (ImageView) view.findViewById(R.id.ivSignLanguage);

        //All Rating bars
        rbWheelchair = (RatingBar) view.findViewById(R.id.rbWheelchair);
        rbRamp = (RatingBar) view.findViewById(R.id.rbRamp);
        rbParking = (RatingBar) view.findViewById(R.id.rbParking);
        rbElevator = (RatingBar) view.findViewById(R.id.rbElevator);
        rbDog = (RatingBar) view.findViewById(R.id.rbDog);
        rbBraille = (RatingBar) view.findViewById(R.id.rbBraille);
        rbLights = (RatingBar) view.findViewById(R.id.rbLights);
        rbSound = (RatingBar) view.findViewById(R.id.rbSound);
        rbSignlanguage = (RatingBar) view.findViewById(R.id.rbSignLanguage);

        //All linear layouts
        lyWheelchair = (LinearLayout) view.findViewById(R.id.lyWheelchair);
        lyRamp = (LinearLayout) view.findViewById(R.id.lyRamp);
        lyParking = (LinearLayout) view.findViewById(R.id.lyParking);
        lyElevator = (LinearLayout) view.findViewById(R.id.lyElevator);
        lyDog = (LinearLayout) view.findViewById(R.id.lyDog);
        lyBraille = (LinearLayout) view.findViewById(R.id.lyBraille);
        lyLights = (LinearLayout) view.findViewById(R.id.lyLights);
        lySound = (LinearLayout) view.findViewById(R.id.lySound);
        lySignlanguage = (LinearLayout) view.findViewById(R.id.lySignLanguage);
        ivTextNeeds= (ImageView) view.findViewById(R.id.ivTextNeeds);


        queryObject();
        return view;

    }




    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Log.i("Services Fragment", placeId);



        lyWheelchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",WEELCHAIR_CODE);
                startActivity(i);

                //RatingFragment.setTypeService(WEELCHAIR_CODE, objectId, placeToRate);<
                //getChildFragmentManager().beginTransaction().replace(R.id.child_fragment_container, ratingFragment).commit();

            }
        });
        lyRamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",RAMP_CODE);
                startActivity(i);

                //RatingFragment.setTypeService(RAMP_CODE, objectId, placeToRate);
                //getChildFragmentManager().beginTransaction().replace(R.id.child_fragment_container, ratingFragment).commit();

            }
        });
        lyParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",PARKING_CODE);
                startActivity(i);
            }
        });
        lyElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",ELEVATOR_CODE);
                startActivity(i);
            }
        });
        lyDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",DOG_CODE);
                startActivity(i);
            }
        });
        lyBraille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",BRAILLE_CODE);
                startActivity(i);
            }
        });
        lyLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",LIGHT_CODE);
                startActivity(i);
            }
        });
        lySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",SOUND_CODE);
                startActivity(i);
            }
        });
        lySignlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId",objectId);
                i.putExtra("namePlace",placeName);
                i.putExtra("code",SIGNLANGUAGE_CODE);
                startActivity(i);
            }
        });


    }

    public static void setPlace(String id,String name, String img) {

        placeId = id;
        placeName=name;
        image=img;
    }

    public void queryObject() {

        //ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceInclusionServices");
        ParseQuery<PlaceServicesRating> query= ParseQuery.getQuery(PlaceServicesRating.class);

        // Finds only the comments that has placeId
        query.whereEqualTo("placeId", placeId);

        query.findInBackground((objects, e) -> {
            if (e == null) {
                for (ParseObject result : objects) {
                    Log.d("Object found Details ", result.getObjectId());
                    this.objectId = result.getObjectId();
                    placeToRate = (PlaceServicesRating) result;
                    //objectId=placeToRate.getObjectId();
                    availableServicesList = checkAvailableServices();
                    placeToRate.setAllServices(availableServicesList);
                    try {
                        placeToRate.save();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    DetailsFragment.setServices(availableServicesList);
                    if(Objects.equals(ParseUser.getCurrentUser().getList("needs"), availableServicesList)){
                        ivTextNeeds.setVisibility(View.VISIBLE);
                    }else{
                        ivTextNeeds.setVisibility(View.GONE);

                    }
/*
                    rvServices.setAdapter(servicesAdapter);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvServices.setLayoutManager(horizontalLayoutManager);
                    servicesAdapter = new ServicesAdapter(getContext(), availableServicesList, placeToRate);*/
                }
            } else {
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            if (this.objectId == null) {
                createObject();
            }

        });
    }

    private List<Integer> checkAvailableServices() {
        List<Integer> listServices = new ArrayList<>();
        for(int x=0;x<9;x++){
            listServices.add(0);
        }


        List<Integer> listRatings = new ArrayList<>();
        float count = 0;
        float meanRating = 0;

        if (placeToRate.getWheelchairRatings().get(0) != 0) {


            listServices.set(0,1);
            ivWheelchair.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wheelchair_icon_2));
            listRatings = placeToRate.getWheelchairRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbWheelchair.setVisibility(View.VISIBLE);
            rbWheelchair.setRating(meanRating);
            count = 0;
            meanRating = 0;

        }
        if (placeToRate.getRampRatings().get(0) != 0) {
            listServices.set(1,1);
            ivRamp.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ramp_icon_2));
            listRatings = placeToRate.getRampRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbRamp.setVisibility(View.VISIBLE);
            rbRamp.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getParkingRatings().get(0) != 0) {
            listServices.set(2,1);
            ivParking.setImageDrawable(getContext().getResources().getDrawable(R.drawable.parking_icon_2));
            listRatings = placeToRate.getParkingRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbParking.setVisibility(View.VISIBLE);
            rbParking.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getElevatorRatings().get(0) != 0) {
            listServices.set(3,1);
            ivElevator.setImageDrawable(getContext().getResources().getDrawable(R.drawable.elevator_icon_2));
            listRatings = placeToRate.getElevatorRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbElevator.setVisibility(View.VISIBLE);
            rbElevator.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getDogRatings().get(0) != 0) {
            listServices.set(4,1);
            ivDog.setImageDrawable(getContext().getResources().getDrawable(R.drawable.dog_icon_2));
            listRatings = placeToRate.getDogRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbDog.setVisibility(View.VISIBLE);
            rbDog.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getBrailleRatings().get(0) != 0) {
            listServices.set(5,1);
            ivBraille.setImageDrawable(getContext().getResources().getDrawable(R.drawable.braille_icon_2));
            listRatings = placeToRate.getBrailleRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbBraille.setVisibility(View.VISIBLE);
            rbBraille.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getLightsRatings().get(0) != 0) {
            listServices.set(6,1);
            ivLights.setImageDrawable(getContext().getResources().getDrawable(R.drawable.light_icon_2));
            listRatings = placeToRate.getLightsRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbLights.setVisibility(View.VISIBLE);
            rbLights.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getSoundRatings().get(0) != 0) {
            listServices.set(7,1);
            ivSound.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sound_icon_2));
            listRatings = placeToRate.getSoundRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbSound.setVisibility(View.VISIBLE);
            rbSound.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }
        if (placeToRate.getSignlanguageRatings().get(0) != 0) {
            listServices.set(8,1);
            ivSignlanguage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.signlanguage_icon_2));
            listRatings = placeToRate.getSignlanguageRatings();
            for (int i = 0; i < listRatings.size(); i++) {
                count = count + listRatings.get(i);
            }
            meanRating = count / listRatings.size();
            rbSignlanguage.setVisibility(View.VISIBLE);
            rbSignlanguage.setRating(meanRating);
            count = 0;
            meanRating = 0;
        }

        //placeToRate.setAllServices(listServices);

        return listServices;
    }

    public void createObject() {

        List<Integer> emptyList = new ArrayList<>();
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
        newObject.setNameofPlace22(placeName);
        newObject.setImageofPlace22(image);
        newObject.setAllServices(emptyList);
        newObject.setWheelchairPosts(new ArrayList<>());
        newObject.setRampPosts(new ArrayList<>());


        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        newObject.saveInBackground(e -> {
            if (e == null) {
                //Save was done
                queryObject();

            } else {
                //Something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}