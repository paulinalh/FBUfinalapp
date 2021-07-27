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

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    private Context context;
    List<Integer> availableServices;
    Place place = null;


    public ServicesAdapter(Context context, List<Integer> services) {
        this.context = context;
        this.availableServices = services;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_rating, parent, false);
        ServicesAdapter.ViewHolder viewHolder = new ServicesAdapter.ViewHolder(view);
        return viewHolder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        //int index= availableServices.get(position);
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
            rbServiceRating=itemView.findViewById(R.id.rbServiceRating);

        }


        public void bind(int indexNeed) {
            int WEELCHAIR_CODE= 0;
            int RAMP_CODE= 1;
            int PARKING_CODE= 2;
            int ELEVATOR_CODE= 3;
            int DOG_CODE= 4;
            int BRAILLE_CODE= 5;
            int LIGHT_CODE= 6;
            int SOUND_CODE= 7;
            int SIGNLANGUAGE_CODE= 8;

            if(indexNeed==WEELCHAIR_CODE){
                tvServiceName.setText("General access of wheelchairs");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.wheelchair_icon_1));

            }else if(indexNeed==RAMP_CODE){
                tvServiceName.setText("Need of quality ramps");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ramp_icon_1));

            }else if(indexNeed==PARKING_CODE){
                tvServiceName.setText("Accessible parking");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.parking_icon_1));

            }else if(indexNeed==ELEVATOR_CODE){
                tvServiceName.setText("Need of quality elevators");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.elevator_icon_1));

            }else if(indexNeed==DOG_CODE){
                tvServiceName.setText("Access to service dogs");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.dog_icon_1));

            }else if(indexNeed==BRAILLE_CODE){
                tvServiceName.setText("Braille services");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.braille_icon_1));

            }else if(indexNeed==LIGHT_CODE){
                tvServiceName.setText("Lights control in case of sensitivity");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.light_icon_1));

            }else if(indexNeed==SOUND_CODE){
                tvServiceName.setText("Sound control in case of sensitivity");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_icon_1));

            }else if(indexNeed==SIGNLANGUAGE_CODE){
                tvServiceName.setText("Sign language services");
                ivServiceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.signlanguage_icon_1));

            }else if(indexNeed==10){
                itemView.setVisibility(View.GONE);
            }
        }

    }


}
