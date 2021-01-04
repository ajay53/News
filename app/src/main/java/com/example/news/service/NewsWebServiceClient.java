package com.example.news.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsWebServiceClient {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static NewsApi newsApi = retrofit.create(NewsApi.class);
}
