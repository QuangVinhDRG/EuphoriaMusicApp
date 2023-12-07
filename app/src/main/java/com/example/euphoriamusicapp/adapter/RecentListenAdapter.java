package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecentListenAdapter extends RecyclerView.Adapter<RecentListenAdapter.RecentListenViewHolder>{
    public static List<MusicAndPodcast> basicMusicInformationList;
    private Context mContext;

    public RecentListenAdapter(Context context,List<MusicAndPodcast> recentListenList) {
        this.mContext = context;
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
        MusicAndPodcast basicMusicInformation = basicMusicInformationList.get(position);

        if (basicMusicInformation == null) {
            return;
        } else {
            Glide
                    .with(holder.ivRecentListen)
                    .load(basicMusicInformation.getImage())
                    .into(holder.ivRecentListen);
            holder.tvSongName.setText(basicMusicInformation.getSongName());
            holder.tvAuthorName.setText(basicMusicInformation.getAuthorName());
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickgotoPlaymusic(basicMusicInformation,position);
                }
            });
        }

    }

    private void onClickgotoPlaymusic(MusicAndPodcast basicMusicInformation,int p) {
        if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying()){
            PlayMusicActivity.mediaPlayer.reset();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs/"+(p+1)+"/count");
        basicMusicInformation.setCount(basicMusicInformation.getCount() + 1);
        databaseReference.setValue(basicMusicInformation.getCount(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent  = new Intent(mContext, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Song",basicMusicInformation);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return basicMusicInformationList.size();
    }

    public class RecentListenViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivRecentListen;
        private TextView tvSongName, tvAuthorName;
        private LinearLayout layoutItem;
        public RecentListenViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            ivRecentListen = itemView.findViewById(R.id.ivRecentListen);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);

        }
    }
}
