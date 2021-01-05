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

public class SourceRecyclerViewAdapter extends RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "NewsRecyclerViewAdapter";

    private final Context context;
    private final List<Article> articles;
    private final NewsRecyclerViewAdapter.OnItemCLickListener onItemCLickListener;

    public SourceRecyclerViewAdapter(Context context, List<Article> articles, NewsRecyclerViewAdapter.OnItemCLickListener onItemCLickListener) {
        this.context = context;
        this.articles = articles;
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public SourceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_list_item, parent, false);
        return new SourceRecyclerViewAdapter.ViewHolder(view, onItemCLickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_source_24)
                .load(articles.get(position).getUrlToImage())
                .into(holder.imgNews);

        holder.tvTitle.setText(articles.get(position).getTitle());
        holder.tvSource.setText(articles.get(position).getSource().getName());
        if (articles.get(position).getAuthor() != null && !"null".equals(articles.get(position).getAuthor()) && !articles.get(position).getAuthor().isEmpty()) {
            holder.tvAuthor.setText(Util.getAuthor(articles.get(position).getAuthor()));
        } else {
            holder.tvSource.setVisibility(View.GONE);
        }

        if (position == articles.size() - 1) {
            holder.listDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NewsRecyclerViewAdapter.OnItemCLickListener onItemCLickListener;
        public ImageView imgNews;
        public TextView tvTitle;
        public TextView tvSource;
        public TextView tvAuthor;
        public View listDivider;

        public ViewHolder(@NonNull View itemView, NewsRecyclerViewAdapter.OnItemCLickListener onItemCLickListener) {

            super(itemView);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgNews = itemView.findViewById(R.id.imgNews);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
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
