package com.playdate.app.ui.blockuser;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.BlockedUser;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.anonymous_question.AnonymousBottomSheet;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockUserActivity extends AppCompatActivity {
    private RecyclerView recycler_blocked_users;
    private TextView no_block_users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_user);
        recycler_blocked_users = findViewById(R.id.recycler_blocked_users);
        no_block_users = findViewById(R.id.no_block_users);
        recycler_blocked_users.setLayoutManager(new LinearLayoutManager(this));
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        callAPI();
    }


    public void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        Call<CommonModel> call = service.getUserBlocked("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ArrayList<BlockedUser> list = response.body().getBlockedUsers();
                        if (null == list) {
                            list = new ArrayList<>();
                        }
                        if (list.size() == 0) {
                            no_block_users.setVisibility(View.VISIBLE);
                            recycler_blocked_users.setVisibility(View.GONE);
                        } else {
                            BlockedUserAdapter adapter = new BlockedUserAdapter(list, BlockUserActivity.this);
                            recycler_blocked_users.setAdapter(adapter);
                        }


                    } else {
                        no_block_users.setVisibility(View.VISIBLE);
                        recycler_blocked_users.setVisibility(View.GONE);
                    }
                } else {
                    no_block_users.setVisibility(View.VISIBLE);
                    recycler_blocked_users.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                no_block_users.setVisibility(View.VISIBLE);
                recycler_blocked_users.setVisibility(View.GONE);
            }
        });
    }

    public void showModel(String userID) {
        try {
            UnblockBottomSheet bottomSheet = new UnblockBottomSheet(userID, this);
            bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
