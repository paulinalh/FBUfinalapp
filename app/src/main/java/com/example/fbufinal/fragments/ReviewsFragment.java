package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fbufinal.R;
import com.example.fbufinal.adapters.ReviewsAdapter;
import com.example.fbufinal.models.Post;
import com.example.fbufinal.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//Fragment wher the user can send a review and see other user reviews
public class ReviewsFragment extends Fragment {
    private static final String TAG = "ReviewsFragment";
    private static String placeId;
    private static String imagePath;
    protected List<Review> allReviews;
    private EditText etMessage;
    private ImageButton ibSend;
    private List<Review> mReviews;
    private RecyclerView rvReviews;
    private ReviewsAdapter adapter;
    boolean mFirstLoad;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    public static void setId(String id) {
        placeId = id;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etMessage = view.findViewById(R.id.etMessage);
        ibSend = view.findViewById(R.id.ibSend);
        rvReviews = view.findViewById(R.id.rvReviews);
        allReviews = new ArrayList<>();

        adapter = new ReviewsAdapter(getContext(), placeId, allReviews);

        rvReviews.setAdapter(adapter);

        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));

        mFirstLoad = true;

        refreshReviews();

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();

                Review review = new Review();
                review.setUserId(ParseUser.getCurrentUser().getObjectId());
                review.setUsername(ParseUser.getCurrentUser().getUsername());
                review.setUserReview(ParseUser.getCurrentUser());
                review.setPlaceId(placeId);
                review.setTextReview(data);

                review.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                            refreshReviews();

                        } else {
                            Log.e(TAG, "Failed to save message", e);
                        }
                    }
                });
                etMessage.setText(null);
            }
        });


    }


    // Set up button event handler which posts the entered message to Parse
    void setupAdapter() {

        mReviews = new ArrayList<>();
        mFirstLoad = true;

        // When send button is clicked, create message object on Parse

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();


                Review review = new Review();
                review.setUserId(ParseUser.getCurrentUser().getObjectId());
                review.setUsername(ParseUser.getCurrentUser().getUsername());
                review.setUserReview(ParseUser.getCurrentUser());
                review.setPlaceId(placeId);
                review.setTextReview(data);

                review.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                            refreshReviews();


                        } else {
                            Log.e(TAG, "Failed to save message", e);
                        }
                    }
                });
                etMessage.setText(null);
            }
        });


    }

    // Query messages from Parse so we can load them into the chat adapter
    void refreshReviews() {
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        // Configure limit and sort order
        query.setLimit(50);
        query.include(Review.USER_KEY);
        query.whereEqualTo("placeId", placeId);
        query.findInBackground(new FindCallback<Review>() {
            public void done(List<Review> reviews, ParseException e) {
                if (e == null) {
                    allReviews = reviews;
                    adapter.addAll(reviews);

                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
        adapter.notifyDataSetChanged(); // update adapter

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    public static void setDetails(String id, String path) {
        placeId = id;
        imagePath = path;
    }


}