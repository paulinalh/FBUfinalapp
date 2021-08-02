package com.example.fbufinal.adapters;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
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
import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.Post;
import com.example.fbufinal.models.Review;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> mReviews;
    private Context mContext;
    private String mUserId;
    private  String mPlaceId;
    private static final int CORRECT_PLACE = 123;
    private static final int INCORRECT_PLACE = 321;

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "https://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }


    public ReviewsAdapter(Context context, String placeId, List<Review> reviews) {
        this.mReviews = reviews;
        this.mPlaceId = placeId;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        try {
            holder.bind(review);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }



    public void clear() {
        mReviews.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Review> list) {
        mReviews.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUser;
        TextView body;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = (ImageView)itemView.findViewById(R.id.ivProfileMe);
            body = (TextView)itemView.findViewById(R.id.tvBody);
            name = (TextView)itemView.findViewById(R.id.tvName);


        }

        public void bind(Review review) throws ParseException {


            String userId = review.getUserId();
            //File img= user1.png;

            //ParseFile userImage= review.getUserReview().getParseFile("picture");
            ParseFile userImage = review.fetchIfNeeded().getParseUser("user").fetchIfNeeded().getParseFile("picture");

            if(userImage!=null) {
                Glide.with(mContext)
                        .load(userImage.getUrl())
                        .circleCrop() // create an effect of a round profile picture
                        .into(imageUser);
            }
            body.setText(review.getTextReview());
            name.setText(review.getUsername());




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
                            Toast.makeText(mContext, "Delete Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            //Something went wrong while deleting the Object
                            Toast.makeText(mContext, "Error: "+e2.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Something went wrong
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }


}

