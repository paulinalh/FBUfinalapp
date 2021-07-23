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
    private static final int MESSAGE_OUTGOING = 123;
    private static final int MESSAGE_INCOMING = 321;

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


    public ReviewsAdapter(Context context, String userId, List<Review> reviews) {
        mReviews = reviews;
        this.mUserId = userId;
        mContext = context;
    }
    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.bindMessage(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return MESSAGE_OUTGOING;

        /*
        if (isMe(position)) {
            return MESSAGE_OUTGOING;
        } else {
            return MESSAGE_INCOMING;
        }*/
    }

    private boolean isMe(int position) {
        Review review = mReviews.get(position);
        return review.getUserId() != null && review.getUserId().equals(mUserId);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

     if (viewType == MESSAGE_OUTGOING) {
            View contactView = inflater.inflate(R.layout.message_outgoing, parent, false);
            return new OutgoingReviewViewHolder(contactView);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }



    public abstract class ReviewViewHolder extends RecyclerView.ViewHolder {

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindMessage(Review review);
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
        public void bindMessage(Review review) {
            String userId = review.getUserId();
            //File img= user1.png;

            /*Glide.with(mContext)
                    .load()
                    .circleCrop() // create an effect of a round profile picture
                    .into(imageMe);*/
            body.setText(review.getTextReview());
            name.setText(review.getUserId()); // in addition to message show user ID


        }
    }


}

