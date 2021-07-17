package com.example.fbufinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.fragments.FeedFragment;
import com.example.fbufinal.models.Place;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity {
    private static final int RC_CAMERA_AND_LOCATION = 123;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigation;
    public String TAG="MainActivity";
    boolean hasLocationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Set default selection
        //bottomNavigation.setSelectedItemId(R.id.action_home);
        //discoverPlaces();

    }



    private void discoverPlaces() {

    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "permissions were granted!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission was denied", Toast.LENGTH_SHORT).show();
            }
        }
        // Forward results to EasyPermissions
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }*/

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
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        fragment = new FeedFragment();
                    /*Intent i = new Intent(MainActivity.this, PlacesActivity.class);
                    startActivity(i);*/

                    break;
                case R.id.action_compose:
                    Toast.makeText(MainActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                    //fragment = new ComposeFragment();
                    break;
                case R.id.action_profile:
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    //fragment = new ProfileFragment();
                    onLogoutButton();
                    break;

            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        }

    };

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