package com.playdate.app.ui.social.upload_media;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.dialogs.FriendDialog;
import com.playdate.app.ui.social.upload_media.adapter.ChipsAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostMediaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_myname;
    private EditText edt_location;
    private EditText edt_desc;
    private ImageView iv_profile;
    private PlayerView pvMain;
    private SimpleExoPlayer absPlayerInternal;
    private boolean isVideo = false;
    private RecyclerView recycler_tag_friend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_photo);
        edt_desc = findViewById(R.id.edt_desc);
        iv_profile = findViewById(R.id.iv_profile);
        ImageView iv_add = findViewById(R.id.iv_add);
        pvMain = findViewById(R.id.ep_video_view);
        recycler_tag_friend = findViewById(R.id.recycler_tag_friend);
        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView iv_done = findViewById(R.id.iv_done);
        txt_myname = findViewById(R.id.txt_myname);
        picasso = Picasso.get();
        ImageView img_upload = findViewById(R.id.img_upload);

        edt_location = findViewById(R.id.edt_location);


        if (getIntent().getStringExtra("videoPath") != null) {
            pvMain.setVisibility(View.VISIBLE);
            img_upload.setVisibility(View.GONE);
            isVideo = true;

            TrackSelector trackSelectorDef = new DefaultTrackSelector();
            absPlayerInternal = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef); //creating a player instance

            String userAgent = Util.getUserAgent(this, this.getString(R.string.app_name));
            DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
            Uri uriOfContentUrl = Uri.parse(getIntent().getStringExtra("videoPath"));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
            absPlayerInternal.prepare(mediaSource);
            absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline

            pvMain.setPlayer(absPlayerInternal);
            pvMain.hideController();
            pvMain.setControllerAutoShow(false);
            pvMain.setControllerHideOnTouch(true);
            pvMain.setUseController(false);


        } else {
            isVideo = false;
            if (null != DashboardActivity.bitmap)
                img_upload.setImageBitmap(DashboardActivity.bitmap);
            pvMain.setVisibility(View.GONE);
            img_upload.setVisibility(View.VISIBLE);
        }


        edt_location.setHint("fetching loaction...");


        setData();


        callGetUserSuggestionAPI();
        iv_done.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);


    }

    private ArrayList<MatchListUser> lstUserSuggestions;

    private void callGetUserSuggestionAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("filter", "");
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);

        Call<FriendsListModel> call = service.getFriendsList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<FriendsListModel>() {
            @Override
            public void onResponse(@NonNull Call<FriendsListModel> call, Response<FriendsListModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lstUserSuggestions = response.body().getUsers();
                        if (lstUserSuggestions == null) {
                            lstUserSuggestions = new ArrayList<>();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FriendsListModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopPlayer();
        super.onDestroy();

    }

    private void stopPlayer() {
        try {
            pvMain.setPlayer(null);
            absPlayerInternal.release();
            absPlayerInternal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Picasso picasso;

    private void setData() {
        SessionPref pref = SessionPref.getInstance(this);
        Toast.makeText(this, "CITY location" + pref.getStringVal("LastCity"), Toast.LENGTH_SHORT).show();
        edt_location.setText(pref.getStringVal("LastCity"));
        String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
        if (img.contains("http")) {
            picasso.load(img)
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(iv_profile);
        } else {
            picasso.load(BASE_URL_IMAGE + img)
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(iv_profile);
        }

        txt_myname.setText(pref.getStringVal(SessionPref.LoginUserusername));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_done) {
            uploadImage();
        } else if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_add) {
            showFriendsDialog();
        }
    }

//    private static final String VIDEO_DIRECTORY = "/playdate";

    private void uploadImage() {
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        //create a file to write bitmap data
        File currentFile;
        if (isVideo) {

            currentFile = new File(getIntent().getStringExtra("videoPath"));
        } else {
            String filename;
            filename = "profile.png";
            currentFile = new File(getCacheDir(), filename);
            try {
                currentFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(currentFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DashboardActivity.bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                fos.write(byteArray);

                fos.flush();

                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart;
        GetDataService service;
        Call<LoginResponse> call;
        if (isVideo) {
            filePart = MultipartBody.Part.createFormData("mediaFeed", currentFile.getName(), RequestBody.create(MediaType.parse("video/mp4"), currentFile));
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            call = service.uploadVideoToFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        } else {
            filePart = MultipartBody.Part.createFormData("mediaFeed", currentFile.getName(), RequestBody.create(MediaType.parse("image/png"), currentFile));
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            call = service.uploadImageToFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        }


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                pd.dismiss();
                if (response.code() == 200) {

                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        LoginUserDetails user = response.body().getUserData();
                        callAPIFeedPost(user.getMediaId());
                    } else {
                        new CommonClass().showDialogMsg(PostMediaActivity.this, "PlayDate", "An error occurred!", "Ok");
                    }


                } else {
                    new CommonClass().showDialogMsg(PostMediaActivity.this, "PlayDate", "An error occurred!", "Ok");

                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });
    }


    private void callAPIFeedPost(String mediaId) {

        String tagFriends = "";
        try {
            if (null != lstUserSuggestions) {
                for (int i = 0; i < lstUserSuggestions.size(); i++) {
                    if (lstUserSuggestions.get(i).isSelected()) {
                        if (tagFriends.isEmpty()) {
                            tagFriends = lstUserSuggestions.get(i).getUserId();
                        } else {
                            tagFriends = tagFriends + "," + lstUserSuggestions.get(i).getUserId();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionPref pref = SessionPref.getInstance(this);
        String Location = edt_location.getText().toString();
        if (Location.isEmpty()) {
            Location = "India";// Hardcode
        }
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("location", Location);
        hashMap.put("mediaId", mediaId);
        hashMap.put("postType", "Normal");// Hardcode
        hashMap.put("tagFriend", tagFriends);
        hashMap.put("tag", edt_desc.getText().toString());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        Call<LoginResponse> call = service.addPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        DashboardActivity.bitmap = null;
                        DashboardActivity.refreshFlag = 1;
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showFriendsDialog() {
        FriendDialog dialog = new FriendDialog(this, lstUserSuggestions, false, "");
        dialog.show();
        dialog.setOnDismissListener(dialog1 -> {
            try {
                ArrayList<MatchListUser> lst = new ArrayList<>();
                for (int i = 0; i < lstUserSuggestions.size(); i++) {
                    if (lstUserSuggestions.get(i).isSelected()) {
                        lst.add(lstUserSuggestions.get(i));
                    }
                }
                recycler_tag_friend.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                recycler_tag_friend.setAdapter(new ChipsAdapter(lst));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}


