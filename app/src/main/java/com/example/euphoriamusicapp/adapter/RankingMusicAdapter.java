package com.example.euphoriamusicapp.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.RankingMusic;

import java.util.List;

public class RankingMusicAdapter extends BaseAdapter {
    private List<RankingMusic> rankingMusicList;

    public RankingMusicAdapter(List<RankingMusic> rankingMusicList) {
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
        RankingMusic rankingMusic = rankingMusicList.get(position);
        if (rankingMusic.getRanking() == 0) {
            tvRankingNumber.setText("Gợi ý");
            tvRankingNumber.setTextColor(Color.parseColor("#F62A2A"));
            tvRankingNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
        } else {
            tvRankingNumber.setText(String.valueOf(rankingMusic.getRanking()));
        }
        ivRankingSongImage.setImageResource(rankingMusic.getResourceId());
        tvRankingSongName.setText(rankingMusic.getSongName());
        tvRankingSongAuthor.setText(rankingMusic.getAuthorName());
        if (rankingMusic.getRanking() == 1) {
            tvRankingNumber.setTextColor(Color.parseColor("#6D0AEE"));
        }
        if (rankingMusic.getRanking() == 2) {
            tvRankingNumber.setTextColor(Color.parseColor("#409895"));
        }
        if (rankingMusic.getRanking() == 3) {
            tvRankingNumber.setTextColor(Color.parseColor("#F62A2A"));
        }
        return view;
    }
}
