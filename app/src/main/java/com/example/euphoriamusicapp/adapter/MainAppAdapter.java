package com.example.euphoriamusicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.euphoriamusicapp.fragment.AccountFragment;
import com.example.euphoriamusicapp.fragment.HomeFragment;
import com.example.euphoriamusicapp.fragment.PlaylistFragment;
import com.example.euphoriamusicapp.fragment.RankFragment;
import com.example.euphoriamusicapp.fragment.SearchFragment;

public class MainAppAdapter extends FragmentStateAdapter {
    public MainAppAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PlaylistFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new RankFragment();
            case 4:
                return new AccountFragment();
            default:
                return new PlaylistFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
