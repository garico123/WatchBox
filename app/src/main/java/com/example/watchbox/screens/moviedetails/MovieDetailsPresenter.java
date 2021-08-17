package com.example.watchbox.screens.moviedetails;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.watchbox.framework.utils.Utils;
import com.example.watchbox.mvp.BasePresenter;
import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.mvp.BaseRepository;
import com.example.watchbox.screens.moviedetails.repository.MovieDetailsRepository;

import static com.example.watchbox.framework.utils.AppConstants.FAV_LIST;
import static com.example.watchbox.framework.utils.AppConstants.REC_LIST;
import static com.example.watchbox.framework.utils.AppConstants.WATCH_LIST;
import static com.example.watchbox.framework.utils.Utils.isEmpty;

public class MovieDetailsPresenter extends BasePresenter implements MovieDetailsContract.Presenter,
        BaseRepository.ImageResponseListener {

    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    private MovieDetailsContract.View mView;
    private MovieDetailsRepository mRepository;
    private MovieDetails mMovieDetails;

    MovieDetailsPresenter(MovieDetailsContract.View view,
                          MovieDetailsRepository repository,
                          MovieDetails movieDetails) {
        super(view);
        this.mView = view;
        this.mRepository = repository;
        this.mMovieDetails = movieDetails;
        this.mView.setPresenter(this);
    }
    @Override
    public void start() {
        mView.initViews();
        mView.initListeners();
        populateFields();
        downloadPoster();
    }

    void downloadPoster() {
        if (!Utils.isEmpty(mMovieDetails.getPosterUrl())) {
            makePosterImgRequest(mMovieDetails.getPosterUrl());
        }
    }

    protected void makePosterImgRequest(String posterUrl) {
        mRepository.makePosterRequest(posterUrl, this);
    }

    protected void populateFields() {
        if (mMovieDetails.getPoster() != null) {
            mView.setPoster(mMovieDetails.getPoster());
        }

        if (!isEmpty(mMovieDetails.getTitle())) {
            mView.setTitle(mMovieDetails.getTitle());
        }

        if (!isEmpty(mMovieDetails.getPlot())) {
            mView.setPlot(mMovieDetails.getPlot());
        }

        if (!isEmpty(mMovieDetails.getDirector())) {
            mView.setDirector(mMovieDetails.getDirector());
        }

        if (!isEmpty(mMovieDetails.getReleased())) {
            mView.setYear(mMovieDetails.getReleased());
        }

        if (!isEmpty(mMovieDetails.getCast())) {
            mView.setCast(mMovieDetails.getCast());
        }

        if (!isEmpty(mMovieDetails.getType())) {
            mView.setType(mMovieDetails.getType());
        }

        if (!isEmpty(mMovieDetails.getRating())) {
            mView.setRatings(mMovieDetails.getRating());
        }

        if (!isEmpty(mMovieDetails.getWriters())) {
            mView.setWriters(mMovieDetails.getWriters());
        }

        mView.setFavourite(mMovieDetails.isFavourite());
        mView.setWatched(mMovieDetails.isWatched());
        mView.setRecommended(mMovieDetails.isRecommended());
    }

    @Override
    public void onFavouriteChanged(boolean isChecked) {
        mMovieDetails.setFavourite(isChecked);
        checkCheckedAndHandle(isChecked, FAV_LIST);
    }

    @Override
    public void onWatchedChanged(boolean isChecked) {
        mMovieDetails.setWatched(isChecked);
        checkCheckedAndHandle(isChecked, WATCH_LIST);
    }

    @Override
    public void onRecommendedChanged(boolean isChecked) {
        mMovieDetails.setRecommended(isChecked);
        checkCheckedAndHandle(isChecked, REC_LIST);
    }

    protected void checkCheckedAndHandle(boolean isChecked, String listKey) {
        if (!isChecked){
            mRepository.removeFromList(mMovieDetails, listKey);
        } else {
            mRepository.saveToList(mMovieDetails, listKey);
        }
    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        mMovieDetails.setPoster(bitmap);
        mView.setPoster(bitmap);
    }

    @Override
    public void onError(String error) {
        //Log error as default poster exists so no need to display error dialog
        Log.e(TAG, error);
    }
}