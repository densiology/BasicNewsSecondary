package com.dennis.basicnewssecondary.activities;

import android.content.Intent;
import android.os.Bundle;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.adapters.pagers.MainActivityPagerAdapter;
import com.dennis.basicnewssecondary.databinding.ActivityMainBinding;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.setPagerAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), this));
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }

    public void openNews(NewsItemModel newsItem) {
        Intent newsIntent = new Intent(this, NewsActivity.class);
        newsIntent.putExtra("news_item", newsItem);
        startActivity(newsIntent);
    }
}
