package com.dennis.basicnewssecondary.bindings;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dennis.basicnewssecondary.adapters.lists.FavoritesAdapter;
import com.dennis.basicnewssecondary.adapters.lists.NewsAdapter;
import com.dennis.basicnewssecondary.adapters.pagers.MainActivityPagerAdapter;
import com.dennis.basicnewssecondary.customviews.LazyRecyclerView;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.utilities.Common;

import java.net.URLDecoder;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class CustomViewBindings {

    @BindingAdapter("setMainPager")
    public static void setupFragmentPager(ViewPager viewPager, MainActivityPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("setNewsAdapter")
    public static void setupNewsList(LazyRecyclerView recyclerView, NewsAdapter adapter) {
        recyclerView.setBeforeLazyLoad(adapter, adapter);
    }

    @BindingAdapter("setFavoritesAdapter")
    public static void setupFavoritesList(RecyclerView recyclerView, FavoritesAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void getImageFromUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .into(imageView);
    }

    @BindingAdapter("setNewsWebView")
    public static void setupNewsWebView(WebView webView, NewsItemModel item) {
        int fontSizeMain = 4;
        int fontSizeHeader = 5;
        int fontSizeDate = 2;

        if (item.getTitle().contains("%")) {
            item.setTitle(item.getTitle().replaceAll("[%]", "%25"));
        }
        item.setTitle(URLDecoder.decode(item.getTitle()));

        String customHtml = "<html><body><b>"
                + "<font size=\"" + fontSizeHeader + "\"><font_reg>" + item.getTitle() + "</font_reg></font></b>"
                + "<font_size=\"" + fontSizeDate + "\"><br><br><i><font_reg>" + item.getDate() + "</font_reg></i></font><br><br>"
                + "<center><img src=\""+ item.getBase_url() + Common.LINK_PHOTO_PREFIX + item.getBase_filename() + "\"></center>"
                + "<font_size=\"" + fontSizeMain + "\"><font_light>" + item.getMain() + "</font_light></font><br><br>"
                + "</body></html>";

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(customHtml, "text/html", "utf-8");
    }
}