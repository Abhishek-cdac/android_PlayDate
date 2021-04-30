package com.playdate.app.util.customcamera.otalia;

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.size.AspectRatio
import com.playdate.app.R
import com.playdate.app.ui.dashboard.DashboardActivity
import com.playdate.app.ui.register.interest.InterestActivity
import com.playdate.app.ui.restaurant.RestaurantActivity

class VideoPreviewActivity : AppCompatActivity() {
    companion object {
        var videoResult: VideoResult? = null
    }

    private val videoView: VideoView by lazy { findViewById<VideoView>(R.id.video) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_preview)
        val result = videoResult ?: run {
            finish()
            return
        }
        videoView.setOnClickListener { playVideo() }
        val iv_next = findViewById<ImageView>(R.id.iv_next).setOnClickListener(View.OnClickListener {
            val intent = Intent(this@VideoPreviewActivity, DashboardActivity::class.java)
            startActivity(intent)
        })
//        val isSnapshot = findViewById<MessageView>(R.id.isSnapshot)
//        val rotation = findViewById<MessageView>(R.id.rotation)
//        val audio = findViewById<MessageView>(R.id.audio)
//        val audioBitRate = findViewById<MessageView>(R.id.audioBitRate)
//        val videoCodec = findViewById<MessageView>(R.id.videoCodec)
//        val audioCodec = findViewById<MessageView>(R.id.audioCodec)
//        val videoBitRate = findViewById<MessageView>(R.id.videoBitRate)
//        val videoFrameRate = findViewById<MessageView>(R.id.videoFrameRate)

        val ratio = AspectRatio.of(result.size)
//        actualResolution.setTitleAndMessage("Size", "${result.size} ($ratio)")
//        isSnapshot.setTitleAndMessage("Snapshot", result.isSnapshot.toString())
//        rotation.setTitleAndMessage("Rotation", result.rotation.toString())
//        audio.setTitleAndMessage("Audio", result.audio.name)
//        audioBitRate.setTitleAndMessage("Audio bit rate", "${result.audioBitRate} bits per sec.")
//        videoCodec.setTitleAndMessage("VideoCodec", result.videoCodec.name)
//        audioCodec.setTitleAndMessage("AudioCodec", result.audioCodec.name)
//        videoBitRate.setTitleAndMessage("Video bit rate", "${result.videoBitRate} bits per sec.")
//        videoFrameRate.setTitleAndMessage("Video frame rate", "${result.videoFrameRate} fps")

        val controller = MediaController(this)
        controller.setAnchorView(videoView)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
        videoView.setVideoURI(Uri.fromFile(result.file))
        videoView.setOnPreparedListener { mp ->
            val lp = videoView.layoutParams
            val videoWidth = mp.videoWidth.toFloat()
            val videoHeight = mp.videoHeight.toFloat()
            val viewWidth = videoView.width.toFloat()
            lp.height = (viewWidth * (videoHeight / videoWidth)).toInt()
            videoView.layoutParams = lp
            playVideo()
            if (result.isSnapshot) {
                // Log the real size for debugging reason.
                Log.e("VideoPreview", "The video full size is " + videoWidth + "x" + videoHeight)
            }
        }
    }

    fun playVideo() {
        if (!videoView.isPlaying) {
            videoView.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            videoResult = null
        }
    }


}