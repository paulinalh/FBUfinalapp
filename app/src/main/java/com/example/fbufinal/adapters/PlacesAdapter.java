package com.example.fbufinal.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.fbufinal.models.Place;


import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private Context context;
    private List<Place> places;
    Place place = null;
    //final FragmentManager fragmentManager = getSupportFragmentManager();
    private IPlaceRecyclerView mListener;

    public PlacesAdapter(Context context, List<Place> places, IPlaceRecyclerView mListener) {
        this.context = context;
        this.places = places;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.ViewHolder holder, int position) {
        place = places.get(position);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivImage;
        private TextView tvDescription;
        private Button btn;
        IPlaceRecyclerView mListener;

        public ViewHolder(@NonNull View itemView, IPlaceRecyclerView mListener) {
            super(itemView);
            this.mListener = mListener;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescriptionPlace);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        place = places.get(position);

                    }
                    mListener.goToPlaceDetails(place);
                }
            });
        }


        public void bind(Place place) {
            tvTitle.setText(place.getTitle());
            tvDescription.setText(place.getDescription());
            Log.i("Adapter", place.getTitle());
            //ParseFile image= place.getImage();
            String image = place.getImagePath();
            if (image != null) {
                Glide.with(context).load(image).into(ivImage);
            }

        }

    }

    public interface IPlaceRecyclerView {
        void goToPlaceDetails(Place place);
    }
}
