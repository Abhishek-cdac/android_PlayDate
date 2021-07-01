package com.playdate.app.videocall.activities;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.videocall.adapters.UsersAdapter;
import com.playdate.app.videocall.db.QbUsersDbManager;
import com.playdate.app.videocall.services.CallService;
import com.playdate.app.videocall.services.LoginService;
import com.playdate.app.videocall.utils.CollectionsUtils;
import com.playdate.app.videocall.utils.Consts;
import com.playdate.app.videocall.utils.PermissionsChecker;
import com.playdate.app.videocall.utils.SharedPrefsHelper;
import com.playdate.app.videocall.utils.ToastUtils;
import com.playdate.app.videocall.utils.UsersUtils;
import com.playdate.app.videocall.utils.WebRtcSessionManager;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.GenericQueryRule;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * QuickBlox team
 */
public class OpponentsActivity extends BaseActivity {
    private static final String TAG = OpponentsActivity.class.getSimpleName();

    private static final int PER_PAGE_SIZE_100 = 100;
    private static final String ORDER_RULE = "order";
    private static final String ORDER_DESC_UPDATED = "desc date updated_at";
    public static final String TOTAL_PAGES_BUNDLE_PARAM = "total_pages";

    private RecyclerView usersRecyclerview;
    private QBUser currentUser;
    private UsersAdapter usersAdapter;
    private int currentPage = 0;
    private Boolean isLoading = false;
    private Boolean hasNextPage = true;

    private QbUsersDbManager dbManager;
    private PermissionsChecker checker;

    public static void start(Context context) {
        Intent intent = new Intent(context, OpponentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_users_vc);
        currentUser = SharedPrefsHelper.getInstance().getQbUser();
        dbManager = QbUsersDbManager.getInstance(getApplicationContext());
        checker = new PermissionsChecker(getApplicationContext());

        initDefaultActionBar();
        initUi();
        startLoginService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isIncomingCall = SharedPrefsHelper.getInstance().get(Consts.EXTRA_IS_INCOMING_CALL, false);
        if (isCallServiceRunning(CallService.class)) {
            Log.d(TAG, "CallService is running now");
            CallActivity.start(this, isIncomingCall);
        }
        clearAppNotifications();
        loadUsers();
    }

    private boolean isCallServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void clearAppNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    private void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
    }

    private void loadUsers() {
        isLoading = true;
        showProgressDialog(R.string.dlg_loading_opponents);
        currentPage += 1;
        ArrayList<GenericQueryRule> rules = new ArrayList<>();
        rules.add(new GenericQueryRule(ORDER_RULE, ORDER_DESC_UPDATED));

        QBPagedRequestBuilder qbPagedRequestBuilder = new QBPagedRequestBuilder();
        qbPagedRequestBuilder.setRules(rules);
        qbPagedRequestBuilder.setPerPage(PER_PAGE_SIZE_100);
        qbPagedRequestBuilder.setPage(currentPage);

        QBUsers.getUserByLogin("60c2fb24021dde678088c4f2").performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUsers, Bundle bundle) {
                Log.d(TAG, "Successfully loaded users");
                dbManager.saveUser(qbUsers);

//                int totalPagesFromParams = (int) bundle.get(TOTAL_PAGES_BUNDLE_PARAM);
//                if (currentPage >= totalPagesFromParams) {
                hasNextPage = false;
//                }

//                if (currentPage == 1) {
                initUsersList();
//                } else {
//                    usersAdapter.addUsers(qbUsers);
//                }
                hideProgressDialog();
                isLoading = false;
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Error load users" + e.getMessage());
                hideProgressDialog();
                isLoading = false;
                currentPage -= 1;
                showErrorSnackbar(R.string.loading_users_error, e, v -> loadUsers());
            }
        });
    }

    Button btn;

    private void initUi() {
        usersRecyclerview = findViewById(R.id.list_select_users);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIsLoggedInChat()) {
                    startCall(true);
                }
                if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                    startPermissionsActivity(false);
                }
            }
        });
    }

    private void initUsersList() {
        List<QBUser> currentOpponentsList = dbManager.getAllUsers();
        Log.d(TAG, "initUsersList currentOpponentsList= " + currentOpponentsList);
        currentOpponentsList.remove(sharedPrefsHelper.getQbUser());
        if (usersAdapter == null) {
            usersAdapter = new UsersAdapter(this, currentOpponentsList);
            usersAdapter.setSelectedItemsCountsChangedListener(new UsersAdapter.SelectedItemsCountChangedListener() {
                @Override
                public void onCountSelectedItemsChanged(Integer count) {
                    updateActionBar(count);
                }
            });

            usersRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            usersRecyclerview.setAdapter(usersAdapter);
//            usersRecyclerview.addOnScrollListener(new ScrollListener((LinearLayoutManager) usersRecyclerview.getLayoutManager()));
        } else {
            usersAdapter.updateUsersList(currentOpponentsList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (usersAdapter != null && !usersAdapter.getSelectedUsers().isEmpty()) {
            getMenuInflater().inflate(R.menu.activity_selected_opponents, menu);
        } else {
            getMenuInflater().inflate(R.menu.activity_opponents, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.update_opponents_list) {
            currentPage = 0;
            loadUsers();
            return true;
        } else if (id == R.id.settings) {
            SettingsActivity.start(this);
            return true;
        } else if (id == R.id.log_out) {//                unsubscribeFromPushesAndLogout();
            return true;
        } else if (id == R.id.start_video_call) {
            if (checkIsLoggedInChat()) {
                startCall(true);
            }
            if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                startPermissionsActivity(false);
            }
            return true;
        } else if (id == R.id.start_audio_call) {
            if (checkIsLoggedInChat()) {
                startCall(false);
            }
            if (checker.lacksPermissions(Consts.PERMISSIONS[1])) {
                startPermissionsActivity(true);
            }
            return true;
        } else if (id == R.id.menu_appinfo) {
//            AppInfoActivity.start(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkIsLoggedInChat() {
        if (!QBChatService.getInstance().isLoggedIn()) {
            startLoginService();
            ToastUtils.shortToast(R.string.dlg_relogin_wait);
            return false;
        }
        return true;
    }

    private void startLoginService() {
        if (sharedPrefsHelper.hasQbUser()) {
            QBUser qbUser = sharedPrefsHelper.getQbUser();
            LoginService.start(this, qbUser);
        }
    }

    private void startCall(boolean isVideoCall) {
        Log.d(TAG, "Starting Call");
        if (usersAdapter.getSelectedUsers().size() > Consts.MAX_OPPONENTS_COUNT) {
            ToastUtils.longToast(String.format(getString(R.string.error_max_opponents_count),
                    Consts.MAX_OPPONENTS_COUNT));
            return;
        }

        ArrayList<Integer> opponentsList = CollectionsUtils.getIdsSelectedOpponents(usersAdapter.getSelectedUsers());
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
        Log.d(TAG, "conferenceType = " + conferenceType);

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());
        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);
        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);

        // Make Users FullName Strings and ID's list for iOS VOIP push
        String newSessionID = newQbRtcSession.getSessionID();
        ArrayList<String> opponentsIDsList = new ArrayList<>();
        ArrayList<String> opponentsNamesList = new ArrayList<>();
        List<QBUser> usersInCall = usersAdapter.getSelectedUsers();

        // the Caller in exactly first position is needed regarding to iOS 13 functionality
        usersInCall.add(0, currentUser);

        for (QBUser user : usersInCall) {
            String userId = user.getId().toString();
            String userName = "";
            if (TextUtils.isEmpty(user.getFullName())) {
                userName = user.getLogin();
            } else {
                userName = user.getFullName();
            }

            opponentsIDsList.add(userId);
            opponentsNamesList.add(userName);
        }

        String opponentsIDsString = TextUtils.join(",", opponentsIDsList);
        String opponentNamesString = TextUtils.join(",", opponentsNamesList);

        Log.d(TAG, "New Session with ID: " + newSessionID + "\n Users in Call: " + "\n" + opponentsIDsString + "\n" + opponentNamesString);
//        PushNotificationSender.sendPushMessage(opponentsList, currentUser.getFullName(), newSessionID, opponentsIDsString, opponentNamesString, isVideoCall);
        CallActivity.start(this, false);
    }

    private void updateActionBar(int countSelectedUsers) {
        if (countSelectedUsers < 1) {
            initDefaultActionBar();
        } else {
            removeActionbarSubTitle();
            setActionBarTitle(String.format(getString(
                    countSelectedUsers > 1
                            ? R.string.tile_many_users_selected
                            : R.string.title_one_user_selected),
                    countSelectedUsers));
        }

        invalidateOptionsMenu();
    }

    private void logOut() {
        Log.d(TAG, "Removing User data, and Logout");
        LoginService.logout(this);
        requestExecutor.signOut(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                UsersUtils.removeUserData(getApplicationContext());
                startLoginActivity();
            }

            @Override
            public void onError(QBResponseException e) {
                showErrorSnackbar(R.string.error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut();
                    }
                });
            }
        });
    }

//    private void unsubscribeFromPushesAndLogout() {
//        if (QBPushManager.getInstance().isSubscribedToPushes()) {
//            QBPushManager.getInstance().addListener(new QBPushSubscribeListenerImpl() {
//                @Override
//                public void onSubscriptionDeleted(boolean success) {
//                    Log.d(TAG, "Subscription Deleted");
//                    QBPushManager.getInstance().removeListener(this);
//                    logOut();
//                }
//            });
//            SubscribeService.unSubscribeFromPushes(OpponentsActivity.this);
//        } else {
//            logOut();
//        }
//    }

    private void startLoginActivity() {
        LoginActivity.start(this);
        finish();
    }

//    private class ScrollListener extends RecyclerView.OnScrollListener {
//        LinearLayoutManager layoutManager;
//
//        ScrollListener(LinearLayoutManager layoutManager) {
//            this.layoutManager = layoutManager;
//        }
//
//        @Override
//        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//            if (!isLoading && hasNextPage && dy > 0) {
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//                boolean needToLoadMore = ((visibleItemCount * 2) + firstVisibleItem) >= totalItemCount;
//                if (needToLoadMore) {
//                    loadUsers();
//                }
//            }
//        }
//    }
//
//    private class QBPushSubscribeListenerImpl implements QBPushManager.QBSubscribeListener {
//        @Override
//        public void onSubscriptionCreated() {
//
//        }
//
//        @Override
//        public void onSubscriptionError(Exception e, int i) {
//
//        }
//
//        @Override
//        public void onSubscriptionDeleted(boolean b) {
//
//        }
//    }
}