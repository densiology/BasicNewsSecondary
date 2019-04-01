package com.dennis.basicnewssecondary.customviews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LazyRecyclerView extends RecyclerView {

    private final int VISIBLE_THRESHOLD = 1;
    private boolean lazyLoading;
    private int lastVisibleItem, totalItemCount;
    private int currentPage, totalPages;

    private LinearLayoutManager layoutManager;
    private LazyLoadListener listener;

    public LazyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LazyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LazyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setBeforeLazyLoad(RecyclerView.Adapter<?> adapter, LazyLoadListener listener) {
        this.listener = listener;
        //this.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.setLayoutManager(this.layoutManager);
        this.setAdapter(adapter);
    }

    public void setAfterLazyLoad(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.lazyLoading = false;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        totalItemCount = layoutManager.getItemCount();
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        if (!lazyLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)
                && totalPages > currentPage) {
            listener.onLazyLoad(currentPage + 1);
            lazyLoading = true;
        }
    }

    public interface LazyLoadListener {
        void onLazyLoad(int page);
    }
}