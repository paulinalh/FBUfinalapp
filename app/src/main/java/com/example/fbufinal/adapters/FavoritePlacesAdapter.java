package com.example.fbufinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.MainActivity;
import com.example.fbufinal.activities.PlaceDetailsActivity;
import com.example.fbufinal.fragments.FavoritesFragment;
import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.Place;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FavoritePlacesAdapter extends RecyclerView.Adapter<FavoritePlacesAdapter.ViewHolder> {
    //Adapts favorite places query in the FavoritesFragment and puts them in recycler view
    private Context context;
    private List<Favorite> favorites;


    public FavoritePlacesAdapter(Context context, List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_place, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite post = favorites.get(position);
        holder.bind(post);
    }

    // Clean all elements of the recycler
    public void clear() {
        favorites.clear();
        notifyDataSetChanged();
    }

    // Add the list of favorite places
    public void addAll(List<Favorite> list) {
        favorites.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private ImageView ivImage;

        public ViewHolder(@NonNull View itemView
        ) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleFavorite);
            ivImage = itemView.findViewById(R.id.ivImageFavorite);
            itemView.setOnClickListener(this);


        }

        public void bind(Favorite fav) {
            // Bind the post data to the view elements
            tvTitle.setText(fav.getFavName());
            //ParseUser image = fav.getUser();
            String picture = fav.getFavImageURL();
            if (picture != null) {
                Glide.with(context).load(picture).into(ivImage);
            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);

                    // Retrieve the object by id
                    query.getInBackground(fav.getObjectId(), (object, e) -> {
                        if (e == null) {
                            //Object was fetched
                            //Deletes the fetched ParseObject from the database
                            object.deleteInBackground(e2 -> {
                                if (e2 == null) {
                                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Something went wrong while deleting the Object
                                    Toast.makeText(context, "Error: " + e2.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            //Something went wrong
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }
            });


        }


        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Favorite fav = favorites.get(position);
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("searchPlaceId", fav.getFavPlaceId());
                intent.putExtra("searchPlaceName", fav.getFavName());

                context.startActivity(intent);

            }

        }
    }


}


