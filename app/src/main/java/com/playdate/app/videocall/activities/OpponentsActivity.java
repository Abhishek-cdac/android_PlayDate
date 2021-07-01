//package com.playdate.app.videocall.activities;
//
//import android.app.ActivityManager;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.TextView;
//
//import com.playdate.app.R;
//import com.playdate.app.videocall.services.CallService;
//import com.playdate.app.videocall.utils.CollectionsUtils;
//import com.playdate.app.videocall.utils.Consts;
//import com.playdate.app.videocall.utils.PermissionsChecker;
//import com.playdate.app.videocall.utils.SharedPrefsHelper;
//import com.playdate.app.videocall.utils.WebRtcSessionManager;
//import com.quickblox.core.QBEntityCallback;
//import com.quickblox.core.exception.QBResponseException;
//import com.quickblox.core.request.GenericQueryRule;
//import com.quickblox.core.request.QBPagedRequestBuilder;
//import com.quickblox.users.QBUsers;
//import com.quickblox.users.model.QBUser;
//import com.quickblox.videochat.webrtc.QBRTCClient;
//import com.quickblox.videochat.webrtc.QBRTCSession;
//import com.quickblox.videochat.webrtc.QBRTCTypes;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * QuickBlox team
// */
//public class OpponentsActivity extends BaseActivity {
//    private static final String TAG = OpponentsActivity.class.getSimpleName();
//
//    private static final int PER_PAGE_SIZE_100 = 100;
//    private static final String ORDER_RULE = "order";
//    private static final String ORDER_DESC_UPDATED = "desc date updated_at";
//    private PermissionsChecker checker;
//    String OppID = "";
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_users_vc);
//        TextView txt_calling = findViewById(R.id.txt_calling);
//        txt_calling.setOnClickListener(v -> startCall(true, sharedPrefsHelper.getQbUser()));
//        checker = new PermissionsChecker(getApplicationContext());
//        OppID = getIntent().getStringExtra("oppo_id");
//        loadUsers();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        boolean isIncomingCall = SharedPrefsHelper.getInstance().get(Consts.EXTRA_IS_INCOMING_CALL, false);
//        if (isCallServiceRunning(CallService.class)) {
//            Log.d(TAG, "CallService is running now");
//            CallActivity.start(this, isIncomingCall);
//        }
//
//    }
//
//    private boolean isCallServiceRunning(Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    private void startPermissionsActivity(boolean checkOnlyAudio) {
//        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
//    }
//
//    private void loadUsers() {
//        ArrayList<GenericQueryRule> rules = new ArrayList<>();
//        rules.add(new GenericQueryRule(ORDER_RULE, ORDER_DESC_UPDATED));
//
//        QBPagedRequestBuilder qbPagedRequestBuilder = new QBPagedRequestBuilder();
//        qbPagedRequestBuilder.setRules(rules);
//        qbPagedRequestBuilder.setPerPage(PER_PAGE_SIZE_100);
//        qbPagedRequestBuilder.setPage(0);
//
//        QBUsers.getUserByLogin(OppID).performAsync(new QBEntityCallback<QBUser>() {
//            @Override
//            public void onSuccess(QBUser qbUsers, Bundle bundle) {
//
//
//                if (checker.lacksPermissions(Consts.PERMISSIONS)) {
//                    startPermissionsActivity(false);
//                }
//
//            }
//
//            @Override
//            public void onError(QBResponseException e) {
//            }
//        });
//    }
//
//
//    private void startCall(boolean isVideoCall, QBUser qbUsers) {
//        Log.d(TAG, "Starting Call");
//        List<QBUser> selectedUsers = new ArrayList<>();
//        selectedUsers.add(qbUsers);
//        ArrayList<Integer> opponentsList = CollectionsUtils.getIdsSelectedOpponents(selectedUsers);
//        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
//                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
//                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
//        Log.d(TAG, "conferenceType = " + conferenceType);
//
//        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());
//        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);
//        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);
//
//        // Make Users FullName Strings and ID's list for iOS VOIP push
//        String newSessionID = newQbRtcSession.getSessionID();
//        ArrayList<String> opponentsIDsList = new ArrayList<>();
//        ArrayList<String> opponentsNamesList = new ArrayList<>();
//        List<QBUser> usersInCall = selectedUsers;
//
//        // the Caller in exactly first position is needed regarding to iOS 13 functionality
//        usersInCall.add(0, qbUsers);
//
//        for (QBUser user : usersInCall) {
//            String userId = user.getId().toString();
//            String userName = "";
//            if (TextUtils.isEmpty(user.getFullName())) {
//                userName = user.getLogin();
//            } else {
//                userName = user.getFullName();
//            }
//
//            opponentsIDsList.add(userId);
//            opponentsNamesList.add(userName);
//        }
//
//        String opponentsIDsString = TextUtils.join(",", opponentsIDsList);
//        String opponentNamesString = TextUtils.join(",", opponentsNamesList);
//
//        Log.d(TAG, "New Session with ID: " + newSessionID + "\n Users in Call: " + "\n" + opponentsIDsString + "\n" + opponentNamesString);
//        CallActivity.start(this, false);
//    }
//
//}