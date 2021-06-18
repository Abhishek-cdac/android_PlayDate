package com.playdate.app.util.videocall;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.connectycube.chat.ConnectycubeChatService;
import com.connectycube.chat.Signaling;
import com.connectycube.chat.WebRTCSignaling;
import com.connectycube.chat.listeners.VideoChatSignalingManagerListener;
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

import org.webrtc.EglBase;
import org.webrtc.RendererCommon;

import java.util.HashMap;
import java.util.Map;

public class VideoCallActivity extends AppCompatActivity implements RTCClientSessionCallbacks, RTCClientVideoTracksCallbacks {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_call);

        RTCSurfaceView localView = findViewById(R.id.localView);
        RTCSurfaceView opponentView = findViewById(R.id.opponentView);
        EglBase eglContext = RTCClient.getInstance(this).getEglContext();
        localView.init(eglContext.getEglBaseContext(), null);
        //localView.release(); // releases all related GL resources
        //localView.setScalingType(scalingType); //Set how the video will fill the allowed layout area
        // localView.setMirror(mirror); //Set if the video stream should be mirrored or not.
        //localView.requestLayout(); // Request to invalidate view when something has changed


        ConnectycubeChatService.getInstance().getVideoChatWebRTCSignalingManager().addSignalingManagerListener(new VideoChatSignalingManagerListener() {
            @Override
            public void signalingCreated(Signaling signaling, boolean createdLocally) {
                if (!createdLocally) {
                    RTCClient.getInstance(VideoCallActivity.this).addSignaling((WebRTCSignaling) signaling);
                }
            }
        });

        RTCClient.getInstance(this).addSessionCallbacksListener(this);
//        RTCClient.getInstance(this).removeSessionsCallbacksListener(this);

        RTCClient.getInstance(this).prepareToProcessCalls();


    }



//    private void fillVideoView(int userId, RTCSurfaceView videoView, RTCVideoTrack videoTrack) {
//        videoTrack.addRenderer(new VideoRenderer(videoView));
//        updateVideoView(videoView, !remoteRenderer, RendererCommon.ScalingType.SCALE_ASPECT_FILL);
//    }

    private void updateVideoView(RTCSurfaceView surfaceView, boolean mirror, RendererCommon.ScalingType scalingType){
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
        Map<String,String> userInfo = session.getUserInfo();
        session.acceptCall(userInfo);
        session.addVideoTrackCallbacksListener(VideoCallActivity.this);
      //  session.removeVideoTrackCallbacksListener(this);
//        session.addAudioTrackCallbacksListener(this);
        //rtcSession.removeAudioTrackCallbacksListener(this);

    }

    @Override
    public void onUserNoActions(RTCSession rtcSession, Integer integer) {

    }

    @Override
    public void onSessionStartClose(RTCSession rtcSession) {

    }

    @Override
    public void onUserNotAnswer(RTCSession rtcSession, Integer integer) {

    }

    @Override
    public void onCallRejectByUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {

    }

    @Override
    public void onCallAcceptByUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {
        Toast.makeText(this, "Call Accepted by Opponent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveHangUpFromUser(RTCSession rtcSession, Integer integer, Map<String, String> map) {

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
