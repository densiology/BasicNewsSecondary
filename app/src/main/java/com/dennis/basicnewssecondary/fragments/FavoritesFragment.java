package com.dennis.basicnewssecondary.fragments;

import android.content.Context;
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
import com.dennis.basicnewssecondary.databinding.FragmentFavoritesListBinding;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.viewmodel.FavoritesViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFavoritesListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites_list, container, false);
        initialize(binding, savedInstanceState);
        return binding.getRoot();
    }

    private void initialize(final FragmentFavoritesListBinding binding, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(FavoritesViewModel.class); // viewmodel of the activity
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        getFavoritesFromDB();

        viewModel.getFavoritesMutable().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                viewModel.getFavoritesAdapter().setFavorites(favorites);
                viewModel.getFavoritesAdapter().notifyDataSetChanged();
            }
        });

        viewModel.getSelectedClick().observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(Favorite favorite) {
                onClick(favorite);
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

    public FavoritesViewModel getViewModel() {
        return viewModel;
    }

    private void getFavoritesFromDB() {
        FavoriteRepository favoriteRepository = new FavoriteRepository(getContext());
        favoriteRepository.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                viewModel.getFavoritesMutable().setValue(favorites);
            }
        });
    }

    private void onClick(Favorite favorite) {
        NewsItemModel item = new NewsItemModel(favorite.getId(), favorite.getTitle(), favorite.getMain(),
                favorite.getTeaser(), favorite.getDate(), favorite.getBase_url(), favorite.getBase_filename());
        ((MainActivity)getActivity()).openNews(item);
    }

    private void onLongClick(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_news_favorites, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete_article) {
                    deleteArticle(position);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void deleteArticle(int position) {
        FavoriteRepository favoriteRepository = new FavoriteRepository(getContext());
        Favorite favorite = viewModel.getFavoritesAdapter().getFavorites().get(position);
        favoriteRepository.deleteFavorite(favorite.getId());

        // new instance to prevent it from being called twice
        new FavoriteRepository(getContext()).getFavorite(favorite.getId()).observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(Favorite favorite) {
                if (favorite == null) {
                    getFavoritesFromDB();
                    Toast.makeText(getContext(), getString(R.string.toast_delete_article), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_delete_article_fail), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}