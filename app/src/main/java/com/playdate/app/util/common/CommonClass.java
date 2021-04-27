package com.playdate.app.util.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class CommonClass {

    public void hideKeyboard(View view, AppCompatActivity activity) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void showDialogMsg(AppCompatActivity activity, String header, String body, String btnOk) {

        LayoutInflater factory = LayoutInflater.from(activity);
        View view = factory.inflate(R.layout.custom_dialog_yes, null);
        AlertDialog deleteDialog = new AlertDialog.Builder(activity).create();
        deleteDialog.setView(view);
        TextView txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setText(body);
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();

    }

}
