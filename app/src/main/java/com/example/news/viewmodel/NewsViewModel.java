package com.example.news.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.model.News;
import com.example.news.service.NewsWebServiceClient;
import com.example.news.utility.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    private static final String TAG = "NewsViewModel";

    private final MutableLiveData<News> mNews;

    public NewsViewModel() {
        this.mNews = new MutableLiveData<>();
    }

    public LiveData<News> getNews() {
        return mNews;
    }

    public void getNewsFromSourceApi(String source) {
        Log.d(TAG, "getHeadlinesApi: ");

        Call<News> headlinesCall = NewsWebServiceClient.newsApi.getNewsFromSource(source, Constant.NEWS_API_KEY);

        headlinesCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Code" + response.code());
                    return;
                }
                mNews.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}