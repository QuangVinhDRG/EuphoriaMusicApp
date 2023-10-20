package com.example.euphoriamusicapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.euphoriamusicapp.fragment.rank.TabActivityFragment;
import com.example.euphoriamusicapp.fragment.rank.TabRankingFragment;

public class ViewPagerRankTabAdapter extends FragmentStatePagerAdapter {
    public ViewPagerRankTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabRankingFragment();
            case 1:
                return new TabActivityFragment();
            default:
                return new TabRankingFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ranking";
            case 1:
                return "Activity";
            default:
                return "Ranking";
        }
    }
}
