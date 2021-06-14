package com.playdate.app.ui.anonymous_question;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCommentData;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.util.session.SessionPref;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comments> list = new ArrayList<>();
    ArrayList<GetCommentData> commentList = new ArrayList<>();
    boolean selected = false;
    int selected_index = -1;
    Onclick itemClick;
    String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    Context mContext;
    onCommentDelete ref;
    String userId, postId, commentId;
    String timeFormat, timeFormat_new;

    public CommentAdapter(Context applicationContext, ArrayList<GetCommentData> lst_getComment, Onclick itemClick) {
        this.commentList = lst_getComment;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        SessionPref pref = SessionPref.getInstance(mContext);
        holder.name.setText(commentList.get(position).getUsername());
        holder.desc.setText(commentList.get(position).getComments().getComment());
        userId = commentList.get(position).getUserId();
        commentId = commentList.get(position).getComments().getCommentId();
        postId = commentList.get(position).getComments().getPostId();
        timeFormat = commentList.get(position).getComments().getEntryDate();


        timeFormat = timeFormat.replace("T", " ");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GTC"));
        Date date = null;
        try {
            date = df.parse(timeFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ddd = null;
        try {
            ddd = sdf.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = ddd.getTime();
        String text = TimeAgo.using(millis);
        holder.time.setText(text.toLowerCase());
        Log.e("MyFinalValue", "" + text);


//        if (selected_index == position) {
//            Log.e("selected_index..",""+selected_index);

        if (commentList.get(position).isSelected) {
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_grey));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.white));
            if (commentList.get(position).getUserId().equals(pref.getStringVal(SessionPref.LoginUserID))) {
                holder.delete.setVisibility(View.VISIBLE);
            } else {
                holder.delete.setVisibility(View.GONE);
            }
        } else {
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.delete.setVisibility(View.GONE);
        }

      /*  if (commentList.get(position).isSelected) {
          //  holder.relativeLayout.setBackgroundColor(Color.parseColor("#88000000"));
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.textColour));
            if (commentList.get(position).getUserId().equals(pref.getStringVal(SessionPref.LoginUserID))) {
                holder.delete.setVisibility(View.VISIBLE);
            } else {
                holder.delete.setVisibility(View.GONE);
            }
        } else {
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundColour));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.textColour));
            holder.delete.setVisibility(View.GONE);


        }*/

        holder.relativeLayout.setOnLongClickListener(v -> {
            int selected_index = position;
            Log.e("relativeLayout", "relativeLayout" + selected_index);
            if (!commentList.get(selected_index).isSelected) {

                for (int i = 0; i < commentList.size(); i++) {
                    if (selected_index != i) {
                        commentList.get(i).setSelected(false);
                    } else {
                        commentList.get(selected_index).setSelected(true);
                        itemClick.onItemClicks(v, selected_index, 11, commentList.get(position).getComments().getCommentId(), postId, commentList.get(position).getUserId());
                    }
                }

                notifyDataSetChanged();


            } else {
                commentList.get(selected_index).setSelected(false);
                notifyDataSetChanged();
            }
            return true;
        });

        //   }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, like, reply, time;
        RelativeLayout relativeLayout;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_name);
            desc = itemView.findViewById(R.id.comment_desc);
            like = itemView.findViewById(R.id.comment_like);
            reply = itemView.findViewById(R.id.comment_reply);
            time = itemView.findViewById(R.id.comment_time);
            delete = itemView.findViewById(R.id.dustbin);
            relativeLayout = itemView.findViewById(R.id.card_comment);
            name.setTypeface(Typeface.DEFAULT_BOLD);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_index = getAdapterPosition();
                    commentList.get(selected_index).setDeleted(true);
                    callDeleteCommentApi();
                    //  commentdeleted(selected_index);


                }
            });

        }

    }

    private void callDeleteCommentApi() {
        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", userId);
        hashMap.put("postId", postId);
        hashMap.put("commentId", commentId);
        Call<GetCommentModel> call = service.deletePostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetCommentModel>() {
            @Override
            public void onResponse(Call<GetCommentModel> call, Response<GetCommentModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        commentdeleted(selected_index);
                        callGetCommentApi();

                    } else {

                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void commentdeleted(int selected_index) {


        if (commentList.get(selected_index).isDeleted) {
            try {
                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragCommentDeleted deleted = new FragCommentDeleted();
                deleted.show(fragmentManager, "comment deleted");
                commentList.remove(selected_index);
                notifyDataSetChanged();
                ref.ChangeCount(commentList.size());
            } catch (Exception e) {
                e.printStackTrace();
                ref.ChangeCount(0);
            }
        } else {
            //code for undo
        }

    }

    private void callGetCommentApi() {

        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("postId", postId);
        Call<GetCommentModel> call = service.getPostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetCommentModel>() {
            @Override
            public void onResponse(Call<GetCommentModel> call, Response<GetCommentModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        commentList = (ArrayList<GetCommentData>) response.body().getData();
                        if (commentList == null) {
                            commentList = new ArrayList<>();
                        }

                        notifyDataSetChanged();

                    } else {

                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String getFormattedDate(Date timeZone) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(timeZone);
        return formattedDate;

    }
}

