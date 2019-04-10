package com.dennis.basicnewssecondary.adapters.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennis.basicnewssecondary.BR;
import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.customviews.LazyRecyclerView;
import com.dennis.basicnewssecondary.databinding.ItemNewsBinding;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.viewmodel.NewsViewModel;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements LazyRecyclerView.LazyLoadListener{

    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    private ArrayList<NewsItemModel> newsItems;
    private NewsViewModel viewModel;

    public NewsAdapter(NewsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        return newsItems.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_news, parent, false);
            return new NewsViewHolder(binding);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).bind(viewModel, position);
        }
    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    @Override
    public void onLazyLoad(int page) {
        // add "Loading..."
        newsItems.add(null);
        this.notifyItemInserted(newsItems.size() - 1);
        viewModel.fetchList(page);
    }

    public void setNewsItems(ArrayList<NewsItemModel> newsItems) {
        this.newsItems = newsItems;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        final ItemNewsBinding binding;
        NewsViewHolder(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(NewsViewModel viewModel, int position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
