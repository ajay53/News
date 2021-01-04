package com.example.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class News implements Parcelable {

    private String status;
    private long totalResults;
    private List<Article> articles;

    public static List<News> getNews() {
        List<News> news = new ArrayList<>();

        List<Article> articles;

        for (int j = 0; j < 10; j++) {
            News news1 = new News();
            articles = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Article article = new Article();
                String url = "https://www.cnn.com/2021/01/04/middleeast/iran-uranium-enrichment-restarted-intl/index.html";
                String publishedAt = "2021-01-04T12:26:00Z";
                Source source = new Source();
                source.setName("Source " + j);
                article.setTitle("Test " + i);
                article.setPublishedAt(publishedAt);
                article.setUrl(url);
                article.setSource(source);

                articles.add(article);
            }
            news1.setArticles(articles);
            news.add(news1);
        }
        return news;
    }

    public News() {
    }

    protected News(Parcel in) {
        status = in.readString();
        totalResults = in.readLong();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeLong(totalResults);
    }
}
