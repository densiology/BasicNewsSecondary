package com.dennis.basicnewssecondary.adapters.pagers;

import android.content.Context;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.fragments.FavoritesFragment;
import com.dennis.basicnewssecondary.fragments.NewsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MainActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else {
            return new FavoritesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.title_news);
        } else {
            return context.getString(R.string.title_favorites);
        }
    }
}