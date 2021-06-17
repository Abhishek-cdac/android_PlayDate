package com.connectycube.messenger

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.connectycube.messenger.utilities.loadAttachImagePreview
import com.playdate.app.R
import kotlinx.android.synthetic.main.activity_attachment_preview.*

const val EXTRA_URL = "attach_url"

class AttachmentPreviewActivity : BaseChatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attachment_preview)
        initToolBar()
        loadAttachment()
    }

    private fun initToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.color_light_black
                )
            )
        )
    }

    private fun loadAttachment() {
        val url = intent.getStringExtra(EXTRA_URL)

        if (url.isNullOrEmpty()) {
            image_view.setImageResource(R.drawable.person)
            return
        }
        loadAttachImagePreview(url, image_view, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}