package com.playdate.app.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.FragCouponParentBusiness;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.model.NotificationCountModel;
import com.playdate.app.model.Premium;
import com.playdate.app.model.PremiumPlans;
import com.playdate.app.service.FcmMessageService;
import com.playdate.app.service.LocationService;
import com.playdate.app.ui.anonymous_question.AnonymousQuestionActivity;
import com.playdate.app.ui.card_swipe.FragCardSwipe;
import com.playdate.app.ui.chat.request.ChatBaseActivity;
import com.playdate.app.ui.coupons.FragCouponParent;
import com.playdate.app.ui.dashboard.adapter.FriendAdapter;
import com.playdate.app.ui.dashboard.data.CallAPI;
import com.playdate.app.ui.dashboard.fragments.FragLanding;
import com.playdate.app.ui.dashboard.fragments.FragSearchUser;
import com.playdate.app.ui.date.DateBaseActivity;
import com.playdate.app.ui.date.games.FragGameLeaderBoard;
import com.playdate.app.ui.date.games.FragStore;
import com.playdate.app.ui.dialogs.FullScreenDialog;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfile;
import com.playdate.app.ui.my_profile_details.FragInstaLikeProfileFriends;
import com.playdate.app.ui.my_profile_details.FragSavedPost;
import com.playdate.app.ui.my_profile_details.FragSettingsParent;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.ui.social.FragSocialFeed;
import com.playdate.app.ui.social.upload_media.PostMediaActivity;
import com.playdate.app.util.common.BaseActivity;
import com.playdate.app.util.image_crop.MainActivity;
import com.playdate.app.util.session.SessionPref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;
import static com.playdate.app.util.session.SessionPref.CompleteProfile;
import static com.playdate.app.util.session.SessionPref.LoginUserFCMID;

public class DashboardActivity extends BaseActivity implements OnInnerFragmentClicks, View.OnClickListener, OnProfilePhotoChageListerner, OnFriendSelected, OnAPIResponce {

    private TextView txt_match;
    private TextView txt_social;
    private TextView txt_count;
    private ImageView iv_love;
    private ImageView iv_profile_sett;
    private ImageView iv_plus;
    private ImageView iv_play_date_logo;
    private ImageView iv_coupons;
    private ImageView profile_image;
    private LinearLayout ll_mainMenu, ll_her;
    private LinearLayout ll_friends;
    private LinearLayout ll_option_love;
    private LinearLayout ll_profile_insta;
    private LinearLayout ll_profile_drop_menu;
    private LinearLayout bottomNavigationView;
    private LinearLayout ll_camera_option;
    private LinearLayout ll_mainMenu2;
    private RecyclerView rv_friends;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendAdapter adapterfriend;
    private Fragment CurrentFrag;
    private NestedScrollView nsv;
    private FragInstaLikeProfile profile;
    private int count = 0;
    private int OPTION_CLICK = 0;
    private final int CAMERA = 2;
    private boolean isAtTop = true;

    public static Bitmap bitmap = null;
    public static int refreshFlag = 0;
    ImageView iv_date;
    LinearLayout ll_love_bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mSwipeRefreshLayout = findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_pink));
        ll_mainMenu2 = findViewById(R.id.ll_mainMenu2);
        txt_count = findViewById(R.id.txt_count);
        nsv = findViewById(R.id.nsv);
        ImageView search = findViewById(R.id.iv_search);
        ll_profile_insta = findViewById(R.id.ll_profile_insta);
        profile_image = findViewById(R.id.profile_image);
        TextView txt_chat = findViewById(R.id.txt_chat);
        ll_her = findViewById(R.id.ll_her);
        ll_mainMenu = findViewById(R.id.ll_mainMenu);
        ll_friends = findViewById(R.id.ll_friends);
        ll_love_bottom = findViewById(R.id.ll_love_bottom);
        LinearLayout ll_coupon = findViewById(R.id.ll_coupon);
        LinearLayout ll_profile_support = findViewById(R.id.ll_profile_support);
        ll_option_love = findViewById(R.id.ll_option_love);
        txt_social = findViewById(R.id.txt_social);
        txt_match = findViewById(R.id.txt_match);
        iv_plus = findViewById(R.id.iv_plus);
        iv_play_date_logo = findViewById(R.id.iv_play_date_logo);
        iv_love = findViewById(R.id.iv_love);
        iv_profile_sett = findViewById(R.id.iv_profile_sett);
        ll_profile_drop_menu = findViewById(R.id.ll_profile_drop_menu);
        ImageView iv_cart = findViewById(R.id.iv_cart);
        ImageView iv_cancel = findViewById(R.id.iv_cancel);
        ImageView iv_gallery = findViewById(R.id.iv_gallery);
        ImageView iv_create_ano_ques = findViewById(R.id.iv_create_ano_ques);
        ImageView iv_dashboard_notification = findViewById(R.id.iv_dashboard_notification);
        iv_coupons = findViewById(R.id.iv_coupons);
        iv_date = findViewById(R.id.iv_date);
        LinearLayout ll_take_photo = findViewById(R.id.ll_take_photo);
        LinearLayout ll_upload_photo = findViewById(R.id.ll_upload_photo);
        LinearLayout ll_Record_video = findViewById(R.id.ll_Record_video);
        LinearLayout ll_upload_video = findViewById(R.id.ll_upload_video);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ll_camera_option = findViewById(R.id.ll_camera_option);

        rv_friends = findViewById(R.id.rv_friends);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(DashboardActivity.this, RecyclerView.HORIZONTAL, false);
        adapterfriend = new FriendAdapter(new ArrayList<>(), DashboardActivity.this);
        rv_friends.setAdapter(adapterfriend);
        rv_friends.setLayoutManager(manager);

        if (pref.getBoolVal(SessionPref.isBusiness)) {
            txt_match.setVisibility(View.GONE);
        } else {
            if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
                txt_match.setVisibility(View.VISIBLE);
            } else {
                txt_match.setVisibility(View.GONE);
            }
        }
        CallNotificationCount();
        boolean isFirstTime = checkFirstFrag();
        Fragment fragOne;
        if (!isFirstTime) {
            fragOne = new FragLanding();
            pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
        } else {
            fragOne = new FragSocialFeed();
        }
        ReplaceFrag(fragOne);

        callAPIShowPremium();
        setValue();
        callAPIFriends();

        nsv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY > oldScrollY) {
                if (CurrentFrag.getClass().getSimpleName().equals("FragInstaLikeProfile")) {
                    if (iv_plus.getVisibility() == View.GONE) {
                        setNormal();
                    }
                }
                isAtTop = false;
            }
            if (scrollY == 0) {
                isAtTop = true;
            }


            if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight()) * -1) {
                if (null != CurrentFrag) {
                    if (CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed")) {
                        FragSocialFeed frag = (FragSocialFeed) CurrentFrag;
                        frag.addMoreData();
                    }
                }
            }


        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            try {
                if (null != CurrentFrag) {
                    if (CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed")) {
                        FragSocialFeed frag = (FragSocialFeed) CurrentFrag;
                        frag.LoadPageAgain();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            callAPIFriends();

//            callNotification();
        });

        updateToken();

        iv_cancel.setOnClickListener(this);
        iv_gallery.setOnClickListener(this);
        ll_profile_insta.setOnClickListener(this);
        iv_plus.setOnClickListener(this);
        ll_profile_support.setOnClickListener(this);
        ll_love_bottom.setOnClickListener(this);
        iv_create_ano_ques.setOnClickListener(this);
        iv_cart.setOnClickListener(this);
        ll_take_photo.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        ll_Record_video.setOnClickListener(this);
        ll_upload_video.setOnClickListener(this);
        search.setOnClickListener(this);
        txt_match.setOnClickListener(this);
        txt_chat.setOnClickListener(this);
        iv_date.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        iv_dashboard_notification.setOnClickListener(this);
        txt_social.setOnClickListener(this);
    }

    ArrayList<PremiumPlans> lstPremium;

    private void callAPIShowPremium() {
        //showPremium();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        Call<Premium> call = service.getPackages("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<Premium>() {
            @Override
            public void onResponse(Call<Premium> call, Response<Premium> response) {
                try {

                    if (response.code() == 200) {
                        if (response.body().getStatus() == 1) {
                            lstPremium = response.body().getLstPremiumPlan();
                            showPremium(lstPremium.get(0));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Premium> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void updateToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("******", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("******", token);
                    SessionPref pref1 = SessionPref.getInstance(DashboardActivity.this);
                    pref1.saveStringKeyVal(LoginUserFCMID, token);
                });

        startService(new Intent(this, FcmMessageService.class));
    }

    private void CallNotificationCount() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        Call<NotificationCountModel> call = service.getNotificationCount("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<NotificationCountModel>() {
            @Override
            public void onResponse(Call<NotificationCountModel> call, Response<NotificationCountModel> response) {
                try {

                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            int countNotification = response.body().getData().get(0).getTotalUnreadNotification();
                            Log.e("countNotification", "" + countNotification);

//                            txt_count.setText("99+");
//                            txt_count.setTextSize(8);
//                            txt_count.setPadding(5, 3, 5, 3);  ///99+
//                            txt_count.setPadding(6, 2, 7, 2);  ///10-99
//                            txt_count.setPadding(10, 0, 10, 0); //1-9

                            if (countNotification > 0 && countNotification <= 9) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setPadding(10, 0, 10, 0); //1-9
                                txt_count.setText("" + countNotification);

                            } else if (countNotification > 9 && countNotification <= 99) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setPadding(6, 2, 7, 2);  ///10-99
                                txt_count.setText("" + countNotification);

                            } else if (countNotification > 99) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setTextSize(8);
                                txt_count.setPadding(5, 3, 5, 3);  ///99+
                                txt_count.setText("99+");

                            } else {
                                txt_count.setVisibility(View.GONE);
                                txt_count.setText("");

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NotificationCountModel> call, Throwable t) {
                t.printStackTrace();
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
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<MatchListUser> lst = response.body().getUsers();
                        if (lst == null) {
                            lst = new ArrayList<>();
                        }
                        if (lst.size() > 0) {
                            rv_friends.setVisibility(View.VISIBLE);
                            adapterfriend.updateList(lst);
                        }
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<FriendsListModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private void setValue() {
        try {

            pref.saveBoolKeyVal(CompleteProfile, true);
            String img = pref.getStringVal(SessionPref.LoginUserprofilePic);
            if (img.contains("http")) {
                picasso.load(img)
                        .placeholder(R.drawable.profile)
                        .into(profile_image);
            } else {
                picasso.load(BASE_URL_IMAGE + img)
                        .placeholder(R.drawable.profile)
                        .into(profile_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Handler mHandler;

    private void showPremium(PremiumPlans premiumPlans) {
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(() -> new FullScreenDialog(DashboardActivity.this, premiumPlans.getLst_packageDescription()).show(), 2 * 30000);
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
            ft.commitAllowingStateLoss();

            mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void NoFriends() {
        ReplaceFrag(new FragLanding());
    }

    @Override
    public void Reset() {
        socialOnMatchOffNotiOff();
        ShowMainMenuWithFriends();
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
        }
    }

    @Override
    public void loadProfile(String UserID) {
        if (pref.getStringVal(SessionPref.LoginUserID).equals(UserID)) {
            ll_profile_insta.performClick();
        } else {
            socialOnMatchOffNotiOff();
            ll_friends.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ReplaceFragWithStack(new FragInstaLikeProfileFriends(true, UserID));
        }


    }

    void socialOnMatchOffNotiOff() {
        txt_social.setTextColor(getResources().getColor(R.color.white));
        txt_social.setBackground(getResources().getDrawable(R.drawable.menu_button));
        txt_match.setTextColor(getResources().getColor(android.R.color.darker_gray));
        txt_match.setBackground(null);
    }

    void socialOffMatchOnNotiOff() {
        txt_social.setTextColor(getResources().getColor(android.R.color.darker_gray));
        txt_social.setBackground(null);
        txt_match.setTextColor(getResources().getColor(R.color.white));
        txt_match.setBackground(getResources().getDrawable(R.drawable.menu_button));
    }


    @Override
    public void loadMatchProfile(String UserID) {
        if (pref.getStringVal(SessionPref.LoginUserID).equals(UserID)) {
            ll_profile_insta.performClick();
        } else {
            socialOnMatchOffNotiOff();

            ll_friends.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ReplaceFragWithStack(new FragInstaLikeProfileFriends(false, UserID));
        }


    }

    void ShowMainMenuWithFriends() {
        ll_friends.setVisibility(View.VISIBLE);
        ll_mainMenu.setVisibility(View.VISIBLE);
        ll_her.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.iv_date) {
            startActivity(new Intent(DashboardActivity.this, DateBaseActivity.class));
           /* ll_her.setVisibility(View.GONE);
            ll_mainMenu2.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ReplaceFrag(new FragSelectDate());*/

        } else if (id == R.id.txt_social) {
            socialOnMatchOffNotiOff();
            ShowMainMenuWithFriends();
            ReplaceFrag(new FragSocialFeed());

        } else if (id == R.id.iv_dashboard_notification) {

            ll_friends.setVisibility(View.GONE);
            ll_mainMenu.setVisibility(View.GONE);
            ll_her.setVisibility(View.GONE);
            ReplaceFrag(new FragNotification("dashboard"));

        } else if (id == R.id.txt_match) {
            socialOffMatchOnNotiOff();
            ShowMainMenuWithFriends();
            ReplaceFrag(new FragCardSwipe());
        } else if (id == R.id.txt_chat) {
            startActivity(new Intent(DashboardActivity.this, ChatBaseActivity.class));
        } else if (id == R.id.ll_love_bottom) {
//            if (OPTION_CLICK == 0) {
//                return;
//            }
            nsv.scrollTo(0, 0);
            OPTION_CLICK = 0;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.VISIBLE);
            ll_friends.setVisibility(View.VISIBLE);
            iv_love.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_love.setImageResource(R.drawable.love_high);
            iv_coupons.setBackground(null);
            iv_coupons.setImageResource(R.drawable.badge);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);

            checkFirstFrag();
            Fragment frag;
            if (!checkFirstFrag()) {
                frag = new FragLanding();
                pref.saveBoolKeyVal(SessionPref.LoginUserSuggestionShown, true);
            } else {
                frag = new FragSocialFeed();
            }

            ShowMainMenuWithFriends();

            ll_mainMenu2.setVisibility(View.VISIBLE);

            socialOnMatchOffNotiOff();

            ReplaceFrag(frag);
            callAPIFriends();
        } else if (id == R.id.ll_coupon) {
            if (OPTION_CLICK == 1) {
                return;
            }

            nsv.scrollTo(0, 0);
            OPTION_CLICK = 1;
            iv_love.setImageResource(R.drawable.love);
            iv_love.setBackground(null);
            iv_coupons.setImageResource(R.drawable.badge_sel);
            iv_coupons.setBackground(getDrawable(R.drawable.rectangle_back));
            ll_option_love.setVisibility(View.GONE);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            ll_friends.setVisibility(View.GONE);
            if (pref.getBoolVal(SessionPref.isBusiness)) {
                ReplaceFrag(new FragCouponParentBusiness());
            } else {
                ReplaceFrag(new FragCouponParent());
            }

            //   ReplaceFrag(new FragLocationTracing());

        } else if (id == R.id.ll_profile_support) {
            if (OPTION_CLICK == 2) {
                return;
            }
            nsv.scrollTo(0, 0);
            OPTION_CLICK = 2;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.GONE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            ll_mainMenu2.setVisibility(View.GONE);
            iv_coupons.setImageResource(R.drawable.badge);
            iv_coupons.setBackground(null);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            iv_profile_sett.setBackground(getDrawable(R.drawable.rectangle_back));
            iv_profile_sett.setImageResource(R.drawable.tech_support_red);

            ReplaceFrag(new FragSettingsParent());

        } else if (id == R.id.ll_profile_insta) {
            if (OPTION_CLICK == 3) {
                return;
            }
            nsv.scrollTo(0, 0);
            OPTION_CLICK = 3;
            iv_play_date_logo.setVisibility(View.VISIBLE);
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            ll_option_love.setVisibility(View.GONE);
            ll_friends.setVisibility(View.GONE);
            iv_coupons.setBackground(null);
            iv_coupons.setImageResource(R.drawable.badge);
            iv_love.setBackground(null);
            iv_love.setImageResource(R.drawable.love);
            ll_mainMenu2.setVisibility(View.GONE);
            iv_profile_sett.setBackground(null);
            iv_profile_sett.setImageResource(R.drawable.tech_support);
            profile = new FragInstaLikeProfile(true);
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
        } else if (id == R.id.iv_cart) {
            ll_profile_drop_menu.setVisibility(View.GONE);
            iv_plus.setVisibility(View.VISIBLE);
            iv_play_date_logo.setVisibility(View.VISIBLE);

            new FragStore(this).show();

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
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (OPTION_CLICK == 3) {
                iv_plus.setVisibility(View.VISIBLE);
            }
            if (requestCode == 857) {

                if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                    CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

//                    if (scanResult.isExpiryValid()) {
//                        scanResult.expiryMonth;
//                        scanResult.expiryYear;

//                    }
//                    if (scanResult.cvv != null) {


//                    }
                    //scanResult.getCardType();


//                    if (CurrentFrag.getClass().getSimpleName().equals("NewPaymentMethod")) {
//                        NewPaymentMethod cls = (NewPaymentMethod) CurrentFrag;
//                        cls.setData(scanResult.cardholderName, scanResult.getFormattedCardNumber(), scanResult.cvv, scanResult.expiryMonth, scanResult.expiryYear);
//                    }


                } else {
                    Toast.makeText(this, "Scan was cancelled", Toast.LENGTH_SHORT).show();
                }
                return;
            }


            if (resultCode == 104) {
                //refresh
                if (null != CurrentFrag) {
                    if (CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed")) {
                        FragSocialFeed frag = (FragSocialFeed) CurrentFrag;
                        frag.LoadPageAgain();
                    }
                }
                return;
            }


            if (resultCode != RESULT_OK) {
                return;
            }
            if (requestCode == PICK_PHOTO_FOR_AVATAR || requestCode == TAKE_PHOTO_CODE) {

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
                    Intent mIntent = new Intent(DashboardActivity.this, MainActivity.class);
                    startActivity(mIntent);
                }
            } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO || requestCode == CAMERA) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    String selectedVideoPath = getPath(contentURI);
                    Intent mIntent = new Intent(DashboardActivity.this, PostMediaActivity.class);
                    mIntent.putExtra("videoPath", selectedVideoPath);
                    startActivity(mIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onBackPressed() {

        if (CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed")) {
            if (isAtTop) {
                super.onBackPressed();
            } else {
                isAtTop = true;
                nsv.smoothScrollTo(0, 0);
            }

        } else if (CurrentFrag.getClass().getSimpleName().equals("FragNotification")) {
            ll_love_bottom.performClick();
        } else if (CurrentFrag.getClass().getSimpleName().equals("FragInstaLikeProfile")) {
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
        ShowMainMenuWithFriends();
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

    CallAPI apiCall;

    void callNotification() {
        if (null != apiCall) {
            apiCall.callGetNotificationAPI(this);
        } else {
            new CallAPI().callGetNotificationAPI(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshFlag == 1) {
            refreshFlag = 0;
            ll_love_bottom.performClick();
        }

        callNotification();
        if (pref.getBoolVal(SessionPref.isBusiness)) {
            txt_match.setVisibility(View.GONE);
            iv_date.setImageResource(R.drawable.ic_white_add_circle);
        } else {
            if (pref.getStringVal(SessionPref.LoginUserrelationship).equals("Single")) {
                txt_match.setVisibility(View.VISIBLE);
            } else {
                txt_match.setVisibility(View.GONE);
            }
        }
        try {
            boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (permissionGranted) {
                startService(new Intent(getApplicationContext(), LocationService.class));
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNotiCount(int count) {
       /* if (count > 0) {
            txt_count.setVisibility(View.VISIBLE);
            txt_count.setText("" + count);
        } else {
            txt_count.setVisibility(View.GONE);
            txt_count.setText("");
        }*/
    }


    public void redirectToLeaderBoard() {
        ReplaceFragWithStack(new FragGameLeaderBoard());
    }

   /*
   @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String data = intent.getStringExtra("data");
            if (data != null) {
                Fragment fragment = new FragNotification("dashboard");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        }
    }
    */
}

