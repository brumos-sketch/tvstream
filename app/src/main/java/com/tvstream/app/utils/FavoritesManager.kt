package com.tvstream.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesManager(context: Context) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("TVStreamPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun saveFavorites(favorites: Set<String>) {
        prefs.edit()
            .putString("favorites", gson.toJson(favorites))
            .apply()
    }
    
    fun loadFavorites(): Set<String> {
        val json = prefs.getString("favorites", null) ?: return emptySet()
        val type = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(json, type)
    }
    
    fun addFavorite(channelId: String) {
        val favorites = loadFavorites().toMutableSet()
        favorites.add(channelId)
        saveFavorites(favorites)
    }
    
    fun removeFavorite(channelId: String) {
        val favorites = loadFavorites().toMutableSet()
        favorites.remove(channelId)
        saveFavorites(favorites)
    }
    
    fun isFavorite(channelId: String): Boolean {
        return loadFavorites().contains(channelId)
    }
    
    fun saveChannels(channels: List<com.tvstream.app.model.Channel>) {
        prefs.edit()
            .putString("channels", gson.toJson(channels))
            .apply()
    }
    
    fun loadChannels(): List<com.tvstream.app.model.Channel> {
        val json = prefs.getString("channels", null) ?: return emptyList()
        val type = object : TypeToken<List<com.tvstream.app.model.Channel>>() {}.type
        return gson.fromJson(json, type)
    }
}
