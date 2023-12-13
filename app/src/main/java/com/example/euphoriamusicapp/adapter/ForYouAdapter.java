package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.ImageOfMusic;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ForYouAdapter extends RecyclerView.Adapter<ForYouAdapter.ForYouViewHolder> {
    public static List<MusicAndPodcast> forYouImageList;
    private Context mContext;
    public ForYouAdapter(Context mContext,List<MusicAndPodcast> forYouImageList) {
        this.forYouImageList = forYouImageList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ForYouViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_you_item, parent, false);
        return new ForYouViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForYouViewHolder holder, int position) {
        MusicAndPodcast forYouImage = forYouImageList.get(position);
        if (forYouImage == null) {
            return;
        } else {
            Glide
                    .with(holder.ivForYou)
                    .load(forYouImage.getImage())
                    .into(holder.ivForYou);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickgotoPlaymusic(forYouImage,position);
                }
            });
        }
    }

    private void onClickgotoPlaymusic(MusicAndPodcast forYouImage, int position) {
        if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying()) {
            PlayMusicActivity.mediaPlayer.release();
            PlayMusicActivity.mediaPlayer = null;
        }
        if(PlayMusicOfflineActivity.mediaPlayeroffline != null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying()) {
            PlayMusicOfflineActivity.mediaPlayeroffline.reset();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(!forYouImage.isLatest() ||  !forYouImage.isFeatured()){
            forYouImage.setLatest(true);
            forYouImage.setFeatured(true);
        }
        firebaseDatabase.getReference().child("recent_music").child(user.getUid()).child(forYouImage.getSongName()).setValue(forYouImage);
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs/"+(position+1)+"/count");
        forYouImage.setCount(forYouImage.getCount() + 1);
        databaseReference.setValue(forYouImage.getCount(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent  = new Intent(mContext, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.StartMusic,forYouImage);
                bundle.putInt(Constant.ListSong,Constant.foryou);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return forYouImageList.size();
    }

    public class ForYouViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivForYou;
        private LinearLayout layout;
        public ForYouViewHolder(@NonNull View itemView) {
            super(itemView);
            ivForYou = itemView.findViewById(R.id.ivForYou);
            layout = itemView.findViewById(R.id.layout_itemforyou);

        }
    }
}
