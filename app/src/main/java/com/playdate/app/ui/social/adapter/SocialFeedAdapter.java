package com.playdate.app.ui.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.anonymous_question.AnonymousQuestionActivity;
import com.playdate.app.ui.anonymous_question.CommentBottomSheet;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.social.FragSocialFeed;
import com.playdate.app.ui.social.model.CommentList;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.ui.social.videoplay.AAH_CustomViewHolder;
import com.playdate.app.ui.social.videoplay.AAH_VideoImage;
import com.playdate.app.ui.social.videoplay.AAH_VideosAdapter;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class SocialFeedAdapter extends AAH_VideosAdapter {


    private ArrayList<PostDetails> lst;
    private final Picasso picasso;
    private final SessionPref pref;
    private final FragmentActivity activity;

    public SocialFeedAdapter(FragmentActivity activity, ArrayList<PostDetails> lst) {
        this.activity = activity;
        this.lst = lst;
        picasso = Picasso.get();
        pref = SessionPref.getInstance(activity);

    }

    public void animateHeart(final ImageView view, View iv) {

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(800);
        animation.setFillAfter(true);

        view.startAnimation(animation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            view.clearAnimation();

            iv.setVisibility(View.GONE);
            notifyDataSetChanged();
        }, 900);


    }

    private void callAPI(String postId, int like) {
        SessionPref pref = SessionPref.getInstance(mContext);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        if (like == 0) {
            hashMap.put("status", "Unlike");
        } else {
            hashMap.put("status", "Like");
        }
        Call<LoginResponse> call = service.addPostLikeUnlike("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public int getItemViewType(int position) {

        if (lst == null) {
            return 0;
        } else if (lst.size() == 0) {
            return 0;
        } else if (lst.get(position).getPostType().equals("Load")) {
            return 100;
        } else if (lst.get(position).getPostType().equals("Normal")) {
            if (lst.get(position).getPostMedia().get(0).getMediaType().equals("Video")) {
                return 1;
            }
        } else if (lst.get(position).getPostType().equals("Question")) {
            return 2;
        }
        return 0;


    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        AAH_CustomViewHolder viewHolder;
        mContext = parent.getContext();
        if (viewType == 100) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_more, parent, false);
            viewHolder = new ViewHolderLoadMore(view);
        } else if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
            viewHolder = new ViewHolderUser(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_3, parent, false);
            viewHolder = new ViewHolderAnonymQuestion(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_video, parent, false);
            viewHolder = new ViewHolderUserVideo(view);
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            ViewHolderUser userViewHolder;
            userViewHolder = (ViewHolderUser) holder;
            userViewHolder.name_friend.setText(lst.get(position).getLstpostby().get(0).getUsername());

            userViewHolder.name_friend.setOnClickListener(view -> {
                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
                ref.loadProfile(lst.get(position).getUserId());
            });


            if (null != lst.get(position).getPostMedia().get(0).getMediaFullPath()) {
                if (!lst.get(position).getPostMedia().get(0).getMediaType().equals("Video")) {
                    picasso.load(lst.get(position).getPostMedia().get(0).getMediaFullPath())


                            .into(userViewHolder.iv_post_image, new ImageLoadedCallback(userViewHolder.animationView) {
                                @Override
                                public void onSuccess() {
                                    if (this.iv_loader != null) {
                                        this.iv_loader.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {

                    // video

                }
            }


            picasso.load(lst.get(position).getLstpostby().get(0).getProfilePicPath())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(userViewHolder.iv_profile);

            if (lst.get(position).getIsLike() == 1) {
                userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
            } else {
                userViewHolder.iv_heart.setImageResource(R.drawable.ic_heart);
            }

            userViewHolder.iv_heart.setOnClickListener(view -> {
                if (lst.get(position).getIsLike() == 1) {
                    lst.get(position).setIsLike(0);
                    lst.get(position).setLikes(lst.get(position).getLikes() - 1);
                    userViewHolder.iv_heart.setImageResource(R.drawable.ic_heart);
                    lst.get(position).setTapCount(0);
                    notifyDataSetChanged();
                    callAPI(lst.get(position).getPostId(), lst.get(position).getIsLike());
                } else if (lst.get(position).getIsLike() == 0) {
                    userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
                    lst.get(position).setIsLike(1);
                    lst.get(position).setLikes(lst.get(position).getLikes() + 1);
                    lst.get(position).setTapCount(0);
                    notifyDataSetChanged();
                    callAPI(lst.get(position).getPostId(), lst.get(position).getIsLike());
                }
            });
            try {
                userViewHolder.txt_heart_count.setText(lst.get(position).getLikes() + " Loves");


                String owner = "";
                if (null != lst.get(position).getTag()) {
                    if (lst.get(position).getTag().isEmpty()) {
                        owner = "";
                    } else {
                        owner = "<b>" + lst.get(position).getLstpostby().get(0).getUsername() + "</b> " + lst.get(position).getTag();
                        userViewHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>"));
                    }

                }
                if (lst.get(position).isCommentStatus()) {
                    if (null != lst.get(position).getComments_list()) {
                        ArrayList<CommentList> lstComm = lst.get(position).getComments_list();

                        StringBuilder temp = new StringBuilder();
                        for (int i = 0; i < lstComm.size(); i++) {
                            String s = "<b>" + lstComm.get(i).getCommentBy().get(0).getUsername() + "</b> " + lstComm.get(i).getComment();
                            if (temp.length() == 0) {
                                temp = new StringBuilder(s);
                            } else {
                                temp.append("<br>").append(s);
                            }
                        }
                        if (temp.length() == 0) {
                            if (userViewHolder.txt_chat.getText().toString().isEmpty())
                                userViewHolder.txt_chat.setVisibility(View.GONE);
                        } else {
                            userViewHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>" + "<br>" + temp));
                        }


                    } else if (lst.get(position).getComments_list() == null) {
                        if (userViewHolder.txt_chat.getText().toString().isEmpty())
                            userViewHolder.txt_chat.setVisibility(View.GONE);
                    } else if (lst.get(position).getComments_list().isEmpty()) {
                        if (userViewHolder.txt_chat.getText().toString().isEmpty())
                            userViewHolder.txt_chat.setVisibility(View.GONE);
                    }
                } else {
                    userViewHolder.et_comment.setVisibility(View.GONE);
                    if (owner.isEmpty()) {
                        if (userViewHolder.txt_chat.getText().toString().isEmpty())
                            userViewHolder.txt_chat.setVisibility(View.GONE);
                    } else {
                        userViewHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>"));
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lst.get(position).getIsGallerySave() == 1) {
                userViewHolder.savePost.setImageResource(R.drawable.ic_icons8_bookmark);
            } else {
                userViewHolder.savePost.setImageResource(R.drawable.icon_bookmark);
            }

            userViewHolder.savePost.setOnClickListener(view -> {
                if (lst.get(position).getIsGallerySave() == 1) {
                    lst.get(position).setIsGallerySave(0);
                    userViewHolder.savePost.setImageResource(R.drawable.icon_bookmark);
                    notifyDataSetChanged();
                    callSavePostAPI(lst.get(position).getPostId(), lst.get(position).getIsGallerySave());
                } else if (lst.get(position).getIsGallerySave() == 0) {
                    userViewHolder.savePost.setImageResource(R.drawable.ic_icons8_bookmark);
                    lst.get(position).setIsGallerySave(1);
                    notifyDataSetChanged();
                    callSavePostAPI(lst.get(position).getPostId(), lst.get(position).getIsGallerySave());
                }
            });

            userViewHolder.iv_msg.setOnClickListener(v -> {
                Intent i = new Intent(activity, AnonymousQuestionActivity.class);
                i.putExtra("post_id", lst.get(position).getPostId());
                i.putExtra("user_id", lst.get(position).getUserId());
                activity.startActivityForResult(i, 100);
            });
            userViewHolder.iv_post_image.setOnClickListener(view -> {

                if (lst.get(position).getIsLike() != 1) {
                    if (lst.get(position).getTapCount() == 1) {

                        lst.get(position).setIsLike(1);
                        lst.get(position).setLikes(lst.get(position).getLikes() + 1);

                        userViewHolder.iv_heart_red.setVisibility(View.VISIBLE);
                        callAPI(lst.get(position).getPostId(), lst.get(position).getLikes());
                        animateHeart(userViewHolder.iv_heart_red, userViewHolder.iv_heart_red);

                    } else {
                        lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                        userViewHolder.iv_heart_red.setVisibility(View.GONE);
                    }

                } else {
                    lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                }
            });

        } else if (holder.getItemViewType() == 100) {

        } else if (holder.getItemViewType() == 1) {
            ViewHolderUserVideo videoHolder;
            holder.setVideoUrl(lst.get(position).getPostMedia().get(0).getMediaFullPath());

            holder.setLooping(false); //optional - true by default


            videoHolder = (ViewHolderUserVideo) holder;
            videoHolder.name_friend.setText(lst.get(position).getLstpostby().get(0).getUsername());
            videoHolder.name_friend.setOnClickListener(view -> {
                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
                ref.loadProfile(lst.get(position).getUserId());
            });
            videoHolder.iv_msg.setOnClickListener(v -> {
                Intent i = new Intent(activity, AnonymousQuestionActivity.class);
                i.putExtra("post_id", lst.get(position).getPostId());
                i.putExtra("user_id", lst.get(position).getUserId());
                activity.startActivityForResult(i, 100);
            });

            picasso.load(lst.get(position).getPostMedia().get(0).getMediaThumbName())
                    .into(videoHolder.videoImg.getImageView());

            picasso.load(lst.get(position).getLstpostby().get(0).getProfilePicPath())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(videoHolder.iv_profile);

            if (lst.get(position).getIsLike() == 1) {
                videoHolder.iv_heart.setImageResource(R.drawable.red_heart);
            } else {
                videoHolder.iv_heart.setImageResource(R.drawable.ic_heart);
            }

            videoHolder.iv_heart.setOnClickListener(view -> {
                if (lst.get(position).getIsLike() == 1) {
                    lst.get(position).setIsLike(0);
                    lst.get(position).setLikes(lst.get(position).getLikes() - 1);
                    videoHolder.iv_heart.setImageResource(R.drawable.ic_heart);
                    lst.get(position).setTapCount(0);
//                    lst.get(position).setHeartSelected(false);
                    notifyDataSetChanged();
                    callAPI(lst.get(position).getPostId(), lst.get(position).getIsLike());
                } else if (lst.get(position).getIsLike() == 0) {
                    videoHolder.iv_heart.setImageResource(R.drawable.red_heart);
                    lst.get(position).setIsLike(1);
                    lst.get(position).setTapCount(0);
                    lst.get(position).setLikes(lst.get(position).getLikes() + 1);
//                    lst.get(position).setHeartSelected(true);
                    notifyDataSetChanged();
                    callAPI(lst.get(position).getPostId(), lst.get(position).getIsLike());
                } else {
//                    callAPI(lst.get(position).getPostId(), lst.get(position).getLikes());
//                    userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
                }
            });
            try {
                videoHolder.txt_heart_count.setText(lst.get(position).getLikes() + " Loves");


                String owner = "";
                if (null != lst.get(position).getTag()) {
                    if (lst.get(position).getTag().isEmpty()) {
                        owner = "";
                    } else {
                        owner = "<b>" + lst.get(position).getLstpostby().get(0).getUsername() + "</b> " + lst.get(position).getTag();
                        videoHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>"));
                    }

                }
                if (lst.get(position).isCommentStatus()) {
                    if (null != lst.get(position).getComments_list()) {
                        ArrayList<CommentList> lstComm = lst.get(position).getComments_list();

                        StringBuilder temp = new StringBuilder();
                        for (int i = 0; i < lstComm.size(); i++) {
                            String s = "<b>" + lstComm.get(i).getCommentBy().get(0).getUsername() + "</b> " + lstComm.get(i).getComment();
                            if (temp.length() == 0) {
                                temp = new StringBuilder(s);
                            } else {
                                temp.append("<br>").append(s);
                            }
                        }

                        if (temp.length() == 0) {
                            if (videoHolder.txt_chat.getText().toString().isEmpty())
                                videoHolder.txt_chat.setVisibility(View.GONE);
                        } else {
                            videoHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>" + "<br>" + temp));
                        }

                    } else if (lst.get(position).getComments_list() == null) {
                        if (videoHolder.txt_chat.getText().toString().isEmpty())
                            videoHolder.txt_chat.setVisibility(View.GONE);
                    } else if (lst.get(position).getComments_list().isEmpty()) {
                        if (videoHolder.txt_chat.getText().toString().isEmpty())
                            videoHolder.txt_chat.setVisibility(View.GONE);
                    }
                } else {
                    if (videoHolder.txt_chat.getText().toString().isEmpty())
                        videoHolder.et_comment.setVisibility(View.GONE);
                    if (owner.isEmpty()) {
                        if (videoHolder.txt_chat.getText().toString().isEmpty())
                            videoHolder.txt_chat.setVisibility(View.GONE);
                    } else {
                        videoHolder.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>"));
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lst.get(position).getIsGallerySave() == 1) {
                videoHolder.savePost.setImageResource(R.drawable.ic_icons8_bookmark);
            } else {
                videoHolder.savePost.setImageResource(R.drawable.icon_bookmark);
            }

            videoHolder.savePost.setOnClickListener(view -> {
                if (lst.get(position).getIsGallerySave() == 1) {
                    lst.get(position).setIsGallerySave(0);
                    videoHolder.savePost.setImageResource(R.drawable.icon_bookmark);
                    notifyDataSetChanged();
                    callSavePostAPI(lst.get(position).getPostId(), lst.get(position).getIsGallerySave());
                } else if (lst.get(position).getIsGallerySave() == 0) {
                    videoHolder.savePost.setImageResource(R.drawable.ic_icons8_bookmark);
                    lst.get(position).setIsGallerySave(1);
                    notifyDataSetChanged();
                    callSavePostAPI(lst.get(position).getPostId(), lst.get(position).getIsGallerySave());
                }
            });


            videoHolder.card_image.setOnClickListener(view -> {

                if (lst.get(position).getLikes() != 1) {
                    if (lst.get(position).getTapCount() == 1) {

                        lst.get(position).setIsLike(1);
                        lst.get(position).setLikes(lst.get(position).getLikes() + 1);
                        videoHolder.iv_heart_red.setVisibility(View.VISIBLE);
                        callAPI(lst.get(position).getPostId(), lst.get(position).getLikes());
                        animateHeart(videoHolder.iv_heart_red, videoHolder.iv_heart_red);

                    } else {
                        lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                        videoHolder.iv_heart_red.setVisibility(View.GONE);
                    }

                } else {
                    lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                }
            });


        } else if (holder.getItemViewType() == 2) {
            try {
                ViewHolderAnonymQuestion viewHolderAnonymQuestion;
                viewHolderAnonymQuestion = (ViewHolderAnonymQuestion) holder;
                viewHolderAnonymQuestion.name_friend.setText("Anonymous Question");

                try {
                    if (null != lst.get(position).getColorCode())
                        viewHolderAnonymQuestion.card_image.setCardBackgroundColor(Color.parseColor(lst.get(position).getColorCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                    viewHolderAnonymQuestion.card_image.setCardBackgroundColor(mContext.getResources().getColor(R.color.color_pink));
                }

//                String ques = lst.get(position).getTag();

                viewHolderAnonymQuestion.question_Anonymous.setText(lst.get(position).getTag());
//                String s = Integer.toString(lst.get(position).getEmojiCode(), 16);
                viewHolderAnonymQuestion.txt_ano_question.setText(new String(Character.toChars(lst.get(position).getEmojiCode())));


                if (pref.getStringVal(SessionPref.LoginUserID).equals(lst.get(position).getUserId())) {
                    viewHolderAnonymQuestion.delete_btn.setVisibility(View.VISIBLE);
                } else {
                    viewHolderAnonymQuestion.delete_btn.setVisibility(View.INVISIBLE);
                    viewHolderAnonymQuestion.respond.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                }

                try {

                    if (lst.get(position).isCommentStatus()) {
                        viewHolderAnonymQuestion.txt_chat.setVisibility(View.VISIBLE);
                        if (null != lst.get(position).getComments_list()) {
                            ArrayList<CommentList> lstComm = lst.get(position).getComments_list();

                            StringBuilder temp = new StringBuilder();
                            for (int i = 0; i < lstComm.size(); i++) {
                                String s = "<b>" + lstComm.get(i).getCommentBy().get(0).getUsername() + "</b> " + lstComm.get(i).getComment();
                                if (temp.length() == 0) {
                                    temp = new StringBuilder(s);
                                } else {
                                    temp.append("<br>").append(s);
                                }
                            }
                            if (temp.length() == 0) {
                                viewHolderAnonymQuestion.txt_chat.setText(Html.fromHtml("<b> No Answer </b>"));
                            } else {
                                viewHolderAnonymQuestion.txt_chat.setText(Html.fromHtml(temp.toString()));
                                viewHolderAnonymQuestion.edt_comment.setHint("Add a comment...");
                            }


                        } else if (lst.get(position).getComments_list() == null) {

                            viewHolderAnonymQuestion.txt_chat.setText(Html.fromHtml("<b> No Answer </b>"));
                        } else if (lst.get(position).getComments_list().isEmpty()) {

                            viewHolderAnonymQuestion.txt_chat.setText((Html.fromHtml("<b> No Answer </b>")));
                        }
                    } else {
                        viewHolderAnonymQuestion.txt_chat.setVisibility(View.GONE);
//                        if (owner.isEmpty()) {
//                            viewHolderAnonymQuestion.txt_chat.setVisibility(View.GONE);
//                        } else {
//                            viewHolderAnonymQuestion.txt_chat.setText(Html.fromHtml("<b>" + owner + "</b>"));
//                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


//                Drawable unwrappedDrawable = AppCompatResources.getDrawable(mContext, R.drawable.btn_pink_filled);
//                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                if(null!=lst.get(position).getColorCode())
//                DrawableCompat.setTint(wrappedDrawable, Color.parseColor(lst.get(position).getColorCode()));

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

        }

//        else if (holder.getItemViewType() == FragSocialFeed.RESTAURANT) {
//            ViewHolderRestaurant restViewHolder = (ViewHolderRestaurant) holder;
//            restViewHolder.name_friend.setText(lst.get(position).getUserName());
//            Picasso.get().load(lst.get(position).getImage())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
//                    .into(restViewHolder.iv_post_image);
//
//            Picasso.get().load(lst.get(position).getSmallUserImage())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
//                    .into(restViewHolder.iv_profile);
//        } else if (holder.getItemViewType() == FragSocialFeed.ADDS) {
//            ViewHolderAdds adds = (ViewHolderAdds) holder;
//            adds.name_friend.setText(lst.get(position).getUserName());
//            Picasso.get().load(lst.get(position).getImage())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
//                    .into(adds.iv_post_image);
//
//            Picasso.get().load(lst.get(position).getSmallUserImage())
//                    .placeholder(R.drawable.cupertino_activity_indicator)
//                    .into(adds.iv_profile);
//        } else {
//            ViewHolderAnonymQuestion anonymQuestionViewHolder = (ViewHolderAnonymQuestion) holder;
//            anonymQuestionViewHolder.name_friend.setText(lst.get(position).getUserName());
//
//
//
//        }

    }

    private void callSavePostAPI(String postId, int save) {

        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        if (save == 0) {
            hashMap.put("status", "Delete");
        } else {
            hashMap.put("status", "Save");
        }

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<CommonModel> call = service.postFileSaveGallery("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    FragSocialFeed fragSocialFeed;

    public void setRef(FragSocialFeed fragSocialFeed) {
        this.fragSocialFeed = fragSocialFeed;
    }

    public class ViewHolderUserVideo extends AAH_CustomViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView iv_heart;
        ImageView iv_msg;
        LottieAnimationView animationView;
        ImageView iv_post_image, iv_more_options;
        CardView card_image;
        TextView name_friend;
        TextView et_comment;
        TextView txt_heart_count;
        ImageView savePost;
        ImageView iv_mute_unmute;
        ImageView img_playback;
        TextView txt_chat;
        AAH_VideoImage videoImg;
        boolean isMuted = false;
        boolean isPlaying = true;

        public ViewHolderUserVideo(@NonNull View itemView) {
            super(itemView);
            savePost = itemView.findViewById(R.id.save);
            iv_mute_unmute = itemView.findViewById(R.id.iv_mute_unmute);
            videoImg = itemView.findViewById(R.id.videoImg);
            img_playback = itemView.findViewById(R.id.img_playback);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
            iv_msg = itemView.findViewById(R.id.iv_msg);
            animationView = itemView.findViewById(R.id.animationView);
            txt_heart_count = itemView.findViewById(R.id.txt_heart_count);
            et_comment = itemView.findViewById(R.id.edt_comment);
            txt_chat = itemView.findViewById(R.id.txt_chat);

            iv_more_options = itemView.findViewById(R.id.friend_request);
            iv_more_options.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));

            iv_msg.setOnClickListener(v -> {
                activity.startActivityForResult(new Intent(v.getContext(), AnonymousQuestionActivity.class), 100);
            });
            et_comment.setOnClickListener(v -> {
                Intent mIntent = new Intent(v.getContext(), AnonymousQuestionActivity.class);
                mIntent.putExtra("post_id", lst.get(getAdapterPosition()).getPostId());
                mIntent.putExtra("user_id", lst.get(getAdapterPosition()).getUserId());
                activity.startActivityForResult(mIntent, 100);
            });
            iv_mute_unmute.setOnClickListener(v -> {
                if (!isMuted) {
                    isMuted = true;
                    muteVideo();
                    iv_mute_unmute.setImageResource(R.drawable.ic_mute);
                } else {
                    isMuted = false;
                    unmuteVideo();
                    iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
                }
            });
            img_playback.setOnClickListener(v -> {
                if (isPlaying) {
                    isPlaying = false;
                    pauseVideo();
                    img_playback.setImageResource(R.drawable.play_circle);

                } else {
                    isPlaying = true;
                    playVideo();
                    img_playback.setImageResource(R.drawable.ic_pause);
                }

            });
        }


        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
            if (isMuted) {
                muteVideo();
                iv_mute_unmute.setImageResource(R.drawable.ic_mute);
            } else {
                unmuteVideo();
                iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
            }
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.play_circle);
        }
    }

    public class ViewHolderLoadMore extends AAH_CustomViewHolder {

        public ViewHolderLoadMore(View view) {
            super(view);
        }
    }

    public class ViewHolderUser extends AAH_CustomViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView iv_heart;
        ImageView iv_msg;
        LottieAnimationView animationView;
        ImageView iv_post_image, iv_more_options;
        CardView card_image;
        TextView name_friend;
        TextView et_comment;
        TextView txt_heart_count;
        ImageView savePost;
        TextView txt_chat;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            savePost = itemView.findViewById(R.id.save);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
            iv_msg = itemView.findViewById(R.id.iv_msg);
            animationView = itemView.findViewById(R.id.animationView);
            txt_heart_count = itemView.findViewById(R.id.txt_heart_count);
            et_comment = itemView.findViewById(R.id.edt_comment);
            txt_chat = itemView.findViewById(R.id.txt_chat);

            iv_more_options = itemView.findViewById(R.id.friend_request);
            iv_more_options.setOnClickListener(v -> showBottomSheet(getAdapterPosition()));


            et_comment.setOnClickListener(v -> {
                Intent mIntent = new Intent(activity, AnonymousQuestionActivity.class);
                mIntent.putExtra("post_id", lst.get(getAdapterPosition()).getPostId());
                mIntent.putExtra("user_id", lst.get(getAdapterPosition()).getUserId());

                activity.startActivityForResult(mIntent, 100);
            });

        }
    }

    private void showBottomSheet(int adapterPosition) {
        boolean notification = lst.get(adapterPosition).getNotifyStatus().equals("On");


        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        CommentBottomSheet sheet = new CommentBottomSheet(notification, lst.get(adapterPosition), this, adapterPosition);
        sheet.show(fragmentManager, "comment bootom sheet");
    }

    void notifyAdapter() {
        notifyDataSetChanged();
    }

//    public class ViewHolderAdds extends RecyclerView.ViewHolder {
//        ImageView iv_heart_red;
//        ImageView iv_profile;
//        ImageView iv_heart;
//        ImageView iv_post_image;
//        CardView card_image;
//        TextView name_friend;
//
//        public ViewHolderAdds(@NonNull View itemView) {
//            super(itemView);
//            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
//            iv_profile = itemView.findViewById(R.id.iv_profile);
//            iv_heart = itemView.findViewById(R.id.iv_heart);
//            iv_post_image = itemView.findViewById(R.id.iv_post_image);
//            card_image = itemView.findViewById(R.id.card_image);
//            name_friend = itemView.findViewById(R.id.name_friend);
//        }
//    }

//    public class ViewHolderRestaurant extends RecyclerView.ViewHolder {
//        ImageView iv_heart_red;
//        ImageView iv_post_image;
//        ImageView iv_heart;
//        CardView card_image;
//        TextView name_friend;
//        ImageView iv_profile;
//
//        public ViewHolderRestaurant(@NonNull View itemView) {
//            super(itemView);
//            iv_post_image = itemView.findViewById(R.id.iv_post_image);
//            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
//            iv_profile = itemView.findViewById(R.id.iv_profile);
//            iv_heart = itemView.findViewById(R.id.iv_heart);
//            card_image = itemView.findViewById(R.id.card_image);
//            name_friend = itemView.findViewById(R.id.name_friend);
//        }
//    }

    public class ViewHolderAnonymQuestion extends AAH_CustomViewHolder {
        ImageView iv_more;
        ImageView iv_post_image;
        CardView card_image;
        TextView name_friend, question_Anonymous;
        TextView txt_ano_question;
        TextView txt_chat;
        TextView edt_comment;
        Button respond, delete_btn;

        //        ImageView iv_profile;
        public ViewHolderAnonymQuestion(@NonNull View itemView) {
            super(itemView);
            iv_more = itemView.findViewById(R.id.iv_more);
            card_image = itemView.findViewById(R.id.card_image);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            edt_comment = itemView.findViewById(R.id.edt_comment);
            question_Anonymous = itemView.findViewById(R.id.question_Anonymous);
            txt_ano_question = itemView.findViewById(R.id.txt_ano_question);
//            iv_heart = itemView.findViewById(R.id.iv_heart);
//            iv_profile = itemView.findViewById(R.id.iv_profile);
            name_friend = itemView.findViewById(R.id.name_friend);
            txt_chat = itemView.findViewById(R.id.txt_chat);
            respond = itemView.findViewById(R.id.respond);
            delete_btn = itemView.findViewById(R.id.delete_btn);

            respond.setOnClickListener(v -> {
//                    Activity origin = (Activity) mContext;
                Intent mIntent = new Intent(activity, AnonymousQuestionActivity.class);
                mIntent.putExtra("Anonymous", true);
                mIntent.putExtra("post_id", lst.get(getAdapterPosition()).getPostId());
                mIntent.putExtra("user_id", lst.get(getAdapterPosition()).getUserId());
                activity.startActivityForResult(mIntent, 410);
            });
            edt_comment.setOnClickListener(v -> {
//                    Activity origin = (Activity) mContext;
                Intent mIntent = new Intent(activity, AnonymousQuestionActivity.class);
                mIntent.putExtra("Anonymous", true);
                mIntent.putExtra("post_id", lst.get(getAdapterPosition()).getPostId());
                mIntent.putExtra("user_id", lst.get(getAdapterPosition()).getUserId());
                activity.startActivityForResult(mIntent, 410);
            });
            delete_btn.setOnClickListener(v -> callAPIDeletePost(lst.get(getAdapterPosition()).getPostId(), getAdapterPosition()));
        }
    }

    private void callAPIDeletePost(String postId, int adapterPosition) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);

        Call<LoginResponse> call = service.deletePost("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        lst.remove(adapterPosition);
        notifyDataSetChanged();
    }

    public void DeleteItem(int index) {
        try {
            if (null != lst) {

                lst.remove(index);
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshPage() {
        try {

            OnRefreshPage ref = fragSocialFeed;
            ref.LoadPageAgain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Context mContext;


    @Override
    public int getItemCount() {
        if (lst == null) {
            return 0;
        }
        return lst.size();
    }

}

class ImageLoadedCallback implements Callback {
    LottieAnimationView iv_loader;

    public ImageLoadedCallback(LottieAnimationView view) {
        iv_loader = view;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Exception e) {

    }


}