package com.example.euphoriamusicapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.euphoriamusicapp.fragment.playlist.TabAlbumFragment;
import com.example.euphoriamusicapp.fragment.playlist.TabPlaylistFragment;
import com.example.euphoriamusicapp.fragment.playlist.TabRecentFragment;

public class ViewPagerPlaylistTabAdapter extends FragmentStatePagerAdapter {
    public ViewPagerPlaylistTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabPlaylistFragment();
            case 1:
                return new TabAlbumFragment();
            case 2:
                return new TabRecentFragment();
            default:
                return new TabPlaylistFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Playlist";
            case 1:
                return "Album";
            case 2:
                return "Gần đây";
            default:
                return "Playlist";
        }
    }
}
