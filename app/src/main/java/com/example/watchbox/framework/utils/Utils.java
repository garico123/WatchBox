package com.example.watchbox.framework.utils;

import com.example.watchbox.framework.data.MovieDetails;
import com.google.gson.JsonObject;

public class Utils {

    /**
     *
     * @param value String to be validated
     * @return boolean value for is the string is empty
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     *
     * @param string String to be cleaned
     * @return Removes trailing whitespace and replaces all " " with "+"
     */
    public static String cleanStringForRequest(String string) {
        return string.trim().replaceAll(AppConstants.SPACE, AppConstants.PLUS);
    }

    /**
     *
     * @param object for string to be converted from
     * @param key object to be returned
     * @return String value for provided key in JSONObject
     */
    public static String getStringFromJsonObject(JsonObject object, String key) {
        String result = AppConstants.EMPTY_STRING;

        if (object != null && object.get(key) != null){
            result = object.get(key).toString().trim();
        }

        if (!isEmpty(result)){
            result = result.substring(1, result.length() - 1);
        }

        return result;
    }

    /**
     *
     * @param object for boolean to be converted from
     * @param key object to be returned
     * @return boolean value for provided key on JSONObject
     */
    public static boolean getBooleanFromJsonObject(JsonObject object, String key) {
        boolean success = false;

        if (object != null && object.get(key) != null) {
            success = object.get(key).getAsBoolean();
        }

        return success;
    }

    //For test purposes during development only
    public static MovieDetails getTestDetails(String id, boolean favourite) {
        MovieDetails details = new MovieDetails();
        details.setId(id);
        details.setTitle("Title" + id);
        details.setPlot("plotnasklnclkancklsncklasn");
        details.setReleased("1985");
        details.setDirector("mr T E Ster");
        details.setWriters("tim, bob, ted");
        details.setCast("james, jim, jonah");
        details.setType("Movie");
        details.setRating("75%");
        details.setFavourite(favourite);
        details.setPosterURL("https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg");
        return details;
    }
}