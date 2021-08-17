package com.example.watchbox.framework.utils;

public class AppConstants {
    public static final String APP_SHARED_PREF = "WATCHBOX_SHARED_PREF";
    public static final String OMDB_API_KEY = "64363613";
    public static final String AMPERSAND = "&";
    public static final String EQUALS = "=";
    public static final String OMDB_REQ_URL = "http://www.omdbapi.com/?apikey=" + OMDB_API_KEY
            + AMPERSAND + RequestConstants.DATA_TYPE + EQUALS + RequestConstants.TYPE_JSON;
    public static final String MOVIE_DETAILS = "MOVIE_DETAILS";
    public static final String FAV_LIST = "FAV_LIST";
    public static final String REC_LIST = "REC_LIST";
    public static final String WATCH_LIST = "WATCH_LIST";
    public static final String SPACE = " ";
    public static final String PLUS = "+";
    public static final String EMPTY_STRING = "";
    public static final int FIRST_ARRAY_POSITION = 0;
    public static final String ERROR_TITLE = "Error";
    public static final String OK_BTN_TEXT = "OK";
}
