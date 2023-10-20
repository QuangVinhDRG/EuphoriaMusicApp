package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.Podcast;

import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {
    private List<Podcast> podcastList;

    public PodcastAdapter(List<Podcast> podcastList) {
        this.podcastList = podcastList;
    }

    @NonNull
    @Override
    public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.podcast_item, parent, false);
        return new PodcastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
        Podcast podcast = podcastList.get(position);
        if (podcast == null) {
            return;
        } else {
            holder.ivPodcast.setImageResource(podcast.getResourceId());
            holder.tvPodcastName.setText(podcast.getPodcastName());
            holder.tvPodcastAuthorName.setText(podcast.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

    public class PodcastViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPodcast;
        private TextView tvPodcastName, tvPodcastAuthorName;
        public PodcastViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPodcast = itemView.findViewById(R.id.ivPodcast);
            tvPodcastName = itemView.findViewById(R.id.tvPodcastName);
            tvPodcastAuthorName = itemView.findViewById(R.id.tvPodcastAuthorName);
        }
    }
}
