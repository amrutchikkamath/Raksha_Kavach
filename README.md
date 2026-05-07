# 🦺 RakshaKavach

RakshaKavach is a smart industrial safety Android application that helps workers improve workplace safety through task-based risk analysis, PPE validation, incident reporting, safety scoring, and daily safety awareness activities.

---

## 🚀 Features

### 🔐 Authentication System
- User Sign In and Sign Up
- Secure local authentication
- Session persistence using SharedPreferences

### 🧑‍🏭 Task-Based Safety Management
- Select industrial work tasks
- View risk level for each task
- Safety recommendations based on selected work

### 🦺 PPE & Safety Gear Validation
- Checklist for required safety equipment
- Dynamic risk reduction based on selected safety gear
- Real-time PPE compliance tracking

### 📊 Risk Meter
- Calculates adjusted risk score
- Displays task danger level visually
- Shows injury prevention insights

### 📝 Incident Logging
- Record workplace incidents
- Store incident history locally using Room Database
- View previous reports

### 🧠 Daily Safety Quiz
- Interactive safety awareness quiz
- Encourages worker learning
- Improves safety engagement

### 🏆 Safety Score System
- Tracks worker safety performance
- Daily streak monitoring
- Reward and badge system

### 🔔 Notification Reminders
- Daily safety reminders
- Background notification scheduling
- Boot receiver support for restarting reminders after device reboot

### 🎨 Modern Android UI
- Material Design Components
- RecyclerView-based dynamic layouts
- Lottie animations support
- Responsive screens

---

## 🏗️ Tech Stack

### Frontend
- Java
- XML Layouts
- Android SDK

### Backend / Storage
- Room Database
- SharedPreferences

### Libraries Used
- AndroidX AppCompat
- Material Design Components
- RecyclerView
- CardView
- Room Database
- Lifecycle ViewModel & LiveData
- WorkManager
- Navigation Components
- Lottie Animations

---

## 📂 Project Structure

```text
com.rakshakavach
│
├── data
│   ├── AppDatabase
│   ├── IncidentDao
│   ├── UserDao
│   ├── TaskRepository
│   └── Entities
│
├── model
│   ├── WorkTask
│   ├── SafetyGear
│   └── QuizQuestion
│
├── receiver
│   ├── AlarmReceiver
│   └── BootReceiver
│
├── ui
│   ├── LoginActivity
│   ├── MainActivity
│   ├── RiskMeterActivity
│   ├── SafetyChecklistActivity
│   ├── IncidentLogActivity
│   ├── DailyQuizActivity
│   ├── SafetyScoreActivity
│   └── Adapters
│
└── utils
    ├── NotificationHelper
    ├── PreferenceManager
    └── AuthUtils
```

---

## ⚙️ Installation Steps

### Prerequisites
- Android Studio Hedgehog or newer
- Android SDK 34
- Java 8+

### Setup
1. Clone or download the project.
2. Open the project in Android Studio.
3. Allow Gradle sync to complete.
4. Connect an Android device or emulator.
5. Click Run ▶️ to launch the application.

---

## 📱 Minimum Requirements

- Android 7.0 (API 24) or above
- 2 GB RAM recommended
- Internet not mandatory for core features

---

## 🔒 Permissions Used

- POST_NOTIFICATIONS
- RECEIVE_BOOT_COMPLETED

---

## 🎯 Core Modules

| Module | Purpose |
|---|---|
| Authentication | User login and registration |
| Task Selector | Select workplace task |
| Safety Checklist | Verify PPE before work |
| Risk Meter | Calculate work risk |
| Incident Logger | Report and track incidents |
| Daily Quiz | Improve safety knowledge |
| Safety Score | Track worker performance |
| Notifications | Daily safety reminders |

---

## 📈 Future Enhancements

- Firebase Cloud Sync
- AI-based Risk Prediction
- Live PPE Detection using Camera
- Supervisor Dashboard
- Voice Commands
- GPS-based Worker Tracking
- Cloud Incident Reporting
- Multi-language Support

---

## 👨‍💻 Developed Using
- Android Studio
- Java
- Material UI
- Room Database
- Android Jetpack Components

---

## 📌 Project Goal

The purpose of RakshaKavach is to create a safer industrial work environment by ensuring workers follow safety standards, wear proper protective equipment, and stay aware of workplace hazards.

---

## 📄 License

This project is developed for educational and safety innovation purposes.
