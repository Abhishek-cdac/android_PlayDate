package com.playdate.app.ui.anonymous_question;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCommentData;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comments> list = new ArrayList<>();
    ArrayList<GetCommentData> commentList = new ArrayList<>();
    boolean selected = false;
    int selected_index = -1;

    Context mContext;
    onCommentDelete ref;
    String userId, postId, commentId;

    public CommentAdapter(Context applicationContext, ArrayList<GetCommentData> lst_getComment) {
        this.commentList = lst_getComment;
        this.mContext = applicationContext;

    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.name.setText(commentList.get(position).getUsername());
        holder.desc.setText(commentList.get(position).getComments().getComment());

        userId = commentList.get(position).getUserId();
        commentId = commentList.get(position).getComments().getCommentId();
        postId = commentList.get(position).getComments().getPostId();

        Log.e("commentId", "" + commentId);
        Log.e("userId", "" + userId);
        Log.e("postId", "" + postId);

//        if (selected_index == position) {
//            Log.e("selected_index..",""+selected_index);

        if (commentList.get(position).isSelected) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#88000000"));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.delete.setVisibility(View.VISIBLE);


        } else {
            holder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.name.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.desc.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.like.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.reply.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.delete.setVisibility(View.GONE);

        }
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
            relativeLayout.setOnLongClickListener(v -> {

                int selected_index = getAdapterPosition();
                Log.e("relativeLayout", "relativeLayout" + selected_index);
                if (!commentList.get(selected_index).isSelected) {

                    for (int i = 0; i < commentList.size(); i++) {
                        if (selected_index != i) {
                            commentList.get(i).setSelected(false);
                        } else {
                            commentList.get(selected_index).setSelected(true);
                        }

                    }

                    notifyDataSetChanged();
                } else {
                    commentList.get(selected_index).setSelected(false);
                    notifyDataSetChanged();
                }
                return true;
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
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            FragCommentDeleted deleted = new FragCommentDeleted();
            deleted.show(fragmentManager, "comment deleted");
            commentList.remove(selected_index);
            notifyDataSetChanged();
          //  ref.ChangeCount(commentList.size());
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


}
