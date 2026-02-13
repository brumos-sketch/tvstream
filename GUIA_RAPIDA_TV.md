# 🚀 Guía Rápida - Android TV

## ⚡ Instalación en 3 Pasos

### 1️⃣ Preparar Android Studio

1. Descarga [Android Studio](https://developer.android.com/studio)
2. Instala y abre
3. `File` → `Open` → Selecciona carpeta `TVStreamApp`
4. Espera sincronización Gradle (2-3 min)

### 2️⃣ Crear Emulador de TV

1. `Tools` → `Device Manager`
2. `Create Device` → **TV** → `Android TV (1080p)`
3. Next → Descarga **API 31** (Android 12)
4. Finish → **Lanza el emulador**

### 3️⃣ Ejecutar

1. Selecciona el emulador en la barra superior
2. Click en ▶️ **Run**
3. ¡Listo! La app se abrirá en el TV

---

## 🎮 Controles del Emulador

**Teclado del PC = Control Remoto:**

| Tecla PC | Función |
|----------|---------|
| Flechas ↑↓←→ | D-pad del control |
| Enter | Botón OK/Select |
| ESC | Botón Back |
| Ctrl + S | Abrir configuración |

---

## 📺 Primeros Pasos en la App

### Explorar canales demo

1. La app abre mostrando 3 canales de prueba
2. Usa **flechas ↑↓** para cambiar de fila
3. Usa **flechas ←→** para navegar entre canales
4. Presiona **Enter** para reproducir

### Reproducir un canal

1. Selecciona cualquier canal
2. **Enter** para reproducir
3. Se abre el reproductor a pantalla completa
4. Usa **Enter** para pausar/reanudar
5. **ESC** para volver

### Añadir a Favoritos

1. Durante reproducción, enfoca el botón de favorito
2. Presiona **Enter**
3. El canal aparecerá en la fila "⭐ Favoritos"

---

## 🔧 Instalación en TV Box Real

### Opción A: Via ADB (Recomendado)

1. **En tu Android TV:**
   - Ajustes → Acerca de → Presiona "Versión" 7 veces (activa modo dev)
   - Ajustes → Opciones de desarrollador → Activa "Depuración USB"
   - Anota la **IP de tu TV** (Ajustes → Red)

2. **En tu PC:**
   ```bash
   # Conectar
   adb connect 192.168.1.XX:5555
   
   # Instalar
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Opción B: Via USB

1. Compila la APK:
   ```bash
   cd TVStreamApp
   ./gradlew assembleDebug
   ```

2. Copia `app/build/outputs/apk/debug/app-debug.apk` a una USB

3. Conecta USB a tu TV Box

4. Usa un File Manager (como X-plore) para instalar

---

## 📋 Añadir tus Canales

### Método 1: Editar código (Más Control)

Edita `app/src/main/java/com/tvstream/app/MainActivity.kt`

Busca el método `loadChannels()` y añade:

```kotlin
allChannels = mutableListOf(
    Channel(
        id = "1",
        name = "Tu Canal",
        url = "https://stream.com/canal.m3u8",
        category = "Deportes",
        logo = "https://logo.com/imagen.png"
    ),
    // Añade más canales aquí...
)
```

### Método 2: URL de lista M3U

Edita `MainActivity.kt`, método `showAddM3UDialog()`:

```kotlin
val exampleM3UUrl = "https://tu-servidor.com/lista.m3u8"
```

Luego en la app:
1. Navega a "Configuración"
2. Selecciona "📋 Cargar M3U"

---

## 🎨 Personalización Rápida

### Cambiar colores

`app/src/main/res/values/colors.xml`:

```xml
<color name="brand_color">#FF5722</color>
<color name="search_color">#4CAF50</color>
```

### Cambiar nombre de la app

`app/src/main/res/values/strings.xml`:

```xml
<string name="app_name">Mi TV</string>
```

---

## 🐛 Problemas Comunes

### "App keeps stopping"

```bash
Build → Clean Project
Build → Rebuild Project
```

### No aparece en el launcher

Verifica `AndroidManifest.xml`:
```xml
<category android:name="android.intent.category.LEANBACK_LAUNCHER" />
```

### Stream no reproduce

1. Prueba la URL en VLC primero
2. Verifica que sea formato soportado (.m3u8, .mp4)
3. Revisa tu conexión a Internet

### Navegación no funciona

- Asegúrate de usar **emulador/dispositivo Android TV**
- No es compatible con tablets/teléfonos normales

---

## 🌐 URLs de Prueba

Copia estos streams para probar:

```
Big Buck Bunny:
https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8

Tears of Steel:
https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8

Sintel:
http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4
```

---

## 💡 Consejos Pro

1. **Logos de canales**: Mejoran mucho la experiencia. Usa URLs de imágenes.

2. **Organiza por categorías**: El parser M3U las detecta automáticamente.

3. **Favoritos**: Usa para acceso rápido a tus canales preferidos.

4. **Prueba en emulador**: Más rápido que compilar e instalar en TV real cada vez.

5. **ADB wireless**: Una vez conectado, puedes desconectar el cable USB.

---

## ✅ Checklist de Instalación

- [ ] Android Studio instalado
- [ ] Proyecto abierto y sincronizado
- [ ] Emulador de TV creado O TV Box con USB debugging
- [ ] App ejecutada exitosamente
- [ ] Canales demo reproduciendo
- [ ] (Opcional) Canales propios añadidos

---

## 🎯 Siguiente Nivel

Una vez que todo funcione:

1. Añade tus propias listas M3U
2. Personaliza colores e iconos
3. Compila versión release:
   ```bash
   ./gradlew assembleRelease
   ```
4. Distribuye a tus amigos con Android TV

---

## 📺 Dispositivos Compatibles

✅ **Funciona en:**
- Fire TV Stick / Fire TV Cube
- Mi Box S / Mi Box 4K
- Nvidia Shield TV / Shield Pro
- Chromecast con Google TV
- Cualquier Android TV Box (API 21+)

❌ **NO funciona en:**
- Teléfonos Android normales
- Tablets Android
- Smart TVs sin Android TV

---

¡Disfruta tu app de streaming! 🎉

**Tip final**: Usa un control remoto físico para mejor experiencia que el teclado.
