package com.example.news.service;

import com.example.news.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<News> getHeadlines(@Query("sources") String source, @Query("apiKey") String API_KEY);
}
