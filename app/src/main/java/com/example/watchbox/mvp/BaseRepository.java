package com.example.watchbox.mvp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.watchbox.framework.ApplicationController;
import com.example.watchbox.framework.utils.AppConstants;
import com.example.watchbox.framework.utils.SharedPreferenceHelper;

import java.util.Map;

public abstract class BaseRepository {
    protected final Map<Object, Object> mDataCache;
    protected final SharedPreferenceHelper mSharedPrefHelper;
    private ApplicationController mAppController;

    protected BaseRepository() {
        mAppController = ApplicationController.getInstance();
        this.mDataCache = mAppController.getDataCache();
        this.mSharedPrefHelper = mAppController.getSharedPrefHelper();
    }

    /**
     * interface to return network response from standard request
     */
    public interface RequestResponseListener {
        void onSuccess(String response);
        void onRequestError(String error);
    }

    /**
     * interface to return network response from Image download requests
     */
    public interface ImageResponseListener {
        void onSuccess(Bitmap bitmap);
        void onError(String error);
    }

    /**
     * Make a Get request using given URL and return response vie listener
     * @param requestURL URL used to make the request
     * @param listener Listener that returns result to presenter
     */
    protected void makeNetworkRequest(String requestURL, final RequestResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(mAppController.getAppContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> listener.onSuccess(response),
                error -> listener.onRequestError(error.getMessage()));

        queue.add(stringRequest);
    }

    /**
     *  Make an Image request using a given URL and return response via listener
     * @param requestUrl URL used to make the request
     * @param listener Listener that returns result to presenter
     */
    protected void makeImageRequest(String requestUrl, final ImageResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(mAppController.getAppContext());

        ImageRequest imageRequest = new ImageRequest(requestUrl,
                response -> listener.onSuccess(response),
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                error -> listener.onError(error.getMessage()));

        queue.add(imageRequest);
    }

    /**
     *
     * @param param Name of URL request item
     * @param value Value of URL request item
     * @return Request url item consisting of parameter and value
     */
    protected String makeRequestItem(String param, String value) {
        return AppConstants.AMPERSAND + param + AppConstants.EQUALS + value;
    }
}