package com.example.fbufinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Set default selection
        bottomNavigation.setSelectedItemId(R.id.action_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_home:
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    //fragment = new PostsFragment();
                    break;
                case R.id.action_compose:
                    Toast.makeText(MainActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                    //fragment = new ComposeFragment();
                    break;
                case R.id.action_profile:
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    //fragment = new ProfileFragment();
                    break;

            }
            //fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        }

    };
}