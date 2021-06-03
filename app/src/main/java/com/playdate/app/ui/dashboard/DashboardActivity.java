package com.playdate.app.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.service.LocationService;
import com.playdate.app.ui.anonymous_question.AnonymousQuestionActivity;
import com.playdate.app.ui.card_swipe.FragCardSwipe;
import com.playdate.app.ui.chat.request.RequestChatFragment;
import com.playdate.app.ui.coupons.FragCouponStore;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.playdate.app.ui.date.DateBaseActivity;
import com.playdate.app.ui.dialogs.FullScreenDialog;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfileFriends;
import com.playdate.app.ui.my_profile_details.FragMyProfileDetails;
import com.playdate.app.ui.my_profile_details.FragMyProfilePayments;
import com.playdate.app.ui.my_profile_details.FragMyProfilePersonal;
import com.playdate.app.ui.my_profile_details.FragSavedPost;
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

import static android.content.ContentValues.TAG;
import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;
import static com.playdate.app.util.session.SessionPref.CompleteProfile;

public class DashboardActivity extends AppCompatActivity implements OnInnerFragmentClicks, View.OnClickListener, OnProfilePhotoChageListerner, OnFriendSelected {
    //    FragmentManager fm;
//    FragmentTransaction ft;
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
    FrameLayout flFragment;
    boolean allowRefresh = true;
//    FrameLayout flFeed;

//    protected static final String strProStaFin_CONTENT_TAG_1 = "contenFragments_1";
//    private HashMap<String, Stack<Fragment>> pri_hMap_FragmentsStack;

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
    ImageView profile_image, search;
    RecyclerView rv_friends;
    SessionPref pref;
    TextView txt_serachfriend;
    NestedScrollView nsv;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
     //   mSwipeRefreshLayout = findViewById(R.id.swipeContainer);

//        pri_hMap_FragmentsStack = new HashMap<>();
//        pri_hMap_FragmentsStack.put(strProStaFin_CONTENT_TAG_1, new Stack<>());


        txt_serachfriend = findViewById(R.id.txt_serachfriend);
        nsv = findViewById(R.id.nsv);
        search = findViewById(R.id.iv_search);
        pref = SessionPref.getInstance(this);
        ll_profile_insta = findViewById(R.id.ll_profile_insta);
        profile_image = findViewById(R.id.profile_image);
        txt_chat = findViewById(R.id.txt_chat);
        rl_main = findViewById(R.id.rl_main);
        flFragment = findViewById(R.id.flFragment);
//        flFeed=findViewById(R.id.flFeed);

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
//        iv_saved = findViewById(R.id.iv_saved);
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
//        iv_saved.setOnClickListener(this);
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
        search.setOnClickListener(this);


        rv_friends = findViewById(R.id.rv_friends);

        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
            txt_match.setVisibility(View.VISIBLE);
        } else {
            txt_match.setVisibility(View.GONE);
        }
        boolean isFirstTime = checkFirstFrag();
        Fragment fragOne;
        if (!isFirstTime) {
            fragOne = new FragLanding();
            pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
        } else {
            fragOne = new FragSocialFeed();
        }
//        addFirst();
        ReplaceFrag(fragOne);
        txt_match.setOnClickListener(this);
        txt_chat.setOnClickListener(this);
        iv_date.setOnClickListener(this);
        iv_coupons.setOnClickListener(this);
        iv_dashboard_notification.setOnClickListener(this);
        txt_social.setOnClickListener(this);
        try {
            showPremium();
        } catch (WindowManager.BadTokenException e) {
            Log.e("BadTokenException", "" + e);
        }
        setValue();
        callAPIFriends();

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.e("mSwipeRefreshLayout", "mSwipeRefreshLayout");
//
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Stop animation (This will be after 3 seconds)
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                    if (CurrentFrag.getClass().getSimpleName().equals("FragInstaLikeProfile")) {
                        if (iv_plus.getVisibility() == View.GONE) {
                            setNormal();
                        }
                    }
                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL");
                }

                if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight()) * -1) {
                    Log.i(TAG, "BOTTOM SCROLL");
//                        Toast.makeText(DashboardActivity.this, "At the bottom", Toast.LENGTH_SHORT).show();
                    if (null != CurrentFrag) {
                        if (CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed")) {
                            FragSocialFeed frag = (FragSocialFeed) CurrentFrag;
                            frag.addMoreData();
                        }
                    }
                }
            }
        });


    }


    private boolean checkFirstFrag() {
        return pref.getBoolVal(SessionPref.LoginUserSuggestionShown);
    }

    private void callAPIFriends() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");

        Call<FriendsListModel> call = service.getFriendsList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<FriendsListModel>() {
            @Override
            public void onResponse(Call<FriendsListModel> call, Response<FriendsListModel> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ArrayList<MatchListUser> lst = response.body().getUsers();
                        if (lst == null) {
                            lst = new ArrayList<>();
                        }
                        if (lst.size() > 0) {
                            txt_serachfriend.setVisibility(View.GONE);
                            rv_friends.setVisibility(View.VISIBLE);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(DashboardActivity.this, RecyclerView.HORIZONTAL, false);
                            FriendAdapter adapterfriend = new FriendAdapter(lst, DashboardActivity.this);
                            rv_friends.setAdapter(adapterfriend);
                            rv_friends.setLayoutManager(manager);
                        } else {
                            txt_serachfriend.setVisibility(View.VISIBLE);
                            rv_friends.setVisibility(View.GONE);
                        }
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

    Handler mHandler;

    private void showPremium() {
        mHandler = new Handler();
        mHandler.postDelayed(() -> {

            FullScreenDialog dialog = new FullScreenDialog(DashboardActivity.this);

            dialog.show();
        }, 30000);


    }


    @Override
    protected void onDestroy() {
        if (null != mHandler) {
            try {
                mHandler.removeCallbacksAndMessages(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    Fragment CurrentFrag;

    @Override
    public void ReplaceFrag(Fragment fragment) {
        try {

            CurrentFrag = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }

//            ft.addToBackStack(null);
            ft.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void NoFriends() {
        ReplaceFrag(new FragLanding());
    }

    @Override
    public void Reset() {
        txt_match.setBackground(null);
        txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));
        txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
        txt_social.setTextColor(getResources().getColor(android.R.color.white));
        txt_chat.setBackground(null);
        txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
        iv_dashboard_notification.setBackground(null);
        iv_dashboard_notification.setImageResource(R.drawable.ic_notifications_well);

        ll_friends.setVisibility(View.VISIBLE);
        ll_mainMenu.setVisibility(View.VISIBLE);
        ll_her.setVisibility(View.VISIBLE);
        ReplaceFrag(new FragSocialFeed());

    }

    public void ReplaceFragWithStack(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadProfile(String UserID) {
        SessionPref pref = SessionPref.getInstance(this);
        if (pref.getStringVal(SessionPref.LoginUserID).equals(UserID)) {
            ll_profile_insta.performClick();
        } else {
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setBackground(null);
            ll_friends.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ReplaceFragWithStack(new FragInstaLikeProfileFriends(true, UserID));
        }


    }

    @Override
    public void loadMatchProfile(String UserID) {
        SessionPref pref = SessionPref.getInstance(this);
        if (pref.getStringVal(SessionPref.LoginUserID).equals(UserID)) {
            ll_profile_insta.performClick();
        } else {
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));

            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setBackground(null);
            ll_friends.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ReplaceFragWithStack(new FragInstaLikeProfileFriends(false, UserID));
        }


    }


    int OPTION_CLICK = 0;

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.iv_date) {
            OPTION_CLICK = 2;
            startActivity(new Intent(DashboardActivity.this, DateBaseActivity.class));

        } else if (id == R.id.txt_social) {

            txt_chat.setBackground(null);
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));

            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.notificationnew);

            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setBackground(null);

            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);
            ReplaceFrag(new FragSocialFeed());

        } else if (id == R.id.iv_dashboard_notification) {
            txt_match.setBackground(null);
            txt_social.setBackground(null);
            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setBackground(null);
            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            iv_dashboard_notification.setBackground(getResources().getDrawable(R.drawable.menu_button));
            iv_dashboard_notification.setImageResource(R.drawable.ic_notifications_well);

            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
            ReplaceFrag(new FragNotification());
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
            iv_dashboard_notification.setImageResource(R.drawable.notificationnew);

            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_match.setBackground(getResources().getDrawable(R.drawable.menu_button));

            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);
            ReplaceFrag(new FragCardSwipe());
        } else if (id == R.id.txt_chat) {
            txt_match.setBackground(null);
            txt_social.setBackground(null);
            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.notificationnew);

            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setBackground(getResources().getDrawable(R.drawable.menu_button));
            txt_chat.setTextColor(getResources().getColor(R.color.white));

            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
            ReplaceFrag(new RequestChatFragment());
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
        } else if (id == R.id.ll_love_bottom) {
            OPTION_CLICK = 1;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.VISIBLE);
            ll_friends.setVisibility(View.VISIBLE);
            ll_profile_menu.setVisibility(View.GONE);
            iv_love.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_love.setImageResource(R.drawable.love_high);
            iv_coupons.setBackground(null);
            iv_coupons.setImageResource(R.drawable.badge);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            txt_chat.setBackground(null);
            txt_match.setBackground(null);
            txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
            txt_chat.setTextColor(getResources().getColor(android.R.color.darker_gray));
            checkFirstFrag();
            Fragment frag;
            if (!checkFirstFrag()) {
                frag = new FragLanding();
                pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
            } else {
                frag = new FragSocialFeed();
            }

            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);

            iv_dashboard_notification.setBackground(null);
            iv_dashboard_notification.setImageResource(R.drawable.notificationnew);
            txt_social.setTextColor(getResources().getColor(R.color.white));
            txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));
            ReplaceFrag(frag);
            callAPIFriends();
        } else if (id == R.id.iv_coupons) {
            OPTION_CLICK = 1;
            iv_love.setImageResource(R.drawable.love);
            iv_love.setBackground(null);
            iv_coupons.setImageResource(R.drawable.badge_sel);
            iv_coupons.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            ll_friends.setVisibility(View.VISIBLE);
            ll_mainMenu.setVisibility(View.VISIBLE);
            ll_her.setVisibility(View.VISIBLE);
            ReplaceFrag(new FragCouponStore());
        } else if (id == R.id.ll_profile_support) {
            OPTION_CLICK = 3;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.VISIBLE);

            iv_coupons.setImageResource(R.drawable.badge);
            iv_coupons.setBackground(null);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_profile_sett.setImageResource(R.drawable.tech_support_red);
            ReplaceFrag(new FragMyProfileDetails());

        } else if (id == R.id.ll_profile_insta) {
            OPTION_CLICK = 4;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_profile_menu.setVisibility(View.GONE);
            profile = new FragInstaLikeProfile(true);
            iv_coupons.setBackground(null);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
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

            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            openCamera();
        } else if (id == R.id.ll_upload_photo) {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
            pickImage();
        } else if (id == R.id.ll_Record_video) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
            takeVideoFromCamera();
        } else if (id == R.id.ll_upload_video) {
            String[] PERMISSIONS = {
                    Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
            pickVideo();
        } else if (id == R.id.iv_gallery) {
            iv_play_date_logo.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.GONE);
            ll_camera_option.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_play_date_logo.setVisibility(View.VISIBLE);
        } else if (id == R.id.iv_search) {

            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
            ReplaceFrag(new FragSearchUser());
        } else if (id == R.id.iv_saved) {

            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
            ReplaceFrag(new FragSavedPost());
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

        if (CurrentFrag.getClass().getSimpleName().equals("FragInstaLikeProfile")) {
            if (iv_plus.getVisibility() == View.GONE) {
                setNormal();
            } else {
                super.onBackPressed();
            }
        } else if (null != profile) {
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

    void setNormal() {
        iv_plus.setVisibility(View.VISIBLE);
        iv_play_date_logo.setVisibility(View.VISIBLE);
        ll_profile_drop_menu.setVisibility(View.GONE);
        ll_camera_option.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateImage() {
        setValue();
    }

    @Override
    public void OnSingleFriendSelected(String Id, String FreindID) {
        ll_friends.setVisibility(View.GONE);
        ll_option_love.setVisibility(View.GONE);
        ReplaceFragWithStack(new FragInstaLikeProfileFriends(true, Id, FreindID));
    }

    @Override
    public void OnFrinedDataClosed() {
        ll_friends.setVisibility(View.VISIBLE);
        ll_option_love.setVisibility(View.VISIBLE);
        ReplaceFrag(new FragSocialFeed());
    }

    @Override
    public void OnSuggestionClosed() {
        ll_friends.setVisibility(View.VISIBLE);
        ll_mainMenu.setVisibility(View.VISIBLE);
        ll_her.setVisibility(View.VISIBLE);
        ReplaceFrag(new FragSocialFeed());


    }

    @Override
    public void OnSuggestionClosed(boolean isFriend, String id) {
        ll_friends.setVisibility(View.GONE);
        ll_mainMenu.setVisibility(View.VISIBLE);
        ll_option_love.setVisibility(View.GONE);
        ll_her.setVisibility(View.VISIBLE);

        ReplaceFragWithStack(new FragInstaLikeProfileFriends(isFriend, id));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
            txt_match.setVisibility(View.VISIBLE);
        } else {
            txt_match.setVisibility(View.GONE);
        }
        try {
            boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (permissionGranted) {
//                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                startService(new Intent(getApplicationContext(), LocationService.class));
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        } catch (Exception e) {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }
}

