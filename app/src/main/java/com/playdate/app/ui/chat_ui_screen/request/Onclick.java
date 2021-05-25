package com.playdate.app.ui.chat_ui_screen.request;

import android.view.View;

public interface Onclick {
    public void onItemClick(View view, int position, int value);
    public void onItemClicks(View view, int position, int value, String s);

    public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId);
}
