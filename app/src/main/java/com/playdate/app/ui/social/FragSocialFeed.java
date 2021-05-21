package com.playdate.app.ui.social;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.model.PostHistory;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSocialFeed extends Fragment {


    public static final String NORMAL = "Normal";
    //    public static final String RESTAURANT = 1;
//    public static final String ANONYMUSQUESTION = 2;
//    public static final String ADDS = 3;
    AAH_CustomRecyclerView recycler_view_feed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_social_feed, container, false);
        recycler_view_feed = view.findViewById(R.id.recycler_view_feed);

        callAPI();

        return view;
    }

    @Override
    public void onStop() {
        try {
         //   recycler_view_feed.stopVideos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();

    }

    private void callAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");// format 1990-08-12
        hashMap.put("pageNo", "1");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<PostHistory> call = service.getPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<PostHistory>() {
            @Override
            public void onResponse(Call<PostHistory> call, Response<PostHistory> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    ArrayList<PostDetails> lst = response.body().getPostDetails();
                    if (lst == null) {
                        lst = new ArrayList<>();
                    }
                    SocialFeedAdapter adapter = new SocialFeedAdapter(getActivity(), lst);


                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    recycler_view_feed.setLayoutManager(manager);
                    recycler_view_feed.setItemAnimator(new DefaultItemAnimator());
                    recycler_view_feed.setActivity(getActivity());
                    recycler_view_feed.setCheckForMp4(false);
                    recycler_view_feed.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default
                    recycler_view_feed.setDownloadVideos(true); // false by default
                    //extra - start downloading all videos in background before loading RecyclerView
                    List<String> urls = null;
                    try {
                        urls = new ArrayList<>();
                        for (PostDetails object : lst) {
                            if (object.getPostMedia().get(0).getMediaFullPath().toLowerCase().contains(".mp4"))
                                urls.add(object.getPostMedia().get(0).getMediaFullPath());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recycler_view_feed.preDownload(urls);
                    recycler_view_feed.setVisiblePercent(70);
                    recycler_view_feed.setPlayOnlyFirstVideo(true);
                    recycler_view_feed.setAdapter(adapter);

                } else {


                }


            }

            @Override
            public void onFailure(Call<PostHistory> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });

    }

}
