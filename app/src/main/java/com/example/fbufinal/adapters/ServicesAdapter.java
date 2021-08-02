package com.example.fbufinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbufinal.R;
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.PlaceServicesRating;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    //The place's services rating
    private Context context;
    List<Integer> availableServices;
    PlaceServicesRating placeObject;

    public ServicesAdapter(Context context, List<Integer> services, PlaceServicesRating place) {
        this.context = context;
        this.placeObject = place;
        this.availableServices = services;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_rating, parent, false);
        ServicesAdapter.ViewHolder viewHolder = new ServicesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        holder.bind(availableServices.get(position));

    }

    @Override
    public int getItemCount() {
        return availableServices.size();
    }

    public void addAll(List<Integer> list) {
        availableServices.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivServiceImage;
        private TextView tvServiceName;
        private RatingBar rbServiceRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            rbServiceRating = itemView.findViewById(R.id.rbServiceRating);

        }


        public void bind(int indexNeed) {
            int WEELCHAIR_CODE = 0;
            int RAMP_CODE = 1;
            int PARKING_CODE = 2;
            int ELEVATOR_CODE = 3;
            int DOG_CODE = 4;
            int BRAILLE_CODE = 5;
            int LIGHT_CODE = 6;
            int SOUND_CODE = 7;
            int SIGNLANGUAGE_CODE = 8;
            List<Integer> listRatings = new ArrayList<>();
            float count = 0;
            float meanRating = 0;

            if (indexNeed == WEELCHAIR_CODE) {
                tvServiceName.setText(R.string.Wheelchair);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.wheelchair_icon_1));
                listRatings = placeObject.getWheelchairRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);


            } else if (indexNeed == RAMP_CODE) {
                tvServiceName.setText(R.string.Ramp);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ramp_icon_1));
                listRatings = placeObject.getRampRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == PARKING_CODE) {
                tvServiceName.setText(R.string.Parking);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.parking_icon_1));
                listRatings = placeObject.getParkingRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == ELEVATOR_CODE) {
                tvServiceName.setText(R.string.Elevator);
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.elevator_icon_1));
                listRatings = placeObject.getElevatorRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == DOG_CODE) {
                tvServiceName.setText(R.string.Dog);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.dog_icon_1));
                listRatings = placeObject.getDogRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == BRAILLE_CODE) {
                tvServiceName.setText(R.string.Braille);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.braille_icon_1));
                listRatings = placeObject.getBrailleRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == LIGHT_CODE) {
                tvServiceName.setText(R.string.Lights);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.light_icon_1));
                listRatings = placeObject.getLightsRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == SOUND_CODE) {
                tvServiceName.setText(R.string.Sound);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_icon_1));
                listRatings = placeObject.getSoundRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == SIGNLANGUAGE_CODE) {
                tvServiceName.setText(R.string.Sign_Language);

                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.signlanguage_icon_1));
                listRatings = placeObject.getSignlanguageRatings();
                for (int i = 0; i < listRatings.size(); i++) {
                    count = count + listRatings.get(i);
                }
                meanRating = count / listRatings.size();
                rbServiceRating.setRating(meanRating);

            } else if (indexNeed == 10) {
                itemView.setVisibility(View.GONE);
            }
        }

    }


}
