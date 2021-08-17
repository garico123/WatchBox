package com.example.watchbox.screens.moviedetails.repository;

import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.mvp.BaseRepository;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailsRepository extends BaseRepository {

    private static MovieDetailsRepository INSTANCE;

    private MovieDetailsRepository(){}

    public static MovieDetailsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieDetailsRepository();
        }
        return INSTANCE;
    }

    /**
     * Checks Item exists in List before removing it from the list
     *
     * @param movieDetails Movie to be removed
     * @param listKey key used to store the list
     */
    public void removeFromList(MovieDetails movieDetails, String listKey) {
        if (mSharedPrefHelper.doesItemExist(listKey)) {
            Map<String, MovieDetails> list
                    =  mSharedPrefHelper.getMovieDetailsMap(listKey);
            list.remove(movieDetails.getId());

            removeListFromSharedPref(list, listKey);
        }
    }

    /**
     * Save updated list to shared preferences or remove if list is empty
     *
     * @param list list to be updated/removed
     * @param listKey key used to store list
     */
    private void removeListFromSharedPref(Map<String, MovieDetails> list, String listKey) {
        if (list.isEmpty()){
            mSharedPrefHelper.removeObject(listKey);
        } else {
            mSharedPrefHelper.setObject(listKey, list);
        }
    }

    /**
     * Save movieDetails to List using given key.
     * If no list exists makes a new one which is then stored
     *
     * @param movieDetails Details to be stored in List
     * @param listKey key used to save List
     */
    public void saveToList(MovieDetails movieDetails, String listKey) {
        Map<String, MovieDetails> list;

       if (mSharedPrefHelper.doesItemExist(listKey)){
           list =  mSharedPrefHelper.getMovieDetailsMap(listKey);

       } else {
           list = new HashMap<>();
       }

        list.put(movieDetails.getId(), movieDetails);
        mSharedPrefHelper.setObject(listKey, list);
    }

    /**
     * Makes request to download poster bitmap and returns value to presenter
     *
     * @param posterUrl URL used to download the poster
     * @param listener listener used to pass result to presenter
     */
    public void makePosterRequest(String posterUrl, ImageResponseListener listener) {
        makeImageRequest(posterUrl, listener);
    }
}