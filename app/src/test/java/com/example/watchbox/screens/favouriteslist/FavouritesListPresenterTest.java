package com.example.watchbox.screens.favouriteslist;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.screens.favouriteslist.repository.FavouritesListRepository;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.watchbox.framework.utils.AppConstants.FAV_LIST;
import static com.example.watchbox.framework.utils.AppConstants.REC_LIST;
import static com.example.watchbox.framework.utils.AppConstants.WATCH_LIST;
import static com.example.watchbox.framework.utils.RequestConstants.*;
import static com.example.watchbox.testutils.TestConstants.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class FavouritesListPresenterTest {

    private FavouritesListPresenter mPresenter;

    @Mock
    private FavouritesListContract.View mView;

    @Mock
    private FavouritesListRepository mRepository;

    @Mock
    private MovieDetails mMovieDetails;

    @Mock
    private List<MovieDetails> mMovieDetailsList;

    @Mock
    private Map<String, MovieDetails> mSearchResults;

    public FavouritesListPresenterTest() {}

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mPresenter = Mockito.spy(new FavouritesListPresenter(mView, mRepository));
    }

    @Test
    public void when_presenterStart_initViews() {
        mPresenter.start();

        verify(mView).initViews();
        verify(mView).initListeners();
    }

    @Test
    public void when_favouritesList_exists_displayList() {
        when(mRepository.doesUserHaveList(FAV_LIST)).thenReturn(POSITIVE_FAVOURITES);
        mPresenter.checkListAndSetList(FAV_LIST);

        verify(mPresenter).checkListAndSetList(FAV_LIST);
        verify(mView, new Times(0)).hideListRecyclerView();
    }

    @Test
    public void when_favouritesList_doesNotExist_hideList() {
        when(mRepository.doesUserHaveList(FAV_LIST)).thenReturn(NEGATIVE_FAVOURITES);
        mPresenter.checkListAndSetList(FAV_LIST);

        verify(mPresenter, new Times(0)).initListRecyclerView(FAV_LIST);
        verify(mView).hideListRecyclerView();
    }

    @Test
    public void when_initFavourites_setUp_recyclerView() {
        when(mRepository.getList(FAV_LIST)).thenReturn(mMovieDetailsList);
        mPresenter.initListRecyclerView(FAV_LIST);

        verify(mView).setListRecyclerView(mMovieDetailsList);
        verify(mView).showListRecyclerView();
    }

    @Test
    public void when_onQueryTextSubmit_makeSearchRequest() {
        mPresenter.onQueryTextSubmit(VALID_SHORT_QUERY);

        verify(mView).showLoadingDialog();
        verify(mRepository).makeFilmRequestFromSearch(VALID_SHORT_QUERY, mPresenter);
    }

    @Test
    public void when_onSearchRequest_networkSuccess_and_apiSuccess_handleSuccess() {
        mPresenter.onSearchSuccessResponse(SUCCESS_SEARCH_API_RESPONSE);

        verify(mView).dismissDialog();
        verify(mPresenter).handleSearchRequestSuccess(SUCCESS_SEARCH_API_RESPONSE);
        verify(mPresenter, new Times(0)).onError(any());
    }

    @Test
    public void when_onSearchRequest_networkSuccess_and_apiError_handleSuccess() {
        mPresenter.onSearchSuccessResponse(ERROR_API_RESPONSE);

        // dismissDialog is also called by onError so requires Times(2)
        verify(mView, new Times(2)).dismissDialog();
        verify(mPresenter).onError(any());
    }

    @Test
    public void when_onSearchRequest_networkError_handleError() {
        mPresenter.onSearchErrorResponse(ERROR_STRING);

        verify(mPresenter).onError(ERROR_STRING);
    }

    @Test
    public void when_searchResultItemCLicked_makeSingleRequest() {
        when(mMovieDetails.getId()).thenReturn(VALID_ID);
        mPresenter.searchResultsItemClicked(mMovieDetails);

        verify(mPresenter).setCurrentSearchItem(mMovieDetails);
        verify(mRepository).makeFilmRequestForMovie(VALID_ID, mPresenter);
    }

    @Test
    public void when_onSingleRequest_networkSuccess_and_apiSuccess_handleSuccess() {
        mPresenter.onSingleSuccessResponse(SUCCESS_SINGLE_API_RESPONSE);

        verify(mView).dismissDialog();
        verify(mPresenter).handleMovieRequestSuccess(SUCCESS_SINGLE_API_RESPONSE);
        verify(mPresenter, new Times(0)).onError(any());
    }

    @Test
    public void when_onSingleRequest_networkSuccess_and_apiError_handleError() {
        mPresenter.setCurrentSearchItem(mMovieDetails);
        mPresenter.onSingleSuccessResponse(ERROR_API_RESPONSE);

        verify(mView).dismissDialog();
        verify(mPresenter).handleSingleRequestError(mMovieDetails);
        verify(mPresenter, new Times(0)).handleMovieRequestSuccess(any());
    }

    @Test
    public void when_onSingleRequest_networkError_handleError() {
        mPresenter.setCurrentSearchItem(mMovieDetails);
        mPresenter.onSingleErrorResponse(ERROR_STRING);

        verify(mView).dismissDialog();
        verify(mPresenter).handleSingleRequestError(mMovieDetails);
    }

    @Test
    public void when_handle_movieResponseError_navigateTo_DetailsScreen() {
        mPresenter.handleSingleRequestError(mMovieDetails);

        verify(mView).navigateToMovieDetailsScreen(mMovieDetails);
    }

    @Test
    public void when_getMessageFromErrorResponse_returnCorrectValue() {
        assert ERROR_MESSAGE.equals(mPresenter.getMessageFromErrorResponse(ERROR_API_RESPONSE));
    }

    @Test
    public void when_onSearchCrossClick_clearSearchResults() {
        mPresenter.mSearchResults = mSearchResults;
        mPresenter.onSearchCrossClick();

        verify(mSearchResults).clear();
        verify(mView).updateSearchResultsList(any());
        verify(mView).hideSearchRecyclerView();
    }

    @Test
    public void when_favouriteItemClick_navigateTo_movieDetailsScreen() {
        mPresenter.ListItemClicked(mMovieDetails);

        verify(mView).navigateToMovieDetailsScreen(mMovieDetails);
    }

    @Test
    public void when_onResume_checkFavouritesAndUpdateList() {
        mPresenter.onResume();

        verify(mPresenter).checkListAndSetList(FAV_LIST);
    }

    @Test
    public void when_onClearFavouritesClicked_clearFavourites() {
        mPresenter.onClearDataClicked();

        verify(mRepository).clearList(FAV_LIST);
        verify(mView).updateListRecyclerView(any());
        verify(mView).hideListRecyclerView();
    }

    @Test
    public void when_handleMovieRequestSuccess_createMovie_andNavigate() {
        mPresenter.handleMovieRequestSuccess(SUCCESS_SINGLE_API_RESPONSE);

        verify(mPresenter).createMovieDetailsFromJsonObject(any());
        verify(mPresenter).makeJsonObj(SUCCESS_SINGLE_API_RESPONSE);
        verify(mView).navigateToMovieDetailsScreen(any());
    }

    @Test
    public void when_hasReceivedApiSuccess_validInput_returnTrue(){
        assert mPresenter.hasReceivedApiSuccess(SUCCESS_SINGLE_API_RESPONSE);
    }

    @Test
    public void when_hasReceivedApiSuccess_errorInput_returnFalse() {
        assert !mPresenter.hasReceivedApiSuccess(ERROR_API_RESPONSE);
    }

    @Test
    public void when_makeJsonObj_returnValidObject() {
        JsonObject baseObject = new JsonObject();
        baseObject.addProperty(VALID_JSON_PARAM, VALID_JSON_VALUE);

        Assert.assertEquals(baseObject, mPresenter.makeJsonObj(VALID_JSON_STRING));
    }

    @Test
    public void when_handleSearchRequestSuccess_createResultAndDisplay() {
        mPresenter.handleSearchRequestSuccess(SUCCESS_SEARCH_API_RESPONSE);

        verify(mPresenter).createSearchResultsFromResponse(SUCCESS_SEARCH_API_RESPONSE);
        verify(mPresenter).displaySearchResult();
    }

    @Test
    public void when_createSearchResultFromResponse_returnValidResult() {
        Map<String, MovieDetails> resultMap = mPresenter.createSearchResultsFromResponse(SUCCESS_SEARCH_API_RESPONSE);
        Assert.assertNotEquals(null, resultMap);
    }

    @Test
    public void when_displaySearchResults_updateRecyclerView(){
        mPresenter.mSearchResults = this.mSearchResults;
        when(mSearchResults.values()).thenReturn(new ArrayList<>());
        mPresenter.displaySearchResult();

        verify(mView).setSearchResultsRecyclerView(any());
        verify(mView).showSearchRecyclerView();
    }

    @Test
    public void when_createMovieDetailsFromJsonObject_returnCorrectObject(){
        MovieDetails details = new MovieDetails();
        details.setId(VALID_ID);
        details.setTitle(VALID_TITLE);
        details.setPlot(VALID_PLOT);

        JsonObject object = new JsonObject();
        object.addProperty(R_ID, VALID_ID);
        object.addProperty(R_TITLE, VALID_TITLE);
        object.addProperty(R_PLOT, VALID_PLOT);

        MovieDetails resultDetails = mPresenter.createMovieDetailsFromJsonObject(object);

        assert resultDetails.getId().equals(details.getId());
        assert resultDetails.getTitle().equals(details.getTitle());
        assert resultDetails.getPlot().equals(details.getPlot());
    }

    @Test
    public void when_getRatingFromJsonObject_returnCorrectRating() {
        JsonObject object = mPresenter.makeJsonObj(SUCCESS_SINGLE_API_RESPONSE);
        assert VALID_RATING.equals(mPresenter.getRatingFromJsonObject(object));
    }

    @Test
    public void when_onFavouritesClicked_setRecyclerViewList() {
        mPresenter.onFavouritesListClicked();

        verify(mPresenter).checkListAndSetList(FAV_LIST);
    }

    @Test
    public void when_onWatchedClicked_setRecyclerViewList() {
        mPresenter.onWatchedListClicked();

        verify(mPresenter).checkListAndSetList(WATCH_LIST);
    }

    @Test
    public void when_onReccomendedClicked_setRecyclerViewList() {
        mPresenter.onRecommendedListClicked();

        verify(mPresenter).checkListAndSetList(REC_LIST);
    }

}