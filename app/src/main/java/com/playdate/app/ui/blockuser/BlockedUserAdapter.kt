package com.playdate.app.ui.blockuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.playdate.app.R
import com.playdate.app.model.BlockedUser
import com.squareup.picasso.Picasso
import java.util.*

class BlockedUserAdapter(
    private val list: ArrayList<BlockedUser>,
    private val blockUserActivity: BlockUserActivity
) : RecyclerView.Adapter<BlockedUserAdapter.ViewHolder>() {
    private val picasso: Picasso = Picasso.get()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_blocked_users, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_name: TextView
        var img_friend: ImageView
        fun setData(user: BlockedUser) {
            txt_name.text = user.username
            picasso.load(user.profilePicPath).into(img_friend)
        }

        init {
            txt_name = itemView.findViewById(R.id.txt_name)
            img_friend = itemView.findViewById(R.id.img_friend)
            itemView.setOnClickListener {
                blockUserActivity.showModel(
                    list[adapterPosition].toUserId
                )
            }
        }
    }

}