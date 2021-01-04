package com.example.news.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.model.News;
import com.example.news.service.NewsWebServiceClient;
import com.example.news.utility.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    private static final String TAG = "NewsViewModel";

    private MutableLiveData<List<News>> mNewsList;
    public News news;

    public NewsViewModel() {
        this.mNewsList = new MutableLiveData<>();
        this.mNewsList.setValue(new ArrayList<>());
    }

    public LiveData<List<News>> getNews() {
        return mNewsList;
    }

    public void getNewsFromSource() {
        String[] sources = new String[]{
                "bbc-news", "cnn", "techradar", "the-verge", "ign", "business-insider", "nbc-news", "the-times-of-india", "espn-cric-info", "techcrunch"
        };

        for (String source :
                sources) {
            getHeadlinesApi(source);
        }
    }

    private void getHeadlinesApi(String source) {
        Log.d(TAG, "getHeadlinesApi: ");

        Call<News> booksCall = NewsWebServiceClient.newsApi.getHeadlines(source, Constant.NEWS_API_KEY);

        booksCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Code" + response.code());
                    return;
                }
                news = response.body();
                List<News> temp = mNewsList.getValue();
                temp.add(news);
                mNewsList.setValue(temp);
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}