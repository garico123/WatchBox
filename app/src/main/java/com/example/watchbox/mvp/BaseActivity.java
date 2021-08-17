package com.example.watchbox.mvp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watchbox.R;
import com.example.watchbox.framework.utils.Utils;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    private Dialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Default OnClickListener for mDialog
     */
    private final DialogInterface.OnClickListener mDefaultDialogClickListener
            = ((dialog, which) -> dismissDialog());

    /**
     * Displays an alertDialog with a single button
     * @param title
     * @param message
     * @param btnText
     * @param isCancelable
     * @param listener Sets a custom click listener for the dialog button
     */
    public void showOneButtonDialog(String title, String message, String btnText,
                                    boolean isCancelable, DialogInterface.OnClickListener listener) {
        if (listener == null) {
            listener = mDefaultDialogClickListener;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        if (!Utils.isEmpty(title)) {
            dialog.setTitle(title);
        }

        dialog.setMessage(message);
        dialog.setCancelable(isCancelable);

        dialog.setPositiveButton(btnText.toUpperCase(), listener);
        mDialog = dialog.show();
    }

    /**
     * Displays animated loading spinner with translucent background, uses common mDialog component
     */
    public void showLoadingDialog() {
        dismissDialog();
        final Dialog dialog = new Dialog(this, R.style.LoadingDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.show();
        mDialog = dialog;
    }

    /**
     * Closes and nulls common mDialog component
     */
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog.cancel();
            mDialog = null;
        }
    }
}