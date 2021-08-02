package com.example.fbufinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    //Adapts posts queried from back 4 app database and place them into Recycler view from PostsFragmemt
    private Context context;
    private List<Post> posts;
    private PlaceServicesRating place;
    private int CODE;
    int likedByUser;
    int likes;


    public PostsAdapter(Context context, List<Post> posts, PlaceServicesRating place, int code) {
        this.context = context;
        this.posts = posts;
        this.place=place;
        this.CODE=code;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of posts
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription, tvLikes;
        private TextView tvTimeStamp;
        private ImageView ivPicture, ivHeart, ivTrash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivTrash = itemView.findViewById(R.id.ivTrash);
            tvLikes = itemView.findViewById(R.id.tvLikes);


        }

        public void bind(Post post) throws ParseException {
            // Bind the post data to the view elements

            tvDescription.setText(post.fetchIfNeeded().getString("textPost"));
            likes = post.fetchIfNeeded().getInt("likes");
            tvLikes.setText("" + likes);
            tvUsername.setText(post.fetchIfNeeded().getParseUser("username").fetchIfNeeded().getString("username"));
            ParseFile image = post.fetchIfNeeded().getParseFile("imagePost");
            ParseFile userPicture = post.fetchIfNeeded().getParseUser("username").fetchIfNeeded().getParseFile("picture");
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            Glide.with(context).load(userPicture.getUrl()).circleCrop().into(ivPicture);


            //Double tap to like gesture
            itemView.setOnTouchListener(new View.OnTouchListener() {

                private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        if (likedByUser == 0) {
                            likedByUser = 1;
                            ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.ufi_heart_active));
                            likes = post.getLikesPost() + 1;
                            //tvLikes.setText(likes);
                            post.setLikesPost(likes);
                            post.saveInBackground();

                        } else if (likedByUser == 1) {
                            likedByUser = 0;
                            ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.ufi_heart));
                            likes = post.getLikesPost() - 1;
                            //tvLikes.setText(likes);
                            post.setLikesPost(likes);
                            post.saveInBackground();

                        }


                        Log.d("TEST", "onDoubleTap");
                        return super.onDoubleTap(e);
                    }

                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                    gestureDetector.onTouchEvent(event);
                    return true;
                }

            });

            String postUser= (post.fetchIfNeeded().getParseUser("username")).fetchIfNeeded().getObjectId();

            String current=ParseUser.getCurrentUser().getObjectId();
            if (postUser.equals(current)) {

                ivTrash.setVisibility(View.VISIBLE);
            }

            ivTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    posts.remove(post);
                    deleteObject(post);

                    if(CODE==0){
                        place.setWheelchairPosts(posts);
                    }else if(CODE==1){
                        place.setRampPosts(posts);
                    }else if(CODE==2){
                        place.setParkingPosts(posts);
                    }else if(CODE==3){
                        place.setElevatorPosts(posts);
                    }else if(CODE==4){
                        place.setDogPosts(posts);
                    }else if(CODE==5){
                        place.setBraillePosts(posts);
                    }else if(CODE==6){
                        place.setLightsPosts(posts);
                    }else if(CODE==7){
                        place.setSoundPosts(posts);
                    }else if(CODE==8){
                        place.setSignPosts(posts);
                    }
                    place.saveInBackground();

                }
            });

        }




        public void deleteObject(Post post) {

            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

            // Retrieve the object by id
            query.getInBackground(post.getObjectId(), (object, e) -> {
                if (e == null) {
                    //Object was fetched
                    //Deletes the fetched ParseObject from the database
                    object.deleteInBackground(e2 -> {
                        if(e2==null){
                            Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            //Something went wrong while deleting the Object
                            Toast.makeText(context, "Error: "+e2.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Something went wrong
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }
}
