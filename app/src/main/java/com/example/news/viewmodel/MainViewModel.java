package com.example.news.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.model.Article;
import com.example.news.model.News;
import com.example.news.service.NewsWebServiceClient;
import com.example.news.utility.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private static final String TAG = "NewsListViewModel";

    public static List<String> sourceIDs;
    public static List<String> sourceNames;
    public static Map<String, Integer> sourceTabPosition;
    private static final Map<String, List<Article>> map = new HashMap<>();
    private static MutableLiveData<News> mNewsList;

    public LiveData<News> getNews() {
        return mNewsList;
    }

    public MainViewModel() {
        mNewsList = new MutableLiveData<>();
    }

    public void getHeadlinesApi() {
        Log.d(TAG, "getHeadlinesApi: ");

        Call<News> headlinesCall = NewsWebServiceClient.newsApi.getHeadlines("us", 100, Constant.NEWS_API_KEY);

        headlinesCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Code" + response.code());
                    return;
                }
                assert response.body() != null;
                new GroupNewsBySourceAsyncTask().execute(response.body().getArticles());
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static class GroupNewsBySourceAsyncTask extends AsyncTask<List<Article>, Void, List<Article>> {

        @SafeVarargs
        @Override
        protected final List<Article> doInBackground(List<Article>... lists) {

            for (int i = 0; i < lists[0].size() && map.size() < 10; i++) {
                Article article = lists[0].get(i);
                String key = article.getSource().getId();

                if (key != null) {
                    if (!map.containsKey(key)) {
                        List<Article> list = new ArrayList<>();
                        list.add(article);

                        map.put(key, list);
                    } else {
                        map.get(key).add(article);
                    }
                }
                Log.d(TAG, "doInBackground: mao size: " + map.size());
            }
            sourceIDs = new ArrayList<>(map.keySet());
            Log.d(TAG, "doInBackground: sources: " + sourceIDs.toString());
            setSourcePosition();

            List<List<Article>> all = new ArrayList<>(map.values());
            List<Article> temp = new ArrayList<>();

            sourceNames = new ArrayList<>();
            for (List<Article> list :
                    all) {
                sourceNames.add(list.get(0).getSource().getName());
                temp.addAll(list);
            }
            return temp;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
            News news = new News();
            news.setArticles(articles);
            mNewsList.setValue(news);
        }
    }

    private static void setSourcePosition() {
        sourceTabPosition = new HashMap<>();

        for (int i = 0; i < sourceIDs.size(); i++) {
            sourceTabPosition.put(sourceIDs.get(i), i);
        }
    }

}
