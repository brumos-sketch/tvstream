package com.tvstream.app.presenter

import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.tvstream.app.R
import com.tvstream.app.model.Channel

class ChannelCardPresenter : Presenter() {
    
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
        }
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val channel = item as Channel
        val cardView = viewHolder.view as ImageCardView

        cardView.titleText = channel.name
        cardView.contentText = channel.category

        // Cargar logo si existe
        if (channel.logo.isNotEmpty()) {
            Glide.with(viewHolder.view.context)
                .load(channel.logo)
                .centerCrop()
                .error(R.drawable.default_channel)
                .into(cardView.mainImageView)
        } else {
            cardView.mainImageView.setImageResource(R.drawable.default_channel)
        }

        // Indicador de favorito
        if (channel.isFavorite) {
            cardView.badgeImage = ContextCompat.getDrawable(
                viewHolder.view.context,
                R.drawable.ic_favorite_badge
            )
        } else {
            cardView.badgeImage = null
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    companion object {
        private const val CARD_WIDTH = 313
        private const val CARD_HEIGHT = 176
    }
}
