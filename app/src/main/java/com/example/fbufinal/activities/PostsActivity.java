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
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected View post;
    public String TAG = "Feed activity";
    private SwipeRefreshLayout swipeContainer;
    final FragmentManager fragmentManager = getSupportFragmentManager();
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
    String objectId,placeName;
    Fragment ratingFragment = new RatingFragment();
    ImageView ivScrim,imageActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posts_view);

        Intent i=getIntent();
        PlaceServicesRating placeToRate = (PlaceServicesRating) Parcels.unwrap(getIntent().getParcelableExtra(PlaceServicesRating.class.getSimpleName()));
        PostsFragment.setPlace(placeToRate,CODE);
        CreatePostFragment.setPlace(placeToRate,CODE);

        objectId=i.getStringExtra("objectId");
        placeName=i.getStringExtra("namePlace");
        CODE = i.getIntExtra("code",11);
        setTitle(placeName);

        ivScrim =findViewById(R.id.ivScrim);
        imageActivity = findViewById(R.id.ivServiceOnPosts);

        if (CODE==WEELCHAIR_CODE) {
            //imageActivity.setImageDrawable(getDrawable(R.drawable.wheelchair_icon_2));
            Glide.with(this).load(imageActivity).into(imageActivity);

        }else if (CODE==RAMP_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.header));
        }else if (CODE==PARKING_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.parking_icon_2));
        }else if (CODE==ELEVATOR_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.elevator_icon_2));
        }else if (CODE==DOG_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.dog_icon_2));
        }else if (CODE==BRAILLE_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.braille_icon_2));
        }else if (CODE==LIGHT_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.light_icon_2));
        }else if (CODE==SOUND_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.sound_icon_2));
        }else if (CODE==SIGNLANGUAGE_CODE) {
            imageActivity.setImageDrawable(getDrawable(R.drawable.signlanguage_icon_2));
        }

        ivScrim.setVisibility(View.GONE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        toolBarLayout.setTitle(getTitle());
        FloatingActionButton create = (FloatingActionButton) findViewById(R.id.create_action);
        FloatingActionButton star = (FloatingActionButton) findViewById(R.id.star_action);
        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back_action);

        //isAlreadyFavorite(fab);
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new PostsFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).addToBackStack(null).commit();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivScrim.setVisibility(View.GONE);

                previousFragment=currentFragment;
                currentFragment = new CreatePostFragment();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).addToBackStack(null).commit();
                //transaction.addToBackStack("feed");

            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RatingFragment.setTypeService(CODE, objectId, placeToRate);
                getSupportFragmentManager().beginTransaction().replace(R.id.child_fragment_container, ratingFragment).addToBackStack(null).commit();
                ivScrim.setVisibility(View.VISIBLE);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();

                if(getSupportFragmentManager().getBackStackEntryCount()<2){
                    finish();

                }else{
                    ivScrim.setVisibility(View.GONE);
                    getSupportFragmentManager().popBackStack();

                }

            }
        });


    }


}
