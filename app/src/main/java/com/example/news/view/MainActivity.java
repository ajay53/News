package com.example.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsRecyclerViewAdapter;
import com.example.news.adapter.SourceRecyclerViewAdapter;
import com.example.news.model.Article;
import com.example.news.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.OnItemCLickListener {
    private static final String TAG = "NewsListActivity";

    //widgets
    private RecyclerView rvNews;
    private SourceRecyclerViewAdapter recyclerViewAdapter;

    //variables
    private List<Article> articles;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        rvNews = findViewById(R.id.rvNews);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getHeadlinesApi();

        viewModel.getNews().observe(this, news -> {
            articles = news.getArticles();
            setRecyclerView(articles);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    private void setRecyclerView(List<Article> articles) {
        Log.d(TAG, "setRecyclerView: ");
        if (articles.size() == 0) return;

        recyclerViewAdapter = new SourceRecyclerViewAdapter(this, articles, this);
        rvNews.setAdapter(recyclerViewAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        intent.putExtra("position", MainViewModel.sourceTabPosition.get(articles.get(position).getSource().getId()));
        intent.putStringArrayListExtra("sourceIDs", (ArrayList<String>) MainViewModel.sourceIDs);
        intent.putStringArrayListExtra("sourceNames", (ArrayList<String>) MainViewModel.sourceNames);
        startActivity(intent);
    }
}