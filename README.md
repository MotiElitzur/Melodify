# 🎵 Melodify: Showcase App for Modern Android Development

**Melodify** is a feature-rich Android application designed to demonstrate my expertise in **Kotlin**, **Jetpack Compose**, and **modern Android development practices**. The app name combines "Modify" and "Melody," reflecting its foundation on **Compose** and its focus on creating a seamless and modular user experience.

---

## 📱 Features

1. **Authentication**:
   - User registration and login using **Firebase Authentication**.
   - Secure and efficient session handling.

2. **Home Screen** (For Module Testing):
   - A simple interface that showcases and tests the app's various modules:
     - Change the app language.
     - Request runtime permissions.
     - Trigger sample notifications.

3. **Multi-Language Support**:
   - Dynamic language switching for localized user experiences.

4. **Permission Handling**:
   - Centralized permission request and lifecycle management.

5. **Notifications**:
   - In-app and system-level notifications to engage users.

6. **Theme Customization**:
   - Modern theming options with dynamic adjustments.

7. **Data Persistence**:
   - Uses **Jetpack DataStore** for secure and efficient user preference storage.

8. **Splash Screen**:
   - Smooth and modern splash screen experience using Jetpack APIs.

9. **Navigation**:
   - **Compose Navigation (NavGraph)** for clean and scalable navigation between screens.

---

## 🏗️ Architecture

Melodify is built using **Clean Architecture**, with a clear separation of concerns into layers: **UI**, **Domain**, and **Data**. The app utilizes **MVVM (Model-View-ViewModel)** as the design pattern and incorporates **UseCases** to encapsulate business logic.

### Architecture Layers
1. **UI Layer**:
   - Built entirely with **Jetpack Compose** for a declarative and responsive user interface.
   - Navigation is managed with **Compose Navigation (NavGraph)** for seamless screen transitions.
2. **Domain Layer**:
   - Contains **UseCases**, which encapsulate the business logic and act as intermediaries between the Data and UI layers.
3. **Data Layer**:
   - Includes **repositories** and **data sources** to handle data operations (local and remote).

---

## 🔧 Tech Stack

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture with MVVM
- **Dependency Injection**: Hilt
- **Asynchronous Programming**: Kotlin Coroutines & Flow
- **Data Persistence**: Jetpack DataStore
- **Firebase**: Authentication and Realtime Database
- **Notifications**: Firebase Cloud Messaging (FCM) and Local Notifications
- **Navigation**: Compose Navigation (NavGraph)
- **Multi-Language Support**: Locale API with Compose integration
- **Permission Handling**: Jetpack Permissions API
- **Splash Screen**: Jetpack SplashScreen API

---

## 🗂️ Modular Design

The project is structured into separate modules to emphasize **scalability**, **reusability**, and **testability**:

1. **Authentication Module**:
   - Handles login, registration, and session management with Firebase.
2. **Language Module**:
   - Manages dynamic language switching with localization.
3. **Permissions Module**:
   - Centralized logic for runtime permissions.
4. **Notifications Module**:
   - Includes logic for both local and push notifications.
5. **Theme Module**:
   - Manages dynamic theme changes (e.g., light and dark modes).
6. **DataStore Module**:
   - Encapsulates user preference storage with Jetpack DataStore.

Each module is designed with **Clean Architecture principles**, utilizing **ViewModels**, **Repositories**, **UseCases**, and **NavGraph** to ensure a clear separation of concerns.

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio Electric Eel** or later
- **Kotlin 1.8.20** or later
- Firebase Project with:
  - **Authentication** enabled
  - **Realtime Database** configured

### Setup
1. Add your `google-services.json` file to the `app/` directory.
