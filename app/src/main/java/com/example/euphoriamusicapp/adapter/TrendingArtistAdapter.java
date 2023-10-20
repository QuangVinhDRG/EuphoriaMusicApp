package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.TrendingArtist;

import java.util.List;

public class TrendingArtistAdapter extends RecyclerView.Adapter<TrendingArtistAdapter.TrendingArtistViewHolder> {
    private List<TrendingArtist> trendingArtistList;

    public TrendingArtistAdapter(List<TrendingArtist> trendingArtistList) {
        this.trendingArtistList = trendingArtistList;
    }

    @NonNull
    @Override
    public TrendingArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_artist_item, parent, false);
        return new TrendingArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingArtistViewHolder holder, int position) {
        TrendingArtist trendingArtist = trendingArtistList.get(position);
        if (trendingArtist == null) {
            return;
        } else {
            holder.ivTrendingArtist.setImageResource(trendingArtist.getResourceId());
            holder.tvTrendingArtist.setText(trendingArtist.getArtistName());
        }
    }

    @Override
    public int getItemCount() {
        return trendingArtistList.size();
    }

    public class TrendingArtistViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivTrendingArtist;
        private TextView tvTrendingArtist;
        public TrendingArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTrendingArtist = itemView.findViewById(R.id.ivTrendingArtist);
            tvTrendingArtist = itemView.findViewById(R.id.tvTrendingArtist);
        }
    }
}
