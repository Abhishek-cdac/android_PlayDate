package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Invite;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    private final ArrayList<Invite> invite_list = new ArrayList<>();
    private FragmentManager fm;
    private final String inviteCode;
    private final String inviteLink;

    public InviteAdapter(String inviteCode, String inviteLink) {
        this.inviteCode = inviteCode;
        this.inviteLink = inviteLink;

        invite_list.add(new Invite(R.drawable.business_ic_icon_feather_user, "Follow Contacts"));
        invite_list.add(new Invite(R.drawable.business_ic_facebook, "Invite Friends by Facebook"));
        invite_list.add(new Invite(R.drawable.business_ic_icon_message_square, "Invite Friends by SMS"));
        invite_list.add(new Invite(R.drawable.business_ic_icon_feather_upload, "Invite Friends by..."));
    }

    Context mContext;

    @NonNull
    @Override
    public InviteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_invite, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);

    }

    public void ReplaceFrag(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.invite_listView, fragment, fragment.getClass().getSimpleName());
//        ft.addToBackStack("tags");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBindViewHolder(@NonNull InviteAdapter.ViewHolder holder, int position) {
        holder.title.setText(invite_list.get(position).getTitle());
        holder.icon.setImageResource(invite_list.get(position).getImage());

        holder.title.setOnClickListener(v -> {
            switch (position) {
                case 0:
                    break;
                case 1:
                    facebook();
                    break;
                case 2:
                case 3:
                    shareTextUrl();
                    break;
            }
        });


    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        mContext.startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
    }

    private void facebook() {
        Intent fbIntent = new Intent(Intent.ACTION_SEND);
        fbIntent.setType("text/plain");
        fbIntent.setPackage("com.facebook.katana");
        fbIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
        try {
            mContext.startActivity(fbIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public int getItemCount() {
        return invite_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icons_invite);
            title = itemView.findViewById(R.id.title_invite);

        }
    }
}
