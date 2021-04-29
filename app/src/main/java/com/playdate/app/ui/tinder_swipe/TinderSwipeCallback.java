package com.playdate.app.ui.tinder_swipe;

import androidx.recyclerview.widget.DiffUtil;

import com.playdate.app.model.TinderSwipe;

import java.util.List;

public class TinderSwipeCallback extends DiffUtil.Callback {
    private List<TinderSwipe> old, newList;

    public TinderSwipeCallback(List<TinderSwipe> old, List<TinderSwipe> newList) {
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
        return old.get(oldItemPosition).getImage() == newList.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
