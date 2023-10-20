package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.RecentMusic;

import java.util.List;

public class RecentListenAdapter extends RecyclerView.Adapter<RecentListenAdapter.RecentListenViewHolder>{
    private List<RecentMusic> recentMusicList;

    public RecentListenAdapter(List<RecentMusic> recentListenList) {
        this.recentMusicList = recentListenList;
    }

    @NonNull
    @Override
    public RecentListenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_listen_item, parent, false);
        return new RecentListenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentListenViewHolder holder, int position) {
        RecentMusic recentMusic = recentMusicList.get(position);
        if (recentMusic == null) {
            return;
        } else {
            holder.ivRecentListen.setImageResource(recentMusic.getResourceId());
            holder.tvSongName.setText(recentMusic.getSongName());
            holder.tvAuthorName.setText(recentMusic.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return recentMusicList.size();
    }

    public class RecentListenViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivRecentListen;
        private TextView tvSongName, tvAuthorName;
        public RecentListenViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRecentListen = itemView.findViewById(R.id.ivRecentListen);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
        }
    }
}
