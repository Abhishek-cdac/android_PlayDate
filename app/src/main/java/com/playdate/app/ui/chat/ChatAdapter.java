package com.playdate.app.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.photoview.PhotoViewActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ArrayList<ChatMessage> lst_chat;
    //    private final ArrayList<PollingQuestion> lst_pollingQuestion;
//    private ArrayList<ChatTotalResponse> lst_chatResponse = new ArrayList<>();
    private boolean isPlaying;
    private int lastAudiPlayPos = -1;
    private boolean isPause;

    private final Onclick itemClick;
    private final Picasso picasso;
    private final SessionPref pref;
    private final String LoginUserID;

    private String todaysDate;
    private final SimpleDateFormat format1;
    private final SimpleDateFormat format2;
    private final SimpleDateFormat format3;
    private final SimpleDateFormat format4;
    private final SimpleDateFormat df;
    private final SimpleDateFormat sdf;

    public ChatAdapter(ArrayList<ChatMessage> chatmsgList, Context mContext, Onclick itemClick) {
        this.lst_chat = chatmsgList;
        this.itemClick = itemClick;
        picasso = Picasso.get();
        pref = SessionPref.getInstance(mContext);
        LoginUserID = pref.getStringVal(SessionPref.LoginUserID);
        this.mContext = mContext;


        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            todaysDate = df.format(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format2 = new SimpleDateFormat("hh:mm aa");
        format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format4 = new SimpleDateFormat("dd/MM/yyyy");
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GTC"));

    }


    @Override
    public int getItemCount() {
        if (lst_chat == null) {
            return 0;
        }
        return lst_chat.size();
    }

    @Override
    public int getItemViewType(int position) {


        if (lst_chat.get(position).getType().toLowerCase().equals("polling")) {
            return 2;
        } else {
            if (LoginUserID.equals(lst_chat.get(position).getUserID())) {
                return 0;
            } else {
                return 1;
            }
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

    MediaPlayer mediaPlayer = null;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            /// ME
            ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
            picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
                    .into(viewHolderMe.profile_image_me);

//            viewHolderMe.tv_date_time.setText(lst_chat.get(position).getEntryDate());
            viewHolderMe.tv_date_time.setText(formattedDate(lst_chat.get(position).getEntryDate()));

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
                    picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath())
                            .fit()
                            .into(viewHolderMe.chat_image);
                } catch (Exception e) {
                    e.printStackTrace(); //working ok , Take a data from that object now
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
                        picasso.load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()).centerCrop().fit().into(viewHolderMe.chat_image);
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


                            if (lastAudiPlayPos != -1) {
                                if (position != lastAudiPlayPos) {
                                    stopPlaying(mediaPlayer);
                                    mediaPlayer = new MediaPlayer();
                                    isPlaying = false;
                                    isPause = false;
                                } else {

                                }
                            }


                            if (isPlaying) {
                                isPlaying = false;
                                isPause = true;
                                mediaPlayer.pause();
                                viewHolderMe.play_audio.setImageResource(R.drawable.play);


                            } else if (isPause) {
                                mediaPlayer.start();
                                isPause = false;
                                isPlaying = true;
                                viewHolderMe.play_audio.setImageResource(R.drawable.exo_icon_pause);
                            } else {
                                isPlaying = true;
                                isPause = false;
                                lastAudiPlayPos = position;
                                viewHolderMe.play_audio.setImageResource(R.drawable.exo_icon_pause);
                                try {
                                    mediaPlayer.setDataSource(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mediaPlayer) {
                                            stopPlaying(mediaPlayer);
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

                if (lst_chat.get(position).getType().equals("location")) {
                    try {
                        double lattitude = Double.parseDouble(lst_chat.get(position).getLattitude());
                        double longitude = Double.parseDouble(lst_chat.get(position).getLongitude());

                        if (lattitude != 0.0 || longitude != 0.0) {
                            Uri gmmIntentUri = Uri.parse("geo:" + lattitude + "," + longitude + "?z=17?q=restaurants");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                                mContext.startActivity(mapIntent);
                            } else {
                                Toast.makeText(mContext, "Can't load Maps", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Can't load Maps, Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    showPhoto(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
                }

            });
            viewHolderMe.img_playback.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));
            viewHolderMe.iv_thumb.setOnClickListener(v -> playVideo(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()));

        } ///
        else if (holder.getItemViewType() == 1) {
            /// OPPONENT
            ViewHolderOponent viewHolderOponent = (ViewHolderOponent) holder;
            Picasso.get().load(lst_chat.get(position).getUserInfo().get(0).getProfilePicPath())
                    .into(viewHolderOponent.iv_profile);
            viewHolderOponent.tv_date_time.setText(formattedDate(lst_chat.get(position).getEntryDate()));
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

                        viewHolderOponent.chat_image.setOnClickListener(v -> {
                            if (lst_chat.get(position).getType().equals("location")) {

                                double lattitude = Double.parseDouble(lst_chat.get(position).getLattitude());
                                double longitude = Double.parseDouble(lst_chat.get(position).getLongitude());

                                Uri gmmIntentUri = Uri.parse("geo:" + lattitude + "," + longitude + "?z=17");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");

                                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                                    mContext.startActivity(mapIntent);
                                } else {
                                    Toast.makeText(mContext, "Can't load Maps", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                case "media":

                    switch (lst_chat.get(position).getMediaInfo().get(0).getMediaType().toLowerCase()) {
                        case "image":
                            viewHolderOponent.tv_msg.setVisibility(View.GONE);
                            viewHolderOponent.card_img.setVisibility(View.VISIBLE);

                            picasso.get().load(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath()).fit().centerCrop()
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


//                            viewHolderOponent.play_audio.setOnClickListener(v -> {
//                                mediaPlayer = new MediaPlayer();
//
//                                if (mediaPlayer.isPlaying()) {
//                                    mediaPlayer.pause();
//                                    viewHolderOponent.play_audio.setImageResource(R.drawable.play);
//
//
//                                } else {
//                                    viewHolderOponent.play_audio.setImageResource(R.drawable.exo_icon_pause);
//                                    try {
//                                        mediaPlayer.setDataSource(lst_chat.get(position).getMediaInfo().get(0).getMediaFullPath());
//                                        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
//                                            stopPlaying();
//                                            viewHolderOponent.play_audio.setImageResource(R.drawable.play);
//
//
//                                        });
//                                        mediaPlayer.prepare();
//                                        mediaPlayer.start();
//                                    } catch (IOException e) {
//                                        Log.d(":playRecording()", e.toString());
//                                    }
//                                }
//
//                            });
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
            try {
                ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
                viewHolderOther.tv_msg.setText(lst_chat.get(position).getPolling().getQuestion());
                viewHolderOther.tv_date_time.setText(formattedDate(lst_chat.get(position).getPolling().getEntryDate()));

                viewHolderOther.answer1.setText(lst_chat.get(position).getPolling().getPollingOption().get(0).getOption());
                viewHolderOther.answer2.setText(lst_chat.get(position).getPolling().getPollingOption().get(1).getOption());

                viewHolderOther.answer1.setOnClickListener(v -> itemClick.onItemClicks(v, position, 10, lst_chat.get(position).getPolling().getPollingOption().get(0).getQuestionId(),
                        lst_chat.get(position).getPolling().getPollingOption().get(0).getOptionId()));
                viewHolderOther.answer2.setOnClickListener(v -> itemClick.onItemClicks(v, position, 10, lst_chat.get(position).getPolling().getPollingOption().get(1).getQuestionId(),
                        lst_chat.get(position).getPolling().getPollingOption().get(1).getOptionId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  ///


    }

    private String formattedDate(String entryDate) {
        try {
//            String timeFormat = lst_chat.get(position).getEntryDate();
            String timeFormat = entryDate;
            timeFormat = timeFormat.replace("T", " ");

            Date date = null;
            try {
                date = df.parse(timeFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(date);

            if (formattedDate.contains(todaysDate)) {

                try {
                    date = format1.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                holder.txt_time.setText(format2.format(date));
                return format2.format(date);

            } else if (findDayDiff(formattedDate) == 1) {
//                holder.txt_time.setText("Yesterday");
                return "Yesterday";

            } else {


                try {
                    date = format3.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                holder.txt_time.setText(format4.format(date));
                return format4.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private int findDayDiff(String formattedDate) {
        try {
            Date date = sdf.parse(formattedDate);
            Date date1 = sdf.parse(todaysDate);
            if (date == null || date1 == null)
                return 0;

            return ((int) ((date.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24))) * -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
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


    private void stopPlaying(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            isPlaying = false;
            mediaPlayer.stop();
            mediaPlayer.release();
            isPause = false;
        }
    }

    public void addToListText(ChatMessage chat) {
        Log.d("Entry Date", "addToListText: " + chat.getEntryDate());
        lst_chat.add(0, chat);
        Log.d("Entry Date", "addToListText: " + lst_chat.get(0).getEntryDate());
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


    public void removeFromList(int positiontoDelete) {
        Log.d("positiontoDelete", "removeFromList: " + positiontoDelete);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", LoginUserID);
        hashMap.put("chatId", lst_chat.get(positiontoDelete).getChatId());
        hashMap.put("messageId", lst_chat.get(positiontoDelete).getMessageId());
        lst_chat.remove(positiontoDelete);
        bottomSheet.dismiss();
        notifyDataSetChanged();
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<CommonModel> call = service.deleteChatMessage("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
//                        lst_chat.remove(positiontoDelete);
//                        notifyDataSetChanged();
                    } else {
//                        Toast.makeText(mContext, "Unable to delete", Toast.LENGTH_SHORT).show();

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
            }
        });


    }

    public void dismissSheet() {
        bottomSheet.dismiss();
        notifyDataSetChanged();
    }

    public void shareChat(int index) {
        String inviteLink;
        if (lst_chat.get(index).getType().equals("text")) {
            inviteLink = lst_chat.get(index).getMessage();
        } else {
            inviteLink = lst_chat.get(index).getMediaInfo().get(0).getMediaFullPath();
        }

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        mContext.startActivity(Intent.createChooser(share, "Share ChatMessage!"));
        bottomSheet.dismiss();
    }


    public class ViewHolderOther extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView answer1;
        TextView answer2;
        TextView tv_date_time;

        public ViewHolderOther(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_chat_other);
            answer1 = itemView.findViewById(R.id.answer1);
            answer2 = itemView.findViewById(R.id.answer2);
            tv_date_time = itemView.findViewById(R.id.tv_date_time);

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
        TextView tv_date_time;
        //        GoogleMap googleMap;

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
            tv_date_time = view.findViewById(R.id.tv_date_time);

            tv_msg.setOnLongClickListener(this);
            chat_image.setOnLongClickListener(this);
            card_video.setOnLongClickListener(this);
            iv_thumb.setOnLongClickListener(this);
            img_playback.setOnLongClickListener(this);
            mv_location.setOnLongClickListener(this);
            rl_audio.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
//            int id = v.getId();
            callCommon(getAbsoluteAdapterPosition());
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
        Log.d("positiontoDelete", "removeFromListBottom: " + selectedPosition);

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
        TextView tv_date_time;

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
            tv_date_time = view.findViewById(R.id.tv_date_time);

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



