package com.hedroid.rtspvideostreamingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.hedroid.rtspvideostreamingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = ExoPlayer.Builder(this).build()
        binding.videoPlayerView.player = player

        binding.startStreamButton.setOnClickListener {
            val rtspUrl = binding.rtspUrlInput.text.toString()
            if (rtspUrl.isNotEmpty()) {
                startStreaming(rtspUrl)
            } else {
                Toast.makeText(this, "Please enter an RTSP URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startStreaming(rtspUrl: String) {
        try {
            val mediaItem = MediaItem.Builder()
                .setUri(rtspUrl)
                .setMimeType("application/x-rtsp")
                .build()

            val mediaSource = RtspMediaSource.Factory().createMediaSource(mediaItem)
            player.setMediaSource(mediaSource)
            player.prepare()
            player.play()
        } catch (e: Exception) {
            Toast.makeText(this, "Error streaming video: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
