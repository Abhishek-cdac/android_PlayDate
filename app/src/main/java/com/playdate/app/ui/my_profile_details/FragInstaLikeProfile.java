package com.playdate.app.ui.my_profile_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.my_profile_details.adapters.InstaPhotosAdapter;

public class FragInstaLikeProfile extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile, container, false);
        RecyclerView recycler_photos = view.findViewById(R.id.recycler_photos);
        recycler_photos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        InstaPhotosAdapter adapter=new InstaPhotosAdapter();
        recycler_photos.setAdapter(adapter);
        return view;
    }
}
