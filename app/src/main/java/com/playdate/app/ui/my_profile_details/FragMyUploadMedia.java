package com.playdate.app.ui.my_profile_details;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.ui.my_profile_details.adapters.InstaPhotosAdapter;
import com.playdate.app.ui.social.adapter.SocialFeedAdapter;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.model.PostHistory;
import com.playdate.app.ui.social.videoplay.AAH_CustomRecyclerView;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragMyUploadMedia extends Fragment {
    String loginId;

    public FragMyUploadMedia(String loginId) {
        this.loginId = loginId;
    }

    TextView txt_no_media;
    RecyclerView recyclerView;
    AAH_CustomRecyclerView recycler_view_feed;
    private ArrayList<PostDetails> lst;

    public FragMyUploadMedia() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_saved_post, container, false);
        recyclerView = view.findViewById(R.id.recycler_photos);
        recycler_view_feed = view.findViewById(R.id.recycler_view_feed);
        txt_no_media = view.findViewById(R.id.txt_no_media);

        callAPI();


        return view;
    }


    private void callAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", loginId);
        hashMap.put("limit", "100");//hardcode
        hashMap.put("pageNo", "1");//hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<PostHistory> call = service.getMyPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<PostHistory>() {
            @Override
            public void onResponse(Call<PostHistory> call, Response<PostHistory> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    lst = response.body().getPostDetails();
                    if (lst == null) {
                        lst = new ArrayList<>();
                    }

                    if (lst.size() == 0) {
                        txt_no_media.setVisibility(View.VISIBLE);
                        recycler_view_feed.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        txt_no_media.setVisibility(View.GONE);
                        setView(0, 0);
                    }


                } else {

                    txt_no_media.setVisibility(View.VISIBLE);
                    recycler_view_feed.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<PostHistory> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });

    }


    int viewType = 0;


    public void setView(int view, int pos) {
        viewType = view;
        if (view == 0) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            InstaPhotosAdapter adapter = new InstaPhotosAdapter(getActivity(), lst, FragMyUploadMedia.this);
            recyclerView.setAdapter(adapter);
            recycler_view_feed.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            recycler_view_feed.setVisibility(View.VISIBLE);
            SocialFeedAdapter adapter = new SocialFeedAdapter(getActivity(), lst);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recycler_view_feed.setLayoutManager(manager);
            recycler_view_feed.setItemAnimator(new DefaultItemAnimator());
            recycler_view_feed.setActivity(getActivity());
            recycler_view_feed.setCheckForMp4(false);
            recycler_view_feed.setVisiblePercent(70);
            recycler_view_feed.setPlayOnlyFirstVideo(true);
            recycler_view_feed.setAdapter(adapter);




        }
    }

//    private void callGetSavedPOstApi() {
//
//        SessionPref pref = SessionPref.getInstance(getActivity());
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
////        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
////        pd.show();
//        Call<SavedPostModel> call = service.getPostSaveGallery("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        call.enqueue(new retrofit2.Callback<SavedPostModel>() {
//            @Override
//            public void onResponse(Call<SavedPostModel> call, Response<SavedPostModel> response) {
//                if (response.code() == 200) {
//                    if (response.body().getStatus() == 1) {
//                        savedPostDataList = (ArrayList<SavedPostData>) response.body().getData();
//                        if (savedPostDataList == null) {
//                            savedPostDataList = new ArrayList<>();
//                        }
//                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//                        SavedPostVedioAdapter adapter = new SavedPostVedioAdapter(getActivity(), (ArrayList<SavedPostData>) savedPostDataList);
//                        recyclerView.setAdapter(adapter);
//                    }
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SavedPostModel> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//    }


}

