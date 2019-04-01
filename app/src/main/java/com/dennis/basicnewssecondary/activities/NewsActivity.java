package com.dennis.basicnewssecondary.activities;

import android.os.Bundle;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.databinding.ActivityNewsBinding;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.viewmodel.NewsContentViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        NewsContentViewModel viewModel = ViewModelProviders.of(this).get(NewsContentViewModel.class);
        binding.setViewModel(viewModel);

        NewsItemModel item = getIntent().getParcelableExtra("news_item");
        viewModel.init(item);
    }
}
