package com.playdate.app.ui.blockuser

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.playdate.app.R
import com.playdate.app.data.api.GetDataService
import com.playdate.app.data.api.RetrofitClientInstance
import com.playdate.app.model.CommonModel
import com.playdate.app.util.common.TransparentProgressDialog
import com.playdate.app.util.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BlockUserActivity : AppCompatActivity() {
    private var recycler_blocked_users: RecyclerView? = null
    private var no_block_users: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_user)
        recycler_blocked_users = findViewById(R.id.recycler_blocked_users)
        no_block_users = findViewById(R.id.no_block_users)

        val iv_back = findViewById<ImageView>(R.id.iv_back)
        iv_back.setOnClickListener { finish() }
        callAPI()
    }

    fun callAPI() {
        val pref = SessionPref.getInstance(this)
        val service = RetrofitClientInstance.getRetrofitInstance().create(
            GetDataService::class.java
        )
        val hashMap: MutableMap<String, String> = HashMap()
        hashMap["userId"] = pref.getStringVal(SessionPref.LoginUserID)
        val pd = TransparentProgressDialog.getInstance(this)
        pd.show()
        val call = service.getUserBlocked(
            "Bearer " + pref.getStringVal(SessionPref.LoginUsertoken),
            hashMap
        )
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(call: Call<CommonModel>, response: Response<CommonModel>) {
                pd.cancel()
                if (response.code() == 200) {
                    if (response.body()!!.status == 1L) {
                        var list = response.body()!!.getBlockedUsers()
                        if (null == list) {
                            list = ArrayList()
                        }
                        if (list.size == 0) {
                            no_block_users!!.visibility = View.VISIBLE
                            recycler_blocked_users!!.visibility = View.GONE
                        } else {
                            recycler_blocked_users?.setLayoutManager(LinearLayoutManager(this@BlockUserActivity))
                            val adapter = BlockedUserAdapter(list, this@BlockUserActivity)
                            recycler_blocked_users!!.adapter = adapter
                        }
                    } else {
                        no_block_users!!.visibility = View.VISIBLE
                        recycler_blocked_users!!.visibility = View.GONE
                    }
                } else {
                    no_block_users!!.visibility = View.VISIBLE
                    recycler_blocked_users!!.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                t.printStackTrace()
                pd.cancel()
                no_block_users!!.visibility = View.VISIBLE
                recycler_blocked_users!!.visibility = View.GONE
            }
        })
    }

    fun showModel(userID: String?) {
        try {
            val bottomSheet = userID?.let { UnblockBottomSheet(it, this) }
            bottomSheet?.show(supportFragmentManager, "ModalBottomSheet")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}