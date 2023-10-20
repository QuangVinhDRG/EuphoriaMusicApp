package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.HotAlbum;

import java.util.List;

public class HotAlbumAdapter extends RecyclerView.Adapter<HotAlbumAdapter.HotAlbumViewHolder> {
    private List<HotAlbum> hotAlbumList;

    public HotAlbumAdapter(List<HotAlbum> hotAlbumList) {
        this.hotAlbumList = hotAlbumList;
    }

    @NonNull
    @Override
    public HotAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_album_item, parent, false);
        return new HotAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotAlbumViewHolder holder, int position) {
        HotAlbum hotAlbum = hotAlbumList.get(position);
        if (hotAlbum == null) {
            return;
        } else {
            holder.ivHotAlbum.setImageResource(hotAlbum.getResourceId());
            holder.tvHotAlbumName.setText(hotAlbum.getAlbumName());
            holder.tvHotAlbumAuthorName.setText(hotAlbum.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return hotAlbumList.size();
    }

    public class HotAlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHotAlbum;
        private TextView tvHotAlbumName, tvHotAlbumAuthorName;
        public HotAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHotAlbum = itemView.findViewById(R.id.ivHotAlbum);
            tvHotAlbumName = itemView.findViewById(R.id.tvHotAlbumName);
            tvHotAlbumAuthorName = itemView.findViewById(R.id.tvHotAlbumAuthorName);
        }
    }
}
