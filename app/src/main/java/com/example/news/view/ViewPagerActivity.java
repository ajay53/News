package com.example.news.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.news.R;
import com.example.news.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //widgets
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageView imgLeft, imgRight;
    private TextView tvSource;

    //variables
    private List<String> sourceIDs;
    private List<String> sourceNames;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        tvSource = findViewById(R.id.tvSource);

        if (getIntent().getExtras() != null) {
            sourceIDs = getIntent().getExtras().getStringArrayList("sourceIDs");
            sourceNames = getIntent().getExtras().getStringArrayList("sourceNames");
            position = getIntent().getExtras().getInt("position", 0);
        }
        init();
    }

    private void init() {
        //switching tabs via buttons
        imgLeft.setOnClickListener(view ->
        {
            if (position > 0) {
                position--;
            }
            viewPager.setCurrentItem(position);
        });
        imgRight.setOnClickListener(view ->
        {
            if (position < 9) {
                position++;
            }
            viewPager.setCurrentItem(position);
        });

        setTabs(sourceIDs);
        viewPager.setCurrentItem(position);
    }

    private void setTabs(List<String> sources) {
        Log.d(TAG, "setTabs: ");

        List<Fragment> fragments = new ArrayList<>();

        for (String source :
                sources) {
            NewsTabItemFragment fragment = NewsTabItemFragment.getInstance(source);
            fragments.add(fragment);
        }
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);

        //setting 1st source
        tvSource.setText(sourceNames.get(0));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(sourceNames.get(position));
                }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                tvSource.setText(sourceNames.get(position));
                Log.d(TAG, "onTabSelected: position: " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}