package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.TopicAndCategory;

import java.util.List;

public class TopicAndCategoryAdapter extends RecyclerView.Adapter<TopicAndCategoryAdapter.TopicCategoryViewHolder> {
    private List<TopicAndCategory> topicAndCategoryList;

    public TopicAndCategoryAdapter(List<TopicAndCategory> topicAndCategoryList) {
        this.topicAndCategoryList = topicAndCategoryList;
    }

    @NonNull
    @Override
    public TopicCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_category_item, parent, false);
        return new TopicCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicCategoryViewHolder holder, int position) {
        TopicAndCategory topicAndCategory = topicAndCategoryList.get(position);
        if (topicAndCategory == null) {
            return;
        } else {
            holder.ivTopicCategory.setImageResource(topicAndCategory.getResourceId());
            holder.tvTopicCategoryName.setText(topicAndCategory.getTopicAndCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return topicAndCategoryList.size();
    }

    public class TopicCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTopicCategory;
        TextView tvTopicCategoryName;
        public TopicCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTopicCategory = itemView.findViewById(R.id.ivTopicCategory);
            tvTopicCategoryName = itemView.findViewById(R.id.tvTopicCategoryName);
        }
    }


}
