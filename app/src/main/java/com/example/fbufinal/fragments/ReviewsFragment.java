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

public class ReviewsFragment extends Fragment {
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "text";
    private static final String TAG = "ReviewsFragment";
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    static String placeId;
    static String imagePath;
    protected List<Review> allReviews;
    EditText etMessage;
    ImageButton ibSend;
    private List<Review> mReviews;
    RecyclerView rvReviews;
    ReviewsAdapter adapter;
    boolean mFirstLoad;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    // Create a handler which can run code periodically
    /*static final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(3);
    Handler myHandler = new android.os.Handler();
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshReviews();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };*/

    public static void setId(String id) {
        placeId=id;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //refreshReviews();

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
        //setContentView(R.layout.activity_feed);

        etMessage = view.findViewById(R.id.etMessage);
        ibSend = view.findViewById(R.id.ibSend);
        rvReviews = view.findViewById(R.id.rvReviews);
        allReviews = new ArrayList<>();

        adapter = new ReviewsAdapter(getContext(), placeId,allReviews);

        rvReviews.setAdapter(adapter);

        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        //linearLayoutManager.setReverseLayout(true);

        mFirstLoad = true;

        refreshReviews();
        //setupAdapter();

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

        // Construct query to execute
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        // Configure limit and sort order
        query.setLimit(50);
        query.include(Review.USER_KEY);
        // get the latest 50 messages, order will show up newest to oldest of this group
        //query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.whereEqualTo("placeId",placeId);
        query.findInBackground(new FindCallback<Review>() {
            public void done(List<Review> reviews, ParseException e) {
                if (e == null) {
                    allReviews=reviews;
                    //adapter.clear();
                    adapter.addAll(reviews);
                    //adapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    /*if (mFirstLoad) {
                        rvReviews.scrollToPosition(0);
                        mFirstLoad = false;
                    }*/
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
        adapter.notifyDataSetChanged(); // update adapter


       /* ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");

        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
        query.whereEqualTo("placeId", placeId);

        query.findInBackground((objects, e) -> {
            if(e == null){
                for (ParseObject result : objects) {
                    Log.d("Object found ",result.getObjectId());
                }
            }else{
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }




    @Override
    public void onResume() {
        super.onResume();

        // Only start checking for new messages when the app becomes active in foreground
        //myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);
    }

    @Override
    public void onPause() {
        // Stop background task from refreshing messages, to avoid unnecessary traffic & battery drain
        //myHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }
    public static void setDetails(String id, String path) {
        placeId = id;
        imagePath = path;
    }



}