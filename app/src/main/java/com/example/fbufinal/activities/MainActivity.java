package com.example.fbufinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fbufinal.R;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.fragments.FavoritesFragment;
import com.example.fbufinal.fragments.QuickDetailsFragment;
import com.example.fbufinal.fragments.FeedFragment;
import com.example.fbufinal.fragments.ProfileFragment;
import com.example.fbufinal.fragments.SearchFragment;
import com.example.fbufinal.fragments.SpecificSearchFragment;
import com.example.fbufinal.models.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity {
    private static final int RC_CAMERA_AND_LOCATION = 123;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    public String TAG = "MainActivity";

    private boolean backNavigation() {
        SlidingUpPanelLayout slideLayout = findViewById(R.id.slide_layout);

        String tag;
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            if (slideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                return true;
            } else if (slideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                getSupportFragmentManager().popBackStackImmediate();
                int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
                tag = backEntry.getName();
                if ("home".equals(tag)) {
                    bottomNavigation.setSelectedItemId(R.id.action_home);
                } else if ("search".equals(tag)) {
                    bottomNavigation.setSelectedItemId(R.id.action_compose);

                } else if ("favorites".equals(tag)) {
                    bottomNavigation.setSelectedItemId(R.id.action_favorites);

                } else if ("profile".equals(tag)) {
                    bottomNavigation.setSelectedItemId(R.id.action_profile);

                } else if ("specificSearch".equals(tag)) {
                    android.app.Fragment fragment = getFragmentManager().findFragmentByTag(tag);
                    getSupportFragmentManager().popBackStackImmediate();
                }

                if (!"specificSearch".equals(tag)) {
                    getSupportFragmentManager().popBackStack();
                }
                return true;
            }
        } else {
            slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return false;
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        if (!backNavigation()) {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (!backNavigation()) {
            onBackPressed();
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FBU");

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // Set default selection
        bottomNavigation.setSelectedItemId(R.id.action_home);

        SlidingUpPanelLayout slideLayout = findViewById(R.id.slide_layout);
        //slider = findViewById(R.id.slide);
        //Slide down panel
        ListView listView = findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
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

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "I will get: "+adapter.getItem(position),Toast.LENGTH_SHORT).show();
                Fragment currentFragment = null;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                String tag = "specificSearch";

                if (position == 1) {
                    bottomNavigation.setSelectedItemId(R.id.action_compose);

                } else if (position == 2) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("wheelchairRatings");
                } else if (position == 3) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("rampRatings");
                } else if (position == 4) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("parkingRatings");
                } else if (position == 5) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("elevatorRatings");
                } else if (position == 6) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("dogRatings");
                } else if (position == 7) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("brailleRatings");
                } else if (position == 8) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("lightsRatings");
                } else if (position == 9) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("soundRatings");
                } else if (position == 10) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("signlanguageRatings");
                } else if (position == 0) {
                    currentFragment = new SpecificSearchFragment();
                    fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tag).commit();
                    SpecificSearchFragment.setKey("recommendedPlaces");
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
        String tagFragment;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = new Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_home:
                    currentFragment = new FeedFragment();
                    tagFragment = "home";
                    break;
                case R.id.action_compose:

                    currentFragment = new SearchFragment();
                    tagFragment = "search";

                    break;
                case R.id.action_favorites:
                    currentFragment = new FavoritesFragment();
                    tagFragment = "favorites";

                    break;
                case R.id.action_profile:
                    currentFragment = new ProfileFragment();
                    tagFragment = "profile";

                    break;

            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, currentFragment).addToBackStack(tagFragment).commit();
            return true;
        }

    };


}