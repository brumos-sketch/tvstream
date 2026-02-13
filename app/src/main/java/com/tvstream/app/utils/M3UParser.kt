package com.tvstream.app.utils

import com.tvstream.app.model.Channel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.UUID

class M3UParser {
    
    fun parseM3U(m3uContent: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        val lines = m3uContent.lines()
        
        var currentName = ""
        var currentCategory = "General"
        var currentLogo = ""
        
        for (i in lines.indices) {
            val line = lines[i].trim()
            
            if (line.startsWith("#EXTINF:")) {
                currentName = extractName(line)
                currentCategory = extractAttribute(line, "group-title") ?: "General"
                currentLogo = extractAttribute(line, "tvg-logo") ?: ""
            } else if (line.isNotEmpty() && !line.startsWith("#")) {
                if (currentName.isNotEmpty()) {
                    channels.add(
                        Channel(
                            id = UUID.randomUUID().toString(),
                            name = currentName,
                            url = line,
                            category = currentCategory,
                            logo = currentLogo
                        )
                    )
                    currentName = ""
                    currentCategory = "General"
                    currentLogo = ""
                }
            }
        }
        
        return channels
    }
    
    fun parseM3UFromUrl(url: String): List<Channel> {
        return try {
            val connection = URL(url).openConnection()
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            val content = reader.readText()
            reader.close()
            
            parseM3U(content)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    private fun extractName(line: String): String {
        val parts = line.split(",")
        return if (parts.size > 1) parts.last().trim() else "Canal"
    }
    
    private fun extractAttribute(line: String, attribute: String): String? {
        val pattern = """$attribute="([^"]*)"""".toRegex()
        val match = pattern.find(line)
        return match?.groupValues?.get(1)
    }
}
