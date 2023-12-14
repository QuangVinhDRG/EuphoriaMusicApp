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
import com.example.euphoriamusicapp.service.MusicPlayerService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {
    public static List<MusicAndPodcast> exploreImageList;
    private Context mContext;
    public ExploreAdapter(Context mCOntext,List<MusicAndPodcast> imageList) {
        this.exploreImageList = imageList;
        this.mContext = mCOntext;
        imageList.get(0).setFeatured(false);
        imageList.get(imageList.size()-1).setFeatured(false);
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        MusicAndPodcast exploreImage = exploreImageList.get(position);
        if (exploreImage == null) {
            return;
        } else {
            Glide
                    .with(holder.ivExplore)
                    .load(exploreImage.getImage())
                    .into(holder.ivExplore);
            holder.ivExplore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickgotoPlaymusic(exploreImage,position);
                }
            });
        }
    }

    private void onClickgotoPlaymusic(MusicAndPodcast exploreImage, int position) {
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
        if(!exploreImage.isLatest() ||  !exploreImage.isFeatured()){
            exploreImage.setLatest(true);
            exploreImage.setFeatured(true);
        }
        firebaseDatabase.getReference().child("recent_music").child(user.getUid()).child(exploreImage.getSongName()).setValue(exploreImage);
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs/"+(position+1)+"/count");
        exploreImage.setCount(exploreImage.getCount() + 1);
        databaseReference.setValue(exploreImage.getCount(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent  = new Intent(mContext, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.StartMusic,exploreImage);
                bundle.putInt(Constant.ListSong,Constant.explore);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exploreImageList.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivExplore;
        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExplore = itemView.findViewById(R.id.ivExplore);

        }
    }
}
