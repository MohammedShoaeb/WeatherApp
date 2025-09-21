# 🌦️ WeatherApp

A modern **Android weather application** built with **Kotlin** and **Jetpack Compose**, powered by the [WeatherAPI](https://www.weatherapi.com/).  
This app provides real-time weather updates, location-based search suggestions, and a clean UI with Material3 styling.

---

## ✨ Features

- 🔍 **Search for cities** with real-time suggestions  
- 🌤 **Current weather conditions** (temperature, humidity, wind, UV, precipitation)  
- 📍 **Location details** (city, country, local date & time)  
- 🖼️ **Dynamic weather icons** with support for HD icons  
- 🎨 **Custom splash screen** and gradient background theme  
- 📱 **Responsive UI** built with **Jetpack Compose**

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose, Material3  
- **Networking:** Retrofit + Gson  
- **Architecture:** MVVM (ViewModel + LiveData)  
- **Image Loading:** Coil  
- **Coroutines:** For async API calls  

---

## 📂 Project Structure

```
weatherapp/
 ├── api/               # Retrofit API, models, and network responses
 ├── component/         # Composable UI components (SplashScreen, WelcomeMsg, etc.)
 ├── ui/theme/          # Colors, typography, theme definitions
 ├── WeatherViewModel   # Business logic & API calls
 ├── MainActivity.kt    # Navigation host (SplashScreen -> WeatherPage)
 └── WeatherPage.kt     # Main weather screen UI
```

---

## 🚀 Getting Started

### 1. Clone the repo
```bash
git clone https://github.com/yourusername/weatherapp.git
cd weatherapp
```

### 2. Add your API key
- Sign up at [WeatherAPI](https://www.weatherapi.com/) and get a free API key.  
- In `local.properties`, add:
  ```
  API_KEY=your_api_key_here
  ```

### 3. Run the project
- Open the project in **Android Studio**.  
- Sync Gradle.  
- Run on emulator or physical device.

---

## 📸 Demo

https://github.com/user-attachments/assets/6717bec6-0d1f-4b55-9239-72382e2b4d9c

