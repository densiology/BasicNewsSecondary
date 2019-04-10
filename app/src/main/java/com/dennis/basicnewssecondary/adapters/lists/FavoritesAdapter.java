package com.dennis.basicnewssecondary.adapters.lists;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dennis.basicnewssecondary.BR;
import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.database.tables.Favorite;
import com.dennis.basicnewssecondary.databinding.ItemFavoritesBinding;
import com.dennis.basicnewssecondary.viewmodel.FavoritesViewModel;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Favorite> favorites;
    private FavoritesViewModel viewModel;

    public FavoritesAdapter(FavoritesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFavoritesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_favorites, parent, false);
        return new FavoritesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        final ItemFavoritesBinding binding;
        FavoritesViewHolder(ItemFavoritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(FavoritesViewModel viewModel, int position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
