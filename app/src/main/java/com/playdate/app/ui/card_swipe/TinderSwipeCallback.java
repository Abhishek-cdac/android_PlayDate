package com.playdate.app.ui.card_swipe;

import androidx.recyclerview.widget.DiffUtil;

import com.playdate.app.model.MatchListUser;

import java.util.List;

public class TinderSwipeCallback extends DiffUtil.Callback {
    private final List<MatchListUser> old;
    private final List<MatchListUser> newList;

    public TinderSwipeCallback(List<MatchListUser> old, List<MatchListUser> newList) {
        this.old = old;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getProfilePicPath().equals(newList.get(newItemPosition).getProfilePicPath());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
