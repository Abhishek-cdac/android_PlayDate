package com.playdate.app.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playdate.app.R;
import com.playdate.app.model.Chat;
import com.playdate.app.model.chat_models.ChatAttachment;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.dialogs.DialogWinner;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.facebook.FacebookSdk.getCacheDir;

//import static com.facebook.FacebookSdk.getCacheDir;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public static int OTHER;
    public static int ME;
    public static int OPPONENT;
    ArrayList<Chat> chat_list = new ArrayList<>();
    ArrayList<ChatAttachment> chatAttachmentList;
    ArrayList<ChatExample> chatExampleList;
    ArrayList<ChatMessage> chatmsgList;
    String urls_image;
    MediaPlayer mediaPlayer;
    GoogleMap googleMap;

    String from;
    String to;
    String myId = "jid_1111";

    public ChatAdapter(ArrayList<ChatMessage> chatmsgList) {
        this.chatmsgList = chatmsgList;
        chat_list.add(new Chat("Hey Myron! looks like we matched", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", ME));
        chat_list.add(new Chat("Hey Kayle! yeah it looks like we can have a nice conversation", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", OPPONENT));
        chat_list.add(new Chat("Nice Smile btw!1", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", ME));
        chat_list.add(new Chat("Not better than you.", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", OPPONENT));
        chat_list.add(new Chat("According to Forbes,which entreprenour became first person in history to have net worth of $400 billion?", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", OTHER));
        chat_list.add(new Chat("Hey Kayle! yeah it looks like we can have a nice conversation", "https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", OPPONENT));
        chat_list.add(new Chat("Nice Smile btw!1", "https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", ME));
        chat_list.add(new Chat("Not better than you.", "https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", OPPONENT));


    }

    @Override
    public int getItemCount() {
        return chatmsgList.size();
    }

    @Override
    public int getItemViewType(int position) {
        from = chatmsgList.get(position).getFrom();
        to = chatmsgList.get(position).getTo();

        if (from.equals(myId)) {
            ME = 1;
        } else if (to.equals(myId)) {
            ME = 2;
        } else {
            ME = 3;
        }

//        int type = chat_list.get(position).getChat_type();
        switch (ME) {
            case 3:
                return OTHER;

            case 1:
                return ME;

            case 2:
                return OPPONENT;

        }
        return ME;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        mContext = parent.getContext();

        if (viewType == ME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_me, parent, false);
            viewHolder = new ViewHolderMe(view);

        } else if (viewType == OPPONENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_oponent, parent, false);
            viewHolder = new ViewHolderOponent(view);


        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_type_other, parent, false);
            viewHolder = new ViewHolderOther(view);

        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ME) {
            /// ME
            ViewHolderMe viewHolderMe = (ViewHolderMe) holder;
//            Picasso.get().load(chatmsgList.get(position).getProfilePhoto())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
//                    .into(viewHolderMe.iv_profile);

            if (chatmsgList.get(position).getType().equals("text")) {
//                viewHolderMe.tv_msg.setVisibility(View.VISIBLE);
//                viewHolderMe.chat_image.setVisibility(View.GONE);
//                viewHolderMe.chat_video.setVisibility(View.GONE);
//                viewHolderMe.rl_audio.setVisibility(View.GONE);
//                viewHolderMe.mv_location.setVisibility(View.GONE);

                viewHolderMe.tv_msg.setText(chatmsgList.get(position).getText());
                //text

            } else if (chatmsgList.get(position).getType().equals("image")) {
                viewHolderMe.tv_msg.setVisibility(View.GONE);
                viewHolderMe.chat_image.setVisibility(View.VISIBLE);
//                viewHolderMe.chat_video.setVisibility(View.GONE);
                viewHolderMe.rl_audio.setVisibility(View.GONE);
//                viewHolderMe.mv_location.setVisibility(View.GONE);

                viewHolderMe.chat_image.setImageDrawable(chatmsgList.get(position).getDrawable());
                //image
            } else if (chatmsgList.get(position).getType().equals("video")) {
                viewHolderMe.tv_msg.setVisibility(View.GONE);
                viewHolderMe.chat_image.setVisibility(View.GONE);
//                viewHolderMe.card_image.setVisibility(View.VISIBLE);
                viewHolderMe.chat_video.setVisibility(View.VISIBLE);

                viewHolderMe.rl_audio.setVisibility(View.GONE);
//                viewHolderMe.mv_location.setVisibility(View.GONE);

                viewHolderMe.chat_video.setVideoURI(chatmsgList.get(position).getUri());

                viewHolderMe.img_playback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolderMe.chat_video.isPlaying()) {
                            viewHolderMe.chat_video.pause();
                            viewHolderMe.img_playback.setImageResource(R.drawable.ic_play_button);
                        } else {
//                            viewHolderMe.iv_post_image.setVisibility(View.GONE);
                            viewHolderMe.chat_video.start();
                            viewHolderMe.img_playback.setImageResource(R.drawable.ic_pause);

                        }
                    }
                });

                //video
            } else if (chatmsgList.get(position).getType().equals("audio")) {
                viewHolderMe.tv_msg.setVisibility(View.GONE);
                viewHolderMe.chat_image.setVisibility(View.GONE);
                viewHolderMe.chat_video.setVisibility(View.GONE);
                viewHolderMe.rl_audio.setVisibility(View.VISIBLE);
//                viewHolderMe.mv_location.setVisibility(View.GONE);


                viewHolderMe.play_audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer = new MediaPlayer();

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            viewHolderMe.play_audio.setImageResource(R.drawable.play);


                        } else {
                            viewHolderMe.play_audio.setImageResource(R.drawable.exo_icon_pause);
                            try {
                                mediaPlayer.setDataSource(chatmsgList.get(position).getText());
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

                    }
                });

                //audio
            } else if (chatmsgList.get(position).getType().equals("location")) {
                viewHolderMe.tv_msg.setVisibility(View.GONE);
                viewHolderMe.chat_image.setVisibility(View.GONE);
                viewHolderMe.chat_video.setVisibility(View.GONE);
                viewHolderMe.rl_audio.setVisibility(View.GONE);
                viewHolderMe.mv_location.setVisibility(View.VISIBLE);
//                GoogleMap googleMap = viewHolderMe.mv_location.getMap();

//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(chatmsgList.get(position).getLattitude(), -chatmsgList.get(position).getLongitude()))
//                        .title("Marker"));

                //location
            }


        } ///
        else if (holder.getItemViewType() == OPPONENT) {
            /// OPPONENT
            ViewHolderOponent viewHolderOponent = (ViewHolderOponent) holder;
            Picasso.get().load(chatmsgList.get(position).getProfilePhoto())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(viewHolderOponent.iv_profile);

            if (chatmsgList.get(position).getType().equals("text")) {
                viewHolderOponent.tv_msg.setText(chatmsgList.get(position).getText());

            } else if (chatmsgList.get(position).getType().equals("image")) {
                viewHolderOponent.tv_msg.setVisibility(View.GONE);
                viewHolderOponent.chat_image.setVisibility(View.VISIBLE);
                chatAttachmentList = new ArrayList<>(chatmsgList.get(position).getAttachment());

                for (int i = 0; i < chatAttachmentList.size(); i++) {
                    urls_image = chatAttachmentList.get(i).getUrl();
                    Log.d("URL OF IMAGE ", urls_image);
                    Picasso.get().load(urls_image)
                            .placeholder(R.drawable.cupertino_activity_indicator)
                            .into(viewHolderOponent.chat_image);
                }
            } else if (chatmsgList.get(position).getType().equals("video")) {
                viewHolderOponent.tv_msg.setVisibility(View.GONE);
                viewHolderOponent.chat_image.setVisibility(View.GONE);

                viewHolderOponent.chat_video.setVisibility(View.VISIBLE);
//                viewHolderOponent.chat_video.setVideoPath(chatmsgList.get(position).getText());
                viewHolderOponent.chat_video.setVideoURI(chatmsgList.get(position).getUri());
                viewHolderOponent.chat_video.start();
//                viewHolderMe.chat_video.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewHolderMe.chat_video.start();
//                    }
//                });
            }

        } ///
        else {
            ViewHolderOther viewHolderOther = (ViewHolderOther) holder;
            viewHolderOther.tv_msg.setText(chat_list.get(position).getMessage());

        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void addToListText(EditText et_msg) {
        chatmsgList.add(new ChatMessage("text", myId, "jid_1109", et_msg.getText().toString()));
        notifyDataSetChanged();
        et_msg.setText("");
    }

    //// code for  saving image as file
    public void addToListImage(Bitmap bitmap) {

        File f = new File(getCacheDir(), "profile");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("userProfilePic", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));

        Log.d("FILEPART", String.valueOf(filePart));


//        chatmsgList.add(filePart);

//        chatAttachmentList.add(new ChatAttachment(uri.toString()));
//        chatmsgList.add(new ChatMessage("image",myId,"jid_1109",chatAttachmentList));
//        notifyDataSetChanged();
    }

    Drawable drawable;

    public void addImage(Drawable d) {
        chatmsgList.add(new ChatMessage("image", myId, "jid_1109", d));
        notifyDataSetChanged();
    }

    public void addSmiley(Drawable smiley) {
        chatmsgList.add(new ChatMessage("image", myId, "jid_1109", smiley));
        notifyDataSetChanged();

    }

    public void addVIdeo(Uri selectedVideoPath) {
        chatmsgList.add(new ChatMessage("video", myId, "jid_1109", selectedVideoPath));
        notifyDataSetChanged();
    }

    public void addToListAudio(String mFileName) {
        chatmsgList.add(new ChatMessage("audio", myId, "jid_1109", mFileName));
        notifyDataSetChanged();
    }

    public void sendLcation(double latttitude, double longitude) {
        chatmsgList.add(new ChatMessage("location", myId, "jid_1109", latttitude, longitude));


        notifyDataSetChanged();
    }

    public class ViewHolderOther extends RecyclerView.ViewHolder {
        TextView tv_msg;
        TextView answer1;

        public ViewHolderOther(@NonNull View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_chat_other);
            answer1 = itemView.findViewById(R.id.answer1);

            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DialogWinner.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public class ViewHolderMe extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        ImageView chat_image;
        TextView tv_msg;
        VideoView chat_video;
        RelativeLayout rl_audio;
        ImageView play_audio;
        //        ImageView iv_post_image;
        ImageView img_playback;
        ImageView iv_mute_unmute;
        CardView card_image;
        MapView mv_location;
        GoogleMap googleMap;

//        SupportMapFragment frag_location;

        public ViewHolderMe(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_me);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat);
            chat_video = view.findViewById(R.id.chat_video);
//            iv_post_image = view.findViewById(R.id.iv_post_image);
            img_playback = view.findViewById(R.id.img_playback);
            iv_mute_unmute = view.findViewById(R.id.iv_mute_unmute);
            card_image = view.findViewById(R.id.card_image);
            rl_audio = view.findViewById(R.id.rl_audio);
            play_audio = view.findViewById(R.id.play_audio);
            mv_location = view.findViewById(R.id.mv_location);

//            mv_location.onCreate(savedInstanceState);

//            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            googleMap = mv_location.getMap();
        }
    }

    public class ViewHolderOponent extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        ImageView chat_image;
        VideoView chat_video;
        TextView tv_msg;

        public ViewHolderOponent(View view) {
            super(view);
            iv_profile = view.findViewById(R.id.profile_image_oponent);
            chat_image = view.findViewById(R.id.chat_image);
            tv_msg = view.findViewById(R.id.tv_chat_oponent);
            chat_video = view.findViewById(R.id.chat_video);

        }
    }
}







