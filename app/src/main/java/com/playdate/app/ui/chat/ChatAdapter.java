package com.playdate.app.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.photoview.PhotoViewActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ArrayList<ChatMessage> lst_chat;
    private MediaPlayer mediaPlayer;
    private GoogleMap googleMap;

    private final Picasso picasso;
    private final SessionPref pref;
    private final String LoginUserID;

    public ChatAdapter(ArrayList<ChatMessage> chatmsgList, Context mContext) {
        this.lst_chat = chatmsgList;
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
        if (LoginUserID.equals(lst_chat.get(position).getUserID())) {
            return 0;
        } else {
            return 1;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

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
        if (holder.getItemViewType() == 0) {
            /// ME
            ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
            picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
                    .into(viewHolderMe.profile_image_me);

            if (lst_chat.get(position).getType().equals("text") || lst_chat.get(position).getType().equals("emoji")) {
                viewHolderMe.tv_msg.setVisibility(View.VISIBLE);
                viewHolderMe.cv_image.setVisibility(View.GONE);
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
            } else if (lst_chat.get(position).getType().equals("location")) {
                viewHolderMe.tv_msg.setVisibility(View.GONE);
                viewHolderMe.cv_image.setVisibility(View.VISIBLE);
                viewHolderMe.iv_thumb.setVisibility(View.GONE);
                viewHolderMe.rl_audio.setVisibility(View.GONE);
                viewHolderMe.img_playback.setVisibility(View.GONE);
                viewHolderMe.card_video.setVisibility(View.GONE);
                viewHolderMe.rl_maps.setVisibility(View.GONE);
                viewHolderMe.mv_location.setVisibility(View.GONE);
                try {
                    picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()).into(viewHolderMe.chat_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {


                switch (lst_chat.get(position).getMediaInfo().get(0).getMediaType().toLowerCase()) {


                    case "image":
                        viewHolderMe.tv_msg.setVisibility(View.GONE);
                        viewHolderMe.cv_image.setVisibility(View.VISIBLE);
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
                        viewHolderMe.cv_image.setVisibility(View.GONE);
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
                        viewHolderMe.cv_image.setVisibility(View.GONE);
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
                                    mediaPlayer.setDataSource(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
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

//                GoogleMap googleMap = viewHolderMe.mv_location.getMap();

//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(chatmsgList.get(position).getLattitude(), -chatmsgList.get(position).getLongitude()))
//                        .title("Marker"));

                    //location
//                        break;
                }
            }
            viewHolderMe.mv_location.setOnClickListener(v -> {
                Toast.makeText(mContext, lst_chat.get(position).getLattitude() + " , " + lst_chat.get(position).getLongitude(), Toast.LENGTH_SHORT).show();
            });
            viewHolderMe.chat_image.setOnClickListener(v -> {
                showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());

            });
            viewHolderMe.img_playback.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
            viewHolderMe.iv_thumb.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));

        } ///
        else if (holder.getItemViewType() == 1) {
            /// OPPONENT
            ViewHolderOponent viewHolderOponent = (ViewHolderOponent) holder;
            Picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
                    .into(viewHolderOponent.iv_profile);

            switch (lst_chat.get(position).getType()) {
                case "text":
                case "emoji":
                    viewHolderOponent.tv_msg.setVisibility(View.VISIBLE);
                    viewHolderOponent.card_img.setVisibility(View.GONE);
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
                    break;
                case "location":

                    try {
                        viewHolderOponent.tv_msg.setVisibility(View.GONE);
                        viewHolderOponent.card_img.setVisibility(View.VISIBLE);

                        picasso.get().load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath())
                                .into(viewHolderOponent.chat_image);
                       // viewHolderOponent.chat_image.setOnClickListener(v -> showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                case "media":

                    switch (lst_chat.get(position).getMediaInfo().get(0).getMediaType().toLowerCase()) {
                        case "image":
                            viewHolderOponent.tv_msg.setVisibility(View.GONE);
                            viewHolderOponent.card_img.setVisibility(View.VISIBLE);

                            picasso.get().load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath())
                                    .into(viewHolderOponent.chat_image);
                            viewHolderOponent.chat_image.setOnClickListener(v -> showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
                            break;

                        case "video":
                            viewHolderOponent.tv_msg.setVisibility(View.GONE);
                            viewHolderOponent.card_img.setVisibility(View.GONE);

                            viewHolderOponent.iv_thumb.setVisibility(View.VISIBLE);
                            picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaThumbName()).into(viewHolderOponent.iv_thumb);
                            viewHolderOponent.iv_thumb.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
                            viewHolderOponent.img_playback.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
                            break;
                        case "audio":
                            viewHolderOponent.tv_msg.setVisibility(View.GONE);
                            viewHolderOponent.chat_image.setVisibility(View.GONE);
                            viewHolderOponent.iv_thumb.setVisibility(View.GONE);
                            viewHolderOponent.rl_audio.setVisibility(View.VISIBLE);
                            viewHolderOponent.img_playback.setVisibility(View.GONE);


                            viewHolderOponent.play_audio.setOnClickListener(v -> {
                                mediaPlayer = new MediaPlayer();

                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                    viewHolderOponent.play_audio.setImageResource(R.drawable.play);


                                } else {
                                    viewHolderOponent.play_audio.setImageResource(R.drawable.exo_icon_pause);
                                    try {
                                        mediaPlayer.setDataSource(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                                        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                                            stopPlaying();
                                            viewHolderOponent.play_audio.setImageResource(R.drawable.play);


                                        });
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();
                                    } catch (IOException e) {
                                        Log.d(":playRecording()", e.toString());
                                    }
                                }

                            });
                            break;


                    }

                    break;
                case "typing":
                    viewHolderOponent.tv_msg.setVisibility(View.VISIBLE);
                    viewHolderOponent.card_img.setVisibility(View.GONE);
                    viewHolderOponent.typing.setVisibility(View.VISIBLE);
                    viewHolderOponent.tv_msg.setVisibility(View.GONE);


                    break;
            }

        } ///
        else if (holder.getItemViewType() == 2) {
            ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
            viewHolderOther.tv_msg.setText(lst_chat.get(position).getMessage());
            viewHolderOther.answer1.setOnClickListener(v -> {
                pref.saveStringKeyVal("Answer", viewHolderOther.answer1.getText().toString());
                new WinnerActivity(mContext, "1").show();
                notifyDataSetChanged();
            });
            viewHolderOther.answer2.setOnClickListener(v -> {
                pref.saveStringKeyVal("Answer", viewHolderOther.answer2.getText().toString());
                new WinnerActivity(mContext, "0").show();

                notifyDataSetChanged();
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

    public void addToListText(ChatMessage chat) {
        lst_chat.add(0, chat);
        notifyDataSetChanged();
    }


    public void removeTyping() {
        lst_chat.remove(0);
        notifyDataSetChanged();
    }

//    public void addQuestion(String question) {
//        chatmsgList.add(new ChatMessage("text", polling, "jid_1109", question));
//        notifyDataSetChanged();
//    }


    public void removeFromList(int position) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", LoginUserID);
        hashMap.put("chatId", lst_chat.get(position).getChatId());
        hashMap.put("messageId", lst_chat.get(position).getMessageId());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
        pd.show();

        Call<CommonModel> call = service.deleteChatMessage("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_chat.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "Unable to delete", Toast.LENGTH_SHORT).show();

                    }
                    bottomSheet.dismiss();
                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });


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


    public class ViewHolderMe extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView profile_image_me;
        ImageView chat_image;
        ImageView iv_thumb;
        TextView tv_msg;
        //        VideoView chat_video;
        CardView card_video;
        CardView cv_image;
        RelativeLayout rl_audio;
        RelativeLayout rl_body;
        RelativeLayout rl_maps;
        ImageView play_audio;
        ImageView img_playback;
        ImageView iv_mute_unmute;
        CardView card_image;
        ImageView mv_location;
        //        GoogleMap googleMap;
        int pos;

        public ViewHolderMe(View view) {
            super(view);
            profile_image_me = view.findViewById(R.id.profile_image_me);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat);
            iv_thumb = view.findViewById(R.id.iv_thumb);
            card_video = view.findViewById(R.id.card_video);
            cv_image = view.findViewById(R.id.cv_image);
            img_playback = view.findViewById(R.id.img_playback);
            iv_mute_unmute = view.findViewById(R.id.iv_mute_unmute);
            card_image = view.findViewById(R.id.card_image);
            rl_audio = view.findViewById(R.id.rl_audio);
            play_audio = view.findViewById(R.id.play_audio);
            mv_location = view.findViewById(R.id.mv_location);
            rl_body = view.findViewById(R.id.rl_body);
            rl_maps = view.findViewById(R.id.rl_maps);
            pos = getAdapterPosition();
            tv_msg.setOnLongClickListener(this);
            chat_image.setOnLongClickListener(this);
            card_video.setOnLongClickListener(this);
            rl_audio.setOnLongClickListener(this);


        }


        @Override
        public boolean onLongClick(View v) {
//            int id = v.getId();
            callCommon(pos);
            return false;
        }
    }

    private void callCommon(int selectedPosition) {
        try {
            showBottomSheet(selectedPosition);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
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
        CardView card_img;
        //        VideoView chat_video;
        TextView tv_msg;
        LottieAnimationView typing;
        RelativeLayout rl_body;
        RelativeLayout rl_audio;
        ImageView play_audio;

        public ViewHolderOponent(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_oponent);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat_oponent);
            iv_thumb = view.findViewById(R.id.iv_thumb);
            img_playback = view.findViewById(R.id.img_playback);
            typing = view.findViewById(R.id.typing);
            card_img = view.findViewById(R.id.card_img);
            rl_body = view.findViewById(R.id.rl_body);
            play_audio = view.findViewById(R.id.play_audio);

            rl_audio = view.findViewById(R.id.rl_audio);

//            tv_msg.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    selectedPosition = getAdapterPosition();
//
////                    ref.onMessageSelectToDelete(getAdapterPosition());
//                    showBottomSheet(selectedPosition);
//                    notifyDataSetChanged();
//                    return true;
//                }
//            });
//            chat_image.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    selectedPosition = getAdapterPosition();
//
////                    ref.onMessageSelectToDelete(getAdapterPosition());
//                    showBottomSheet(selectedPosition);
//                    notifyDataSetChanged();
//                    return true;
//                }
//            });
//            rl_audio.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    selectedPosition = getAdapterPosition();
//
////                    ref.onMessageSelectToDelete(getAdapterPosition());
//                    showBottomSheet(selectedPosition);
//                    notifyDataSetChanged();
//                    return true;
//                }
//            });

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



