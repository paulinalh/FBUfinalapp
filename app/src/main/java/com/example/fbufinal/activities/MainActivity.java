package com.example.fbufinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.FavoritePlacesAdapter;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.fragments.FavoritesFragment;
import com.example.fbufinal.fragments.QuickDetailsFragment;
import com.example.fbufinal.fragments.FeedFragment;
import com.example.fbufinal.fragments.ProfileFragment;
import com.example.fbufinal.fragments.SearchFragment;
import com.example.fbufinal.fragments.SectionsFragment;
import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.Place;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements PlacesAdapter.IPlaceRecyclerView {
    private static final int RC_CAMERA_AND_LOCATION = 123;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigation;
    private FragmentContainer flContainer;
    private View queryText;
    private Button mSearchButton;
    private TextView mSearchResult;
    private StringBuilder mResult;
    public String TAG = "MainActivity";
    RecyclerView rvFavorites;
    List<Favorite> favorites = new ArrayList<>();
    FavoritePlacesAdapter favAdapter;
    //boolean hasLocationPermission = false;
    SlidingPaneLayout slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //flContainer.onFindViewById(R.id.flContainer);
        //String apiKey = getString(R.string.api_key);


/*

        rvFavorites= findViewById(R.id.rvFavorites);
        rvFavorites.setAdapter(favAdapter);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        queryFavorites();

        favAdapter = new FavoritePlacesAdapter(this, favorites);*/
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set default selection
        bottomNavigation.setSelectedItemId(R.id.action_home);

SlidingUpPanelLayout slideLayout=findViewById(R.id.slide_layout);
         //slider = findViewById(R.id.slide);
        //Slide down panel
       ListView listView=findViewById(R.id.listView);
       ArrayAdapter adapter=new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_1,
               new String[]{"Recommended Places",
                       "Search by name",
                       "Places with wheelchair accessibility",
                       "Places with quality ramps",
                       "Places with accessible parking",
                       "Places with quality elevators",
                       "Places with access to service dogs",
                       "Places with services in Braille",
                       "Places with lights control for people with sensitivity",
                       "Places with sound control for people with sensitivity",
                       "Places with services in sign language"
               });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Toast.makeText(MainActivity.this, "I will get: "+adapter.getItem(position),Toast.LENGTH_SHORT).show();
                Fragment currentFragment = null;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(position==0){
                   slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                    //currentFragment = new SearchFragment();
                    //fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).commit();
                    bottomNavigation.setSelectedItemId(R.id.action_compose);

                }

            }
        });


    }



    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, RC_CAMERA_AND_LOCATION, perms)
                            .setRationale(R.string.camera_and_location_rationale)
                            .setPositiveButtonText(R.string.rationale_ask_ok)
                            .setNegativeButtonText(R.string.rationale_ask_cancel)
                            .setTheme(R.style.my_style)
                            .build());
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_home:
                    currentFragment = new FeedFragment();
                    //ft.replace(R.id.flContainer, currentFragment).commit();
                    /*Intent i = new Intent(MainActivity.this, PlacesActivity.class);
                    startActivity(i);*/

                    break;
                case R.id.action_compose:
                    Toast.makeText(MainActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                    //currentFragment = new ServicesFragment();
                    //onLogoutButton();
                    //initialize map fragment
                    currentFragment = new SearchFragment();
                    //open fragment
                    //getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,fragment).commit();
                    break;
                case R.id.action_favorites:
                    //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    currentFragment= new FavoritesFragment();
                    //onLogoutButton();
                    break;
                case R.id.action_profile:
                    //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    currentFragment= new ProfileFragment();
                    //onLogoutButton();
                    break;

            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).commit();
            return true;
        }

    };


    Fragment detailsFragment = new QuickDetailsFragment();
    Fragment sectionsFragment = new SectionsFragment();

    @Override
    public void goToPlaceDetails(Place place) {
        String placeId= place.getPlaceId();
        String imagePath = place.getImagePath();
        detailsFragment= QuickDetailsFragment.newInstance(placeId, imagePath);


    }


}