package com.playdate.app.videocall.fragments;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.playdate.app.R;
import com.playdate.app.videocall.activities.CallActivity;
import com.playdate.app.videocall.utils.CollectionsUtils;
import com.playdate.app.videocall.utils.SharedPrefsHelper;
import com.playdate.app.videocall.utils.UiUtils;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.AppRTCAudioManager;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;


public class AudioConversationFragment extends BaseConversationFragment implements CallActivity.OnChangeAudioDevice {
    private static final String TAG = AudioConversationFragment.class.getSimpleName();

    public static final String SPEAKER_ENABLED = "is_speaker_enabled";

    private ToggleButton audioSwitchToggleButton;
    private TextView alsoOnCallText;
    private TextView firstOpponentNameTextView;
    private TextView otherOpponentsTextView;

    @Override
    public void onStart() {
        super.onStart();
        if (conversationFragmentCallback != null) {
            conversationFragmentCallback.addOnChangeAudioDeviceListener(this);
        }
    }

    @Override
    protected void configureOutgoingScreen() {
        outgoingOpponentsRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        allOpponentsTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_outgoing_opponents_names_audio_call));
        ringingTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_call_type));
    }

    @Override
    protected void configureToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color.toolbar_title_color));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(getActivity(), R.color.toolbar_subtitle_color));
    }

    @Override
    protected void configureActionBar() {
        actionBar.setSubtitle(String.format(getString(R.string.subtitle_text_logged_in_as), currentUser.getFullName()));
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        timerCallText = view.findViewById(R.id.timer_call);

        ImageView firstOpponentAvatarImageView = view.findViewById(R.id.image_caller_avatar);
//        try {
//            firstOpponentAvatarImageView.setBackgroundDrawable(UiUtils.getColorCircleDrawable(opponents.get(0).getId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        alsoOnCallText = view.findViewById(R.id.text_also_on_call);
        setVisibilityAlsoOnCallTextView();

        firstOpponentNameTextView = view.findViewById(R.id.text_caller_name);
        firstOpponentNameTextView.setText(opponents.get(0).getFullName());

        otherOpponentsTextView = view.findViewById(R.id.text_other_inc_users);
        otherOpponentsTextView.setText(getOtherOpponentsNames());

        audioSwitchToggleButton = view.findViewById(R.id.toggle_speaker);
        audioSwitchToggleButton.setVisibility(View.VISIBLE);
        audioSwitchToggleButton.setChecked(SharedPrefsHelper.getInstance().get(SPEAKER_ENABLED, true));

        actionButtonsEnabled(false);

        if (conversationFragmentCallback != null && conversationFragmentCallback.isCallState()) {
            onCallStarted();
        }
    }

    private void setVisibilityAlsoOnCallTextView() {
        if (opponents.size() < 2) {
            alsoOnCallText.setVisibility(View.INVISIBLE);
        }
    }

    private String getOtherOpponentsNames() {
        ArrayList<QBUser> otherOpponents = new ArrayList<>();
        otherOpponents.addAll(opponents);
        otherOpponents.remove(0);
        return CollectionsUtils.makeStringFromUsersFullNames(otherOpponents);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (conversationFragmentCallback != null) {
            conversationFragmentCallback.removeOnChangeAudioDeviceListener(this);
        }
    }

    @Override
    protected void initButtonsListener() {
        super.initButtonsListener();
        audioSwitchToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefsHelper.getInstance().save(SPEAKER_ENABLED, isChecked);
                if (conversationFragmentCallback != null) {
                    conversationFragmentCallback.onSwitchAudio();
                }
            }
        });
    }

    @Override
    protected void actionButtonsEnabled(boolean enabled) {
        super.actionButtonsEnabled(enabled);
        audioSwitchToggleButton.setActivated(enabled);
    }

    @Override
    int getFragmentLayout() {
        return R.layout.fragment_audio_conversation;
    }

    @Override
    public void onOpponentsListUpdated(ArrayList<QBUser> newUsers) {
        super.onOpponentsListUpdated(newUsers);
        firstOpponentNameTextView.setText(opponents.get(0).getFullName());
        otherOpponentsTextView.setText(getOtherOpponentsNames());
    }

    @Override
    public void onCallTimeUpdate(String time) {
        timerCallText.setText(time);
    }

    @Override
    public void audioDeviceChanged(AppRTCAudioManager.AudioDevice newAudioDevice) {
        audioSwitchToggleButton.setChecked(newAudioDevice != AppRTCAudioManager.AudioDevice.SPEAKER_PHONE);
    }
}