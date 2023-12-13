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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.NewReleaseMusic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NewReleaseAdapter extends RecyclerView.Adapter<NewReleaseAdapter.NewReleaseViewHolder> {
    public static List<MusicAndPodcast> newReleaseMusicList;
    private Context mContext;

    public NewReleaseAdapter(Context context,List<MusicAndPodcast> newReleaseMusicList) {
        this.newReleaseMusicList = newReleaseMusicList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_release_item, parent, false);
        return new NewReleaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewReleaseViewHolder holder, int position) {
        MusicAndPodcast newReleaseMusic = newReleaseMusicList.get(position);
        if (newReleaseMusic == null) {
            return;
        } else {
            Glide
                    .with(holder.ivNewRelease)
                    .load(newReleaseMusic.getImage())
                    .into(holder.ivNewRelease);
            holder.tvNewReleaseSongName.setText(newReleaseMusic.getSongName());
            holder.tvNewReleaseAuthorName.setText(newReleaseMusic.getAuthorName());
            holder.lllayout_newRelease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickgotoPlaymusic(newReleaseMusic,position);
                }
            });
        }
    }

    private void onClickgotoPlaymusic(MusicAndPodcast newReleaseMusic, int position) {
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
        if(!newReleaseMusic.isLatest() ||  !newReleaseMusic.isFeatured()){
            newReleaseMusic.setLatest(true);
            newReleaseMusic.setFeatured(true);
        }
        firebaseDatabase.getReference().child("recent_music").child(user.getUid()).child(newReleaseMusic.getSongName()).setValue(newReleaseMusic);
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs/"+(position+1)+"/count");
        newReleaseMusic.setCount(newReleaseMusic.getCount() + 1);
        databaseReference.setValue(newReleaseMusic.getCount(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Intent intent  = new Intent(mContext, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.StartMusic,newReleaseMusic);
                bundle.putInt(Constant.ListSong,Constant.newrelease);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newReleaseMusicList.size();
    }

    public class NewReleaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNewRelease;
        private TextView tvNewReleaseSongName, tvNewReleaseAuthorName, tvNewReleaseTime;
        private LinearLayout lllayout_newRelease;
        public NewReleaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNewRelease = itemView.findViewById(R.id.ivNewRelease);
            tvNewReleaseSongName = itemView.findViewById(R.id.tvNewReleaseSongName);
            tvNewReleaseAuthorName = itemView.findViewById(R.id.tvNewReleaseAuthorName);
            lllayout_newRelease = itemView.findViewById(R.id.lllayout_newRelease);

        }
    }
}
