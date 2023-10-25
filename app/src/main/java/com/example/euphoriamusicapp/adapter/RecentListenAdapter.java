package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;

import java.util.List;

public class RecentListenAdapter extends RecyclerView.Adapter<RecentListenAdapter.RecentListenViewHolder>{
    private List<BasicMusicInformation> basicMusicInformationList;

    public RecentListenAdapter(List<BasicMusicInformation> recentListenList) {
        this.basicMusicInformationList = recentListenList;
    }

    @NonNull
    @Override
    public RecentListenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_listen_item, parent, false);
        return new RecentListenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentListenViewHolder holder, int position) {
        BasicMusicInformation basicMusicInformation = basicMusicInformationList.get(position);
        if (basicMusicInformation == null) {
            return;
        } else {
            holder.ivRecentListen.setImageResource(basicMusicInformation.getResourceId());
            holder.tvSongName.setText(basicMusicInformation.getSongName());
            holder.tvAuthorName.setText(basicMusicInformation.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return basicMusicInformationList.size();
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
