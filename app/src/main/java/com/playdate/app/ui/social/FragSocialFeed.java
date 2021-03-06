package com.playdate.app.ui.social;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.social.adapter.OnRefreshPage;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.model.PostHistory;
import com.playdate.app.ui.social.videoplay.AAH_CustomRecyclerView;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSocialFeed extends Fragment implements OnRefreshPage {

    public FragSocialFeed() {
    }

    private int PageNo = 1;
    private boolean NoMorePages = false;
    private AAH_CustomRecyclerView recycler_view_feed;
    private boolean boolApiCalling = false;
    private ArrayList<PostDetails> lst = new ArrayList<>();
    private SocialFeedAdapter adapter;
    private OnInnerFragmentClicks ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_social_feed, container, false);
        ref = (OnInnerFragmentClicks) getActivity();
        recycler_view_feed = view.findViewById(R.id.recycler_view_feed);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycler_view_feed.setLayoutManager(manager);
        callAPI();
        return view;
    }

    public void addMoreData() {
        if (null != adapter) {
            if (!boolApiCalling) {
                if (!NoMorePages) {


                    PostDetails pd = new PostDetails();
                    pd.setPostType("Load");
                    lst.add(pd);
                    adapter.notifyDataSetChanged();
                    recycler_view_feed.scrollToPosition(lst.size() - 1);
                    PageNo = PageNo + 1;
                    callAPI();
                }
            }

        }

    }


    @Override
    public void onStop() {
        try {
            recycler_view_feed.stopVideos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();

    }


    private void callAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "10");
        hashMap.put("pageNo", "" + PageNo);
        boolApiCalling = true;
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<PostHistory> call = service.getPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<PostHistory>() {
            @Override
            public void onResponse(@NonNull Call<PostHistory> call, @NonNull Response<PostHistory> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    ArrayList<PostDetails> lstData = response.body().getPostDetails();
                    if (lstData == null) {
                        lstData = new ArrayList<>();
                        if (PageNo == 1) {
                            Objects.requireNonNull(ref).NoFriends();
                            return;
                        }
                    } else if (PageNo == 1) {
                        if (lstData.isEmpty()) {
                            Objects.requireNonNull(ref).NoFriends();
                            return;
                        }

                    }


                    if (lst.size() > 0) {
                        lst.remove(lst.size() - 1);
                        lst.addAll(lstData);
                        adapter.notifyDataSetChanged();
                    } else {
                        lst = lstData;
                        adapter = new SocialFeedAdapter(getActivity(), lst);
                        adapter.setRef(FragSocialFeed.this);
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
                            recycler_view_feed.preDownload(urls);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        recycler_view_feed.setVisiblePercent(70);
                        recycler_view_feed.setPlayOnlyFirstVideo(true);
                        recycler_view_feed.setAdapter(adapter);
                        recycler_view_feed.smoothScrollBy(0, 1);
                        recycler_view_feed.smoothScrollBy(0, -1);

                    }


                    boolApiCalling = false;


                } else {
                    NoMorePages = true;
                    try {
                        if (null != lst) {
                            if (lst.get(lst.size() - 1).getPostType().equals("Load")) {
                                lst.remove(lst.size() - 1);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (PageNo == 1) {
                        Objects.requireNonNull(ref).NoFriends();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<PostHistory> call, @NonNull Throwable t) {
                t.printStackTrace();
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

    @Override
    public void LoadPageAgain() {
        PageNo = 1;
        boolApiCalling = false;
        NoMorePages = false;
        lst.clear();
        adapter.notifyDataSetChanged();
        callAPI();
    }


}
