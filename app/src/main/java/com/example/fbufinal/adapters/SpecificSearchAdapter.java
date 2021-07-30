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
import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.PlaceServicesRating;

import java.util.List;

public class SpecificSearchAdapter extends RecyclerView.Adapter<SpecificSearchAdapter.ViewHolder> {
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

    // Add a list of items -- change to type used
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
            //ParseUser image = fav.getUser();
            String picture = search.getImageofPlace22();
            if (picture != null) {
                Glide.with(context).load(picture).into(ivImage);
            }
            //Glide.with(context).load(picture.getUrl()).transform(new RoundedCornersTransformation(500, 0)).into(ivPicture);
        }


        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                PlaceServicesRating search = specificSearchList.get(position);
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("searchPlaceId", search.getRatingPlaceId());
                intent.putExtra("searchPlaceName", search.getNameofPlace22());

                // create intent for the new activity
                //Intent intent = new Intent(context, PlaceDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                //intent.putExtra(Favorite.class.getSimpleName(), Parcels.wrap(fav));
                // show the activity
                context.startActivity(intent);

            }

        }
    }
}
