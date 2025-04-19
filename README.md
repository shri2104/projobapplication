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

<img width="306" alt="Screenshot 2025-04-19 at 7 50 49 PM" src="https://github.com/user-attachments/assets/85d508cb-05a9-46e2-86ff-9bf874607458" />
<img width="302" alt="Screenshot 2025-04-19 at 7 46 50 PM" src="https://github.com/user-attachments/assets/879a0f74-9844-48b4-8b3e-d28e5b25e076" />
<img width="295" alt="Screenshot 2025-04-19 at 7 47 03 PM" src="https://github.com/user-attachments/assets/470138b5-835c-419f-b8ff-3b9212dfefab" />
<img width="295" alt="Screenshot 2025-04-19 at 7 47 25 PM" src="https://github.com/user-attachments/assets/a8b67e0f-bd07-4f6d-aef5-4199f5004454" />
<img width="304" alt="Screenshot 2025-04-19 at 7 47 50 PM" src="https://github.com/user-attachments/assets/1450163e-8bac-4647-be18-ea19001783a9" />
<img width="306" alt="Screenshot 2025-04-19 at 7 47 59 PM" src="https://github.com/user-attachments/assets/1f902485-61f2-4851-9810-52130bb9e0bd" />
<img width="297" alt="Screenshot 2025-04-19 at 7 58 21 PM" src="https://github.com/user-attachments/assets/2d0a859a-43d2-443a-9cc0-2b09df567b84" />
<img width="300" alt="Screenshot 2025-04-19 at 7 58 31 PM" src="https://github.com/user-attachments/assets/20a76c39-fc57-4e10-ad50-a62b93eab8c0" />
<img width="301" alt="Screenshot 2025-04-19 at 7 58 57 PM" src="https://github.com/user-attachments/assets/a4a8f158-b6c7-47ab-b8f3-1a016779bb07" />







   


   

🏁 Conclusion
1. This project gave me hands-on experience in:
    Android development with Compose
    Authentication & Firebase integration
    Backend integration using MongoDB
    Designing user-focused features for two distinct user types

   
