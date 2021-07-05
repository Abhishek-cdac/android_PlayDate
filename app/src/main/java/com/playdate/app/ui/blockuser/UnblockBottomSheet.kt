package com.playdate.app.ui.blockuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.playdate.app.R
import com.playdate.app.data.api.GetDataService
import com.playdate.app.data.api.RetrofitClientInstance
import com.playdate.app.model.LoginResponse
import com.playdate.app.util.common.TransparentProgressDialog
import com.playdate.app.util.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UnblockBottomSheet(
    private val userID: String,
    private val blockUserActivity: BlockUserActivity
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_unblock_bottom_sheet, container, false)
        val rl_unblock = view.findViewById<RelativeLayout>(R.id.rl_unblock)
        rl_unblock.setOnClickListener { v: View? -> callUnBlockUser() }
        return view
    }

    private fun callUnBlockUser() {
        val pref = SessionPref.getInstance(activity)
        val service = RetrofitClientInstance.getRetrofitInstance().create(
            GetDataService::class.java
        )
        val hashMap: MutableMap<String, String> = HashMap()
        hashMap["userId"] = pref.getStringVal(SessionPref.LoginUserID)
        hashMap["action"] = "Block" //Block or Report
        hashMap["toUserId"] = userID
        val pd = TransparentProgressDialog.getInstance(activity)
        pd.show()
        val call = service.removeUserReportBlock(
            "Bearer " + pref.getStringVal(SessionPref.LoginUsertoken),
            hashMap
        )
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                pd.cancel()
                dismiss()
                if (response.code() == 200) {
                    if (response.body()!!.status == 1) {
                        Toast.makeText(
                            activity,
                            "User unblocked successsfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        blockUserActivity.callAPI()
                    } else {
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                pd.cancel()
            }
        })
    }
}