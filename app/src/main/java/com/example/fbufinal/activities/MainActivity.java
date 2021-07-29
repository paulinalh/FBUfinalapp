package com.example.fbufinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.fragments.QuickDetailsFragment;
import com.example.fbufinal.fragments.FeedFragment;
import com.example.fbufinal.fragments.ProfileFragment;
import com.example.fbufinal.fragments.SearchFragment;
import com.example.fbufinal.fragments.SectionsFragment;
import com.example.fbufinal.models.Place;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements PlacesAdapter.IPlaceRecyclerView {
    private static final int RC_CAMERA_AND_LOCATION = 123;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigation;
    private FragmentContainer flContainer;
    public String TAG = "MainActivity";
    //boolean hasLocationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //flContainer.onFindViewById(R.id.flContainer);

        String apiKey = getString(R.string.google_android_key);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

// Create a new Places client instance.
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull @NotNull com.google.android.libraries.places.api.model. Place searchPlace) {
                // TODO: Get info about the selected place.
                //Toast.makeText(MainActivity.this,searchPlace.getId(), Toast.LENGTH_SHORT ).show();
                String searchPlaceId=searchPlace.getId();
                Log.i(TAG, "Place: " + searchPlace.getName() + ", " + searchPlace.getId());
                Intent i = new Intent(MainActivity.this, PlaceDetailsActivity.class);
                i.putExtra("searchPlaceId",searchPlaceId);
                startActivity(i);
            }

            @Override
            public void onError(@NonNull @NotNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        //Slide down panel
        ListView listView=findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"uno", "dos", "tres"}));

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set default selection
        bottomNavigation.setSelectedItemId(R.id.action_home);
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

    public void onLogoutButton() {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        // navigate backwards to Login screen
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
        finish();

    }
}