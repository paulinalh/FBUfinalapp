package com.example.fbufinal.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.fbufinal.models.Review;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ReviewsFragment extends Fragment {
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "text";
    private static final String TAG = "ReviewsFragment";
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    static String placeId;
    static String imagePath;
    EditText etMessage;
    ImageButton ibSend;
    private List<Review> mReviews;
    RecyclerView rvChat;
    ReviewsAdapter mAdapter;
    boolean mFirstLoad;
    static final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(3);
    Handler myHandler = new android.os.Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Review.class);

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // any network interceptors must be added with the Configuration Builder given this syntax
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // Set applicationId and server based on the values in the Back4App settings.
        Parse.initialize(new Parse.Configuration.Builder(getContext())
                .applicationId("MRjGoRuk6WryotgaNXvpBv2B0ntvUO4kRS4ZxwbS") // ⚠️ TYPE IN A VALID APPLICATION ID HERE
                .clientKey("c3Zl3ou44eUPgiM3PrRU7WKAUSSdyKQSbRxnfFps") // ⚠️ TYPE IN A VALID CLIENT KEY HERE
                .clientBuilder(builder)
                .server("https://parseapi.back4app.com/").build());  // ⚠️ TYPE IN A VALID SERVER URL HERE


        // Load existing messages to begin with
        refreshMessages();

        // Make sure the Parse server is setup to configured for live queries
        // Enter the websocket URL of your Parse server
        String websocketUrl = "wss://PASTE_SERVER_WEBSOCKET_URL_HERE"; // ⚠️ TYPE IN A VALID WSS:// URL HERE

        ParseLiveQueryClient parseLiveQueryClient = null;
        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI(websocketUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ParseQuery<Review> parseQuery = ParseQuery.getQuery(Review.class);
        // This query can even be more granular (i.e. only refresh if the entry was added by some other user)
        // parseQuery.whereNotEqualTo(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());

        // Connect to Parse server
        SubscriptionHandling<Review> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

        // Listen for CREATE events on the Message class
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, (query, object) -> {
            mReviews.add(0, object);

            // RecyclerView updates need to be run on the UI thread
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    rvChat.scrollToPosition(0);
                }
            });
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        etMessage = view.findViewById(R.id.etMessage);
        ibSend = view.findViewById(R.id.ibSend);
        rvChat = view.findViewById(R.id.rvChat);


        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser();
        }

        return view;
    }

    // Get the userId from the cached currentUser object
    void startWithCurrentUser() {
        setupMessagePosting();
    }

    // Set up button event handler which posts the entered message to Parse
    void setupMessagePosting() {
        // Find the text field and button
        //etMessage = getView().findViewById(R.id.etMessage);
        //ibSend = getView().findViewById(R.id.ibSend);
        //rvChat = getView().findViewById(R.id.rvChat);
        mReviews = new ArrayList<>();
        mFirstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        mAdapter = new ReviewsAdapter(getContext(), placeId, mReviews);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(linearLayoutManager);

        // When send button is clicked, create message object on Parse

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();


                Review review = new Review();
                review.setUserId(ParseUser.getCurrentUser().getObjectId());
                review.setUsername(ParseUser.getCurrentUser().getUsername());
                review.setPlaceId(placeId);
                review.setTextReview(data);



                review.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                            refreshMessages();


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
    void refreshMessages() {
        // Construct query to execute
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Review>() {
            public void done(List<Review> reviews, ParseException e) {
                if (e == null) {
                    mReviews.clear();
                    mReviews.addAll(reviews);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });

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
        myHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }
    public static void setDetails(String id, String path) {
        placeId = id;
        imagePath = path;
    }



}