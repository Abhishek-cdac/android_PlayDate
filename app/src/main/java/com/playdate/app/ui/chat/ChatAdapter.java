package com.playdate.app.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.playdate.app.R;
import com.playdate.app.model.UserInfo;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.photoview.PhotoViewActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private boolean isImageFitToScreen = false;
    public static int OTHER = 0;
    public static int ME = 2;
    public static int OPPONENT = 0;
    //    private ArrayList<ChatExample> chatExampleList;
    private ArrayList<ChatMessage> lst_chat;
    //    private String urls_image;
    private MediaPlayer mediaPlayer;
    private GoogleMap googleMap;
    private Picasso picasso;

    //    private String from;
//    private String to;
//    private final static String myId = "jid_1111";
//    private final static String polling = "polling";
    //    FragChatMain ref;
    SessionPref pref;
    String LoginUserID;

    public ChatAdapter(ArrayList<ChatMessage> chatmsgList, Context mContext) {
        this.lst_chat = chatmsgList;
//        this.ref = ref;
        picasso = Picasso.get();
        pref = SessionPref.getInstance(mContext);
        LoginUserID = pref.getStringVal(SessionPref.LoginUserID);
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        if (lst_chat == null)
            return 0;
        return lst_chat.size();
    }

    @Override
    public int getItemViewType(int position) {
//        from = chatmsgList.get(position).getFrom();
//        to = chatmsgList.get(position).getTo();

        if (LoginUserID.equals(lst_chat.get(position).getUserID())) {
            Log.d("***ddd***", "ME");
//            if (myId.equals(from)) {
            ME = 0;
//            } else if (myId.equals(to)) {
//                ME = 1;
//            } else if (from.equals(polling)) {
//                ME = 2;
//            }
            return 0;
        } else {
            return 1;
        }

//        switch (ME) {
//            case 0:
//                return 0;
//            case 1:
//                return 1;
//            case 2:
//                return 2;
//        }
//        return OTHER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
//        mContext = parent.getContext();

        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_me, parent, false);
            viewHolder = new ViewHolderMe(view);

        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_oponent, parent, false);
            viewHolder = new ViewHolderOponent(view);


        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_other, parent, false);
            viewHolder = new ViewHolderOther(view);

        }

        return Objects.requireNonNull(viewHolder);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (lst_chat.get(position).getType() == null)
//            lst_chat.get(position).setType("text");
        if (holder.getItemViewType() == 0) {
            /// ME
            ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
            Picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderMe.profile_image_me);

            if (lst_chat.get(position).getType().equals("text") || lst_chat.get(position).getType().equals("emoji")) {
                viewHolderMe.tv_msg.setVisibility(View.VISIBLE);
                viewHolderMe.chat_image.setVisibility(View.GONE);
                viewHolderMe.iv_thumb.setVisibility(View.GONE);
                viewHolderMe.img_playback.setVisibility(View.GONE);
                viewHolderMe.card_video.setVisibility(View.GONE);
                viewHolderMe.rl_audio.setVisibility(View.GONE);
                viewHolderMe.rl_maps.setVisibility(View.GONE);
                viewHolderMe.mv_location.setVisibility(View.GONE);


                //temp

                try {
                    String msgTemp = lst_chat.get(position).getMessage();
                    int p = Integer.parseInt(msgTemp);
                    viewHolderMe.tv_msg.setText(new String(Character.toChars(p)));

                    viewHolderMe.tv_msg.setBackground(null);
                    viewHolderMe.tv_msg.setBackgroundColor(Color.WHITE);
                    viewHolderMe.tv_msg.setTextSize(mContext.getResources().getDimension(R.dimen._12sdp));
                } catch (Exception e) {
                    e.printStackTrace();
                    viewHolderMe.tv_msg.setText(lst_chat.get(position).getMessage());
                }
                //temp
            } else {


                switch (lst_chat.get(position).getMediaInfo().get(0).getMediaType().toLowerCase()) {


                    case "image":
                        viewHolderMe.tv_msg.setVisibility(View.GONE);
                        viewHolderMe.chat_image.setVisibility(View.VISIBLE);
                        viewHolderMe.iv_thumb.setVisibility(View.GONE);
                        viewHolderMe.rl_audio.setVisibility(View.GONE);
                        viewHolderMe.img_playback.setVisibility(View.GONE);
                        viewHolderMe.card_video.setVisibility(View.GONE);
                        viewHolderMe.rl_maps.setVisibility(View.GONE);
                        viewHolderMe.mv_location.setVisibility(View.GONE);
                        picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()).into(viewHolderMe.chat_image);
                        //image
                        break;
                    case "video":
                        viewHolderMe.tv_msg.setVisibility(View.GONE);
                        viewHolderMe.chat_image.setVisibility(View.GONE);
                        viewHolderMe.card_video.setVisibility(View.VISIBLE);
                        viewHolderMe.img_playback.setVisibility(View.VISIBLE);
                        viewHolderMe.iv_thumb.setVisibility(View.VISIBLE);
                        viewHolderMe.rl_audio.setVisibility(View.GONE);
                        viewHolderMe.mv_location.setVisibility(View.GONE);
                        viewHolderMe.rl_maps.setVisibility(View.GONE);


                        picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaThumbName()).into(viewHolderMe.iv_thumb);


                        //video
                        break;
                    case "audio":
                        viewHolderMe.tv_msg.setVisibility(View.GONE);
                        viewHolderMe.chat_image.setVisibility(View.GONE);
                        viewHolderMe.iv_thumb.setVisibility(View.GONE);
                        viewHolderMe.rl_audio.setVisibility(View.VISIBLE);
                        viewHolderMe.img_playback.setVisibility(View.GONE);
                        viewHolderMe.card_video.setVisibility(View.GONE);
                        viewHolderMe.rl_maps.setVisibility(View.GONE);
                        viewHolderMe.mv_location.setVisibility(View.GONE);


                        viewHolderMe.play_audio.setOnClickListener(v -> {
                            mediaPlayer = new MediaPlayer();

                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                                viewHolderMe.play_audio.setImageResource(R.drawable.play);


                            } else {
                                viewHolderMe.play_audio.setImageResource(R.drawable.exo_icon_pause);
                                try {
                                    mediaPlayer.setDataSource(lst_chat.get(position).getMessage());
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mediaPlayer) {
                                            stopPlaying();
                                            viewHolderMe.play_audio.setImageResource(R.drawable.play);


                                        }
                                    });
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                } catch (IOException e) {
                                    Log.d(":playRecording()", e.toString());
                                }
                            }

                        });

                        //audio
                        break;
                    case "location":
                        viewHolderMe.tv_msg.setVisibility(View.GONE);
                        viewHolderMe.chat_image.setVisibility(View.GONE);
                        viewHolderMe.iv_thumb.setVisibility(View.GONE);
                        viewHolderMe.img_playback.setVisibility(View.GONE);
                        viewHolderMe.card_video.setVisibility(View.GONE);
                        viewHolderMe.rl_audio.setVisibility(View.GONE);
                        viewHolderMe.rl_maps.setVisibility(View.VISIBLE);
                        viewHolderMe.mv_location.setVisibility(View.VISIBLE);

//                GoogleMap googleMap = viewHolderMe.mv_location.getMap();

//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(chatmsgList.get(position).getLattitude(), -chatmsgList.get(position).getLongitude()))
//                        .title("Marker"));

                        //location
                        break;
                }
            }
            viewHolderMe.mv_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ref.onMapClick(chatmsgList.get(position).getLattitude(), chatmsgList.get(position).getLongitude());
                    Toast.makeText(mContext, lst_chat.get(position).getLattitude() + " , " + lst_chat.get(position).getLongitude(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolderMe.chat_image.setOnClickListener(v -> {
                showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());

            });
            viewHolderMe.img_playback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                }
            });
            viewHolderMe.iv_thumb.setOnClickListener(v -> {
                playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());

            });

        } ///
        else if (holder.getItemViewType() == 1) {
            /// OPPONENT
            ViewHolderOponent viewHolderOponent = (ViewHolderOponent) holder;
            Picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
                    .into(viewHolderOponent.iv_profile);

            if (lst_chat.get(position).getType().equals("text") || lst_chat.get(position).getType().equals("emoji")) {
                viewHolderOponent.tv_msg.setVisibility(View.VISIBLE);
                viewHolderOponent.chat_image.setVisibility(View.GONE);
                //temp

                try {
                    String msgTemp = lst_chat.get(position).getMessage();
                    int p = Integer.parseInt(msgTemp);
                    viewHolderOponent.tv_msg.setText(new String(Character.toChars(p)));
                    viewHolderOponent.tv_msg.setBackground(null);
                    viewHolderOponent.tv_msg.setBackgroundColor(Color.WHITE);
                    viewHolderOponent.tv_msg.setTextSize(mContext.getResources().getDimension(R.dimen._12sdp));
                } catch (Exception e) {
                    e.printStackTrace();
                    viewHolderOponent.tv_msg.setText(lst_chat.get(position).getMessage());
                }
                //temp
//                viewHolderOponent.tv_msg.setText(lst_chat.get(position).getMessage());

            } else if (lst_chat.get(position).getType().equals("media")) {

                switch (lst_chat.get(position).getMediaInfo().get(0).getMediaType().toLowerCase()) {
                    case "image":
                        viewHolderOponent.tv_msg.setVisibility(View.GONE);
                        viewHolderOponent.chat_image.setVisibility(View.VISIBLE);
//                        ArrayList<ChatAttachment> chatAttachmentList = new ArrayList<>(lst_chat.get(position).getAttachment());

//                        for (int i = 0; i < chatAttachmentList.size(); i++) {
//                            urls_image = chatAttachmentList.get(i).getUrl();
//
//                        }

                        Picasso.get().load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath())
                                .placeholder(R.drawable.cupertino_activity_indicator)
                                .into(viewHolderOponent.chat_image);
                        viewHolderOponent.chat_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                            }
                        });
                        break;

                    case "video":
                        viewHolderOponent.tv_msg.setVisibility(View.GONE);
                        viewHolderOponent.chat_image.setVisibility(View.GONE);

                        viewHolderOponent.iv_thumb.setVisibility(View.VISIBLE);
                        picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaThumbName()).into(viewHolderOponent.iv_thumb);
//                        viewHolderOponent.iv_thumb.setVideoURI(lst_chat.get(position).getUri());
//                        viewHolderOponent.iv_thumb.start();
                        viewHolderOponent.iv_thumb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                            }
                        });
                        viewHolderOponent.img_playback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                            }
                        });
                        break;
                    case "audio":
                        break;

                    case "location":
                        break;

                }

            } else if (lst_chat.get(position).getType().equals("typing")) {
                viewHolderOponent.tv_msg.setVisibility(View.VISIBLE);
                viewHolderOponent.chat_image.setVisibility(View.GONE);
                viewHolderOponent.typing.setVisibility(View.VISIBLE);
                viewHolderOponent.tv_msg.setVisibility(View.GONE);


            }

        } ///
        else if (holder.getItemViewType() == 2) {
            ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
            viewHolderOther.tv_msg.setText(lst_chat.get(position).getMessage());
            viewHolderOther.answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, viewHolderOther.answer1.getText().toString(), Toast.LENGTH_SHORT).show();
                    SessionPref pref = SessionPref.getInstance(mContext);
                    pref.saveStringKeyVal("Answer", viewHolderOther.answer1.getText().toString());
                    new WinnerActivity(mContext, "1").show();
                    notifyDataSetChanged();
                }
            });
            viewHolderOther.answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, viewHolderOther.answer2.getText().toString(), Toast.LENGTH_SHORT).show();
                    SessionPref pref = SessionPref.getInstance(mContext);
                    pref.saveStringKeyVal("Answer", viewHolderOther.answer2.getText().toString());
                    new WinnerActivity(mContext, "0").show();

                    notifyDataSetChanged();
                }
            });
        }
    }

    private void showPhoto(String path) {
        Intent mIntent = new Intent(mContext, PhotoViewActivity.class);
        mIntent.putExtra("data", path);
        mIntent.putExtra("isVideo", false);
        mContext.startActivity(mIntent);
    }

    private void playVideo(String path) {
        Intent mIntent = new Intent(mContext, ExoPlayerActivity.class);
        mIntent.putExtra("video", path);
        mContext.startActivity(mIntent);
    }


    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

//    EnlargeMediaChat enlarge;

    //    public void addToListText(EditText et_msg) {
//        chatmsgList.add(new ChatMessage("text", myId, "jid_1109", et_msg.getText().toString()));
//        notifyDataSetChanged();
//        et_msg.setText("");
//    }
    public void addToListText(String msg, String UserID, String userName, String userImage) {
        UserInfo userInfo = new UserInfo();
        ArrayList<UserInfo> info = new ArrayList<>();
        info.add(userInfo);
        userInfo.setProfilePicPath(userImage);
        ChatMessage chat = new ChatMessage("text", userName, userImage, UserID, msg);
        chat.setUserInfo(info);
        lst_chat.add(0, chat);
        notifyDataSetChanged();
//      et_msg.setText("");
    }

    public void addTyping(String UserID, String userName, String userImage) {
        UserInfo userInfo = new UserInfo();
        ArrayList<UserInfo> info = new ArrayList<>();
        info.add(userInfo);
        userInfo.setProfilePicPath(userImage);
        ChatMessage chat = new ChatMessage("typing", userName, userImage, UserID, "");
        chat.setUserInfo(info);
        lst_chat.add(0, chat);

        notifyDataSetChanged();
//        et_msg.setText("");
    }

    public void removeTyping() {
        lst_chat.remove(0);
        notifyDataSetChanged();
    }

//    public void addQuestion(String question) {
//        chatmsgList.add(new ChatMessage("text", polling, "jid_1109", question));
//        notifyDataSetChanged();
//    }


    Drawable drawable;

//    public void addImage(Drawable d) {
//        chatmsgList.add(new ChatMessage("image", myId, "jid_1109", d));
//        notifyDataSetChanged();
//    }

//    public void addSmiley(Drawable smiley) {
//        chatmsgList.add(new ChatMessage("image", myId, "jid_1109", smiley));
//        notifyDataSetChanged();
//
//    }
//
//    public void addVIdeo(Uri selectedVideoPath) {
//        chatmsgList.add(new ChatMessage("video", myId, "jid_1109", selectedVideoPath));
//        notifyDataSetChanged();
//    }
//
//    public void addToListAudio(String mFileName) {
//        chatmsgList.add(new ChatMessage("audio", myId, "jid_1109", mFileName));
//        notifyDataSetChanged();
//    }
//
//    public void sendLcation(double latttitude, double longitude) {
//        chatmsgList.add(new ChatMessage("location", myId, "jid_1109", latttitude, longitude));
//        notifyDataSetChanged();
//    }

//    public void resizeToNrmal() {
//        enlarge.cancel();
//    }

    public void removeFromList(int position) {
        lst_chat.remove(position);
        notifyDataSetChanged();
        bottomSheet.dismiss();
    }

    public void dismissSheet() {
        bottomSheet.dismiss();
        notifyDataSetChanged();
    }


    public class ViewHolderOther extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView answer1;
        TextView answer2;

        public ViewHolderOther(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_chat_other);
            answer1 = itemView.findViewById(R.id.answer1);
            answer2 = itemView.findViewById(R.id.answer2);

        }
    }

    int selectedPosition = -1;

    public class ViewHolderMe extends RecyclerView.ViewHolder {
        ImageView profile_image_me;
        ImageView chat_image;
        ImageView iv_thumb;
        TextView tv_msg;
        //        VideoView chat_video;
        CardView card_video;
        RelativeLayout rl_audio;
        RelativeLayout rl_body;
        RelativeLayout rl_maps;
        ImageView play_audio;
        ImageView img_playback;
        ImageView iv_mute_unmute;
        CardView card_image;
        MapView mv_location;
        GoogleMap googleMap;

//        SupportMapFragment frag_location;

        public ViewHolderMe(View view) {
            super(view);
            profile_image_me = view.findViewById(R.id.profile_image_me);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat);
            iv_thumb = view.findViewById(R.id.iv_thumb);
            card_video = view.findViewById(R.id.card_video);
//            iv_post_image = view.findViewById(R.id.iv_post_image);
            img_playback = view.findViewById(R.id.img_playback);
            iv_mute_unmute = view.findViewById(R.id.iv_mute_unmute);
            card_image = view.findViewById(R.id.card_image);
            rl_audio = view.findViewById(R.id.rl_audio);
            play_audio = view.findViewById(R.id.play_audio);
            mv_location = view.findViewById(R.id.mv_location);
            rl_body = view.findViewById(R.id.rl_body);
            rl_maps = view.findViewById(R.id.rl_maps);

//            mv_location.onCreate(savedInstanceState);

//            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            googleMap = mv_location.getMap();
            tv_msg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();
//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });
            chat_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();
//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });
            card_video.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();

//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });
            rl_audio.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();
//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });


        }
    }

    LandingBottomSheet bottomSheet;

    private void showBottomSheet(int selectedPosition) {
        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        bottomSheet = new LandingBottomSheet(this, selectedPosition, "chat");
        bottomSheet.show(fragmentManager, "ModalBottomSheet");
    }

    public class ViewHolderOponent extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        ImageView chat_image;
        ImageView iv_thumb;
        ImageView img_playback;
        //        VideoView chat_video;
        TextView tv_msg;
        LottieAnimationView typing;
        RelativeLayout rl_body;

        public ViewHolderOponent(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_oponent);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat_oponent);
            iv_thumb = view.findViewById(R.id.iv_thumb);
            img_playback = view.findViewById(R.id.img_playback);
            typing = view.findViewById(R.id.typing);
            rl_body = view.findViewById(R.id.rl_body);

            tv_msg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();

//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });
            chat_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();

//                    ref.onMessageSelectToDelete(getAdapterPosition());
                    showBottomSheet(selectedPosition);
                    notifyDataSetChanged();
                    return true;
                }
            });

//            card_video.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    ref.onMessageSelectToDelete(getAdapterPosition());
//                    notifyDataSetChanged();
//                    return true;
//                }
//            });
//            rl_audio.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    ref.onMessageSelectToDelete(getAdapterPosition());
//                    notifyDataSetChanged();
//                    return true;

//                }
//            })

        }
    }
}



