package com.playdate.app.ui.chat.request;

import android.view.View;

public interface Onclick {
    public void onItemClick(View view, int position, int value);
    public void onItemClicks(View view, int position, int value, String s);
    public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId);
    public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId);
}
