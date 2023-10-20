package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.RecentSearch;

import java.util.List;

public class RecentSearchAdapter extends BaseAdapter {
    private List<RecentSearch> recentSearchList;

    public RecentSearchAdapter(List<RecentSearch> recentSearchList) {
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
        TextView tvSearchType = view.findViewById(R.id.tvRecentSearchType);
        TextView tvSearchArtist = view.findViewById(R.id.tvRecentSearchArtist);
        RecentSearch recentSearch = recentSearchList.get(position);
        ivRecentSearch.setImageResource(recentSearch.getResourceId());
        tvSearchContent.setText(recentSearch.getSearchContent());
        tvSearchType.setText(recentSearch.getSearchType());
        tvSearchArtist.setText(recentSearch.getSearchArtist());
        return view;
    }
}
