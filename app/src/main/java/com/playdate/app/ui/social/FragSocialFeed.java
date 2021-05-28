package com.playdate.app.ui.social;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.ui.social.adapter.PaginationListener;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.model.PostHistory;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSocialFeed extends Fragment {

    public FragSocialFeed() {
    }
    int PageNo=1;
    boolean NoMorePages=false;

    public static final String NORMAL = "Normal";
    //    public static final String RESTAURANT = 1;
//    public static final String ANONYMUSQUESTION = 2;
//    public static final String ADDS = 3;
    private AAH_CustomRecyclerView recycler_view_feed;

    boolean boolApiCalling = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_social_feed, container, false);
        recycler_view_feed = view.findViewById(R.id.recycler_view_feed);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler_view_feed.setLayoutManager(manager);
//        recycler_view_feed.setHasFixedSize(true);

//        recycler_view_feed.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                Toast.makeText(getActivity(), "LAst", Toast.LENGTH_LONG).show();
//            }
//        });
        callAPI();

        return view;
    }

    public void addMoreData() {
        if (null != adapter) {
            if (!boolApiCalling) {
                if(!NoMorePages){


                PostDetails pd = new PostDetails();
                pd.setPostType("Load");
                lst.add(pd);
                adapter.notifyDataSetChanged();
                recycler_view_feed.scrollToPosition(lst.size()-1);
                PageNo=PageNo+1;
                callAPI();
                }
            }

        }

    }

    @Override
    public void onStop() {
        try {
            recycler_view_feed.stopVideos();
            recycler_view_feed.removeAllViews();
//            Toast.makeText(getActivity(), "Video Stopped", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();

    }

    ArrayList<PostDetails> lst=new ArrayList<>();
    SocialFeedAdapter adapter;

    private void callAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "10");// Hardcode
        hashMap.put("pageNo", ""+PageNo);// Hardcode
        boolApiCalling=true;
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
//        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<PostHistory> call = service.getPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<PostHistory>() {
            @Override
            public void onResponse(Call<PostHistory> call, Response<PostHistory> response) {
//                pd.cancel();

                if (response.code() == 200) {
                    assert response.body() != null;
                    ArrayList<PostDetails> lstData = response.body().getPostDetails();
                    if (lstData == null) {
                        lstData = new ArrayList<>();
                    }


                    if(lst.size()>0){
                        lst.remove(lst.size()-1);
//                        adapter.notifyDataSetChanged();
                        lst.addAll(lstData);
                        adapter.notifyDataSetChanged();
//                        recycler_view_feed.scrollToPosition(lst.size()-1);
                    }else{
                        lst=lstData;
                        adapter = new SocialFeedAdapter(getActivity(), lst);

                        recycler_view_feed.setItemAnimator(new DefaultItemAnimator());
                        recycler_view_feed.setActivity(getActivity());
                        recycler_view_feed.setCheckForMp4(false);

                        recycler_view_feed.setDownloadPath(Environment.getExternalStorageDirectory() + "/PlayDate/Video");
                        recycler_view_feed.setDownloadVideos(true); // false by default
//                    extra - start downloading all videos in background before loading RecyclerView
                        List<String> urls = null;
                        try {
                            urls = new ArrayList<>();
                            for (PostDetails object : lst) {
                                if (object.getPostMedia().get(0).getMediaFullPath().toLowerCase().contains(".mp4"))
                                    urls.add(object.getPostMedia().get(0).getMediaFullPath());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        recycler_view_feed.preDownload(urls);
                        recycler_view_feed.setVisiblePercent(70);
                        recycler_view_feed.setPlayOnlyFirstVideo(true);
                        recycler_view_feed.setAdapter(adapter);
                        recycler_view_feed.smoothScrollBy(0, 1);
                        recycler_view_feed.smoothScrollBy(0, -1);
                    }


                    boolApiCalling=false;


                } else {
                    NoMorePages=true;
                    try {
                        if(null!=lst){
                            if(lst.get(lst.size()-1).getPostType().equals("Load")){
                                lst.remove(lst.size()-1);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<PostHistory> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            recycler_view_feed.playAvailableVideos(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
