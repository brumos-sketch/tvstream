# 🚀 Compilar APK Online (SIN INSTALAR NADA)

Esta guía te permite compilar tu app Android TV **gratis y automáticamente** usando GitHub Actions.

## 📋 Requisitos

- ✅ Cuenta de GitHub (gratis)
- ✅ Navegador web
- ❌ **NO necesitas** Android Studio
- ❌ **NO necesitas** instalar nada en tu PC

---

## 🎯 Pasos Completos

### 1️⃣ Crear cuenta en GitHub (si no tienes)

1. Ve a https://github.com
2. Click en "Sign up"
3. Sigue los pasos (es gratis)

### 2️⃣ Subir el proyecto a GitHub

**Opción A: Interfaz Web (más fácil)**

1. Ve a https://github.com
2. Click en el botón **"+"** (arriba derecha) → **"New repository"**
3. Nombre: `TVStreamApp-AndroidTV`
4. Marca como **"Public"** (o Private si prefieres)
5. **NO marques** "Add a README file"
6. Click en **"Create repository"**

7. En la nueva página, verás "uploading an existing file"
8. Click ahí
9. **Arrastra** la carpeta `TVStreamApp` completa (descomprimida)
10. Espera a que suba todo
11. Click en **"Commit changes"**

**Opción B: Git desde terminal**

```bash
cd TVStreamApp
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TVStreamApp-AndroidTV.git
git push -u origin main
```

### 3️⃣ Activar GitHub Actions

1. En tu repositorio, ve a la pestaña **"Actions"**
2. Si pregunta, click en **"I understand my workflows, go ahead and enable them"**

### 4️⃣ Compilar el APK

**Primera vez (automático):**
- GitHub Actions empezará a compilar automáticamente
- Verás un círculo amarillo girando 🟡
- Espera 3-5 minutos

**Compilaciones posteriores:**
1. Ve a la pestaña **"Actions"**
2. Click en **"Build Android TV APK"** (en el menú izquierdo)
3. Click en **"Run workflow"** (botón derecho)
4. Click en **"Run workflow"** verde
5. Espera 3-5 minutos

### 5️⃣ Descargar el APK

1. Cuando termine (✅ verde), click en el workflow completado
2. Baja hasta **"Artifacts"**
3. Verás dos archivos:
   - **app-debug** ← Descarga este para probar
   - **app-release** ← Para versión final (sin firmar)
4. Click en **app-debug** para descargar
5. Descomprime el ZIP
6. ¡Tienes tu APK! 🎉

---

## 📱 Instalar en tu Android TV

### Método 1: Via USB

1. Copia `app-debug.apk` a una USB
2. Conecta la USB a tu Android TV
3. Usa un File Manager (como X-plore File Manager)
4. Navega a la USB
5. Click en el APK para instalar

### Método 2: Via ADB (Wireless)

1. **En tu Android TV:**
   - Ajustes → Acerca de
   - Presiona "Versión" 7 veces (activa modo desarrollador)
   - Ajustes → Opciones de desarrollador
   - Activa "Depuración USB"
   - Anota la **IP** (Ajustes → Red)

2. **En tu PC:**
   ```bash
   adb connect 192.168.1.XX:5555
   adb install app-debug.apk
   ```

### Método 3: Apps como "Send Files to TV"

1. Instala "Send Files to TV" en tu TV y en tu móvil
2. Envía el APK del móvil a la TV
3. Usa File Manager en la TV para instalarlo

---

## 🎨 Personalizar ANTES de compilar

### Añadir tus canales

Antes de subir a GitHub, edita este archivo:
```
TVStreamApp/app/src/main/java/com/tvstream/app/MainActivity.kt
```

Busca el método `loadChannels()` y añade:

```kotlin
allChannels = mutableListOf(
    Channel("1", "Mi Canal", "https://stream.com/canal.m3u8", "Deportes", "https://logo.png"),
    Channel("2", "Otro Canal", "https://otro.com/live.m3u8", "Noticias", ""),
    // Más canales...
)
```

### Cambiar nombre de la app

Edita:
```
TVStreamApp/app/src/main/res/values/strings.xml
```

```xml
<string name="app_name">Mi TV App</string>
```

### Cambiar colores

Edita:
```
TVStreamApp/app/src/main/res/values/colors.xml
```

```xml
<color name="brand_color">#FF5722</color>
<color name="search_color">#4CAF50</color>
```

---

## 🔄 Flujo de Trabajo Típico

1. **Edita** el código en tu PC (con cualquier editor de texto)
2. **Sube** los cambios a GitHub:
   - Via web: arrastra archivos modificados
   - Via git: `git add . && git commit -m "Cambios" && git push`
3. **Compila** con GitHub Actions (automático o manual)
4. **Descarga** el APK nuevo
5. **Instala** en tu TV

---

## 🆓 Límites de GitHub Actions (Gratis)

- ✅ **2,000 minutos/mes** de compilación
- ✅ **Ilimitado** para repos públicos
- ✅ Cada compilación tarda ~3-5 minutos
- ✅ Puedes compilar ~400 veces al mes gratis

---

## 🐛 Solución de Problemas

### Error: "Workflow not found"

- Asegúrate de subir la carpeta `.github/workflows/build.yml`

### Error durante compilación

1. Ve a la pestaña "Actions"
2. Click en el workflow fallido
3. Lee los logs rojos
4. Generalmente es un error de sintaxis en el código

### No aparece "Actions"

- Verifica que el repositorio sea público
- O activa Actions en Settings → Actions → Allow all actions

### APK no funciona en TV

- Descarga **app-debug**, no app-release
- app-release necesita ser firmado

---

## 💡 Tips Pro

1. **Crea branches** para probar cambios:
   ```bash
   git checkout -b test-canales
   # Haz cambios
   git push origin test-canales
   ```

2. **Usa releases** para versiones estables:
   - Ve a "Releases" → "Create a new release"
   - Adjunta el APK manualmente

3. **Automatiza** más:
   - El workflow puede enviarte el APK por email
   - Puede publicar automáticamente en releases

4. **Colabora**:
   - Invita a otras personas a tu repo
   - Pueden hacer cambios y compilar

---

## 📊 Monitoreo

Puedes ver el estado de compilación en:
- Badge en el README: ![Build Status](https://github.com/TU_USUARIO/TVStreamApp-AndroidTV/workflows/Build%20Android%20TV%20APK/badge.svg)

---

## 🎓 Próximos Pasos

Una vez que domines esto:

1. **Firma tu APK** para Google Play
2. **Publica** en Play Store
3. **Automatiza testing** con Espresso
4. **CI/CD completo** con deploy automático

---

## ❓ FAQ

**P: ¿Es gratis para siempre?**
R: Sí, GitHub Actions es gratis para repos públicos.

**P: ¿Puedo compilar versión release firmada?**
R: Sí, pero necesitas crear un keystore y configurar secrets.

**P: ¿Cuánto tiempo tarda?**
R: 3-5 minutos por compilación.

**P: ¿Puedo ver el progreso?**
R: Sí, en tiempo real en la pestaña Actions.

**P: ¿Se pueden compilar varias ramas?**
R: Sí, cada push a cualquier rama compila automáticamente.

---

## 🔐 Seguridad

- ✅ No compartas tu APK firmado públicamente
- ✅ Usa repos privados si tienes URLs secretas
- ✅ No subas contraseñas o API keys al código

---

## 📧 Soporte

Si tienes problemas:
1. Revisa los logs en Actions
2. Busca el error en Google
3. Pregunta en GitHub Issues

---

¡Listo! Ahora puedes compilar APKs Android sin instalar Android Studio 🎉

**Recuerda**: Edita el código → Sube a GitHub → Compila automático → Descarga APK → Instala en TV
