package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.my_profile_details.adapters.InstaPhotosAdapter;
import com.playdate.app.ui.my_profile_details.adapters.SocialFeedNormal;
import com.playdate.app.ui.playvideo.VideoPlayActivity;
import com.playdate.app.ui.social.SocialFeed;
import com.playdate.app.ui.social.SocialFeedAdapter;
import com.playdate.app.util.customcamera.otalia.VideoPreviewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragInstaLikeProfile extends Fragment implements onPhotoClick, View.OnClickListener {
    RecyclerView recycler_photos;
    public static final int USER = 0;
    ImageView iv_send_request;
    ImageView iv_chat;
    ImageView profile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_insta_profile, container, false);
        iv_send_request = view.findViewById(R.id.iv_send_request);
        profile_image = view.findViewById(R.id.profile_image);
        iv_chat = view.findViewById(R.id.iv_chat);
        recycler_photos = view.findViewById(R.id.recycler_photos);


        onTypeChange(0);
        Picasso.get().load("https://i.pinimg.com/564x/b8/03/78/b80378993da7282e58b35bdd3adbce89.jpg").placeholder(R.drawable.profile)
                .into(profile_image);

        iv_send_request.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        return view;
    }

    @Override
    public void onTypeChange(int type) {

        if (type == 1) {
            if (InstaPhotosAdapter.isLocked) {
                return;
            }
            recycler_photos.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            ArrayList<SocialFeed> lst = new ArrayList<>();
            lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
            lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));

            lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
            lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
            lst.add(new SocialFeed("Isabella", 4854, false, 0, USER, "https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg", "https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));


            SocialFeedNormal adapter = new SocialFeedNormal(lst);
            recycler_photos.setAdapter(adapter);
        } else {
            recycler_photos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            InstaPhotosAdapter adapter = new InstaPhotosAdapter(this);
            recycler_photos.setAdapter(adapter);
        }


    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_send_request) {
            iv_send_request.setImageResource(R.drawable.sent_request_sel);
            iv_chat.setImageResource(R.drawable.chat_sel);
            InstaPhotosAdapter.isLocked = false;
            onTypeChange(0);
        } else if (id == R.id.profile_image) {
            startActivity(new Intent(getActivity(), VideoPlayActivity.class));
        }
    }
}


interface onPhotoClick {
    void onTypeChange(int type);
}