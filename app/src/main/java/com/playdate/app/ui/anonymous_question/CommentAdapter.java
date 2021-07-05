package com.playdate.app.ui.anonymous_question;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCommentData;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.util.session.SessionPref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<GetCommentData> commentList;
    private int selected_index = -1;
    private final Onclick itemClick;
    private Context mContext;
    private onCommentDelete ref;
    private String postId;
    private String commentId;

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

        String name = commentList.get(position).getUsername();
        String comment = commentList.get(position).getComments().getComment();

        String sourceString = "<b>" + name + "</b> " + comment;
        holder.name.setText(Html.fromHtml(sourceString));


        commentId = commentList.get(position).getComments().getCommentId();
        postId = commentList.get(position).getComments().getPostId();
        String timeFormat = commentList.get(position).getComments().getEntryDate();


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


        holder.relativeLayout.setOnLongClickListener(v -> {
            int selected_index = position;
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

        holder.delete.setOnClickListener(v -> {
            try {
                selected_index = position;
                commentList.get(selected_index).setDeleted(true);
                callDeleteCommentApi();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

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
        }

    }

    private void callDeleteCommentApi() {
        SessionPref pref = SessionPref.getInstance(mContext);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        Log.e("deleteCommentPostId", "" + postId);
        Log.e("commentId", "" + commentId);
        Log.e("deleteCommentUId", "" + pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        hashMap.put("commentId", commentId);
        Call<GetCommentModel> call = service.deletePostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetCommentModel>() {
            @Override
            public void onResponse(Call<GetCommentModel> call, Response<GetCommentModel> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        commentDeleted(selected_index);
                        callGetCommentApi();

                    }
                }


            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void commentDeleted(int selected_index) {


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
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        commentList = (ArrayList<GetCommentData>) response.body().getData();
                        if (commentList == null) {
                            commentList = new ArrayList<>();
                        }

                        notifyDataSetChanged();

                    }
                }


            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}

