package com.playdate.app.util.common;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.playdate.app.R;

import java.util.ArrayList;

public class CommonClass {

    static CommonClass common;

    public static CommonClass getInstance() {
        if (common == null) {
            common = new CommonClass();
        }
        return common;
    }

    public void hideKeyboard(View view, AppCompatActivity activity) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public int getScreenHeight(FragmentActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void showDialogMsg(AppCompatActivity activity, String header, String body, String btnOk) {

        LayoutInflater factory = LayoutInflater.from(activity);
        View view = factory.inflate(R.layout.custom_dialog_yes, null);
        AlertDialog deleteDialog = new AlertDialog.Builder(activity).create();
        activity.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        deleteDialog.setView(view);
        TextView txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setText(body);
        view.findViewById(R.id.btn_ok).setOnClickListener(view1 -> deleteDialog.dismiss());

        deleteDialog.show();

    }

    public ArrayList<Integer> getEmojiArr() {
        ArrayList<Integer> lstSmiley = new ArrayList<>();
        Integer[] intEmoji = {
                0x1F600, 0x1F603, 0x1F604, 0x1F601, 0x1F606, 0x1F605, 0x1F923, 0x1F602, 0x1F61A, 0x1F619,
                0x1F642, 0x1F643, 0x1F609, 0x1F60A, 0x1F607, 0x1F60B, 0x1F60D, 0x1F929, 0x1F618, 0x1F617,
                0x1F61C, 0x1F92A, 0x1F61D, 0x1F911, 0x1F917, 0x1F92B, 0x1F914, 0x1F910, 0x1F928, 0x1F610,
        };
        for (int i = 0; i <= intEmoji.length; i++) {
            try {
                lstSmiley.add(intEmoji[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return lstSmiley;
    }


    public void showDialogMsgFrag(FragmentActivity activity, String header, String body, String btnOk) {

        LayoutInflater factory = LayoutInflater.from(activity);
        View view = factory.inflate(R.layout.custom_dialog_yes, null);
        AlertDialog deleteDialog = new AlertDialog.Builder(activity).create();

        deleteDialog.setView(view);
        TextView txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setText(body);
        view.findViewById(R.id.btn_ok).setOnClickListener(view1 -> deleteDialog.dismiss());

        deleteDialog.show();

        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }


}
