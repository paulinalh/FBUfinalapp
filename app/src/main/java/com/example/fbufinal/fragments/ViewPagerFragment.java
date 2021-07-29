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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView ivPlace;
    TextView tvName;
    static String placeId, imagePath, placeName;
    Fragment detailsFragment = new QuickDetailsFragment();


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(String placeId, String imagePath) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setImage(String path, String name) {
        imagePath=path;
        placeName=name;
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
        //ivPlace=view.findViewById(R.id.ivPlace);
        //tvName=view.findViewById(R.id.tvName);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*tvName.setText(placeName);
        if (imagePath != "") {
            Glide.with(getContext()).load(imagePath).into(ivPlace);
        }*/
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
    }

    private void setUpViewPager(ViewPager viewPager) {

        SectionDetailsAdapter sectionAdapter = new SectionDetailsAdapter(getChildFragmentManager());
        sectionAdapter.addFragment(new DetailsFragment(), "details");
        sectionAdapter.addFragment(new MapFragment(), "maps");
        sectionAdapter.addFragment(new ReviewsFragment(), "reviews");

        viewPager.setAdapter(sectionAdapter);
    }
}