package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.NewReleaseMusic;

import java.util.List;

public class NewReleaseAdapter extends RecyclerView.Adapter<NewReleaseAdapter.NewReleaseViewHolder> {
    private List<NewReleaseMusic> newReleaseMusicList;

    public NewReleaseAdapter(List<NewReleaseMusic> newReleaseMusicList) {
        this.newReleaseMusicList = newReleaseMusicList;
    }

    @NonNull
    @Override
    public NewReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_release_item, parent, false);
        return new NewReleaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewReleaseViewHolder holder, int position) {
        NewReleaseMusic newReleaseMusic = newReleaseMusicList.get(position);
        if (newReleaseMusic == null) {
            return;
        } else {
            holder.ivNewRelease.setImageResource(newReleaseMusic.getResourceId());
            holder.tvNewReleaseSongName.setText(newReleaseMusic.getSongName());
            holder.tvNewReleaseAuthorName.setText(newReleaseMusic.getAuthorName());
            holder.tvNewReleaseTime.setText(newReleaseMusic.getTimeRelease());
        }
    }

    @Override
    public int getItemCount() {
        return newReleaseMusicList.size();
    }

    public class NewReleaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNewRelease;
        private TextView tvNewReleaseSongName, tvNewReleaseAuthorName, tvNewReleaseTime;
        public NewReleaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNewRelease = itemView.findViewById(R.id.ivNewRelease);
            tvNewReleaseSongName = itemView.findViewById(R.id.tvNewReleaseSongName);
            tvNewReleaseAuthorName = itemView.findViewById(R.id.tvNewReleaseAuthorName);
            tvNewReleaseTime = itemView.findViewById(R.id.tvNewReleaseTime);
        }
    }
}
