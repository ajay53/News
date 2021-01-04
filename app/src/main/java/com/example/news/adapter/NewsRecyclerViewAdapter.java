package com.example.news.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.model.Article;
import com.example.news.utility.Util;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "NewsRecyclerViewAdapter";

    private final Context context;
    private final List<Article> articles;
    private final OnItemCLickListener onItemCLickListener;

    public NewsRecyclerViewAdapter(Context context, List<Article> articles, OnItemCLickListener onItemCLickListener) {
        this.context = context;
        this.articles = articles;
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view, onItemCLickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_source_24)
                .load(articles.get(position).getUrlToImage())
                .into(holder.imgNews);

        holder.tvTitle.setText(articles.get(position).getTitle());
        holder.tvTime.setText(Util.DateTimeFormatter(articles.get(position).getPublishedAt()));

        if (position == articles.size() - 1) {
            holder.listDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemCLickListener onItemCLickListener;
        public ImageView imgNews;
        public TextView tvTitle;
        public TextView tvTime;
        public View listDivider;

        public ViewHolder(@NonNull View itemView, OnItemCLickListener onItemCLickListener) {

            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgNews = itemView.findViewById(R.id.imgNews);
            listDivider = itemView.findViewById(R.id.listDivider);

            this.onItemCLickListener = onItemCLickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemCLickListener.onNewsClick(getAdapterPosition());
        }
    }

    public interface OnItemCLickListener {
        void onNewsClick(int position);
    }
}
