package com.example.fbufinal.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.PlacesActivity;
import com.example.fbufinal.models.Place;
import com.parse.Parse;
import com.parse.ParseFile;

import java.util.List;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private Context context;
    private List<Place> places;

    public PlacesAdapter(Context context, List<Place> places){
        this.context=context;
        this.places=places;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Place place= places.get(position);
    holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void addAll(List<Place> list) {
        places.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivImage;
        private TextView tvDescription;
        private Button btn;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            //ivImage=itemView.findViewById(R.id.ivImage);
            tvDescription=itemView.findViewById(R.id.tvDescription);
        }


        public void bind(Place place) {
            tvTitle.setText(place.getTitle());
            tvDescription.setText(place.getDescription());
            ParseFile image= place.getImage();
            if(image!=null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }


        }
    }
}
