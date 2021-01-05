package com.example.news.service;

import com.example.news.model.News;
import com.example.news.model.NewsSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<News> getHeadlines(@Query("country") String country, @Query("pageSize") int pageSize, @Query("apiKey") String API_KEY);

    @GET("sources")
    Call<NewsSource> getSources(@Query("language") String language, @Query("country") String country, @Query("apiKey") String API_KEY);

    @GET("top-headlines")
    Call<News> getNewsFromSource(@Query("sources") String source, @Query("apiKey") String API_KEY);
}
