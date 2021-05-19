package com.playdate.app.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.anonymous_question.AnonymousQuestionActivity;
import com.playdate.app.ui.card_swipe.FragCardSwipe;
import com.playdate.app.ui.chat.request.RequestChatFragment;
import com.playdate.app.ui.coupons.FragCouponStore;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.date.DateBaseActivity;
import com.playdate.app.ui.dialogs.FullScreenDialog;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.ui.my_profile_details.FragMyProfileDetails;
import com.playdate.app.ui.my_profile_details.FragMyProfilePayments;
import com.playdate.app.ui.my_profile_details.FragMyProfilePersonal;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.ui.social.FragSocialFeed;
import com.playdate.app.ui.social.upload_media.PostMediaActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;
import static com.playdate.app.util.session.SessionPref.CompleteProfile;

public class DashboardActivity extends AppCompatActivity implements OnInnerFragmentClicks, View.OnClickListener, OnProfilePhotoChageListerner {
    FragmentManager fm;
    FragmentTransaction ft;
    TextView txt_match, txt_chat;
    TextView txt_social;
    TextView txt_payment;
    TextView txt_account;
    TextView txt_personal;
    ImageView iv_love;
    ImageView iv_profile_sett;
    ImageView iv_plus;
    ImageView iv_play_date_logo;
    ImageView iv_cancel;
    ImageView iv_create_ano_ques;
    ImageView iv_gallery;
    ImageView iv_dashboard_notification;
    ImageView iv_coupons;
    ImageView iv_date;

    //    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout ll_mainMenu, ll_her;
    LinearLayout ll_friends;
    LinearLayout ll_profile_menu;
    LinearLayout ll_option_love;
    LinearLayout ll_profile_support;
    LinearLayout ll_love_bottom;
    LinearLayout ll_profile_insta;
    LinearLayout ll_profile_drop_menu;

    LinearLayout ll_take_photo;
    LinearLayout ll_upload_photo;
    LinearLayout ll_Record_video;
    LinearLayout ll_upload_video;
    LinearLayout bottomNavigationView;
    LinearLayout ll_camera_option;


    RelativeLayout rl_main;
    ImageView profile_image;
    RecyclerView rv_friends;
    SessionPref pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        pref = SessionPref.getInstance(this);
        ll_profile_insta = findViewById(R.id.ll_profile_insta);
        profile_image = findViewById(R.id.profile_image);
        txt_chat = findViewById(R.id.txt_chat);
        rl_main = findViewById(R.id.rl_main);
        ll_her = findViewById(R.id.ll_her);
        ll_mainMenu = findViewById(R.id.ll_mainMenu);
        ll_friends = findViewById(R.id.ll_friends);
        ll_love_bottom = findViewById(R.id.ll_love_bottom);
        ll_profile_support = findViewById(R.id.ll_profile_support);
        ll_profile_menu = findViewById(R.id.ll_profile_menu);
        ll_option_love = findViewById(R.id.ll_option_love);
        txt_social = findViewById(R.id.txt_social);
        txt_match = findViewById(R.id.txt_match);
        txt_payment = findViewById(R.id.txt_payment);
        txt_account = findViewById(R.id.txt_account);
        txt_personal = findViewById(R.id.txt_personal);
        iv_plus = findViewById(R.id.iv_plus);
        iv_play_date_logo = findViewById(R.id.iv_play_date_logo);
        iv_love = findViewById(R.id.iv_love);
        iv_profile_sett = findViewById(R.id.iv_profile_sett);
        ll_profile_drop_menu = findViewById(R.id.ll_profile_drop_menu);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_gallery = findViewById(R.id.iv_gallery);
        iv_create_ano_ques = findViewById(R.id.iv_create_ano_ques);
        iv_dashboard_notification = findViewById(R.id.iv_dashboard_notification);
        iv_coupons = findViewById(R.id.iv_coupons);
        iv_date = findViewById(R.id.iv_date);

        ll_take_photo = findViewById(R.id.ll_take_photo);
        ll_upload_photo = findViewById(R.id.ll_upload_photo);
        ll_Record_video = findViewById(R.id.ll_Record_video);
        ll_upload_video = findViewById(R.id.ll_upload_video);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ll_camera_option = findViewById(R.id.ll_camera_option);

        iv_cancel.setOnClickListener(this);
        iv_gallery.setOnClickListener(this);
        ll_profile_insta.setOnClickListener(this);
        iv_plus.setOnClickListener(this);
        txt_payment.setOnClickListener(this);
        txt_account.setOnClickListener(this);
        txt_personal.setOnClickListener(this);
        ll_profile_support.setOnClickListener(this);
        ll_love_bottom.setOnClickListener(this);
        iv_create_ano_ques.setOnClickListener(this);
        ll_take_photo.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        ll_Record_video.setOnClickListener(this);
        ll_upload_video.setOnClickListener(this);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        rv_friends = findViewById(R.id.rv_friends);
        boolean isFirstTime = checkFirstFrag();
        Fragment fragOne;
        if (!isFirstTime) {
            fragOne = new FragLanding();
            pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
        } else {
            fragOne = new FragSocialFeed();
        }
        ft.add(R.id.flFragment, fragOne);
        ft.commit();

        txt_match.setOnClickListener(this);
        txt_chat.setOnClickListener(this);
        iv_date.setOnClickListener(this);
        iv_coupons.setOnClickListener(this);
        iv_dashboard_notification.setOnClickListener(this);
        txt_social.setOnClickListener(this);


        showPremium();
        setValue();
        callAPIFriends();


    }

    private boolean checkFirstFrag() {
        return pref.getBoolVal(SessionPref.LoginUserSuggestionShown);
    }

    private void callAPIFriends() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
//        pd.show();

        Call<FriendsListModel> call = service.getFriendsList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<FriendsListModel>() {
            @Override
            public void onResponse(Call<FriendsListModel> call, Response<FriendsListModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(DashboardActivity.this, RecyclerView.HORIZONTAL, false);
                        ArrayList<MatchListUser> lst = response.body().getUsers();
                        if (lst == null) {
                            lst = new ArrayList<>();
                        }
                        FriendAdapter adapterfriend = new FriendAdapter(lst);
                        rv_friends.setAdapter(adapterfriend);
                        rv_friends.setLayoutManager(manager);

                    } else {
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                    } catch (Exception e) {
//                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<FriendsListModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setValue() {
        try {
            SessionPref pref = SessionPref.getInstance(this);
            pref.saveBoolKeyVal(CompleteProfile, true);
            String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
            if (img.contains("http")) {
                Picasso.get().load(img)
                        .placeholder(R.drawable.profile)
                        .into(profile_image);
            } else {
                Picasso.get().load(BASE_URL_IMAGE + img)
                        .placeholder(R.drawable.profile)
                        .into(profile_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showPremium() {

        new Handler().postDelayed(new Runnable() {
            public void run() {

                FullScreenDialog dialog = new FullScreenDialog(DashboardActivity.this);
                dialog.show();

//                AnonymousMedalDialog dialog = new AnonymousMedalDialog(DashboardActivity.this);
//                dialog.show();
            }
        }, 20000);


    }


    @Override
    public void ReplaceFrag(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
//        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();
    }


    @Override
    public void loadProfile() {
        ll_profile_insta.performClick();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.iv_date) {
            startActivity(new Intent(DashboardActivity.this, DateBaseActivity.class));

        } else if (id == R.id.txt_social) {
            txt_match.setBackground(null);
            txt_chat.setBackground(null);
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));

            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.ic_bell);

            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            ReplaceFrag(new FragSocialFeed());
            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);

        } else if (id == R.id.iv_dashboard_notification) {
            txt_match.setBackground(null);
            txt_social.setBackground(null);
            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setBackground(null);
            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            iv_dashboard_notification.setBackground(getResources().getDrawable(R.drawable.menu_button));
            // iv_dashboard_notification.setColorFilter(R.color.white);
            iv_dashboard_notification.setImageResource(R.drawable.ic_notifications_well);
            ReplaceFrag(new FragNotification());
            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
        } else if (id == R.id.txt_payment) {


            txt_payment.setTextColor(getResources().getColor(R.color.white));
            txt_payment.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_personal.setBackground(null);
            txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_account.setBackground(null);
            txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));

            ReplaceFrag(new FragMyProfilePayments());
        } else if (id == R.id.txt_match) {
            txt_social.setBackground(null);
            txt_chat.setBackground(null);
            txt_match.setTextColor(getResources().getColor(R.color.white));
            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.ic_bell);

            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setBackground(getResources().getDrawable(R.drawable.menu_button));
            ReplaceFrag(new FragCardSwipe());
            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);
        } else if (id == R.id.txt_chat) {
            txt_match.setBackground(null);
            txt_social.setBackground(null);
            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.ic_bell);

            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setBackground(getResources().getDrawable(R.drawable.menu_button));
            txt_chat.setTextColor(getResources().getColor(R.color.white));
            ReplaceFrag(new RequestChatFragment());
//                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
//                startActivity(intent);
            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
        } else if (id == R.id.txt_personal) {

            txt_personal.setTextColor(getResources().getColor(R.color.white));
            txt_personal.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_account.setBackground(null);
            txt_account.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_payment.setBackground(null);
            txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));

            ReplaceFrag(new FragMyProfilePersonal());
        } else if (id == R.id.txt_account) {

            txt_account.setTextColor(getResources().getColor(R.color.white));
            txt_account.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_personal.setBackground(null);
            txt_personal.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_payment.setBackground(null);
            txt_payment.setTextColor(getResources().getColor(android.R.color.darker_gray));

            ReplaceFrag(new FragMyProfileDetails());
        } else if (id == R.id.ll_profile_support) {
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.VISIBLE);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_profile_sett.setImageResource(R.drawable.tech_support_red);
            ReplaceFrag(new FragMyProfileDetails());

        } else if (id == R.id.ll_love_bottom) {
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.VISIBLE);
            ll_friends.setVisibility(View.VISIBLE);
            ll_profile_menu.setVisibility(View.GONE);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            iv_love.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_love.setImageResource(R.drawable.love_high);
            checkFirstFrag();
            Fragment frag;
            if (!checkFirstFrag()) {
                frag = new FragLanding();
                pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
            } else {
                frag = new FragSocialFeed();
            }
            ReplaceFrag(frag);
            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);

            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.ic_bell);
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));


        } else if (id == R.id.ll_profile_insta) {
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.GONE);
            profile = new FragInstaLikeProfile();
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            iv_profile_sett.setBackground(null);


            ReplaceFrag(profile);
        } else if (id == R.id.iv_plus) {
            ll_profile_drop_menu.setVisibility(View.VISIBLE);
            iv_plus.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.INVISIBLE);
        } else if (id == R.id.iv_cancel) {
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
        } else if (id == R.id.iv_create_ano_ques) {
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
            Intent mIntent = new Intent(DashboardActivity.this, AnonymousQuestionActivity.class);
            mIntent.putExtra("new", true);
            startActivity(mIntent);
        } else if (id == R.id.ll_take_photo) {

            String[] PERMISSIONS = {
                    Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            openCamera();
            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
        } else if (id == R.id.ll_upload_photo) {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickImage();
            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);

        } else if (id == R.id.ll_Record_video) {
            takeVideoFromCamera();
            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
        } else if (id == R.id.ll_upload_video) {
            String[] PERMISSIONS = {
                    Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);
            pickVideo();
            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);

        } else if (id == R.id.iv_gallery) {
            iv_play_date_logo.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.GONE);
            ll_camera_option.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
        } else if (id == R.id.iv_coupons) {
            iv_love.setImageResource(R.drawable.love);
            ReplaceFrag(new FragCouponStore());
            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);

        }


    }


    public void openCamera() {
        try {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int GALLERY = 1, CAMERA = 2;

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void pickVideo() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);

    }

    FragInstaLikeProfile profile;
    int count = 0;

    public static Bitmap bitmap = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                iv_plus.setVisibility(View.VISIBLE);
                return;
            }
            if (requestCode == PICK_PHOTO_FOR_AVATAR) {

                if (data.getData() == null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if (null != bitmap) {
                    iv_plus.setVisibility(View.VISIBLE);
                    Intent mIntent = new Intent(DashboardActivity.this, PostMediaActivity.class);
                    startActivity(mIntent);
                }

            } else if (requestCode == TAKE_PHOTO_CODE) {


                if (data.getData() == null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if (null != bitmap) {
                    iv_plus.setVisibility(View.VISIBLE);
                    Intent mIntent = new Intent(DashboardActivity.this, PostMediaActivity.class);

                    startActivity(mIntent);
                }

            } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {

                if (data != null) {
                    Uri contentURI = data.getData();

                    String selectedVideoPath = getPath(contentURI);
                    Log.d("path", selectedVideoPath);
                    iv_plus.setVisibility(View.VISIBLE);
                    Intent mIntent = new Intent(DashboardActivity.this, PostMediaActivity.class);
                    mIntent.putExtra("videoPath", selectedVideoPath);
                    startActivity(mIntent);

                }
            } else if (requestCode == CAMERA) {
                iv_plus.setVisibility(View.VISIBLE);
                Uri contentURI = data.getData();
                String recordedVideoPath = getPath(contentURI);
                Log.d("frrr", recordedVideoPath);
                Intent mIntent = new Intent(DashboardActivity.this, PostMediaActivity.class);
                mIntent.putExtra("videoPath", recordedVideoPath);
                startActivity(mIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onBackPressed() {


        if (null != profile) {
            if (count == 0) {
                count = 1;
                profile.onTypeChange(0);
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }


    }


    @Override
    public void updateImage() {
        setValue();
    }
}



