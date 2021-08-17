package com.example.watchbox.screens.moviedetails;

import android.graphics.Bitmap;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.screens.moviedetails.repository.MovieDetailsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.example.watchbox.framework.utils.AppConstants.FAV_LIST;
import static com.example.watchbox.framework.utils.AppConstants.REC_LIST;
import static com.example.watchbox.framework.utils.AppConstants.WATCH_LIST;
import static com.example.watchbox.testutils.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
class MovieDetailsPresenterTest {

    @Mock
    private Bitmap VALID_BITMAP;

    private MovieDetailsPresenter mPresenter;

    @Mock
    private MovieDetailsContract.View mView;

    @Mock
    private MovieDetailsRepository mRepository;

    @Mock
    private MovieDetails mMovieDetails;

    public MovieDetailsPresenterTest() {}

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mPresenter = Mockito.spy(new MovieDetailsPresenter(mView, mRepository, mMovieDetails));
    }

    @Test
    public void when_OnStart_InitFunctionsCalled() {
        mPresenter.start();

        verify(mView).initViews();
        verify(mView).initListeners();
        verify(mPresenter).populateFields();
        verify(mPresenter).downloadPoster();
    }

    @Test
    public void when_posterUrl_exists_call_downloadUrl() {
        when(mMovieDetails.getPosterUrl()).thenReturn(VALID_POSTER_URL);
        mPresenter.downloadPoster();

        verify(mPresenter).makePosterImgRequest(VALID_POSTER_URL);
    }

    @Test
    public void when_posterUrl_doesNot_exist_doNothing() {
        when(mMovieDetails.getPosterUrl()).thenReturn(null);
        mPresenter.downloadPoster();

        verify(mPresenter, times(0)).makePosterImgRequest(any());
    }

    @Test
    public void when_makePosterImgReq_success_saveAndSetPoster() {
        mPresenter.makePosterImgRequest(VALID_POSTER_URL);

        verify(mRepository).makePosterRequest(VALID_POSTER_URL, mPresenter);
    }

    @Test
    public void when_movieDetails_exist_populateFields() {
        when(mMovieDetails.getPoster()).thenReturn(VALID_BITMAP);
        when(mMovieDetails.getCast()).thenReturn(VALID_CAST);
        when(mMovieDetails.getDirector()).thenReturn(VALID_DIRECTOR);
        when(mMovieDetails.getPlot()).thenReturn(VALID_PLOT);
        when(mMovieDetails.getRating()).thenReturn(VALID_RATING);
        when(mMovieDetails.getReleased()).thenReturn(VALID_RELEASED);
        when(mMovieDetails.getWriters()).thenReturn(VALID_WRITERS);
        when(mMovieDetails.getTitle()).thenReturn(VALID_TITLE);
        when(mMovieDetails.getType()).thenReturn(VALID_TYPE);
        when(mMovieDetails.isFavourite()).thenReturn(POSITIVE_FAVOURITES);
        mPresenter.populateFields();

        verify(mView).setPoster(VALID_BITMAP);
        verify(mView).setCast(VALID_CAST);
        verify(mView).setDirector(VALID_DIRECTOR);
        verify(mView).setPlot(VALID_PLOT);
        verify(mView).setRatings(VALID_RATING);
        verify(mView).setYear(VALID_RELEASED);
        verify(mView).setWriters(VALID_WRITERS);
        verify(mView).setTitle(VALID_TITLE);
        verify(mView).setType(VALID_TYPE);
        verify(mView).setFavourite(POSITIVE_FAVOURITES);
    }

    @Test
    public void when_movieDetails_null_dontPopulateFields() {
        when(mMovieDetails.getPoster()).thenReturn(null);
        when(mMovieDetails.getCast()).thenReturn(null);
        when(mMovieDetails.getDirector()).thenReturn(null);
        when(mMovieDetails.getPlot()).thenReturn(null);
        when(mMovieDetails.getRating()).thenReturn(null);
        when(mMovieDetails.getReleased()).thenReturn(null);
        when(mMovieDetails.getWriters()).thenReturn(null);
        when(mMovieDetails.getTitle()).thenReturn(null);
        when(mMovieDetails.getType()).thenReturn(null);
        when(mMovieDetails.isFavourite()).thenReturn(NEGATIVE_FAVOURITES);
        mPresenter.populateFields();

        verify(mView, new Times(0)).setPoster(any());
        verify(mView, new Times(0)).setCast(any());
        verify(mView, new Times(0)).setDirector(any());
        verify(mView, new Times(0)).setPlot(any());
        verify(mView, new Times(0)).setRatings(any());
        verify(mView, new Times(0)).setYear(any());
        verify(mView, new Times(0)).setWriters(any());
        verify(mView, new Times(0)).setTitle(any());
        verify(mView, new Times(0)).setType(any());
        verify(mView).setFavourite(NEGATIVE_FAVOURITES);
    }

    @Test
    public void when_onFavouriteChanged_checked_addToList() {
        mPresenter.onFavouriteChanged(POSITIVE_FAVOURITES);

        verify(mMovieDetails).setFavourite(POSITIVE_FAVOURITES);
        verify(mPresenter).checkCheckedAndHandle(POSITIVE_FAVOURITES, FAV_LIST);
    }

    @Test
    public void when_onFavouriteChanged_notChecked_removeFromList() {
        mPresenter.onFavouriteChanged(NEGATIVE_FAVOURITES);

        verify(mMovieDetails).setFavourite(NEGATIVE_FAVOURITES);
        verify(mPresenter).checkCheckedAndHandle(NEGATIVE_FAVOURITES, FAV_LIST);
    }

    @Test
    public void when_onWatchedChanged_checked_addToList() {
        mPresenter.onWatchedChanged(POSITIVE_FAVOURITES);

        verify(mMovieDetails).setWatched(POSITIVE_FAVOURITES);
        verify(mPresenter).checkCheckedAndHandle(POSITIVE_FAVOURITES, WATCH_LIST);
    }

    @Test
    public void when_onReccomendedChanged_checked_addToList() {
        mPresenter.onRecommendedChanged(POSITIVE_FAVOURITES);

        verify(mMovieDetails).setRecommended(POSITIVE_FAVOURITES);
        verify(mPresenter).checkCheckedAndHandle(POSITIVE_FAVOURITES, REC_LIST);
    }

    @Test
    public void when_getPosterRequest_Success_setAndDisplayPoster() {
        mPresenter.onSuccess(VALID_BITMAP);

        verify(mMovieDetails).setPoster(VALID_BITMAP);
        verify(mView).setPoster(VALID_BITMAP);
    }

    @Test
    public void when_getPosterRequest_error_logError() {
        //Currently used to log only so no need to test
    }
}