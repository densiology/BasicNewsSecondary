package com.dennis.basicnewssecondary.viewmodel;

import com.dennis.basicnewssecondary.adapters.pagers.MainActivityPagerAdapter;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MainActivityPagerAdapter pagerAdapter;

    public MainActivityPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(MainActivityPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }
}
