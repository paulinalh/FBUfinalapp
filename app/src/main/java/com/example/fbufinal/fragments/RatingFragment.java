package com.example.fbufinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbufinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment {
    private static int CODE;
    TextView tvServiceType;
    ImageView ivService;
    int WHEELCHAIR_CODE = 0;
    int RAMP_CODE= 1;
    int PARKING_CODE= 2;
    int ELEVATOR_CODE= 3;
    int DOG_CODE= 4;
    int BRAILLE_CODE= 5;
    int LIGHT_CODE= 6;
    int SOUND_CODE= 7;
    int SIGNLANGUAGE_CODE= 8;

    public RatingFragment() {
        // Required empty public constructor
    }


    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setTypeService(int code){
        CODE=code;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rating, container, false);

        tvServiceType= view.findViewById(R.id.tvRateType);
        ivService=view.findViewById(R.id.ivService);

        checkType();
        return view;
    }

    private void checkType() {
        if(CODE== WHEELCHAIR_CODE){
            tvServiceType.setText(R.string.Wheelchair);
            ivService.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wheelchair_icon_2));
        }else if(CODE==RAMP_CODE){

        }else if(CODE==PARKING_CODE){

        }else if(CODE==ELEVATOR_CODE){

        }else if(CODE==DOG_CODE){

        }else if(CODE==BRAILLE_CODE){

        }else if(CODE==LIGHT_CODE){

        }else if(CODE==SOUND_CODE){

        }else if(CODE==SIGNLANGUAGE_CODE){

        }
    }
}