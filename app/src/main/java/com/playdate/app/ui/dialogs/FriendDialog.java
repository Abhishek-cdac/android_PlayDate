package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.interfaces.OnRelationShipSelected;
import com.playdate.app.ui.invite.InviteFriendActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendDialog extends Dialog implements OnRelationShipSelected {

    public FriendDialog(Context context, ArrayList<MatchListUser> lstUserSuggestions, boolean isForRelationship, String inviteLink) {
        super(context, R.style.My_Dialog);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_friend_list, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        FriendAdapter adapter = new FriendAdapter(lstUserSuggestions, isForRelationship, FriendDialog.this);
        recycler_view.setAdapter(adapter);
        TextView txt_cancel = view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(v -> dismiss());
        ImageView iv_next = view.findViewById(R.id.iv_next);
        EditText edt_search = view.findViewById(R.id.edt_search);
        Button btn_not_on_playdate = view.findViewById(R.id.btn_not_on_playdate);
        if (isForRelationship) {
            btn_not_on_playdate.setVisibility(View.VISIBLE);
            iv_next.setVisibility(View.GONE);
        }
        btn_not_on_playdate.setOnClickListener(v -> {
            try {
                dismiss();
                Intent intent = new Intent(context, InviteFriendActivity.class);
                intent.putExtra("inviteCode", inviteLink);
                intent.putExtra("inviteLink", inviteLink);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        iv_next.setOnClickListener(v -> dismiss());
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    ArrayList<MatchListUser> temp = new ArrayList<>();
                    for (int i = 0; i < lstUserSuggestions.size(); i++) {
                        if (s.toString().toLowerCase().trim().contains(lstUserSuggestions.get(i).getUsername().toLowerCase().trim())) {
                            temp.add(lstUserSuggestions.get(i));
                        }

                    }
                    FriendAdapter adapter = new FriendAdapter(temp, isForRelationship, FriendDialog.this);
                    recycler_view.setAdapter(adapter);
                } else {
                    FriendAdapter adapter = new FriendAdapter(lstUserSuggestions, isForRelationship, FriendDialog.this);
                    recycler_view.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void closed() {
        dismiss();
    }
}


class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context mContext;
    private final ArrayList<MatchListUser> lstUserSuggestions;
    private final boolean isForRelationship;
    private final FriendDialog ref;

    public FriendAdapter(ArrayList<MatchListUser> lstUserSuggestions, boolean isForRelationship, FriendDialog ref) {
        this.lstUserSuggestions = lstUserSuggestions;
        this.isForRelationship = isForRelationship;
        this.ref = ref;
    }

    private final Picasso picasso = Picasso.get();

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friends_dialog, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        try {
            holder.name.setText(lstUserSuggestions.get(position).getUsername());
            if (lstUserSuggestions.get(position).getProfilePicPath() == null) {
                holder.image.setBackgroundColor(mContext.getResources().getColor(R.color.color_grey_light));
            }
            if (lstUserSuggestions.get(position).isSelected()) {
                holder.iv_select.setVisibility(View.VISIBLE);
            } else {
                holder.iv_select.setVisibility(View.GONE);
            }

            picasso.load(lstUserSuggestions.get(position).getProfilePicPath()).placeholder(R.drawable.ic_baseline_person_24)
                    .fit()
                    .placeholder(R.drawable.profile)
                    .centerCrop()
                    .into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if (lstUserSuggestions == null)
            return 0;
        return lstUserSuggestions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        ImageView iv_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            iv_select = itemView.findViewById(R.id.iv_select);
            itemView.setOnClickListener(v -> {
                if (isForRelationship) {
                    lstUserSuggestions.get(getAbsoluteAdapterPosition()).setSelected(!lstUserSuggestions.get(getAbsoluteAdapterPosition()).isSelected());
                    ref.closed();
                } else {
                    lstUserSuggestions.get(getAbsoluteAdapterPosition()).setSelected(!lstUserSuggestions.get(getAbsoluteAdapterPosition()).isSelected());
                    notifyDataSetChanged();
                }

            });

        }
    }
}
