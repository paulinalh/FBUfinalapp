package com.example.fbufinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.PlaceDetailsActivity;
import com.example.fbufinal.fragments.QuickDetailsFragment;
import com.example.fbufinal.fragments.ReviewsFragment;
import com.example.fbufinal.fragments.ServicesFragment;
import com.example.fbufinal.models.PlaceServicesRating;

import java.util.List;

public class SpecificSearchAdapter extends RecyclerView.Adapter<SpecificSearchAdapter.ViewHolder> {
    //Adapts to vertical Recycler view all the places that correspond to the specific search
    private Context context;
    private List<PlaceServicesRating> specificSearchList;

    public SpecificSearchAdapter(Context context, List<PlaceServicesRating> specificSearchList) {
        this.context = context;
        this.specificSearchList = specificSearchList;
    }

    @Override
    public int getItemCount() {
        return specificSearchList.size();
    }

    @NonNull
    @Override
    public SpecificSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_place, parent, false);
        return new SpecificSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificSearchAdapter.ViewHolder holder, int position) {
        PlaceServicesRating search = specificSearchList.get(position);
        holder.bind(search);
    }

    // Clean all elements of the recycler
    public void clear() {
        specificSearchList.clear();
        notifyDataSetChanged();
    }

    // Add a list of Places from specific search
    public void addAll(List<PlaceServicesRating> list) {
        specificSearchList.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTimeStamp;
        private ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleFavorite);
            ivImage = itemView.findViewById(R.id.ivImageFavorite);
            itemView.setOnClickListener(this);
        }

        public void bind(PlaceServicesRating search) {
            // Bind the post data to the view elements
            tvTitle.setText(search.getNameofPlace22());
            String picture = search.getImageofPlace22();
            if (picture != null) {
                Glide.with(context).load(picture).into(ivImage);
            }
        }


        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                PlaceServicesRating search = specificSearchList.get(position);
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("searchPlaceId", search.getRatingPlaceId());
                intent.putExtra("searchPlaceName", search.getNameofPlace22());

                QuickDetailsFragment.setDetails(search.getRatingPlaceId(), search.getImageofPlace22());
                ReviewsFragment.setDetails(search.getRatingPlaceId(), search.getImageofPlace22());
                ServicesFragment.setPlace(search.getRatingPlaceId(), search.getNameofPlace22(), search.getImageofPlace22());
                //SectionsFragment.setImage(search.getImageofPlace22(), search.getNameofPlace22());

                context.startActivity(intent);

            }


        }
    }
}
