package com.tvstream.app

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.PlaybackControlsRow
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.tvstream.app.utils.FavoritesManager

class PlayerActivity : FragmentActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerGlue: VideoPlayerGlue
    private lateinit var favoritesManager: FavoritesManager
    
    private var channelName: String = ""
    private var channelUrl: String = ""
    private var channelId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        channelName = intent.getStringExtra("channel_name") ?: "Canal"
        channelUrl = intent.getStringExtra("channel_url") ?: ""
        channelId = intent.getStringExtra("channel_id") ?: ""

        favoritesManager = FavoritesManager(this)

        if (channelUrl.isEmpty()) {
            Toast.makeText(this, "URL no válida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.playback_fragment) as VideoSupportFragment
        setupPlayer(fragment)
    }

    private fun setupPlayer(fragment: VideoSupportFragment) {
        player = ExoPlayer.Builder(this).build()

        val playerAdapter = LeanbackPlayerAdapter(this, player, 16)
        val host = VideoSupportFragmentGlueHost(fragment)
        
        playerGlue = VideoPlayerGlue(this, playerAdapter, channelId, favoritesManager) { action ->
            when (action.id.toInt()) {
                ACTION_FAVORITE -> toggleFavorite()
            }
        }
        
        playerGlue.host = host
        playerGlue.title = channelName
        playerGlue.subtitle = "En vivo"

        // Preparar media
        val mediaItem = MediaItem.fromUri(channelUrl)
        
        if (channelUrl.contains(".m3u8")) {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
            player.setMediaSource(hlsMediaSource)
        } else {
            player.setMediaItem(mediaItem)
        }

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(
                    this@PlayerActivity,
                    "Error: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        player.prepare()
        player.playWhenReady = true
    }

    private fun toggleFavorite() {
        val isFavorite = favoritesManager.isFavorite(channelId)
        if (isFavorite) {
            favoritesManager.removeFavorite(channelId)
            Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
        } else {
            favoritesManager.addFavorite(channelId)
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
        }
        playerGlue.updateFavoriteAction()
    }

    override fun onPause() {
        super.onPause()
        playerGlue.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    inner class VideoPlayerGlue(
        context: PlayerActivity,
        playerAdapter: LeanbackPlayerAdapter,
        private val channelId: String,
        private val favManager: FavoritesManager,
        private val onActionClicked: (Action) -> Unit
    ) : PlaybackTransportControlGlue<LeanbackPlayerAdapter>(context, playerAdapter) {

        private val favoriteAction = PlaybackControlsRow.ClosedCaptioningAction(context).apply {
            index = if (favManager.isFavorite(channelId)) 1 else 0
        }

        init {
            isSeekEnabled = false
        }

        override fun onCreatePrimaryActions(adapter: ArrayObjectAdapter) {
            super.onCreatePrimaryActions(adapter)
            adapter.add(favoriteAction)
        }

        override fun onActionClicked(action: Action) {
            if (action == favoriteAction) {
                onActionClicked(action)
            } else {
                super.onActionClicked(action)
            }
        }

        fun updateFavoriteAction() {
            favoriteAction.index = if (favManager.isFavorite(channelId)) 1 else 0
            notifyActionChanged(favoriteAction)
        }

        private fun notifyActionChanged(action: Action) {
            val adapter = controlsRow?.primaryActionsAdapter as? ArrayObjectAdapter
            val index = adapter?.indexOf(action) ?: -1
            if (index >= 0) {
                adapter?.notifyArrayItemRangeChanged(index, 1)
            }
        }
    }

    companion object {
        private const val ACTION_FAVORITE = 1L
    }
}
