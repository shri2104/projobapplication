<img width="262" alt="Screenshot 2025-04-18 at 10 58 54 PM" src="https://github.com/user-attachments/assets/ec063f34-c832-4306-b270-e1b40cd0dec4" /># Projob – Job & Internship Recruitment Platform

**Projob** is a modern Android application designed to connect job seekers with employers in a seamless and secure way. Built with **Jetpack Compose**, **Kotlin**, **Firebase**, and **MongoDB**, it offers dedicated dashboards for candidates and employers, making job search and hiring fast, intuitive, and efficient.

> 🛠️ Developed during internship (Dec 2024 – Mar 2025)

---

## 🔍 Overview

Projob aims to:
- Help **candidates** discover and apply for relevant jobs & internships.
- Enable **employers** to post jobs and manage applications easily.
- Provide secure **authentication** for both user types.

---

## 🔐 Authentication

Supports two user types:
- **Candidate (Job Seeker)**
- **Employer (Recruiter)**

Authentication methods:
- Email/Password login
- OTP-based phone authentication (via Firebase)

---

## 👤 Candidate Features

- 🔎 Browse & filter job and internship listings  
- 📁 Manage profile and resume  
- 💼 Track applied jobs  
- ⭐ Follow companies for updates  
- 📞 Access Help & Support section  

---

## 🏢 Employer Features

- 📋 Post new jobs with full details  
- 📬 Manage and track applications  
- 🏢 Edit and update company profile  
- 📊 View job listing performance  

---

## 🧰 Tech Stack

### 🔹 Frontend
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin](https://kotlinlang.org/)

### 🔹 Backend & DB
- 🔗 **Firebase Authentication** – Secure login (email + OTP)  
- 💾 **MongoDB** – Cloud database for users, jobs, and companies  
- 💽 **Room DB** – Local offline storage  
- 🔄 **Retrofit** – API network calls  

---

## 📦 Project Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Fulcrum-Resources/ProJobMobileApp.git
2. Add Firebase configuration
- This project uses Firebase for authentication. To run it:
- Create a project at  https://firebase.google.com/
- Enable Email and Phone (OTP) authentication
- Download google-services.json
- Place it in your project’s app/ directory

⚠️ google-services.json is excluded from the repo for security.
Create your own Firebase project to test the app.

3. Open in Android Studio
   Sync Gradle
   Build and run on emulator/device
   
<img width="262" alt="Screenshot 2025-04-18 at 10 58 54 PM" src="https://github.com/user-attachments/assets/6667cdcb-e53b-4118-b186-e017f624d242" />

   

🏁 Conclusion
1. This project gave me hands-on experience in:
    Android development with Compose
    Authentication & Firebase integration
    Backend integration using MongoDB
    Designing user-focused features for two distinct user types

   
