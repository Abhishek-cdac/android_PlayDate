package com.playdate.app.util.videocall;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.connectycube.auth.session.ConnectycubeSettings;
import com.connectycube.chat.ConnectycubeChatService;
import com.connectycube.chat.Signaling;
import com.connectycube.chat.WebRTCSignaling;
import com.connectycube.chat.listeners.VideoChatSignalingManagerListener;
import com.connectycube.core.LogLevel;
import com.connectycube.videochat.BaseSession;
import com.connectycube.videochat.RTCClient;
import com.connectycube.videochat.RTCSession;
import com.connectycube.videochat.callbacks.RTCClientSessionCallbacks;
import com.connectycube.videochat.callbacks.RTCClientVideoTracksCallback;
import com.connectycube.videochat.callbacks.RTCClientVideoTracksCallbacks;
import com.connectycube.videochat.callbacks.RTCSessionConnectionCallbacks;
import com.connectycube.videochat.view.RTCSurfaceView;
import com.connectycube.videochat.view.RTCVideoTrack;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.util.session.SessionPref;

import org.webrtc.EglBase;
import org.webrtc.RendererCommon;

import java.util.HashMap;
import java.util.Map;

public class VideoCallActivity extends AppCompatActivity implements RTCClientSessionCallbacks, RTCClientVideoTracksCallbacks {
    SessionPref pref;
    String userId;
    RTCVideoTrack videoTrack;
    RTCSurfaceView localView;
    RTCSurfaceView opponentView;

    /* connetycube playdate credentials */
    static final String APP_ID = "5073";
    static final String AUTH_KEY = "wrvU83gweWL7Q3G";
    static final String AUTH_SECRET = "WhS3-apvmGqagVP";
    static final String ACCOUNT_KEY = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_call);
        pref = SessionPref.getInstance(this);
        userId = pref.getStringVal(SessionPref.LoginUserID);

        localView = findViewById(R.id.localView);
        opponentView = findViewById(R.id.opponentView);

        ConnectycubeSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        ConnectycubeSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        ConnectycubeSettings.getInstance().setLogLevel(LogLevel.NOTHING);


        ConnectycubeChatService.getInstance().getVideoChatWebRTCSignalingManager().addSignalingManagerListener(new VideoChatSignalingManagerListener() {
            @Override
            public void signalingCreated(Signaling signaling, boolean createdLocally) {
                if (!createdLocally) {
                    RTCClient.getInstance(VideoCallActivity.this).addSignaling((WebRTCSignaling) signaling);
                }
            }
        });

        RTCClient.getInstance(this).addSessionCallbacksListener(this);
        //  RTCClient.getInstance(this).removeSessionsCallbacksListener(this);


        EglBase eglContext = RTCClient.getInstance(this).getEglContext();
        localView.init(eglContext.getEglBaseContext(), null);
        localView.release(); // releases all related GL resources
//      localView.setScalingType(scalingType); //Set how the video will fill the allowed layout area
//      localView.setMirror(mirror); //Set if the video stream should be mirrored or not.
        localView.requestLayout(); // Request to invalidate view when something has changed

        fillVideoView(Integer.parseInt(userId), localView, videoTrack);

        RTCClient.getInstance(this).prepareToProcessCalls();


    }


    private void fillVideoView(int userId, RTCSurfaceView videoView, RTCVideoTrack videoTrack) {
//        videoTrack.addRenderer(new VideoRenderer(videoView));
//        updateVideoView(videoView, !remoteRenderer, RendererCommon.ScalingType.SCALE_ASPECT_FILL);
    }

    private void updateVideoView(RTCSurfaceView surfaceView, boolean mirror, RendererCommon.ScalingType scalingType) {
        surfaceView.setScalingType(scalingType);
        surfaceView.setMirror(mirror);
        surfaceView.requestLayout();
    }

    public void addSessionCallbacksListener(RTCSessionConnectionCallbacks callback) {

    }

    public void addVideoTrackCallbacksListener(RTCClientVideoTracksCallback<RTCSession> callback) {

    }

    public void addSessionCallbacksListener(RTCClientSessionCallbacks callback) {

    }

    @Override
    public void onReceiveNewSession(RTCSession session) {
        Map<String, String> userInfo = session.getUserInfo();
        session.acceptCall(userInfo);
        session.addVideoTrackCallbacksListener(VideoCallActivity.this);
        //  session.removeVideoTrackCallbacksListener(this);
        //  session.addAudioTrackCallbacksListener(this);
        //  rtcSession.removeAudioTrackCallbacksListener(this);

    }

    @Override
    public void onUserNoActions(RTCSession rtcSession, Integer integer) {

    }

    @Override
    public void onSessionStartClose(RTCSession rtcSession) {

    }

    @Override
    public void onUserNotAnswer(RTCSession rtcSession, Integer integer) {
        Toast.makeText(this, "Call not answered by Opponent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCallRejectByUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {
        Toast.makeText(this, "Call Accepted by Opponent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCallAcceptByUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {
        Toast.makeText(this, "Call Rejected by Opponent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveHangUpFromUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {
        Toast.makeText(this, "Call HangUp by Opponent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSessionClosed(RTCSession rtcSession) {

    }

    @Override
    public void onLocalVideoTrackReceive(BaseSession baseSession, RTCVideoTrack rtcVideoTrack) {

    }

    @Override
    public void onRemoteVideoTrackReceive(BaseSession baseSession, RTCVideoTrack rtcVideoTrack, Integer integer) {

    }
}
