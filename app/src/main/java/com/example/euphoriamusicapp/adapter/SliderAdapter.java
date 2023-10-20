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

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<ImageOfMusic> sliderImageList;

    public SliderAdapter(List<ImageOfMusic> listSlider) {
        this.sliderImageList = listSlider;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        ImageOfMusic sliderImage = sliderImageList.get(position);
        if (sliderImage == null) {
            return;
        } else {
            holder.ivSlider.setImageResource(sliderImage.getResourceId());
        }
    }

    @Override
    public int getItemCount() {
        return sliderImageList.size();
    }
    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivSlider;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSlider = itemView.findViewById(R.id.ivSlider);
        }
    }
}
