package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.fragment.playlist.playlistTab.ManagePlaylistFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSongAdapter extends BaseAdapter {
    List<BasicMusicInformation> list;

    public PlaylistSongAdapter(List<BasicMusicInformation> list) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_song_recommend_item, parent, false);
        ImageView ivSongImage = view.findViewById(R.id.ivSongImage);
        TextView tvSongName = view.findViewById(R.id.tvSongName);
        TextView tvArtistName = view.findViewById(R.id.tvArtistName);
        ImageButton ibAddSongToPlaylist = view.findViewById(R.id.ibAddSongToPlaylist);
        BasicMusicInformation basicMusicInformation = list.get(position);
        ivSongImage.setImageResource(basicMusicInformation.getResourceId());
        tvSongName.setText(basicMusicInformation.getSongName());
        tvArtistName.setText(basicMusicInformation.getAuthorName());
        ibAddSongToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new BasicMusicInformation(R.drawable.cruel_summer_image, "Cruel Summer", "Taylor Swift"));
                BasicMusicInformation songAddInformation = new BasicMusicInformation();
                songAddInformation.setSongName(basicMusicInformation.getSongName());
                songAddInformation.setAuthorName(basicMusicInformation.getAuthorName());
                songAddInformation.setResourceId(basicMusicInformation.getResourceId());
                ManagePlaylistFragment managePlaylistFragment = new ManagePlaylistFragment();
                managePlaylistFragment.setAddedPlaylistSong(list);
//                Toast.makeText(view.getContext(), basicMusicInformation.getSongName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
