package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;

import com.example.euphoriamusicapp.data.MusicAndPodcast;

import java.util.List;

public class FavouriteSongAdapter extends BaseAdapter {
    public static List<MusicAndPodcast> list;
    Context mContext;


    public FavouriteSongAdapter(List<MusicAndPodcast> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_song_item, parent, false);
        ImageView ivSongImage = view.findViewById(R.id.ivSongImage);
        TextView tvSongName = view.findViewById(R.id.tvSongName);
        TextView tvArtistName = view.findViewById(R.id.tvArtistName);
        LinearLayout llSong = view.findViewById(R.id.llSong);
        MusicAndPodcast basicMusicInformation = list.get(position);
        ivSongImage.setImageBitmap(stringToBitmap(basicMusicInformation.getImage()));
        tvSongName.setText(basicMusicInformation.getSongName());
        tvArtistName.setText(basicMusicInformation.getAuthorName());
        llSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayMusicOfflineActivity.mediaPlayeroffline != null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying()) {
                    PlayMusicOfflineActivity.mediaPlayeroffline.reset();
                }
                if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying()) {
                    PlayMusicActivity.mediaPlayer.reset();
                }
                Intent intent  = new Intent(mContext, PlayMusicOfflineActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.StartMusic,basicMusicInformation);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
    public static Bitmap stringToBitmap(String encodedString) {
        byte[] decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
