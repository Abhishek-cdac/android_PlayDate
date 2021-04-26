package com.playdate.app.util.common;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.playdate.app.R;

public class CommonClass {

    public void hideKeyboard(View view, AppCompatActivity activity) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void showDialogMsg(AppCompatActivity activity, String header, String body, String btnOk) {
        new AwesomeInfoDialog(activity)
                .setTitle(header)
                .setMessage(body)
                .setCancelable(true)
                .setPositiveButtonText("Ok")
                .setPositiveButtonbackgroundColor(R.color.color_pink)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick(() -> {

                })
                .show();
    }

}
