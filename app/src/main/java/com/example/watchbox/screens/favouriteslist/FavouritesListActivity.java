package com.example.watchbox.screens.favouriteslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchbox.R;
import com.example.watchbox.framework.ApplicationController;
import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.framework.recyclerviews.movieItem.MovieItemRecyclerAdapter;
import com.example.watchbox.framework.utils.AppConstants;
import com.example.watchbox.mvp.BaseActivity;
import com.example.watchbox.screens.favouriteslist.repository.FavouritesListRepository;
import com.example.watchbox.screens.moviedetails.MovieDetailsActivity;

import java.util.List;

public class FavouritesListActivity extends BaseActivity implements FavouritesListContract.View {
    private FavouritesListContract.Presenter mPresenter;
    private SearchView svSearchBar;
    private MovieItemRecyclerAdapter raSearchResultsAdapter, raListAdapter;
    private RecyclerView rvSearchResultsRecyclerView, rvFavouritesRecyclerView;
    private ImageView ivSearchCrossBtn, ivFavouritesDivider;
    private ConstraintLayout clSearchPlaceholder, clFavouritesPlaceholder;
    private Button btClearFavourites, btFavouritesList, btWatchedList, btReccomendedList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_activity_favourites_list);

        ApplicationController.initialize(getApplicationContext());

        new FavouritesListPresenter(this, FavouritesListRepository.getInstance());
    }

    @Override
    public void setPresenter(FavouritesListContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void initViews() {
        svSearchBar = findViewById(R.id.sv_search_bar);
        svSearchBar.setSubmitButtonEnabled(true);

        ivSearchCrossBtn = svSearchBar.findViewById(R.id.search_close_btn);
        ivFavouritesDivider = findViewById(R.id.iv_favourites_divider);

        rvSearchResultsRecyclerView = findViewById(R.id.rv_search_results);
        rvFavouritesRecyclerView = findViewById(R.id.rv_favourites_list);

        clFavouritesPlaceholder = findViewById(R.id.cl_favourites_placeholder);
        clSearchPlaceholder = findViewById(R.id.cl_search_placeholder);

        btClearFavourites = findViewById(R.id.bt_clear_data);
        btFavouritesList = findViewById(R.id.bt_favourite_list);
        btWatchedList = findViewById(R.id.bt_watched_list);
        btReccomendedList = findViewById(R.id.bt_reccomended_list);
    }

    @Override
    public void initListeners() {
        svSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onQueryTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ivSearchCrossBtn.setOnClickListener(v -> mPresenter.onSearchCrossClick());
        btClearFavourites.setOnClickListener(v -> mPresenter.onClearDataClicked());
        btFavouritesList.setOnClickListener(v -> mPresenter.onFavouritesListClicked());
        btWatchedList.setOnClickListener(v -> mPresenter.onWatchedListClicked());
        btReccomendedList.setOnClickListener(v -> mPresenter.onRecommendedListClicked());
    }

    @Override
    public void setSearchResultsRecyclerView(List<MovieDetails> results) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchResultsRecyclerView.setLayoutManager(layoutManager);

        raSearchResultsAdapter = new MovieItemRecyclerAdapter(this, results,
                position -> mPresenter.searchResultsItemClicked(results.get(position))
        );

        rvSearchResultsRecyclerView.setAdapter(raSearchResultsAdapter);
    }

    @Override
    public void setListRecyclerView(List<MovieDetails> favourites) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFavouritesRecyclerView.setLayoutManager(layoutManager);

        raListAdapter = new MovieItemRecyclerAdapter(this, favourites,
                position -> mPresenter.ListItemClicked(favourites.get(position)));

        rvFavouritesRecyclerView.setAdapter(raListAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void navigateToMovieDetailsScreen(MovieDetails details) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(AppConstants.MOVIE_DETAILS, details);
        startActivity(intent);
    }

    @Override
    public void updateSearchResultsList(List<MovieDetails> list) {
        raSearchResultsAdapter.setList(list);
        raSearchResultsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideSearchRecyclerView() {
        rvSearchResultsRecyclerView.setVisibility(View.GONE);
        clSearchPlaceholder.setVisibility(View.VISIBLE);
        ivFavouritesDivider.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchRecyclerView() {
        rvSearchResultsRecyclerView.setVisibility(View.VISIBLE);
        clSearchPlaceholder.setVisibility(View.GONE);
        ivFavouritesDivider.setVisibility(View.GONE);
    }

    @Override
    public void showListRecyclerView() {
        rvFavouritesRecyclerView.setVisibility(View.VISIBLE);
        clFavouritesPlaceholder.setVisibility(View.GONE);
    }

    @Override
    public void hideListRecyclerView() {
        rvFavouritesRecyclerView.setVisibility(View.GONE);
        clFavouritesPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateListRecyclerView(List<MovieDetails> favourites) {
        raListAdapter.setList(favourites);
        raListAdapter.notifyDataSetChanged();
    }
}