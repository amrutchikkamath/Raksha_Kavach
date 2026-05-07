# RakshaKavach

RakshaKavach — Android application repository.

This project contains the Android app built with Gradle. Use the instructions below to build and run the app locally.

## Requirements
- Java JDK 11+
- Android SDK (command-line tools)
- Gradle (wrapper included)
- A connected Android device or emulator

## Build
Windows:

```powershell
.\\gradlew assembleDebug
```

Unix/macOS:

```bash
./gradlew assembleDebug
```

## Install APK

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Run tests

```bash
# RakshaKavach

RakshaKavach — A lightweight Android app focused on safety, monitoring, and quick-response features.

Overview
- RakshaKavach helps users monitor safety-related events and offers tools to respond quickly in emergency situations. The app collects sensor and user-triggered events, presents actionable alerts, and integrates with device capabilities for location and communication.

Key features
- Real-time incident reporting and alerting
- Location-aware monitoring and geofencing support
- Quick-response actions for emergencies
- Clean modular architecture for easy extensions

How it's built
- Platform: Android (project root contains the `app/` module)
- Build system: Gradle (wrapper included)
- Languages: Kotlin and/or Java (see `app/src/main/java`)
- Dependencies: Android SDK, AndroidX libraries, and common Gradle-managed libraries

Project structure & notes
- `app/`: main Android application module with source, resources, and manifest
- `gradle/`, `gradle.properties`, and wrapper files: standard Gradle project configuration

Licensing & contribution
- If you'd like others to contribute, add a `LICENSE` file and update repository settings.
- Contributions are welcome via issues and pull requests.

Credits
- Created and maintained by the project team. Replace this section with author and contact information.

Thank you for using RakshaKavach — built to help keep people safe.

