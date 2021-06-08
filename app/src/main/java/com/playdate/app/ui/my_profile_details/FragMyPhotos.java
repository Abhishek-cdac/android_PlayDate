package com.playdate.app.ui.my_profile_details;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.playdate.app.R;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

public class FragMyPhotos extends Fragment implements View.OnClickListener {
    public FragMyPhotos() {
    }

    private View view_1;
    private View view_2;

    private TextView txt_header1;
    private TextView txt_header2;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tab_my_photos, container, false);

        view_1 = view.findViewById(R.id.view_1);
        view_2 = view.findViewById(R.id.view_2);
        txt_header1 = view.findViewById(R.id.txt_header1);
        txt_header2 = view.findViewById(R.id.txt_header2);
        txt_header1.setOnClickListener(this);
        txt_header2.setOnClickListener(this);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SessionPref pref = SessionPref.getInstance(getActivity());
        ft.add(R.id.fl_myPhotos, new FragMyUploadMedia(pref.getStringVal(SessionPref.LoginUserID)));
        ft.commit();


        return view;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txt_header1) {
            txt_header1.setTypeface(Typeface.DEFAULT_BOLD);
            txt_header2.setTypeface(Typeface.DEFAULT);
            view_1.setBackgroundColor(getResources().getColor(R.color.color_pink));
            view_2.setBackgroundColor(getResources().getColor(R.color.color_grey));
            SessionPref pref = SessionPref.getInstance(getActivity());
            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fl_myPhotos, new FragMyUploadMedia(pref.getStringVal(SessionPref.LoginUserID)));
            ft.commit();

        } else {
            txt_header2.setTypeface(Typeface.DEFAULT_BOLD);
            txt_header1.setTypeface(Typeface.DEFAULT);
            view_1.setBackgroundColor(getResources().getColor(R.color.color_grey));
            view_2.setBackgroundColor(getResources().getColor(R.color.color_pink));
            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fl_myPhotos, new FragSavedPost());
            ft.commit();
        }
    }
}
