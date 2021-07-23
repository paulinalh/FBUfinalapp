package com.example.fbufinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbufinal.R;
import com.example.fbufinal.models.Review;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

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
        mReviews = reviews;
        this.mPlaceId = placeId;
        mContext = context;
    }
    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.bindReview(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return CORRECT_PLACE;


        if (isCurrentPlace(position)) {
            return CORRECT_PLACE;
        } else {
            return INCORRECT_PLACE;
        }

    }

    private boolean isCurrentPlace(int position) {
        Review review = mReviews.get(position);
        return review.getPlaceId() != null && review.getPlaceId().equals(mPlaceId);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

     if (viewType == CORRECT_PLACE) {
            View contactView = inflater.inflate(R.layout.review_outgoing, parent, false);
            return new OutgoingReviewViewHolder(contactView);
        } else if (viewType == INCORRECT_PLACE){
         View contactView = inflater.inflate(R.layout.no_review_layout, parent, false);
         return new IncorrectReviewViewHolder(contactView);

     }else {
            throw new IllegalArgumentException("Unknown view type");
        }

    }



    public abstract class ReviewViewHolder extends RecyclerView.ViewHolder {

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindReview(Review review);
    }


    public class OutgoingReviewViewHolder extends ReviewViewHolder {
        ImageView imageMe;
        TextView body;
        TextView name;

        public OutgoingReviewViewHolder(View itemView) {
            super(itemView);
            imageMe = (ImageView)itemView.findViewById(R.id.ivProfileMe);
            body = (TextView)itemView.findViewById(R.id.tvBody);
            name = (TextView)itemView.findViewById(R.id.tvName);

        }

        @Override
        public void bindReview(Review review) {
            String userId = review.getUserId();
            //File img= user1.png;

            /*Glide.with(mContext)
                    .load()
                    .circleCrop() // create an effect of a round profile picture
                    .into(imageMe);*/
            body.setText(review.getTextReview());
            name.setText(review.getUsername()); // in addition to message show user ID


        }
    }
    public class IncorrectReviewViewHolder extends ReviewViewHolder {

        ImageView imageOther;
        TextView body;
        TextView name;

        public IncorrectReviewViewHolder(View itemView) {
            super(itemView);
            itemView.setVisibility(View.GONE);
            itemView.setVisibility(View.INVISIBLE);
            /*
            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);
            body = (TextView)itemView.findViewById(R.id.tvBody);
            name = (TextView)itemView.findViewById(R.id.tvName);*/
        }

        @Override
        public void bindReview(Review review) {
            // TODO: implement later
        }
    }



}

