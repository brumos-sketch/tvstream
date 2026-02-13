package com.tvstream.app.model

data class Channel(
    val id: String,
    val name: String,
    val url: String,
    val category: String = "General",
    val logo: String = "",
    var isFavorite: Boolean = false
)
