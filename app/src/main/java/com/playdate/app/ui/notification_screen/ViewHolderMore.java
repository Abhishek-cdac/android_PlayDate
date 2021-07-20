package com.playdate.app.ui.notification_screen;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class ViewHolderMore extends RecyclerView.ViewHolder {
    TextView txt_view_more;

    public ViewHolderMore(View view) {
        super(view);
        txt_view_more = view.findViewById(R.id.txt_view_more);
    }
}
