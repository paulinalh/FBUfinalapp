package com.example.fbufinal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PostsAdapter;
import com.example.fbufinal.fragments.CreatePostFragment;
import com.example.fbufinal.fragments.PostsFragment;
import com.example.fbufinal.fragments.QuickDetailsFragment;
import com.example.fbufinal.fragments.RatingFragment;
import com.example.fbufinal.fragments.ViewPagerFragment;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {
    //Activity that shows all the posts each service of each place has
    // Queries all posts from back 4 app database, as an array of pointers
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected View post;
    public String TAG = "PostsActivity";
    public final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment currentFragment = null;
    Fragment previousFragment = null;
    int WEELCHAIR_CODE = 0;
    int RAMP_CODE = 1;
    int PARKING_CODE = 2;
    int ELEVATOR_CODE = 3;
    int DOG_CODE = 4;
    int BRAILLE_CODE = 5;
    int LIGHT_CODE = 6;
    int SOUND_CODE = 7;
    int SIGNLANGUAGE_CODE = 8;
    int CODE;
    String objectId, placeName;

    Fragment ratingFragment = new RatingFragment();
    public ImageView ivScrim, imageActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts_view);

        Intent i = getIntent();
        PlaceServicesRating placeToRate = Parcels.unwrap(getIntent().getParcelableExtra(PlaceServicesRating.class.getSimpleName()));


        objectId = i.getStringExtra("objectId");
        placeName = i.getStringExtra("namePlace");
        CODE = i.getIntExtra("code", 11);
        setTitle(placeName);

        ivScrim = findViewById(R.id.ivScrim);
        imageActivity = findViewById(R.id.ivServiceOnPosts);

        PostsFragment.setPlace(placeToRate, CODE);
        CreatePostFragment.setPlace(placeToRate, CODE);

        if (CODE == WEELCHAIR_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header1));
            //Glide.with(this).load(imageActivity).into(imageActivity);

        } else if (CODE == RAMP_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header2));
        } else if (CODE == PARKING_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header3));
        } else if (CODE == ELEVATOR_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header4));
        } else if (CODE == DOG_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header5));
        } else if (CODE == BRAILLE_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header6));
        } else if (CODE == LIGHT_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header7));
        } else if (CODE == SOUND_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header9));
        } else if (CODE == SIGNLANGUAGE_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header8));
        }

        ivScrim.setVisibility(View.GONE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                //getActivity().onBackPressed();

                if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
                    finish();

                } else {
                    ivScrim.setVisibility(View.GONE);
                    getSupportFragmentManager().popBackStackImmediate();

                }
            }
        });

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);

        toolBarLayout.setTitle(getTitle());
        FloatingActionButton create = findViewById(R.id.create_action);
        FloatingActionButton star = findViewById(R.id.star_action);
        FloatingActionButton seePosts = findViewById(R.id.see_posts_action);


        currentFragment = new PostsFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).addToBackStack("posts").commit();


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivScrim.setVisibility(View.GONE);

                previousFragment = currentFragment;
                currentFragment = new CreatePostFragment();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).addToBackStack("create").commit();
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RatingFragment.setTypeService(CODE, objectId, placeToRate);
                getSupportFragmentManager().beginTransaction().replace(R.id.child_fragment_container, ratingFragment).addToBackStack("star").commit();
                ivScrim.setVisibility(View.GONE);


            }
        });

        seePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivScrim.setVisibility(View.GONE);
                previousFragment = currentFragment;
                currentFragment = new PostsFragment();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment, "seePosts").addToBackStack(null).commit();

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ivScrim.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ivScrim.setVisibility(View.GONE);

    }
}
