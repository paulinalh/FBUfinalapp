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

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.PostsActivity;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Shows services in fragment of Place Details. Show the rating and darkens image when the service is available
public class ServicesFragment extends Fragment {
    private static final int WEELCHAIR_CODE = 0;
    private static final int RAMP_CODE = 1;
    private static final int PARKING_CODE = 2;
    private static final int ELEVATOR_CODE = 3;
    private static final int DOG_CODE = 4;
    private static final int BRAILLE_CODE = 5;
    private static final int LIGHT_CODE = 6;
    private static final int SOUND_CODE = 7;
    private static final int SIGNLANGUAGE_CODE = 8;
    private static final String TAG = "Services fragment";
    private static String placeId, placeName, image;
    private PlaceServicesRating placeToRate;
    private String objectId;
    private TextView tvWheelchair, tvRamp, tvParking, tvElevator, tvDog, tvBraille, tvLights, tvSound, tvSignlanguage;
    private ImageView ivWheelchair, ivRamp, ivParking, ivElevator, ivDog, ivBraille, ivLights, ivSound, ivSignlanguage, ivTextNeeds;
    private RatingBar rbWheelchair, rbRamp, rbParking, rbElevator, rbDog, rbBraille, rbLights, rbSound, rbSignlanguage;
    private LinearLayout lyWheelchair, lyRamp, lyParking, lyElevator, lyDog, lyBraille, lyLights, lySound, lySignlanguage;
    private List<Integer> availableServicesList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //refreshPlaceData();
        queryObject();

    }

    private void refreshPlaceData() {

        ParseQuery<PlaceServicesRating> query = new ParseQuery<>(PlaceServicesRating.class);
        if (placeToRate != null) {
            query.getInBackground(placeToRate.getObjectId(), (object, e) -> {
                if (e == null) {
                    placeToRate = object;
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
        tvWheelchair = view.findViewById(R.id.tvWheelchair);
        tvRamp = view.findViewById(R.id.tvRamp);
        tvParking = view.findViewById(R.id.tvParking);
        tvElevator = view.findViewById(R.id.tvElevator);
        tvDog = view.findViewById(R.id.tvDog);
        tvBraille = view.findViewById(R.id.tvBraille);
        tvLights = view.findViewById(R.id.tvLights);
        tvSound = view.findViewById(R.id.tvSound);
        tvSignlanguage = view.findViewById(R.id.tvSignLanguage);

        //All image views
        ivWheelchair = view.findViewById(R.id.ivWheelchair);
        ivRamp = view.findViewById(R.id.ivRamp);
        ivParking = view.findViewById(R.id.ivParking);
        ivElevator = view.findViewById(R.id.ivElevator);
        ivDog = view.findViewById(R.id.ivDog);
        ivBraille = view.findViewById(R.id.ivBraille);
        ivLights = view.findViewById(R.id.ivLights);
        ivSound = view.findViewById(R.id.ivSound);
        ivSignlanguage = view.findViewById(R.id.ivSignLanguage);

        //All Rating bars
        rbWheelchair = view.findViewById(R.id.rbWheelchair);
        rbRamp = view.findViewById(R.id.rbRamp);
        rbParking = view.findViewById(R.id.rbParking);
        rbElevator = view.findViewById(R.id.rbElevator);
        rbDog = view.findViewById(R.id.rbDog);
        rbBraille = view.findViewById(R.id.rbBraille);
        rbLights = view.findViewById(R.id.rbLights);
        rbSound = view.findViewById(R.id.rbSound);
        rbSignlanguage = view.findViewById(R.id.rbSignLanguage);

        //All linear layouts
        lyWheelchair = view.findViewById(R.id.lyWheelchair);
        lyRamp = view.findViewById(R.id.lyRamp);
        lyParking = view.findViewById(R.id.lyParking);
        lyElevator = view.findViewById(R.id.lyElevator);
        lyDog = view.findViewById(R.id.lyDog);
        lyBraille = view.findViewById(R.id.lyBraille);
        lyLights = view.findViewById(R.id.lyLights);
        lySound = view.findViewById(R.id.lySound);
        lySignlanguage = view.findViewById(R.id.lySignLanguage);
        ivTextNeeds = view.findViewById(R.id.ivTextNeeds);


        //queryObject();
        return view;

    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lyWheelchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", WEELCHAIR_CODE);
                startActivity(i);

            }
        });
        lyRamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", RAMP_CODE);
                startActivity(i);

            }
        });
        lyParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", PARKING_CODE);
                startActivity(i);
            }
        });
        lyElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", ELEVATOR_CODE);
                startActivity(i);
            }
        });
        lyDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", DOG_CODE);
                startActivity(i);
            }
        });
        lyBraille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", BRAILLE_CODE);
                startActivity(i);
            }
        });
        lyLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", LIGHT_CODE);
                startActivity(i);
            }
        });
        lySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", SOUND_CODE);
                startActivity(i);
            }
        });
        lySignlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostsActivity.class);
                i.putExtra(PlaceServicesRating.class.getSimpleName(), Parcels.wrap(placeToRate));
                i.putExtra("objectId", objectId);
                i.putExtra("namePlace", placeName);
                i.putExtra("code", SIGNLANGUAGE_CODE);
                startActivity(i);
            }
        });


    }

    public static void setPlace(String id, String name, String img) {
        //String img
        placeId = id;
        placeName = name;
        image = img;
    }

    private void queryObject() {

        ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);

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
                    if (Objects.equals(ParseUser.getCurrentUser().getList("needs"), availableServicesList)) {
                        ivTextNeeds.setVisibility(View.VISIBLE);
                    } else {
                        ivTextNeeds.setVisibility(View.GONE);

                    }



                }


            } else{
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            } if(this.objectId == null) {
                createObject();
            }

        });



    }

    private List<Integer> checkAvailableServices() {
        List<Integer> listServices = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            listServices.add(0);
        }


        List<Integer> listRatings = new ArrayList<>();
        float count = 0;
        float meanRating = 0;

        if (placeToRate.getWheelchairRatings().get(0) != 0) {


            listServices.set(0, 1);
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
            listServices.set(1, 1);
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
            listServices.set(2, 1);
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
            listServices.set(3, 1);
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
            listServices.set(4, 1);
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
            listServices.set(5, 1);
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
            listServices.set(6, 1);
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
            listServices.set(7, 1);
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
            listServices.set(8, 1);
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

        if (placeId != null) {
            newObject.setRatingPlaceId(placeId);
        }
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
        if (image != null) {
            newObject.setImageofPlace22(image);

        }
        newObject.setAllServices(emptyList);
        newObject.setWheelchairPosts(new ArrayList<>());
        newObject.setRampPosts(new ArrayList<>());
        newObject.setParkingPosts(new ArrayList<>());
        newObject.setElevatorPosts(new ArrayList<>());
        newObject.setDogPosts(new ArrayList<>());
        newObject.setBraillePosts(new ArrayList<>());
        newObject.setLightsPosts(new ArrayList<>());
        newObject.setSoundPosts(new ArrayList<>());
        newObject.setSignPosts(new ArrayList<>());

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        newObject.saveInBackground(e -> {
            if (e == null) {
                //Save was done
                this.objectId = newObject.getObjectId();

                queryObject();

            } else {
                //Something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}