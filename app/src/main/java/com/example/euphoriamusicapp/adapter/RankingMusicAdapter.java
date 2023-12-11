package com.example.euphoriamusicapp.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.RankingMusic;

import java.util.List;

public class RankingMusicAdapter extends BaseAdapter {
    private List<MusicAndPodcast> rankingMusicList;

    public RankingMusicAdapter(List<MusicAndPodcast> rankingMusicList) {
        this.rankingMusicList = rankingMusicList;
    }

    @Override
    public int getCount() {
        return rankingMusicList.size();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        TextView tvRankingNumber = view.findViewById(R.id.tvRankingNumber);
        ImageView ivRankingSongImage = view.findViewById(R.id.ivRankingSongImage);
        TextView tvRankingSongName = view.findViewById(R.id.tvRankingSongName);
        TextView tvRankingSongAuthor = view.findViewById(R.id.tvRankingSongAuthor);
        MusicAndPodcast rankingMusic = rankingMusicList.get(position);
        tvRankingNumber.setText(String.valueOf(position+1));
        Glide
                .with(ivRankingSongImage)
                .load(rankingMusic.getImage())
                .into(ivRankingSongImage);
        tvRankingSongName.setText(rankingMusic.getSongName());
        tvRankingSongAuthor.setText(rankingMusic.getAuthorName());

        return view;
    }
}
