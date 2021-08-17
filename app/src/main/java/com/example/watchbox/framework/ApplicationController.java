package com.example.watchbox.framework;

import android.content.Context;
import android.util.Log;

import com.example.watchbox.framework.utils.SharedPreferenceHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Application Controller class to allow access to sharedPrefHelper, mAppDataCache and applicationContext from anywhere
 */
public class ApplicationController {
    private static final String TAG = ApplicationController.class.getSimpleName();
    private final Map<Object, Object> mAppDataCache;
    private final SharedPreferenceHelper mSharedPrefHelper;

    private final Context mContext;

    private static ApplicationController INSTANCE;

    private ApplicationController(Context context) {
        mContext = context.getApplicationContext();
        mAppDataCache = new HashMap<>();
        mSharedPrefHelper = new SharedPreferenceHelper(context);
    }

    /**
     * Call this object once in the applications lifespan
     * @param context
     */
    public static void initialize(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new ApplicationController(context);
        } else {
            Log.e(TAG, "Already Initialized, ignoring");
        }
    }

    /**
     *
     * @return an instance of ApplicationController if it exists
     */
    public static ApplicationController getInstance() {
        if (INSTANCE == null) {
            String message = TAG + "object does not exist";
            Log.e(TAG, message);
            throw new IllegalStateException(message);
        } else {
            return INSTANCE;
        }
    }

    /**
     *
     * @return application context
     */
    public Context getAppContext() {
        return mContext;
    }

    /**
     *
     * @return application data cache
     */
    public Map<Object, Object> getDataCache() {
        return mAppDataCache;
    }

    public SharedPreferenceHelper getSharedPrefHelper() {
        return mSharedPrefHelper;
    }
}