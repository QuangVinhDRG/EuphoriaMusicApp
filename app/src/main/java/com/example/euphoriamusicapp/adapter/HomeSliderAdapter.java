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

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.HomeSliderViewHolder> {
    List<ImageOfMusic> homeSliderImageList;

    public HomeSliderAdapter(List<ImageOfMusic> imageOfMusicList) {
        this.homeSliderImageList = imageOfMusicList;
    }

    @NonNull
    @Override
    public HomeSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_item, parent, false);
        return new HomeSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSliderViewHolder holder, int position) {
        ImageOfMusic homeSliderImage = homeSliderImageList.get(position);
        if (homeSliderImage == null) {
            return;
        } else {
            holder.ivHomeSlider.setImageResource(homeSliderImage.getResourceId());
        }
    }

    @Override
    public int getItemCount() {
        return homeSliderImageList.size();
    }

    public class HomeSliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHomeSlider;
        public HomeSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHomeSlider = itemView.findViewById(R.id.ivHomeSlider);
        }
    }
}
