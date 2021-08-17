package com.example.watchbox.screens.moviedetails;

import android.graphics.Bitmap;

import com.example.watchbox.mvp.BasePresenterInterface;
import com.example.watchbox.mvp.BaseViewInterface;

class MovieDetailsContract {

    interface View extends BaseViewInterface<Presenter> {

        void initViews();

        void setTitle(String title);

        void setPlot(String plot);

        void setDirector(String director);

        void setYear(String year);

        void setCast(String cast);

        void setType(String type);

        void setRatings(String rating);

        void setWriters(String writers);

        void setFavourite(boolean isFavourite);

        void initListeners();

        void setPoster(Bitmap poster);

        void setWatched(boolean watched);

        void setRecommended(boolean recommended);
    }

    interface Presenter extends BasePresenterInterface {

        void onFavouriteChanged(boolean isChecked);

        void onWatchedChanged(boolean isChecked);

        void onRecommendedChanged(boolean isChecked);
    }
}