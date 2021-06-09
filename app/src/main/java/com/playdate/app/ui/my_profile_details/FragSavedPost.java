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
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.SavedPostData;
import com.playdate.app.model.SavedPostModel;

import com.playdate.app.ui.my_profile_details.adapters.SavedPostVedioAdapter;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FragSavedPost extends Fragment {

    private RecyclerView recyclerView;
    private List<SavedPostData> savedPostDataList ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_saved_post, container, false);
        recyclerView = view.findViewById(R.id.recycler_photos);
        callGetSavedPOstApi();
        return view;
    }

    private void callGetSavedPOstApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();
        Call<SavedPostModel> call = service.getPostSaveGallery("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<SavedPostModel>() {
            @Override
            public void onResponse(Call<SavedPostModel> call, Response<SavedPostModel> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        savedPostDataList = (ArrayList<SavedPostData>) response.body().getData();
                        if (savedPostDataList == null) {
                            savedPostDataList = new ArrayList<>();
                        }
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                        SavedPostVedioAdapter adapter = new SavedPostVedioAdapter(getActivity(), (ArrayList<SavedPostData>) savedPostDataList);
                        recyclerView.setAdapter(adapter);
                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<SavedPostModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }




}
