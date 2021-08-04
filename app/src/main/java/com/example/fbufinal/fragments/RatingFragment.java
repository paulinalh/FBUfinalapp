package com.example.fbufinal.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.PostsActivity;
import com.example.fbufinal.models.PlaceServicesRating;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.List;

//Shows small fragment over fragment where user can rate the specific service of each place
public class RatingFragment extends Fragment {
    private static final int WHEELCHAIR_CODE = 0;
    private static final int RAMP_CODE = 1;
    private static final int PARKING_CODE = 2;
    private static final int ELEVATOR_CODE = 3;
    private static final int DOG_CODE = 4;
    private static final int BRAILLE_CODE = 5;
    private static final int LIGHT_CODE = 6;
    private static final int SOUND_CODE = 7;
    private static final int SIGNLANGUAGE_CODE = 8;
    private static int CODE;
    private static String objectId;
    private static PlaceServicesRating placeToUpdate;
    private List<Integer> ratingsList;
    private TextView tvServiceType;
    private ImageView ivService, star1, star2, star3, star4, star5, ivClose, ivScrim;
    private Button btnStars;
    private int finalStarsRating;

    public RatingFragment() {
        // Required empty public constructor
    }

    public static void setTypeService(int code, String id, PlaceServicesRating place) {
        CODE = code;
        objectId = id;
        placeToUpdate = place;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        tvServiceType = view.findViewById(R.id.tvRateType);
        ivService = view.findViewById(R.id.ivService);
        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);
        ivClose = view.findViewById(R.id.ivCloseRating);
        btnStars = view.findViewById(R.id.btnStars);
        ivScrim = view.findViewById(R.id.ivScrim);
        checkType();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.star_big_on);
                star2.setImageResource(android.R.drawable.star_off);
                star3.setImageResource(android.R.drawable.star_off);
                star4.setImageResource(android.R.drawable.star_off);
                star5.setImageResource(android.R.drawable.star_off);

                finalStarsRating = 1;
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.star_big_on);
                star2.setImageResource(android.R.drawable.star_big_on);
                star3.setImageResource(android.R.drawable.star_off);
                star4.setImageResource(android.R.drawable.star_off);
                star5.setImageResource(android.R.drawable.star_off);

                finalStarsRating = 2;
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.star_big_on);
                star2.setImageResource(android.R.drawable.star_big_on);
                star3.setImageResource(android.R.drawable.star_big_on);
                star4.setImageResource(android.R.drawable.star_off);
                star5.setImageResource(android.R.drawable.star_off);

                finalStarsRating = 3;
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.star_big_on);
                star2.setImageResource(android.R.drawable.star_big_on);
                star3.setImageResource(android.R.drawable.star_big_on);
                star4.setImageResource(android.R.drawable.star_big_on);
                star5.setImageResource(android.R.drawable.star_off);

                finalStarsRating = 4;
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(android.R.drawable.star_big_on);
                star2.setImageResource(android.R.drawable.star_big_on);
                star3.setImageResource(android.R.drawable.star_big_on);
                star4.setImageResource(android.R.drawable.star_big_on);
                star5.setImageResource(android.R.drawable.star_big_on);

                finalStarsRating = 5;
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(RatingFragment.this).commit();

            }
        });
        btnStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKey();
            }
        });

    }

    private void getKey() {

        if (CODE == 0) {
            ratingsList = placeToUpdate.getWheelchairRatings();
        } else if (CODE == 1) {
            ratingsList = placeToUpdate.getRampRatings();
        } else if (CODE == 2) {
            ratingsList = placeToUpdate.getParkingRatings();
        } else if (CODE == 3) {
            ratingsList = placeToUpdate.getElevatorRatings();
        } else if (CODE == 4) {
            ratingsList = placeToUpdate.getDogRatings();
        } else if (CODE == 5) {
            ratingsList = placeToUpdate.getBrailleRatings();
        } else if (CODE == 6) {
            ratingsList = placeToUpdate.getLightsRatings();
        } else if (CODE == 7) {
            ratingsList = placeToUpdate.getSoundRatings();
        } else if (CODE == 8) {
            ratingsList = placeToUpdate.getSignlanguageRatings();
        }

        addNewStar();
    }

    private void addNewStar() {
        if (ratingsList.get(0) == 0) {
            ratingsList.remove(0);
        }
        ratingsList.add(finalStarsRating);

        updateParse();
    }

    private void updateParse() {
        ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);
        // Retrieve the object by id
        query.getInBackground(objectId, (object, e) -> {
            if (e == null) {
                //Object was successfully retrieved
                // Update the fields we want to
                if (CODE == 0) {
                    object.setWheelchairRatings(ratingsList);
                } else if (CODE == 1) {
                    object.setRampRatings(ratingsList);

                } else if (CODE == 2) {
                    object.setParkingRatings(ratingsList);

                } else if (CODE == 3) {
                    object.setElevatorRatings(ratingsList);

                } else if (CODE == 4) {
                    object.setDogRatings(ratingsList);

                } else if (CODE == 5) {
                    object.setBrailleRatings(ratingsList);

                } else if (CODE == 6) {
                    object.setLightsRatings(ratingsList);

                } else if (CODE == 7) {
                    object.setSoundRatings(ratingsList);

                } else if (CODE == 8) {
                    object.setSignlanguageRatings(ratingsList);

                }
                //All other fields will remain the same
                object.saveInBackground();
                getFragmentManager().beginTransaction().remove(RatingFragment.this).commit();

            } else {
                // something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkType() {
        if (CODE == WHEELCHAIR_CODE) {
            tvServiceType.setText(R.string.Wheelchair);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wheelchair_icon_2));
        } else if (CODE == RAMP_CODE) {
            tvServiceType.setText(R.string.Ramp);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ramp_icon_2));
        } else if (CODE == PARKING_CODE) {
            tvServiceType.setText(R.string.Parking);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.parking_icon_2));
        } else if (CODE == ELEVATOR_CODE) {
            tvServiceType.setText(R.string.Elevator);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.elevator_icon_2));
        } else if (CODE == DOG_CODE) {
            tvServiceType.setText(R.string.Dog);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.dog_icon_2));
        } else if (CODE == BRAILLE_CODE) {
            tvServiceType.setText(R.string.Braille);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.braille_icon_2));
        } else if (CODE == LIGHT_CODE) {
            tvServiceType.setText(R.string.Lights);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.light_icon_2));
        } else if (CODE == SOUND_CODE) {
            tvServiceType.setText(R.string.Sound);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.sound_icon_2));
        } else if (CODE == SIGNLANGUAGE_CODE) {
            tvServiceType.setText(R.string.Sign_Language);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.signlanguage_icon_2));
        }
    }

}