# Raksha-Kavach — API Configuration Guide

## App runs 100% OFFLINE by default — no API key required!

All core features work without any API:
- Task Selector ✅
- Safety Gear Checklist ✅
- Risk Meter ✅
- Incident Log (Room DB) ✅
- Daily Safety Quiz ✅
- Safety Score & Badges ✅
- Daily Notifications ✅

---

## Optional: Google Gemini AI (for AI-generated quiz questions)

If you want the Daily Quiz to use **live AI-generated questions** instead of built-in ones:

### Step 1 — Get a FREE Gemini API Key
1. Go to: https://aistudio.google.com/app/apikey
2. Sign in with your Google account
3. Click **"Create API Key"**
4. Copy the key (starts with `AIza...`)

### Step 2 — Add the key to the project
Open: `app/src/main/res/values/strings.xml`

Find this line:
```xml
<string name="gemini_api_key">YOUR_GEMINI_API_KEY_HERE</string>
```
Replace with your actual key:
```xml
<string name="gemini_api_key">AIzaSy...your_actual_key...</string>
```

### Step 3 — The app will automatically use Gemini for quiz questions!

> ⚠️ **Security Note:** For production apps, move the API key to `local.properties` and use BuildConfig. This setup is for development/internship purposes only.

---

## local.properties — Android SDK Path

Open `local.properties` and set your SDK path:

**Windows:**
```
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

**Mac:**
```
sdk.dir=/Users/YourName/Library/Android/sdk
```

**Linux:**
```
sdk.dir=/home/YourName/Android/Sdk
```

---

## How to Open in Android Studio

1. Unzip `RakshaKavach.zip`
2. Open Android Studio → **File → Open** → select the `RakshaKavach` folder
3. Update `local.properties` with your SDK path (Android Studio may do this automatically)
4. Wait for Gradle sync to finish
5. Connect a device or start an emulator (API 24+)
6. Click ▶ **Run**
