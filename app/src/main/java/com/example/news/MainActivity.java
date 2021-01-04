package com.example.news;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.news.adapter.ViewPagerAdapter;
import com.example.news.model.News;
import com.example.news.view.NewsViewPagerFragment;
import com.example.news.viewmodel.NewsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //widgets
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    public ViewPagerAdapter tabAdapter;
    private ImageView imgLeft, imgRight;
    private TextView tvSource;

    //variables
    private NewsViewModel viewModel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        tvSource = findViewById(R.id.tvSource);

        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

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

        viewModel.getNewsFromSource();

        viewModel.getNews().observe(this, newsList -> {
            //set newsArr
            if (newsList.size() == 10) {
                setTabs(newsList);
            }
        });
//        setTabs(News.getNews());
    }

    private void setTabs(List<News> newsArr) {
        Log.d(TAG, "setTabs: ");

        List<Fragment> fragments = new ArrayList<>();

        for (News news :
                newsArr) {
            NewsViewPagerFragment fragment = NewsViewPagerFragment.getInstance(news);
            fragments.add(fragment);
        }
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);

        tvSource.setText(newsArr.get(0).getArticles().get(0).getSource().getName());
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    String s = newsArr.get(position).getArticles().get(0).getSource().getName();
                    tab.setText(s);
                }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                tvSource.setText(newsArr.get(position).getArticles().get(0).getSource().getName());
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