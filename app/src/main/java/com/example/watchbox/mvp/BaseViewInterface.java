package com.example.watchbox.mvp;

import android.content.DialogInterface;

public interface BaseViewInterface<T> {
    void setPresenter(T presenter);
    void dismissDialog();
    void showOneButtonDialog(String title, String message, String btnText,
                             boolean isCancelable, DialogInterface.OnClickListener listener);
    void showLoadingDialog();
}