package com.playdate.app.ui.anonymous_question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.playdate.app.R
import com.playdate.app.data.api.GetDataService
import com.playdate.app.data.api.RetrofitClientInstance
import com.playdate.app.model.GetCommentModel
import com.playdate.app.model.LoginResponse
import com.playdate.app.ui.dashboard.DashboardActivity
import com.playdate.app.util.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AnonymousBottomSheet : BottomSheetDialogFragment() {
    private var postId: String? = null
    private var userId: String? = null
    private var postIdA: String? = null
    private var userIdA: String? = null
    private var commentId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_anonymous_bottom_sheet, container, false)
        val bundle = this.arguments
        postId = bundle!!.getString("postIdAQ")
        postIdA = bundle.getString("post_id")
        userId = bundle.getString("userIdAQ")
        userIdA = bundle.getString("user_id")
        commentId = bundle.getString("commentIdAQ")
        val rl_comment_on_off = view.findViewById<RelativeLayout>(R.id.rl_comment_on_off)
        val switch_comment_onoff: SwitchCompat = view.findViewById(R.id.switch_comment_onoff)
        val report_comment_rl = view.findViewById<RelativeLayout>(R.id.report_comment_rl)
        val pref = SessionPref.getInstance(activity)
        try {
            if (userIdA == pref.getStringVal(SessionPref.LoginUserID)) {
                rl_comment_on_off.visibility = View.VISIBLE
            } else {
                rl_comment_on_off.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        report_comment_rl.setOnClickListener { callReportCommentApi(postId, userId, commentId) }
        switch_comment_onoff.setOnCheckedChangeListener { buttonView, isChecked ->
            callAPICommentONOff(isChecked)
            dismiss()
        }
        return view
    }

    private fun callAPICommentONOff(comment: Boolean) {
        val pref = SessionPref.getInstance(activity)
        val service = RetrofitClientInstance.getRetrofitInstance().create(
            GetDataService::class.java
        )
        val hashMap: MutableMap<String, String?> = HashMap()
        hashMap["postId"] = postIdA
        hashMap["userId"] = userIdA
        if (comment) {
            hashMap["commentStatus"] = "1"
        } else {
            hashMap["commentStatus"] = "0"
        }
        val call = service.postCommentOnOff(
            "Bearer " + pref.getStringVal(SessionPref.LoginUsertoken),
            hashMap
        )
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun callReportCommentApi(postId: String?, userId: String?, commentId: String?) {
        val pref = SessionPref.getInstance(activity)
        val service = RetrofitClientInstance.getRetrofitInstance().create(
            GetDataService::class.java
        )
        val hashMap: MutableMap<String, String?> = HashMap()
        hashMap["postId"] = postId
        hashMap["userId"] = userId
        hashMap["commentId"] = commentId
        val call = service.reportPostComment(
            "Bearer " + pref.getStringVal(SessionPref.LoginUsertoken),
            hashMap
        )
        call.enqueue(object : Callback<GetCommentModel> {
            override fun onResponse(
                call: Call<GetCommentModel>,
                response: Response<GetCommentModel>
            ) {
                if (response.code() == 200) {
                    if (response.body()!!.status == 1L) {
                        Toast.makeText(activity, "" + response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, DashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(activity, "" + response.body()!!.message, Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(activity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<GetCommentModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}