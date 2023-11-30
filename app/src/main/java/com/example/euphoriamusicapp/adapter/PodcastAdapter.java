package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.Podcast;

import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder> {
    public static List<Podcast> podcastList;
    private Context mContext;

    public PodcastAdapter(Context Context,List<Podcast> podcastList) {
        this.podcastList = podcastList;
        this.mContext = Context;
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
            Glide
                    .with(holder.ivPodcast)
                    .load(podcast.getImage())
                    .into(holder.ivPodcast);
            holder.tvPodcastName.setText(podcast.getPodcastName());
            holder.tvPodcastAuthorName.setText(podcast.getAuthorname());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickgotoPlayPodcast(podcast);
                }
            });
        }
    }
    private void onClickgotoPlayPodcast(Podcast podcast) {
        if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying()){
            PlayMusicActivity.mediaPlayer.reset();
        }
        Intent intent  = new Intent(mContext, PlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("podcast",podcast);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

    public class PodcastViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView ivPodcast;
        private TextView tvPodcastName, tvPodcastAuthorName;
        public PodcastViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPodcast = itemView.findViewById(R.id.ivPodcast);
            tvPodcastName = itemView.findViewById(R.id.tvPodcastName);
            tvPodcastAuthorName = itemView.findViewById(R.id.tvPodcastAuthorName);
            layout = itemView.findViewById(R.id.layout_item);

        }
    }
}
