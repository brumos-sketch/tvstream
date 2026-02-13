package com.tvstream.app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.tvstream.app.model.Channel
import com.tvstream.app.presenter.ChannelCardPresenter
import com.tvstream.app.utils.FavoritesManager
import com.tvstream.app.utils.M3UParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : FragmentActivity() {

    private lateinit var browseFragment: BrowseSupportFragment
    private lateinit var favoritesManager: FavoritesManager
    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private var allChannels = mutableListOf<Channel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoritesManager = FavoritesManager(this)
        setupBrowseFragment()
        loadChannels()
    }

    private fun setupBrowseFragment() {
        browseFragment = supportFragmentManager
            .findFragmentById(R.id.browse_fragment) as BrowseSupportFragment

        browseFragment.apply {
            title = "TV Streaming"
            headersState = BrowseSupportFragment.HEADERS_ENABLED
            isHeadersTransitionOnBackEnabled = true
            brandColor = ContextCompat.getColor(this@MainActivity, R.color.brand_color)
            searchAffordanceColor = ContextCompat.getColor(this@MainActivity, R.color.search_color)
        }

        browseFragment.onItemViewClickedListener = ItemViewClickedListener()
        browseFragment.adapter = rowsAdapter

        // Agregar botón de configuración
        browseFragment.setOnSearchClickedListener {
            showAddChannelOptions()
        }
    }

    private fun loadChannels() {
        // Intentar cargar canales guardados
        val savedChannels = favoritesManager.loadChannels()
        
        if (savedChannels.isNotEmpty()) {
            allChannels = savedChannels.toMutableList()
        } else {
            // Canales de ejemplo
            allChannels = mutableListOf(
                Channel("1", "Big Buck Bunny", "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8", "Demo", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Big_buck_bunny_poster_big.jpg/800px-Big_buck_bunny_poster_big.jpg"),
                Channel("2", "Tears of Steel", "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8", "Demo", "https://mango.blender.org/wp-content/uploads/2013/05/01_thom_celia_bridge.jpg"),
                Channel("3", "Sintel", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "Demo", "https://durian.blender.org/wp-content/uploads/2010/06/05.8b_comp_000272.jpg")
            )
        }

        loadFavoriteStatus()
        updateRows()
    }

    private fun loadFavoriteStatus() {
        val favorites = favoritesManager.loadFavorites()
        allChannels.forEach { channel ->
            channel.isFavorite = favorites.contains(channel.id)
        }
    }

    private fun updateRows() {
        rowsAdapter.clear()

        // Fila de favoritos
        val favorites = allChannels.filter { it.isFavorite }
        if (favorites.isNotEmpty()) {
            val favoritesAdapter = ArrayObjectAdapter(ChannelCardPresenter())
            favorites.forEach { favoritesAdapter.add(it) }
            val favoritesHeader = HeaderItem(0, "⭐ Favoritos")
            rowsAdapter.add(ListRow(favoritesHeader, favoritesAdapter))
        }

        // Filas por categoría
        val categories = allChannels.groupBy { it.category }
        var headerId = 1L
        
        categories.forEach { (category, channels) ->
            val categoryAdapter = ArrayObjectAdapter(ChannelCardPresenter())
            channels.forEach { categoryAdapter.add(it) }
            val header = HeaderItem(headerId++, category)
            rowsAdapter.add(ListRow(header, categoryAdapter))
        }

        // Fila de opciones
        val optionsAdapter = ArrayObjectAdapter(ChannelCardPresenter())
        optionsAdapter.add(Channel("add_url", "➕ Añadir URL", "", "Opciones"))
        optionsAdapter.add(Channel("add_m3u", "📋 Cargar M3U", "", "Opciones"))
        val optionsHeader = HeaderItem(headerId, "Configuración")
        rowsAdapter.add(ListRow(optionsHeader, optionsAdapter))
    }

    private fun showAddChannelOptions() {
        // En una app real, aquí mostrarías un diálogo con teclado en pantalla
        Toast.makeText(this, "Usa el control remoto para navegar a 'Configuración'", Toast.LENGTH_LONG).show()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is Channel) {
                when (item.id) {
                    "add_url" -> showAddUrlDialog()
                    "add_m3u" -> showAddM3UDialog()
                    else -> playChannel(item)
                }
            }
        }
    }

    private fun playChannel(channel: Channel) {
        val intent = Intent(this, PlayerActivity::class.java).apply {
            putExtra("channel_name", channel.name)
            putExtra("channel_url", channel.url)
            putExtra("channel_id", channel.id)
        }
        startActivity(intent)
    }

    private fun showAddUrlDialog() {
        // Para Android TV, necesitarías implementar un diálogo con teclado en pantalla
        // Por simplicidad, mostramos un toast
        Toast.makeText(this, "Función disponible próximamente. Usa la carga de M3U por ahora.", Toast.LENGTH_LONG).show()
    }

    private fun showAddM3UDialog() {
        // Aquí podrías implementar un diálogo con un campo de texto para TV
        // Por ahora, usaremos una URL de ejemplo
        val exampleM3UUrl = "https://iptv-org.github.io/iptv/index.m3u"
        
        Toast.makeText(this, "Cargando canales de ejemplo...", Toast.LENGTH_SHORT).show()
        
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val parser = M3UParser()
                val channels = parser.parseM3UFromUrl(exampleM3UUrl)
                
                withContext(Dispatchers.Main) {
                    if (channels.isNotEmpty()) {
                        allChannels.addAll(channels.take(20)) // Limitar a 20 para el ejemplo
                        favoritesManager.saveChannels(allChannels)
                        loadFavoriteStatus()
                        updateRows()
                        Toast.makeText(this@MainActivity, "${channels.size} canales cargados", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "No se pudieron cargar canales", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Recargar estado de favoritos cuando volvemos del reproductor
        loadFavoriteStatus()
        updateRows()
    }
}
