package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.RecentSearch;

import java.util.List;

public class RecentSearchAdapter extends BaseAdapter {
    private List<BasicMusicInformation> recentSearchList;

    public RecentSearchAdapter(List<BasicMusicInformation> recentSearchList) {
        this.recentSearchList = recentSearchList;
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

        BasicMusicInformation BasicMusicInformation = recentSearchList.get(position);
        Glide
                .with(ivRecentSearch)
                .load(BasicMusicInformation.getImage())
                .into(ivRecentSearch);
        tvSearchContent.setText(BasicMusicInformation.getSongName());
        tvSearchArtist.setText(BasicMusicInformation.getAuthorName());
        return view;
    }
}
