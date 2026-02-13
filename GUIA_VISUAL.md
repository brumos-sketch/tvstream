# 🎬 Guía Visual Rápida - Compilar sin Android Studio

## 🚀 En 5 minutos, desde cero

### Paso 1: GitHub
```
🌐 github.com → Sign up (gratis)
```

### Paso 2: Nuevo Repositorio
```
➕ New repository
📝 Nombre: TVStreamApp-AndroidTV
✅ Public
🚫 NO marcar "Add README"
✔️ Create repository
```

### Paso 3: Subir Archivos
```
📁 Descomprime TVStreamApp.zip
🖱️ Arrastra carpeta TVStreamApp a GitHub
⏳ Espera subida
✅ Commit changes
```

### Paso 4: Compilar
```
⚙️ Pestaña "Actions"
▶️ Automáticamente empieza a compilar
🟡 Amarillo = compilando (3-5 min)
✅ Verde = listo
```

### Paso 5: Descargar
```
✅ Click en workflow completado
⬇️ Scroll a "Artifacts"
📦 Click en "app-debug"
💾 Descarga ZIP
📂 Descomprime
📱 Tienes app-debug.apk
```

### Paso 6: Instalar en TV
```
💾 Copia APK a USB
🔌 USB → Android TV
📂 File Manager → USB
📱 Instalar APK
✨ ¡Listo!
```

---

## 🔄 Para Actualizar

```
✏️ Edita código en PC
⬆️ Sube archivos a GitHub (arrastra)
⚙️ Actions → Run workflow
⏳ Espera 3 min
⬇️ Descarga nuevo APK
📱 Reinstala en TV
```

---

## 💡 Ejemplo: Añadir Canales

### Antes de subir a GitHub:

1. **Abre** con Notepad/Bloc de notas:
   ```
   TVStreamApp/app/src/main/java/com/tvstream/app/MainActivity.kt
   ```

2. **Busca** línea ~105:
   ```kotlin
   allChannels = mutableListOf(
   ```

3. **Reemplaza** con tus canales:
   ```kotlin
   allChannels = mutableListOf(
       Channel("1", "ESPN", "https://stream.com/espn.m3u8", "Deportes", "https://logo.png"),
       Channel("2", "CNN", "https://stream.com/cnn.m3u8", "Noticias", ""),
   )
   ```

4. **Guarda** y sube a GitHub

5. **Automático**: se compila con tus canales

---

## ✅ Checklist Rápida

- [ ] Cuenta GitHub creada
- [ ] Repositorio creado
- [ ] Archivos subidos
- [ ] Workflow ejecutado (3-5 min)
- [ ] APK descargado
- [ ] USB preparado
- [ ] Instalado en TV
- [ ] App funcionando

---

## 🎯 Recursos

📘 **Guía completa**: `COMPILAR_ONLINE.md`
📺 **Guía TV**: `GUIA_RAPIDA_TV.md`  
📖 **README**: `README.md`

---

## 🆘 Ayuda Rápida

**❌ Error al compilar:**
→ Actions → Click workflow → Lee logs rojos

**❌ No aparece Actions:**
→ Repo debe ser público O actívalo en Settings

**❌ APK no instala:**
→ Descarga "app-debug" (no release)

**❌ Tarda mucho:**
→ 3-5 min es normal, toma un café ☕

---

## 🎊 ¡Eso es todo!

```
GitHub = Tu Android Studio gratis en la nube
```

🎉 **Sin instalar NADA en tu PC** 🎉
