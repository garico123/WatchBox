package com.example.watchbox.screens.favouriteslist;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.mvp.BasePresenterInterface;
import com.example.watchbox.mvp.BaseViewInterface;

import java.util.List;

class FavouritesListContract {

    interface View extends BaseViewInterface<Presenter>{
        void initViews();

        void initListeners();

        void setSearchResultsRecyclerView(List<MovieDetails> resultsList);

        void navigateToMovieDetailsScreen(MovieDetails details);

        void updateSearchResultsList(List<MovieDetails> list);

        void hideSearchRecyclerView();

        void showSearchRecyclerView();

        void setListRecyclerView(List<MovieDetails> list);

        void showListRecyclerView();

        void hideListRecyclerView();

        void updateListRecyclerView(List<MovieDetails> list);
    }

    interface Presenter extends BasePresenterInterface {

        void onQueryTextSubmit(String newText);

        void searchResultsItemClicked(MovieDetails results);

        void onSearchCrossClick();

        void ListItemClicked(MovieDetails results);

        void onResume();

        void onClearDataClicked();

        void onFavouritesListClicked();

        void onWatchedListClicked();

        void onRecommendedListClicked();
    }
}