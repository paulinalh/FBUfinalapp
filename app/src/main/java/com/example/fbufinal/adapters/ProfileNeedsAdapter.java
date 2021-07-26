package com.example.fbufinal.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.models.Place;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ProfileNeedsAdapter extends RecyclerView.Adapter<ProfileNeedsAdapter.ViewHolder> {
    private Context context;
    List<Integer> userNeedsList;
    Place place = null;


    public ProfileNeedsAdapter(Context context, List<Integer> needs) {
        this.context = context;
        this.userNeedsList = needs;
    }

    @NonNull
    @Override
    public ProfileNeedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_need, parent, false);
        ProfileNeedsAdapter.ViewHolder viewHolder = new ProfileNeedsAdapter.ViewHolder(view);
        return viewHolder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileNeedsAdapter.ViewHolder holder, int position) {
        int index= userNeedsList.get(position);
        if(userNeedsList.get(position)==1) {
            holder.bind(position);
        }/*else{
            holder.bind(10);
        }*/


    }

    @Override
    public int getItemCount() {
        return userNeedsList.size();
    }

    public void addAll(List<Integer> list) {
        userNeedsList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImageNeed;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageNeed = itemView.findViewById(R.id.ivImageProfileNeeds);
            tvDescription = itemView.findViewById(R.id.tvDescriptionNeed);

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
                tvDescription.setText("General access of wheelchairs");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.wheelchair_icon_1));

            }else if(indexNeed==RAMP_CODE){
                tvDescription.setText("Need of quality ramps");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.ramp_icon_1));

            }else if(indexNeed==PARKING_CODE){
                tvDescription.setText("Accessible parking");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.parking_icon_1));

            }else if(indexNeed==ELEVATOR_CODE){
                tvDescription.setText("Need of quality elevators");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.elevator_icon_1));

            }else if(indexNeed==DOG_CODE){
                tvDescription.setText("Access to service dogs");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.dog_icon_1));

            }else if(indexNeed==BRAILLE_CODE){
                tvDescription.setText("Braille services");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.braille_icon_1));

            }else if(indexNeed==LIGHT_CODE){
                tvDescription.setText("Lights control in case of sensitivity");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.light_icon_1));

            }else if(indexNeed==SOUND_CODE){
                tvDescription.setText("Sound control in case of sensitivity");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_icon_1));

            }else if(indexNeed==SIGNLANGUAGE_CODE){
                tvDescription.setText("Sign language services");
                ivImageNeed.setImageDrawable(context.getResources().getDrawable(R.drawable.signlanguage_icon_1));

            }else if(indexNeed==10){
                itemView.setVisibility(View.GONE);

            }



        }

    }
}
