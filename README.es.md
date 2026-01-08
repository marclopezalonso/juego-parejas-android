# Juego de Memoria por Parejas (Android)

## 📱 Descripción del Proyecto

Este proyecto es un **juego de memoria para Android**, también conocido como *Juego de Parejas*, desarrollado en **Java** usando **Android Studio**.  
El objetivo del juego es **poner a prueba y mejorar la memoria del jugador**, encontrando las parejas de cartas en un tablero con posiciones aleatorias.

La aplicación ha sido diseñada como un **juego para dos jugadores**, incluye **guardado persistente de la partida** y cuenta con un **ranking de los 10 mejores** para almacenar las puntuaciones más altas obtenidas por los jugadores.

---

## 🎮 Reglas y Mecánica del Juego

- El tablero está compuesto por una **rejilla de 4x5**, con un total de **20 cartas**.  
- Estas cartas forman **10 parejas**.  
- Todas las posiciones de las cartas se generan **aleatoriamente** al iniciar cada partida para garantizar la rejugabilidad.  
- Los jugadores deben descubrir **dos cartas a la vez**:
  - Si las cartas coinciden, se considera que la pareja ha sido encontrada.  
  - Cada pareja correcta otorga **1 punto**.  
- La **puntuación máxima** posible en una partida es de **10 puntos**.

---

## 👥 Jugadores

- El juego está pensado para **dos jugadores**.  
- Cada jugador puede introducir un **nombre personalizado**, que se utilizará para registrar su puntuación en el ranking si logra una puntuación alta.  
- Los nombres de los jugadores permiten dejar un **toque personal** en el juego y competir por un puesto en el ranking.

---

## 🏆 Sistema de Ranking

- La aplicación incluye un **ranking de los 10 mejores**.  
- El ranking almacena las **mejores puntuaciones** obtenidas por los jugadores, junto con sus nombres.  
- Solo se muestran las **10 puntuaciones más altas**.  
- Esta funcionalidad fomenta la **competición y la rejugabilidad**.

---

## 💾 Gestión del Estado de la Partida

El juego cuenta con un sistema sencillo pero eficaz de **guardar y cargar la partida**, controlado por tres botones principales:

### ▶️ Iniciar
- Comienza una nueva partida.  
- Inicializa el tablero con cartas barajadas aleatoriamente.  
- Reinicia la puntuación actual.

### 💾 Guardar
- Guarda el **estado actual de la partida**, incluyendo:
  - Configuración del tablero  
  - Parejas encontradas  
  - Puntuación actual  
- Si el usuario cierra la aplicación y vuelve a abrirla, la partida **guardada permanece almacenada**.  
- Solo se puede tener **una partida guardada** a la vez.  
  - Guardar una nueva partida sobrescribe la anterior.

### 📂 Cargar
- Carga la partida previamente guardada.  
- La partida guardada **no se carga automáticamente** al abrir la app.  
- El usuario debe pulsar explícitamente el botón **Cargar** para restaurar el estado guardado.  
- Si no se ha guardado ninguna partida, no se cargará nada.

---

## 🔀 Aleatoriedad

- Las posiciones de las cartas se **barajan al inicio de cada partida**.  
- Esto evita memorizar posiciones de partidas anteriores y garantiza una experiencia **justa y dinámica**.

---

## 🛠️ Detalles Técnicos

- **Lenguaje:** Java  
- **IDE:** Android Studio  
- **Sistema de Compilación:** Gradle (Kotlin DSL)  
- **SDK Mínimo:** Definido en la configuración de Gradle  
- **Arquitectura:** Estructura simple basada en activities  
- **Almacenamiento Persistente:** Para guardar el estado de la partida y los datos del ranking

---

## 📂 Estructura del Proyecto

El proyecto sigue la estructura estándar de un proyecto Android:

- `MainActivity.java` – Lógica principal del juego e interacción con el usuario  
- `Carta.java` – Representa una carta del juego  
- `Ranking.java` – Gestiona el sistema de ranking  
- `res/` – Layouts, drawables y recursos  
- `AndroidManifest.xml` – Configuración de la aplicación

---

## 🚀 Cómo Ejecutar el Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/marclopezalonso/juego-parejas-android.git

