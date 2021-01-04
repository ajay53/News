package com.example.news.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsRecyclerViewAdapter;
import com.example.news.model.Article;
import com.example.news.model.News;
import com.example.news.utility.Util;
import com.example.news.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewPagerFragment extends Fragment implements NewsRecyclerViewAdapter.OnItemCLickListener {
    private static final String TAG = "NewsFragment";

    //widgets
    private RecyclerView rvNews;
    private NewsRecyclerViewAdapter recyclerViewAdapter;

    //variables
    private News news;
    private Context context;
    private NewsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initializeViews(view);
        setRecyclerView(new ArrayList<>());
    }

    public static NewsViewPagerFragment getInstance(News news) {
        NewsViewPagerFragment fragment = new NewsViewPagerFragment();

        if (news != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", news);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    private void init() {
        context = getContext();

        if (getArguments() != null) {
            this.news = getArguments().getParcelable("news");
        }
    }

    private void initializeViews(View view) {
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        rvNews = view.findViewById(R.id.rvNews);

        setRecyclerView(news.getArticles());
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setRecyclerView(List<Article> articles) {
        Log.d(TAG, "setRecyclerView: ");
        if (articles.size() == 0) return;

        recyclerViewAdapter = new NewsRecyclerViewAdapter(context, articles, this);
        rvNews.setAdapter(recyclerViewAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onNewsClick(int position) {
        Log.d(TAG, "onNewsClick: ");

        Util.DateTimeFormatter(news.getArticles().get(position).getPublishedAt());
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", news.getArticles().get(position).getUrl());
        getActivity().startActivity(intent);


    }
}