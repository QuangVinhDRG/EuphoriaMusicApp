package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;

import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.service.MusicPlayerService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class RecentSearchAdapter extends BaseAdapter {
    public static List<MusicAndPodcast> recentSearchList;
    private Context mContext;
    public RecentSearchAdapter(Context mContext,List<MusicAndPodcast> recentSearchList) {
        this.mContext = mContext;
        if(!recentSearchList.isEmpty()){
            this.recentSearchList = recentSearchList;
        }else{
            this.recentSearchList = new ArrayList<>();
        }


    }

    @Override
    public int getCount() {
        return recentSearchList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_item, parent, false);
        ImageView ivRecentSearch = view.findViewById(R.id.ivRecentSearch);
        TextView tvSearchContent = view.findViewById(R.id.tvRecentSearchContent);
        TextView tvSearchArtist = view.findViewById(R.id.tvRecentSearchArtist);
        LinearLayout layoutsearch = view.findViewById(R.id.layout_item);
        MusicAndPodcast BasicMusicInformation = recentSearchList.get(position);
        Glide
                .with(ivRecentSearch)
                .load(BasicMusicInformation.getImage())
                .into(ivRecentSearch);
        tvSearchContent.setText(BasicMusicInformation.getSongName());
        tvSearchArtist.setText(BasicMusicInformation.getAuthorName());
        layoutsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickgotoPlaymusic(BasicMusicInformation,position);
            }
        });

        return view;
    }

    private void onClickgotoPlaymusic(MusicAndPodcast basicMusicInformation, int position) {
        if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying()) {
            PlayMusicActivity.mediaPlayer.reset();
            PlayMusicActivity.mediaPlayer.release();
            PlayMusicActivity.mediaPlayer = null;
        }
        if(PlayMusicOfflineActivity.mediaPlayeroffline != null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying()) {
            PlayMusicOfflineActivity.mediaPlayeroffline.reset();
            PlayMusicOfflineActivity.mediaPlayeroffline.release();
            PlayMusicOfflineActivity.mediaPlayeroffline = null;
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        boolean lastChangeLatest = basicMusicInformation.isLatest();
        boolean lastChangeFeatured = basicMusicInformation.isFeatured();
        if(!basicMusicInformation.isLatest() ||  !basicMusicInformation.isFeatured()){
            basicMusicInformation.setLatest(true);
            basicMusicInformation.setFeatured(true);
        }
        firebaseDatabase.getReference().child("recent_music").child(user.getUid()).child(basicMusicInformation.getSongName()).setValue(basicMusicInformation);
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs/"+(position+1)+"/count");
        basicMusicInformation.setCount(basicMusicInformation.getCount() + 1);
        databaseReference.setValue(basicMusicInformation.getCount(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent  = new Intent(mContext, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                basicMusicInformation.setLatest(lastChangeLatest);
                basicMusicInformation.setFeatured(lastChangeFeatured);
                bundle.putSerializable(Constant.StartMusic,basicMusicInformation);
                bundle.putInt(Constant.ListSong,Constant.search);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
