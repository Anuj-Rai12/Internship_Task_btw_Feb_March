package com.example.relevel.adaptor

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.relevel.MyApplication
import com.example.relevel.databinding.ViedoItemDisplayBinding
import com.example.relevel.model.Msg
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache

typealias itemClicked = (data: Msg) -> Unit

class MainRecycleView(private val context: Context, private val itemClicked: itemClicked) :
    ListAdapter<Msg, MainRecycleView.MainPlayerViewHolder>(diffUtil) {
    inner class MainPlayerViewHolder(private val binding: ViedoItemDisplayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var httpDataSourceFactory: HttpDataSource.Factory
        private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
        private lateinit var cacheDataSourceFactory: DataSource.Factory
        private lateinit var simpleExoPlayer: SimpleExoPlayer
        private val simpleCache: SimpleCache = MyApplication.simpleCache

        fun setData(data: Msg, itemClicked: itemClicked, context: Context) {
            initPlayer(data.video, context)
        }


        private fun initPlayer(uri: String, context: Context) {
            httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)

            defaultDataSourceFactory = DefaultDataSourceFactory(
                binding.root.context, httpDataSourceFactory
            )

            cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

            simpleExoPlayer = SimpleExoPlayer.Builder(binding.root.context)
                .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).build()

            val videoUri = Uri.parse(uri)
            val mediaItem = MediaItem.fromUri(videoUri)
            val mediaSource =
                ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem)
            binding.playerView.player = simpleExoPlayer
            simpleExoPlayer.addListener(object : Player.Listener {
                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    Log.i("ANUJ", "onIsLoadingChanged: $isLoading")
                    if (isLoading)
                        binding.playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                    else
                        binding.playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                }
            })
            simpleExoPlayer.playWhenReady = true
            simpleExoPlayer.seekTo(0, 0)
            simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
            simpleExoPlayer.setMediaSource(mediaSource, true)
            simpleExoPlayer.prepare()
        }


    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Msg>() {
            override fun areItemsTheSame(
                oldItem: Msg,
                newItem: Msg
            ) = oldItem.id1 == newItem.id1

            override fun areContentsTheSame(
                oldItem: Msg,
                newItem: Msg
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPlayerViewHolder {
        val binding =
            ViedoItemDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPlayerViewHolder, position: Int) {
        val currItem = getItem(position)
        currItem?.let {
            holder.setData(it, itemClicked, context)
        }
    }

}