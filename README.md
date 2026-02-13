# 📺 TV Streaming App - Android TV Edition

App de streaming de TV en vivo optimizada para **Android TV**, con soporte para control remoto, navegación con D-pad y experiencia de sala de estar.

## ✨ Características Principales

- ✅ **Interfaz Leanback** optimizada para TV
- ✅ Navegación completa con **control remoto**
- ✅ Reproducción de canales en vivo (HLS, DASH, MP4)
- ✅ Carga de **listas M3U/IPTV** desde URL
- ✅ Sistema de **categorías** automático
- ✅ **Favoritos** persistentes
- ✅ Tarjetas visuales con logos de canales
- ✅ Reproductor integrado con controles TV
- ✅ **ExoPlayer** para máxima compatibilidad

## 🎮 Navegación con Control Remoto

### Pantalla Principal
- **↑↓**: Cambiar entre filas (categorías)
- **←→**: Navegar entre canales
- **OK/Enter**: Reproducir canal
- **Back**: Salir de la app

### Reproductor
- **OK/Enter**: Play/Pausa
- **←→**: (No aplicable en streams en vivo)
- **Back**: Volver a la lista
- **Botón de favorito**: Añadir/quitar de favoritos

## 📋 Requisitos

### Para Desarrollo
- Android Studio Hedgehog (2023.1.1) o superior
- Android SDK 21 o superior
- JDK 8 o superior

### Para Instalación
- **Android TV Box** (Fire TV, Mi Box, Nvidia Shield, etc.)
- Android TV 5.0 o superior
- Conexión a Internet

## 🚀 Instalación

### Método 1: Android Studio + Android TV Emulador

1. **Crear emulador de TV:**
   - Tools → Device Manager
   - Create Device → TV → Android TV (1080p)
   - Selecciona API 31 o superior
   - Finish

2. **Abrir proyecto:**
   ```
   File → Open → Seleccionar carpeta TVStreamApp
   ```

3. **Ejecutar:**
   - Selecciona el emulador de TV
   - Click en Run ▶️

### Método 2: Instalación en TV Box Real

1. **Compilar APK:**
   ```bash
   cd TVStreamApp
   ./gradlew assembleDebug
   ```

2. **Transferir a TV:**
   - Copia `app/build/outputs/apk/debug/app-debug.apk` a USB
   - Inserta USB en tu TV Box
   - Usa un File Manager para instalar

3. **O vía ADB:**
   ```bash
   adb connect <IP_DE_TU_TV>
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Método 3: Sideload desde PC

1. Activa "Depuración USB" en tu Android TV
2. Conecta TV y PC a la misma red
3. Usa ADB:
   ```bash
   adb connect 192.168.1.XX:5555
   adb install app-debug.apk
   ```

## 📱 Uso de la App

### Primera Ejecución

La app viene con **3 canales demo** para que pruebes de inmediato:
- Big Buck Bunny
- Tears of Steel  
- Sintel

### Añadir tus Canales

#### Opción 1: Cargar Lista M3U

1. Navega hasta la fila "Configuración"
2. Selecciona "📋 Cargar M3U"
3. La app cargará canales de ejemplo
4. *Para añadir tu propia URL, modifica el código (ver Personalización)*

#### Opción 2: Modificar el código

Edita `MainActivity.kt`, método `loadChannels()`:

```kotlin
allChannels = mutableListOf(
    Channel(
        id = "1",
        name = "Mi Canal",
        url = "https://mi-servidor.com/stream.m3u8",
        category = "Noticias",
        logo = "https://mi-servidor.com/logo.png"
    ),
    // Más canales...
)
```

### Gestionar Favoritos

1. Durante la reproducción, usa el **botón de favorito** en los controles
2. Los favoritos aparecen en una fila especial al inicio
3. Se guardan automáticamente

## 🎨 Estructura del Proyecto

```
app/
├── model/
│   └── Channel.kt              # Modelo de datos
├── presenter/
│   └── ChannelCardPresenter.kt # Tarjetas visuales
├── utils/
│   ├── M3UParser.kt            # Parser M3U/IPTV
│   └── FavoritesManager.kt     # Gestión de favoritos
├── MainActivity.kt              # Lista de canales (Leanback)
└── PlayerActivity.kt           # Reproductor de video
```

## 🔧 Personalización

### Cambiar colores

Edita `res/values/colors.xml`:

```xml
<color name="brand_color">#TU_COLOR</color>
<color name="search_color">#TU_COLOR</color>
```

### Añadir logo personalizado

Reemplaza:
- `res/drawable/app_icon_tv.xml` (icono)
- `res/drawable/app_banner.xml` (banner de 320x180)

### Modificar URL de M3U por defecto

En `MainActivity.kt`, método `showAddM3UDialog()`:

```kotlin
val exampleM3UUrl = "https://tu-servidor.com/lista.m3u"
```

## 📦 Dependencias Principales

```gradle
// Leanback para Android TV
implementation 'androidx.leanback:leanback:1.0.0'

// ExoPlayer para streaming
implementation 'com.google.android.exoplayer:exoplayer:2.19.1'
implementation 'com.google.android.exoplayer:extension-leanback:2.19.1'

// Glide para cargar imágenes
implementation 'com.github.bumptech.glide:glide:4.15.1'
```

## 🎯 Formato M3U Compatible

```m3u
#EXTM3U
#EXTINF:-1 tvg-id="canal1" tvg-logo="https://logo.png" group-title="Noticias",Canal 1
https://stream-server.com/canal1/playlist.m3u8
#EXTINF:-1 tvg-id="canal2" tvg-logo="https://logo2.png" group-title="Deportes",Canal 2
https://stream-server.com/canal2/playlist.m3u8
```

### Atributos soportados:
- `tvg-logo`: URL del logo del canal
- `group-title`: Categoría del canal
- El nombre va después de la última coma

## 🔐 Permisos

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🐛 Solución de Problemas

### No aparece en el launcher de TV

Verifica que `AndroidManifest.xml` tenga:
```xml
<category android:name="android.intent.category.LEANBACK_LAUNCHER" />
```

### Los streams no reproducen

1. **Verifica la URL** en un navegador
2. Algunos streams requieren **headers específicos**
3. Prueba con los canales demo primero
4. Revisa si el stream es **geo-bloqueado**

### Navegación con control no funciona

1. Asegúrate de estar usando un **emulador/dispositivo Android TV**
2. Verifica que las tarjetas sean **focusables**
3. Usa las teclas de D-pad, no touch

### Error de compilación

```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

## 🌐 URLs de Prueba

### Streams HLS de ejemplo:
```
https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8
https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8
http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4
```

### Listas M3U públicas:
```
https://iptv-org.github.io/iptv/index.m3u8
```

⚠️ **Nota**: Usa solo contenido legal y del que tengas derechos.

## 🚧 Roadmap

- [ ] Diálogo con teclado en pantalla para añadir URLs
- [ ] EPG (Guía de programación electrónica)
- [ ] Búsqueda de canales por voz
- [ ] Recomendaciones personalizadas
- [ ] Soporte para Chromecast
- [ ] Grabación de streams
- [ ] Control parental con PIN
- [ ] Múltiples perfiles de usuario
- [ ] Sincronización en la nube

## 📊 Compatibilidad

✅ **Probado en:**
- Android TV Emulator (API 31+)
- Fire TV Stick 4K
- Mi Box S
- Nvidia Shield TV

✅ **Soporta:**
- Streams HLS (.m3u8)
- MP4 directo
- DASH
- Smooth Streaming

## ⚠️ Disclaimer

Esta app es **solo para fines educativos** y para reproducir contenido del que tengas derechos legales. El desarrollador no se hace responsable del contenido reproducido ni de infracciones de copyright.

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas!

1. Fork el proyecto
2. Crea tu rama (`git checkout -b feature/nuevaCaracteristica`)
3. Commit tus cambios (`git commit -m 'Añadir nueva característica'`)
4. Push a la rama (`git push origin feature/nuevaCaracteristica`)
5. Abre un Pull Request

## 📄 Licencia

Proyecto de código abierto para fines educativos.

## 💡 Tips para Android TV

1. **Optimiza imágenes**: Logos en 313x176px para mejor rendimiento
2. **Usa logos**: Mejora mucho la experiencia visual
3. **Categorías claras**: Organiza bien tus canales
4. **Streams estables**: Prueba URLs antes de añadirlas
5. **Menos es más**: No sobrecargues con miles de canales

## 📧 Soporte

Si tienes problemas:
1. Revisa la sección "Solución de problemas"
2. Verifica que la app funcione con los canales demo
3. Abre un issue en GitHub con detalles

---

**Desarrollado con ❤️ para la comunidad Android TV**

Disfruta de tus canales favoritos en la pantalla grande! 📺✨
