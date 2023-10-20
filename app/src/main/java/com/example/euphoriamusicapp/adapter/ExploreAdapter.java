package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.ImageOfMusic;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {
    private List<ImageOfMusic> exploreImageList;

    public ExploreAdapter(List<ImageOfMusic> imageList) {
        this.exploreImageList = imageList;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        ImageOfMusic exploreImage = exploreImageList.get(position);
        if (exploreImage == null) {
            return;
        } else {
            holder.ivExplore.setImageResource(exploreImage.getResourceId());
        }
    }

    @Override
    public int getItemCount() {
        return exploreImageList.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivExplore;
        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExplore = itemView.findViewById(R.id.ivExplore);
        }
    }
}
