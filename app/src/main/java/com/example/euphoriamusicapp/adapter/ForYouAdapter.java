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

public class ForYouAdapter extends RecyclerView.Adapter<ForYouAdapter.ForYouViewHolder> {
    private List<ImageOfMusic> forYouImageList;

    public ForYouAdapter(List<ImageOfMusic> forYouImageList) {
        this.forYouImageList = forYouImageList;
    }

    @NonNull
    @Override
    public ForYouViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_you_item, parent, false);
        return new ForYouViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForYouViewHolder holder, int position) {
        ImageOfMusic forYouImage = forYouImageList.get(position);
        if (forYouImage == null) {
            return;
        } else {
            holder.ivForYou.setImageResource(forYouImage.getResourceId());
        }
    }

    @Override
    public int getItemCount() {
        return forYouImageList.size();
    }

    public class ForYouViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivForYou;
        public ForYouViewHolder(@NonNull View itemView) {
            super(itemView);
            ivForYou = itemView.findViewById(R.id.ivForYou);
        }
    }
}
