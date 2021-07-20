package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PackageDescription;
import com.playdate.app.ui.dashboard.adapter.PremiumAdapter;

import java.util.ArrayList;

public class FullScreenDialog extends Dialog {

    public FullScreenDialog(Context context, ArrayList<PackageDescription> lst_packageDescription) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_get_premium, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        Button getNowButton = view.findViewById(R.id.login_button);
        RecyclerView recy_premiun=view.findViewById(R.id.recy_premiun);
        PremiumAdapter adapter=new PremiumAdapter(lst_packageDescription,0);
        LinearLayoutManager maLinearLayout=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recy_premiun.setLayoutManager(maLinearLayout);
        recy_premiun.setAdapter(adapter);
        getNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv_close.setOnClickListener(view1 -> dismiss());
    }




}
