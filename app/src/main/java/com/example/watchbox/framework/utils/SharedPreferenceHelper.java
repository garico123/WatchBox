package com.example.watchbox.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.watchbox.framework.data.MovieDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class SharedPreferenceHelper {

    private final SharedPreferences mSharedPref;

    public SharedPreferenceHelper(Context context) {
        this.mSharedPref = context.getSharedPreferences(AppConstants.APP_SHARED_PREF, Context.MODE_PRIVATE);
    }

    /**
     * Get string from sharedPreferences
     *
     * @param key
     * @param defaultValue
     * @return string from sharedPreferences
     */
    public String getString(String key, String defaultValue) {
        return mSharedPref.getString(key, defaultValue);
    }

    /**
     *
     * @param key Key used to store item in sharedPreferences
     * @return if the item exists
     */
    public boolean doesItemExist(String key) {
        return mSharedPref.contains(key);
    }

    /**
     * Store string in sharedPreferences
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        if (key != null) {
            mSharedPref.edit().putString(key, value).apply();
        }
    }

    /**
     * @param key
     * @param object
     * @return Object from sharedPreferences
     */
    public Object getObject(String key, Object object) {
        String json = getString(key, "");

        return new Gson().fromJson(json, Object.class);
    }

    /**
     *
     * @return favourites list from sharedPreferences
     */
    public Map<String, MovieDetails> getMovieDetailsMap(String key){
        Type mapType = new TypeToken<Map<String, MovieDetails>>() {}.getType();
        return new Gson().fromJson(getString(key,AppConstants.EMPTY_STRING), mapType);
    }

    /**
     * Saves Object in sharedPreferences
     *
     * @param key
     * @param object
     */
    public void setObject(String key, Object object) {
        String json = new Gson().toJson(object);

        setString(key, json);
    }

    /**
     * remove object from sharedPreferences using given key
     *
     * @param key key to remove
     */
    public void removeObject(String key) {
        mSharedPref.edit().remove(key).apply();
    }
}