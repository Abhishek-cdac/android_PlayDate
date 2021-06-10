package com.playdate.app.ui.chat.request;

import android.view.View;

public interface Onclick {
    void onItemClick(View view, int position, int value);

    void onItemClicks(View view, int position, int value, String s);

    void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId);

    void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId);

    void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath);
}
