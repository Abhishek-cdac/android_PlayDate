package com.playdate.app.ui.blockuser;

import android.os.Bundle;
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
import com.playdate.app.model.LoginResponse;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class UnblockBottomSheet extends BottomSheetDialogFragment {

    private final String userID;
    private final BlockUserActivity blockUserActivity;

    public UnblockBottomSheet(String userID, BlockUserActivity blockUserActivity) {
        this.userID = userID;
        this.blockUserActivity = blockUserActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_unblock_bottom_sheet, container, false);
        RelativeLayout rl_unblock = view.findViewById(R.id.rl_unblock);
        rl_unblock.setOnClickListener(v -> callUnBlockUser());

        return view;
    }

    private void callUnBlockUser() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("action", "Block");//Block or Report
        hashMap.put("toUserId", userID);


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();

        Call<LoginResponse> call = service.removeUserReportBlock("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
//                        socialFeedAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "User unblocked successsfully!", Toast.LENGTH_SHORT).show();
                        blockUserActivity.callAPI();
                    } else {
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
