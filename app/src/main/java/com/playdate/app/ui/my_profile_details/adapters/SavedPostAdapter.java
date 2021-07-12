package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.SavedPostData;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.photoview.PhotoViewActivity;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SavedPostAdapter extends RecyclerView.Adapter<SavedPostAdapter.ViewHolder> {


    private final FragmentActivity activity;

    @Override
    public int getItemCount() {
        return lst.size();
    }

    private final Picasso picasso;
    private final ArrayList<SavedPostData> lst;

    public SavedPostAdapter(FragmentActivity activity, ArrayList<SavedPostData> lst) {
        this.activity = activity;
        this.lst = lst;
        picasso = Picasso.get();
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_post_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SavedPostAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }


    void removeItem(int index) {
        lst.remove(index);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_post_image;
        private final ImageView play_icon;
        private final CardView card_grid;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_post_image = itemView.findViewById(R.id.iv_media);
            play_icon = itemView.findViewById(R.id.play_icon);
            card_grid = itemView.findViewById(R.id.card_grid);
            card_grid.setOnClickListener(v -> {
                try {
                    int pos = getAdapterPosition();
                    if (lst.get(pos).getMediaType().toLowerCase().equals("image")) {
                        Intent mIntent = new Intent(activity, PhotoViewActivity.class);
                        mIntent.putExtra("data", lst.get(pos).getMediaFullPath());
                        //                    mIntent.putExtra("id", lst.get(pos).getId());
                        mIntent.putExtra("isVideo", false);
                        //                    ActivityOptionsCompat options = ActivityOptionsCompat.
                        //                            makeSceneTransitionAnimation(activity, iv_post_image, "profile");
                        activity.startActivity(mIntent);
                    } else {
                        Intent mIntent = new Intent(activity, ExoPlayerActivity.class);
                        mIntent.putExtra("video", lst.get(pos).getMediaFullPath());
                        activity.startActivity(mIntent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }

        public void setData(int position) {
            try {
                if (lst.get(position).getMediaType().toLowerCase().equals("image")) {
                    picasso.load(lst.get(position).getMediaFullPath()).into(iv_post_image);
                    play_icon.setVisibility(View.GONE);
                } else {//Video
                    picasso.load(lst.get(position).getMediaThumbName()).into(iv_post_image);
                    play_icon.setVisibility(View.VISIBLE);
                }

                card_grid.setOnLongClickListener(v -> {

                    UnLike un = new UnLike(lst.get(position).getPostId(), position, activity, SavedPostAdapter.this);
                    un.show(activity.getSupportFragmentManager(), "ModalBottomSheet");
                    return false;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public static class UnLike extends BottomSheetDialogFragment {

        String id;
        int pos;
        Context mContext;
        SavedPostAdapter savedPostAdapter;

        public UnLike(String id, int position, Context mContext, SavedPostAdapter savedPostAdapter) {
            this.id = id;
            this.pos = position;
            this.mContext = mContext;
            this.savedPostAdapter = savedPostAdapter;

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view;

            view = inflater.inflate(R.layout.frag_unlike, container, false);
            LinearLayout ll_close = view.findViewById(R.id.ll_close);
            ll_close.setOnClickListener(v -> {
                savedPostAdapter.removeItem(pos);
                callSavePostAPI();
                dismiss();
            });
            return view;
        }

        private void callSavePostAPI() {

            SessionPref pref = SessionPref.getInstance(mContext);
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
            hashMap.put("postId", id);
            hashMap.put("status", "Delete");

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
    }

}

