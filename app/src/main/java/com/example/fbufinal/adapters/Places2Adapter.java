package com.example.fbufinal.adapters;

import android.content.Context;
import android.nfc.Tag;
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
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.Place2;
import com.parse.ParseFile;

import java.util.List;

public class Places2Adapter extends RecyclerView.Adapter<Places2Adapter.ViewHolder> {
    private Context context;
    private List<Place2> places;

    public Places2Adapter(Context context, List<Place2> places){
        this.context=context;
        this.places=places;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        //return new Places2Adapter.ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Places2Adapter.ViewHolder holder, int position) {
        Place2 place= places.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void addAll(List<Place2> list) {
        places.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivImage;
        private TextView tvDescription;
        private Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            ivImage=itemView.findViewById(R.id.ivImage);
            tvDescription=itemView.findViewById(R.id.tvDescription);
        }


        public void bind(Place2 place) {
            tvTitle.setText(place.getTitle());
            tvDescription.setText(place.getDescription());
            Log.i("Adapter", place.getTitle());
            //ParseFile image= place.getImage();
            String image=place.getImagePath();
            if(image!=null){
                Glide.with(context).load(image).into(ivImage);
            }


        }
    }
}
