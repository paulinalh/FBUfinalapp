package com.example.fbufinal.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbufinal.R;
import com.example.fbufinal.adapters.SectionDetailsAdapter;
import com.google.android.material.tabs.TabLayout;

//View pager for the Place details activity
public class ViewPagerFragment extends Fragment {
    private static int fragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    static String imagePath, placeName;

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    public static void setImage(String path, String name) {
        imagePath = path;
        placeName = name;
    }

    public static void setFragment(int f) {
        fragment = f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Need to define the child fragment layout
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);


        //Views for section fragments
        viewPager = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout2);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(fragment, false);

    }

    private void setUpViewPager(ViewPager viewPager) {

        SectionDetailsAdapter sectionAdapter = new SectionDetailsAdapter(getChildFragmentManager());
        sectionAdapter.addFragment(new DetailsFragment(), "details");
        sectionAdapter.addFragment(new ServicesFragment(), "services");
        sectionAdapter.addFragment(new MapFragment(), "maps");
        sectionAdapter.addFragment(new ReviewsFragment(), "reviews");

        viewPager.setAdapter(sectionAdapter);
    }
}