package com.playdate.app.ui.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.anonymous_question.AnonymousQuestionActivity;
import com.playdate.app.ui.anonymous_question.AnonymousBottomSheet;
import com.playdate.app.ui.anonymous_question.CommentBottomSheet;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.social.model.PostDetails;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class SocialFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    @Override
    public int getItemCount() {
        return lst.size();
    }

    ArrayList<PostDetails> lst;

    public SocialFeedAdapter(ArrayList<PostDetails> lst) {
        this.lst = lst;
    }

    public void animateHeart(final ImageView view, ViewHolderUser holder) {

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

        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.clearAnimation();

                holder.iv_heart_red.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        }, 900);


    }

    private void callAPI(String postId, int like) {
        SessionPref pref = SessionPref.getInstance(mContext);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        if (like == 1) {
            hashMap.put("status", "UnLike");
        } else {
            hashMap.put("status", "Like");
        }

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<LoginResponse> call = service.addPostLikeUnlike("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                    } else {
                    }
                } else {

                }


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

        String type = lst.get(position).getPostType();
        switch (type) {
            case FragSocialFeed.NORMAL:
                return 0;

//            case FragSocialFeed.RESTAURANT:
//                return FragSocialFeed.RESTAURANT;
//
//            case FragSocialFeed.ANONYMUSQUESTION:
//                return FragSocialFeed.ANONYMUSQUESTION;
//
//            case FragSocialFeed.ADDS:
//                return FragSocialFeed.ADDS;


        }
        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        mContext = parent.getContext();
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
            viewHolder = new ViewHolderUser(view);
        }
//        else if (viewType == FragSocialFeed.RESTAURANT) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
//            viewHolder = new ViewHolderRestaurant(view);
//        }
//        else if (viewType == FragSocialFeed.ADDS) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_4, parent, false);
//            viewHolder = new ViewHolderAdds(view);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_3, parent, false);
//            viewHolder = new ViewHolderAnonymQuestion(view);
//        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            ViewHolderUser userViewHolder = (ViewHolderUser) holder;
            userViewHolder.name_friend.setText(lst.get(position).getLstpostby().get(0).getUsername());

            userViewHolder.name_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
                    ref.loadProfile();
                }
            });

            userViewHolder.iv_post_image.setOnClickListener(view -> {
//                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
//                    ref.loadProfile();
            });

            Picasso.get().load(lst.get(position).getPostMedia().get(0).getMediaFullPath())


                    .into(userViewHolder.iv_post_image, new ImageLoadedCallback(userViewHolder.animationView) {
                        @Override
                        public void onSuccess() {
                            if (this.iv_loader != null) {
                                this.iv_loader.setVisibility(View.GONE);
                            }
                        }
                    });

            Picasso.get().load(lst.get(position).getLstpostby().get(0).getProfilePicPath())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(userViewHolder.iv_profile);

            if (lst.get(position).getLikes() == 1) {
                userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
            } else {
                userViewHolder.iv_heart.setImageResource(R.drawable.heart);
            }

            userViewHolder.iv_heart.setOnClickListener(view -> {
                if (lst.get(position).getLikes() == 1) {
                    userViewHolder.iv_heart.setImageResource(R.drawable.heart);
                    lst.get(position).setTapCount(0);
                    lst.get(position).setHeartSelected(false);
                    notifyDataSetChanged();
                    callAPI(lst.get(position).getPostId(), lst.get(position).getLikes());
                } else {
                    userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
                }
            });
            userViewHolder.iv_post_image.setOnClickListener(view -> {

                if (!lst.get(position).isHeartSelected()) {
                    if (lst.get(position).getTapCount() == 1) {

                        lst.get(position).setHeartSelected(true);
                        userViewHolder.iv_heart_red.setVisibility(View.VISIBLE);
                        callAPI(lst.get(position).getPostId(),lst.get(position).getLikes());
                        animateHeart(userViewHolder.iv_heart_red, userViewHolder);

                    } else {
                        lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                        userViewHolder.iv_heart_red.setVisibility(View.GONE);
                    }

                } else {
                    lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                }
            });

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

    public class ViewHolderUser extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView iv_heart;
        ImageView iv_msg;
        LottieAnimationView animationView;
        ImageView iv_post_image, iv_more_options;
        CardView card_image;
        TextView name_friend;
        //        EditText et_comment;
        TextView et_comment;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
            iv_msg = itemView.findViewById(R.id.iv_msg);
            animationView = itemView.findViewById(R.id.animationView);
            et_comment = itemView.findViewById(R.id.edt_comment);

            iv_more_options = itemView.findViewById(R.id.friend_request);
            iv_more_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    CommentBottomSheet sheet = new CommentBottomSheet();
                    sheet.show(fragmentManager, "comment bootom sheet");
                }
            });

            iv_msg.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), AnonymousQuestionActivity.class)));
            et_comment.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), AnonymousQuestionActivity.class)));

        }
    }

    public class ViewHolderAdds extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView iv_heart;
        ImageView iv_post_image;
        CardView card_image;
        TextView name_friend;

        public ViewHolderAdds(@NonNull View itemView) {
            super(itemView);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
        }
    }

    public class ViewHolderRestaurant extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        ImageView iv_post_image;
        ImageView iv_heart;
        CardView card_image;
        TextView name_friend;
        ImageView iv_profile;

        public ViewHolderRestaurant(@NonNull View itemView) {
            super(itemView);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
        }
    }

    public class ViewHolderAnonymQuestion extends RecyclerView.ViewHolder {
        ImageView iv_more;
        ImageView iv_post_image;
        //        ImageView iv_heart;
//        CardView card_image;
        TextView name_friend;
        Button respomd;

        //        ImageView iv_profile;
        public ViewHolderAnonymQuestion(@NonNull View itemView) {
            super(itemView);
            iv_more = itemView.findViewById(R.id.iv_more);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
//            iv_heart = itemView.findViewById(R.id.iv_heart);
//            card_image = itemView.findViewById(R.id.card_image);
//            iv_profile = itemView.findViewById(R.id.iv_profile);
            name_friend = itemView.findViewById(R.id.name_friend);
            respomd = itemView.findViewById(R.id.respond);

            respomd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity origin = (Activity) mContext;
                    Intent mIntent = new Intent(origin, AnonymousQuestionActivity.class);
                    mIntent.putExtra("Anonymous", true);
                    origin.startActivityForResult(mIntent, 410);
                }
            });
//            respomd.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), AnonymousQuestionActivity.class)));
        }
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