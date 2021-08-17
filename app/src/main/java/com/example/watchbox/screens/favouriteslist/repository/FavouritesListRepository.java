package com.example.watchbox.screens.favouriteslist.repository;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.framework.utils.AppConstants;
import com.example.watchbox.framework.utils.RequestConstants;
import com.example.watchbox.framework.utils.Utils;
import com.example.watchbox.mvp.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouritesListRepository extends BaseRepository {

    private static FavouritesListRepository INSTANCE;

    private FavouritesListRepository(){}

    public static FavouritesListRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavouritesListRepository();
        }
        return INSTANCE;
    }

    /**
     * interface to return search response to presenter
     */
    public interface SearchResponseListener {
        void onSearchSuccessResponse(String result);
        void onSearchErrorResponse(String error);
    }

    /**
     * interface to return single details response to presenter
     */
    public interface SingleDetailsResponseListener {
        void onSingleSuccessResponse(String result);
        void onSingleErrorResponse(String error);
    }

    /**
     *
     * @param listKey key used to store list in sharedPreferences
     * @return If the given list exists in sharedPreferences
     */
    public boolean doesUserHaveList(String listKey) {
        return mSharedPrefHelper.doesItemExist(listKey);
    }

    /**
     * Returns a list of any movies which match the film title provided
     *
     * @param filmTitle title used to search IMDB database
     * @param listener listener to return interection to presenter
     */
    public void makeFilmRequestFromSearch(String filmTitle, SearchResponseListener listener) {
        makeNetworkRequest(createFilmRequestURL(filmTitle, RequestConstants.SEARCH), new RequestResponseListener() {
            @Override
            public void onSuccess(String response) {
                listener.onSearchSuccessResponse(response);
            }

            @Override
            public void onRequestError(String error) {
                listener.onSearchErrorResponse(error);
            }
        });
    }

    /**
     * Returns movie details for the selected ID if they exist
     *
     * @param movieId IMDB_ID for movie
     * @param listener listener to return interaction to presenter
     */
    public void makeFilmRequestForMovie(String movieId, SingleDetailsResponseListener listener) {
        makeNetworkRequest(createFilmRequestURL(movieId, RequestConstants.IMDB_ID), new RequestResponseListener() {
            @Override
            public void onSuccess(String response) {
                listener.onSingleSuccessResponse(response);
            }

            @Override
            public void onRequestError(String error) {
                listener.onSingleErrorResponse(error);
            }
        });
    }

    /**
     * Builds request URL for a film title according to searchType
     *
     * @param filmTitle Title used to search
     * @param searchType Type of search to be performed
     * @return Request URL
     */
    private String createFilmRequestURL(String filmTitle, String searchType) {
        return AppConstants.OMDB_REQ_URL + makeRequestItem(searchType, Utils.cleanStringForRequest(filmTitle));
    }

    /**
     * Returns a List of MovieDetails from SharedPreferences using key provided
     *
     * @param listKey key used to store List
     * @return List of MovieDetails
     */
    public List<MovieDetails> getList(String listKey) {
        Map<String, MovieDetails> map = mSharedPrefHelper.getMovieDetailsMap(listKey);
        return  new ArrayList<>(map.values());
    }

    /**
     * Removes list from sharedPreferences using key provided
     * @param listKey key used to store List
     */
    public void clearList(String listKey) {
        mSharedPrefHelper.removeObject(listKey);
    }
}