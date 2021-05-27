package com.playdate.app.ui.anonymous_question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.util.session.SessionPref;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Response;

public class AnonymousBottomSheet extends BottomSheetDialogFragment {
    RelativeLayout report_comment_rl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_anonymous_bottom_sheet, container, false);

        Bundle bundle = this.getArguments();
        String postId = bundle.getString("postIdAQ");
        String userId = bundle.getString("userIdAQ");
        String commentId = bundle.getString("commentIdAQ");


        report_comment_rl = view.findViewById(R.id.report_comment_rl);
        report_comment_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callReportCommentApi(postId, userId, commentId);
            }
        });

        return view;
    }

    private void callReportCommentApi(String postId, String userId, String commentId) {

        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("postId", postId);
        hashMap.put("userId", userId);
        hashMap.put("commentId", commentId);
        Call<GetCommentModel> call = service.reportPostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetCommentModel>() {
            @Override
            public void onResponse(Call<GetCommentModel> call, Response<GetCommentModel> response) {

                if (response.code() == 200) {
                    Log.e("getStatus..",""+response.body().getStatus());
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
