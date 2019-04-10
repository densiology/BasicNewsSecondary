package com.dennis.basicnewssecondary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.activities.MainActivity;
import com.dennis.basicnewssecondary.database.repositories.FavoriteRepository;
import com.dennis.basicnewssecondary.database.tables.Favorite;
import com.dennis.basicnewssecondary.databinding.FragmentNewsListBinding;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.viewmodel.FavoritesViewModel;
import com.dennis.basicnewssecondary.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NewsFragment extends Fragment {

    private NewsViewModel viewModel;
    private FavoritesViewModel faveViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNewsListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false);
        initialize(binding, savedInstanceState);
        return binding.getRoot();
    }

    private void initialize(final FragmentNewsListBinding binding, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        faveViewModel = ViewModelProviders.of(getActivity()).get(FavoritesViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        viewModel.refreshing.set(true);
        viewModel.fetchList(1);

        viewModel.getStoriesMutable().observe(this, new Observer<ArrayList<NewsItemModel>>() {
            @Override
            public void onChanged(ArrayList<NewsItemModel> newsItemModels) {
                binding.recyclerView.setAfterLazyLoad(viewModel.getPageNumber(), viewModel.getTotalPages());
                viewModel.setDataInAdapter(newsItemModels);
            }
        });

        viewModel.getSelectedClick().observe(this, new Observer<NewsItemModel>() {
            @Override
            public void onChanged(NewsItemModel newsItemModel) {
                onClick(newsItemModel);
            }
        });

        viewModel.getSelectedLongClick().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                View view = binding.recyclerView.findViewHolderForAdapterPosition(integer).itemView;
                onLongClick(view, integer);
            }
        });
    }

    private void onClick(NewsItemModel newsItemModel) {
        ((MainActivity)getActivity()).openNews(newsItemModel);
    }

    private void onLongClick(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_news_list, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.save_article) {
                    saveArticle(position);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void saveArticle(int position) {
        FavoriteRepository favoriteRepository = new FavoriteRepository(getContext());
        final NewsItemModel item = viewModel.getNewsListModel().getStoriesMutable().getValue().get(position);

        favoriteRepository.getFavorite(item.getId()).observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(Favorite favorite) {
                if (favorite != null) {
                    Toast.makeText(getContext(), getString(R.string.toast_favorite_exists), Toast.LENGTH_LONG).show();
                } else {
                    // new instance to prevent it from being called twice
                    if (new FavoriteRepository(getContext()).saveFavorite(item)) {
                        refreshFavorites();
                        Toast.makeText(getContext(), getString(R.string.toast_save_article), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.toast_save_article_fail), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void refreshFavorites() {
        FavoriteRepository favoriteRepository = new FavoriteRepository(getContext());
        favoriteRepository.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                faveViewModel.getFavoritesMutable().setValue(favorites);
            }
        });
    }
}