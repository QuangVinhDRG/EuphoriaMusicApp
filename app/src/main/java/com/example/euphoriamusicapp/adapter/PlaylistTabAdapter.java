package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.Playlist;

import java.util.List;

public class PlaylistTabAdapter extends BaseAdapter {
    private List<String> playlists;
    public PlaylistTabAdapter(List<String> playlists) {
        this.playlists = playlists;
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        TextView tvPlaylistName = view.findViewById(R.id.tvPlaylistName);
        TextView tvPlaylistAuthor = view.findViewById(R.id.tvPlaylistAuthor);
        String playlist = playlists.get(position);
        tvPlaylistName.setText(playlist);
        tvPlaylistAuthor.setText("0 bài hát");
        return view;
    }
}
