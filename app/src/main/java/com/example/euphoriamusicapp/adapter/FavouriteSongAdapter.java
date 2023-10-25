package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;

import java.util.List;

public class FavouriteSongAdapter extends BaseAdapter {
    List<BasicMusicInformation> list;

    public FavouriteSongAdapter(List<BasicMusicInformation> list) {
        this.list = list;
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
        BasicMusicInformation basicMusicInformation = list.get(position);
        ivSongImage.setImageResource(basicMusicInformation.getResourceId());
        tvSongName.setText(basicMusicInformation.getSongName());
        tvArtistName.setText(basicMusicInformation.getAuthorName());
        return view;
    }
}
