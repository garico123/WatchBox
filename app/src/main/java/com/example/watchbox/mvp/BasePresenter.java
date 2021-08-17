package com.example.watchbox.mvp;

import com.example.watchbox.framework.utils.AppConstants;

public class BasePresenter {
    private BaseViewInterface mView;

    protected BasePresenter(BaseViewInterface baseView) {
        this.mView = baseView;
    }

    /**
     * Displays an error dialog that can be closed with an OK button
     * @param errorMsg text to be displayed as error dialog body text
     */
    public void onError(String errorMsg) {
        mView.dismissDialog();
        mView.showOneButtonDialog(AppConstants.ERROR_TITLE,
                errorMsg,
                AppConstants.OK_BTN_TEXT,
                true,
                null
        );
    }
}