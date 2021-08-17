package com.example.watchbox.screens.favouriteslist;

import android.util.Log;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.framework.utils.AppConstants;
import com.example.watchbox.framework.utils.RequestConstants;
import com.example.watchbox.mvp.BasePresenter;
import com.example.watchbox.screens.favouriteslist.repository.FavouritesListRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.watchbox.framework.utils.AppConstants.FAV_LIST;
import static com.example.watchbox.framework.utils.AppConstants.REC_LIST;
import static com.example.watchbox.framework.utils.AppConstants.WATCH_LIST;
import static com.example.watchbox.framework.utils.Utils.getBooleanFromJsonObject;
import static com.example.watchbox.framework.utils.Utils.getStringFromJsonObject;

public class FavouritesListPresenter extends BasePresenter implements FavouritesListContract.Presenter,
        FavouritesListRepository.SearchResponseListener,
        FavouritesListRepository.SingleDetailsResponseListener {
    private final static String TAG = FavouritesListPresenter.class.getSimpleName();
    private FavouritesListContract.View mView;
    private FavouritesListRepository mRepository;
    protected Map<String,MovieDetails> mSearchResults;
    private MovieDetails mCurrentSearchItem;
    private String mCurrentListKey = FAV_LIST;

    FavouritesListPresenter(FavouritesListContract.View view, FavouritesListRepository repository) {
        super(view);
        this.mView = view;
        this.mRepository = repository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.initViews();
        mView.initListeners();

        mView.setListRecyclerView(new ArrayList<>());
        mView.hideListRecyclerView();
    }

    protected void checkListAndSetList(String listKey) {
        mCurrentListKey = listKey;

        if (mRepository.doesUserHaveList(listKey)) {
            mView.updateListRecyclerView(mRepository.getList(listKey));
            mView.showListRecyclerView();

        } else {
            mView.hideListRecyclerView();
        }
    }

    protected void initListRecyclerView(String listKey) {
        mView.setListRecyclerView(mRepository.getList(listKey));
        mView.showListRecyclerView();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        mView.showLoadingDialog();
        mRepository.makeFilmRequestFromSearch(query, this);
    }

    @Override
    public void onSearchSuccessResponse(String response) {
        mView.dismissDialog();

        if (hasReceivedApiSuccess(response)){
            handleSearchRequestSuccess(response);
        } else {
            onError(getMessageFromErrorResponse(response));
        }
    }

    @Override
    public void onSearchErrorResponse(String error) {
        onError(error);
    }

    @Override
    public void searchResultsItemClicked(MovieDetails result) {
        setCurrentSearchItem(result);
        mRepository.makeFilmRequestForMovie(result.getId(), this);
    }

    @Override
    public void onSingleSuccessResponse(String response) {
        mView.dismissDialog();

        if (hasReceivedApiSuccess(response)){
            handleMovieRequestSuccess(response);

        } else {
            Log.e(TAG, getMessageFromErrorResponse(response));
            handleSingleRequestError(mCurrentSearchItem);
        }
    }

    @Override
    public void onSingleErrorResponse(String error) {
        mView.dismissDialog();
        Log.e(TAG, error);
        handleSingleRequestError(mCurrentSearchItem);
    }

    protected void handleSingleRequestError(MovieDetails details) {
        mView.navigateToMovieDetailsScreen(details);
    }

    protected String getMessageFromErrorResponse(String response) {
        return makeJsonObj(response).get(RequestConstants.R_ERROR).getAsString();
    }

    @Override
    public void onSearchCrossClick() {
        mSearchResults.clear();
        mView.updateSearchResultsList(new ArrayList<>());
        mView.hideSearchRecyclerView();
    }

    @Override
    public void ListItemClicked(MovieDetails details) {
        mView.navigateToMovieDetailsScreen(details);
    }

    @Override
    public void onResume() {
        checkListAndSetList(mCurrentListKey);
    }

    @Override
    public void onClearDataClicked() {
        mRepository.clearList(FAV_LIST);
        mRepository.clearList(WATCH_LIST);
        mRepository.clearList(REC_LIST);
        mView.updateListRecyclerView(new ArrayList<>());
        mView.hideListRecyclerView();
    }

    @Override
    public void onFavouritesListClicked() {
        checkListAndSetList(FAV_LIST);
    }

    @Override
    public void onWatchedListClicked() {
        checkListAndSetList(WATCH_LIST);
    }

    @Override
    public void onRecommendedListClicked() {
        checkListAndSetList(REC_LIST);
    }

    protected void handleMovieRequestSuccess(String response) {
        MovieDetails details = createMovieDetailsFromJsonObject(makeJsonObj(response));
        mView.navigateToMovieDetailsScreen(details);
    }

    protected boolean hasReceivedApiSuccess(String response) {
        return getBooleanFromJsonObject(makeJsonObj(response), RequestConstants.R_RESULT);
    }

    protected JsonObject makeJsonObj(String string) {
        return new Gson().fromJson(string, JsonObject.class);
    }

    protected void handleSearchRequestSuccess(String response) {
        mSearchResults = createSearchResultsFromResponse(response);
        displaySearchResult();
    }

    protected Map<String, MovieDetails> createSearchResultsFromResponse(String response) {
        Map<String, MovieDetails> resultsMap = new HashMap<>();
        JsonObject object = new Gson().fromJson(response, JsonObject.class);
        JsonArray resultsArray = object.getAsJsonArray(RequestConstants.R_SEARCH_RESULTS);

        for (int i = 0; i < resultsArray.size(); i++) {
            MovieDetails details = createMovieDetailsFromJsonObject(resultsArray.get(i).getAsJsonObject());
            resultsMap.put(details.getId(), details);
        }

        return resultsMap;
    }

    protected void displaySearchResult() {
        mView.setSearchResultsRecyclerView(getResultsList());
        mView.showSearchRecyclerView();
    }

    private List<MovieDetails> getResultsList() {
        return new ArrayList<>(mSearchResults.values());
    }

    protected MovieDetails createMovieDetailsFromJsonObject(JsonObject object) {
        MovieDetails details = new MovieDetails();

        details.setId(getStringFromJsonObject(object, RequestConstants.R_ID));
        details.setTitle(getStringFromJsonObject(object, RequestConstants.R_TITLE));
        details.setPlot(getStringFromJsonObject(object, RequestConstants.R_PLOT));
        details.setDirector(getStringFromJsonObject(object, RequestConstants.R_DIRECTOR));
        details.setWriters(getStringFromJsonObject(object, RequestConstants.R_WRITER));
        details.setCast(getStringFromJsonObject(object, RequestConstants.R_CAST));
        details.setReleased(getStringFromJsonObject(object, RequestConstants.R_YEAR));
        details.setRating(getRatingFromJsonObject(object));
        details.setType(getStringFromJsonObject(object, RequestConstants.R_TYPE));
        details.setPosterURL(getStringFromJsonObject(object, RequestConstants.R_POSTER_URL));

      return details;
    }

    protected String getRatingFromJsonObject(JsonObject object) {
        String result = AppConstants.EMPTY_STRING;
        JsonArray ratingsArray = object.getAsJsonArray(RequestConstants.R_RATING);

        if (ratingsArray != null && ratingsArray.size() > 0){
            JsonObject firstRating = ratingsArray.get(AppConstants.FIRST_ARRAY_POSITION).getAsJsonObject();
            result = getStringFromJsonObject(firstRating, RequestConstants.R_RATING_VALUE);
        }

        return result;
    }

    protected void setCurrentSearchItem(MovieDetails details) {
        mCurrentSearchItem = details;
    }
}