package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.ui.my_profile_details.FragMyUploadMedia;
import com.playdate.app.ui.my_profile_details.FragSavedPost;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

public class MyPhotosPagerAdapter extends FragmentPagerAdapter {
    public MyPhotosPagerAdapter(@NonNull @NotNull FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    Context mContext;

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            SessionPref pref = SessionPref.getInstance(mContext);

            return new FragMyUploadMedia(pref.getStringVal(SessionPref.LoginUserID));
        } else {
            return new FragSavedPost();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
