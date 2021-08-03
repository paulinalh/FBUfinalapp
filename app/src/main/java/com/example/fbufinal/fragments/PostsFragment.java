package com.example.fbufinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PostsAdapter;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//Show posts from that specific place querying parse, and refreshed using pulling up
public class PostsFragment extends Fragment {
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected View post;
    public String TAG = "Feed activity";
    static PlaceServicesRating place;
    static int CODE;
    private SwipeRefreshLayout swipeContainer;


    public PostsFragment() {
        // Required empty public constructor
    }

    public static void setPlace(PlaceServicesRating placeToRate, int code) {
        place = placeToRate;
        CODE = code;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts, place, CODE);
        queryPosts();


        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));


        //Refresh
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync(int page) {
        adapter.clear();
        queryPosts();
        swipeContainer.setRefreshing(false);

    }


    private void queryPosts() {


        List<Post> posts = new ArrayList<>();

        if (CODE == 0) {
            posts = place.getWheelchairPosts();
        } else if (CODE == 1) {
            posts = place.getRampPosts();
        } else if (CODE == 2) {
            posts = place.getParkingPosts();
        } else if (CODE == 3) {
            posts = place.getElevatorPosts();
        } else if (CODE == 4) {
            posts = place.getDogPosts();
        } else if (CODE == 5) {
            posts = place.getBraillePosts();
        } else if (CODE == 6) {
            posts = place.getLightsPosts();
        } else if (CODE == 7) {
            posts = place.getSoundPosts();
        } else if (CODE == 8) {
            posts = place.getSignPosts();
        }

        allPosts = posts;
        adapter.addAll(posts);
        adapter.notifyDataSetChanged();

    }

}