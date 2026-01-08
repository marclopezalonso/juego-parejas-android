# Memory Matching Game (Android)

## 📱 Project Overview

This project is an **Android memory matching game**, also known as the *Pairs Game*, developed in **Java** using **Android Studio**.  
The objective of the game is to test and improve the player’s memory by finding matching pairs of cards on a board with randomized positions.

The application has been designed as a **two-player game**, includes **persistent game state saving**, and features a **Top 10 ranking system** to store the best scores achieved by players.

---

## 🎮 Game Rules and Mechanics

- The game board consists of a **4x5 grid**, containing **20 cards** in total.
- These cards form **10 matching pairs**.
- All card positions are **randomly generated** at the start of each new game to ensure replayability.
- Players must uncover two cards at a time:
  - If the cards match, the pair is considered found.
  - Each correctly matched pair awards **1 point**.
- The **maximum possible score** in a game is **10 points**.

---

## 👥 Players

- The game is designed for **two players**.
- Each player can enter a **custom name**, which will be used to record their score in the ranking system if they achieve a high score.
- Player names allow users to leave their personal mark on the game and compete for a place in the ranking.

---

## 🏆 Ranking System

- The application includes a **Top 10 ranking** feature.
- The ranking stores the **best scores achieved by players**, along with their chosen names.
- Only the **10 highest scores** are displayed.
- This feature encourages competition and replayability.

---

## 💾 Game State Management

The game includes a simple but effective **save and load system**, controlled by three main buttons:

### ▶️ Start
- Starts a new game.
- Initializes the board with randomly shuffled cards.
- Resets the current score.

### 💾 Save
- Saves the **current state of the game**, including:
  - Board configuration
  - Matched pairs
  - Current score
- If the user closes the application and opens it again, the saved game **remains stored**.
- Only **one saved game** can exist at a time.
  - Saving a new game overwrites the previous saved game.

### 📂 Load
- Loads the previously saved game.
- The saved game **does not load automatically** when reopening the app.
- The user must explicitly press the **Load** button to restore the saved state.
- If no game has been saved, no game will be loaded.

---

## 🔀 Randomization

- Card positions are **randomized at the start of every new game**.
- This prevents memorizing card positions across multiple games and ensures a fair and dynamic experience.

---

## 🛠️ Technical Details

- **Language:** Java
- **IDE:** Android Studio
- **Build System:** Gradle (Kotlin DSL)
- **Minimum SDK:** Defined in the Gradle configuration
- **Architecture:** Simple activity-based structure
- **Persistent Storage:** Used to save game state and ranking data

---

## 📂 Project Structure

The project follows the standard Android project structure:

- `MainActivity.java` – Main game logic and user interaction
- `Carta.java` – Represents a card in the game
- `Ranking.java` – Manages the ranking system
- `res/` – Layouts, drawables, and resources
- `AndroidManifest.xml` – Application configuration

---

## 🚀 How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/marclopezalonso/juego-parejas-android.git

---

## 🌍 Language

This README is also available in Spanish.

➡️ [Read this documentation in Spanish](README.es.md)
