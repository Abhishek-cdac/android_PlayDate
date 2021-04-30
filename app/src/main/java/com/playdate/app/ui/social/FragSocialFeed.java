package com.playdate.app.ui.social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class FragSocialFeed extends Fragment {


    public static final int USER = 0;
    public static final int RESTAURANT = 1;
    public static final int ANONYMUSQUESTION = 2;
    public static final int ADDS = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_social_feed, container, false);
        RecyclerView recycler_view_feed = view.findViewById(R.id.recycler_view_feed);

        ArrayList<SocialFeed> lst = new ArrayList<>();
        lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER,"https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("RetroDiner", 451, false, 0, RESTAURANT,"https://cdn.cnn.com/cnnnext/dam/assets/160726132158-us-beautiful-hotels-4-four-seasons-maui-2-super-169.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER,"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Isabella", 4854, false, 0, USER,"https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Isabella", 4854, false, 0, USER,"https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Anonymous Question", 9874, false, 0, ANONYMUSQUESTION,"https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("SPFine&Dine", 3002, false, 0, RESTAURANT,"https://cdn.vox-cdn.com/thumbor/o3GOFgm-hmPjBWnnaLWNfHrXm1M=/0x0:1200x900/1200x800/filters:focal(0x0:1200x900)/cdn.vox-cdn.com/uploads/chorus_image/image/45554236/empire-logo-2.0.0.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("NetMeds", 3002, false, 0, ADDS,"https://images.freekaamaal.com/post_images/1564038343.png","https://www.netmeds.com/images/cms/Netmeds_Pill_icon_1.png"));

        lst.add(new SocialFeed("Olivia251", 1548, false, 0, USER,"https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("RetroDiner", 451, false, 0, RESTAURANT,"https://cdn.cnn.com/cnnnext/dam/assets/160726132158-us-beautiful-hotels-4-four-seasons-maui-2-super-169.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("AvaSophia", 1542, false, 0, USER,"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Isabella", 4854, false, 0, USER,"https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Isabella", 4854, false, 0, USER,"https://blog.ainfluencer.com/wp-content/uploads/2021/01/Cinta-Laura-868x1024.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("Anonymous Question", 9874, false, 0, ANONYMUSQUESTION,"https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("SPFine&Dine", 3002, false, 0, RESTAURANT,"https://cdn.vox-cdn.com/thumbor/o3GOFgm-hmPjBWnnaLWNfHrXm1M=/0x0:1200x900/1200x800/filters:focal(0x0:1200x900)/cdn.vox-cdn.com/uploads/chorus_image/image/45554236/empire-logo-2.0.0.jpg","https://i.pinimg.com/originals/09/31/b5/0931b5399d9f1a3afe4417ee83eff961.jpg"));
        lst.add(new SocialFeed("OYO Hotels", 3002, false, 0, ADDS,"https://4.bp.blogspot.com/-Eh2LvkX8m3k/WfhJYyMOb-I/AAAAAAAAA74/-YTvPG3krcg9ixTFc56w7W4SmuPAb7SjACLcBGAs/s1600/CPS-Affiliates-30_off_banner_700x500.jpg","https://upload.wikimedia.org/wikipedia/commons/1/19/OYO_Rooms_%28logo%29.png"));



        SocialFeedAdapter adapter = new SocialFeedAdapter(lst);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler_view_feed.setLayoutManager(manager);
        recycler_view_feed.setAdapter(adapter);

        recycler_view_feed.addOnScrollListener(new RecyclerView.OnScrollListener() {
        });

        return view;
    }

}
