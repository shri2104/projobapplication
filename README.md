# Projob – Job & Internship Recruitment Platform

**Projob** is a modern Android application designed to connect job seekers with employers in a seamless and secure way. Built with **Jetpack Compose**, **Kotlin**, **Firebase**, and **MongoDB**, it offers dedicated dashboards for candidates and employers, making job search and hiring fast, intuitive, and efficient.


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

<h3 align="center">👤 Candidate Dashboard</h3>

<p align="center">
  <img src="https://github.com/user-attachments/assets/85d508cb-05a9-46e2-86ff-9bf874607458" width="35%" />
  <img src="https://github.com/user-attachments/assets/879a0f74-9844-48b4-8b3e-d28e5b25e076" width="35%" />
</p>
<p align="center">
  <b>Login</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Candidate Dashboard</b>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/470138b5-835c-419f-b8ff-3b9212dfefab" width="35%" />
  <img src="https://github.com/user-attachments/assets/a8b67e0f-bd07-4f6d-aef5-4199f5004454" width="35%" />
</p>
<p align="center">
  <b>Candidate Dashboard 2</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Job Listings</b>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/c6c0ae21-46e8-4fe4-baf3-0cfa61608a80" width="35%" />
  <img src="https://github.com/user-attachments/assets/81991e55-5ff5-4e33-b4c4-75aafeeb7f2b" width="35%" />
</p>
<p align="center">
  <b>Apply For Job</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Apply For Job</b>
</p>


<p align="center">
  <img src="https://github.com/user-attachments/assets/1450163e-8bac-4647-be18-ea19001783a9" width="35%" />
  <img src="https://github.com/user-attachments/assets/1f902485-61f2-4851-9810-52130bb9e0bd" width="35%" />
</p>
<p align="center">
  <b>Profile</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Profile</b>
</p>

<br/>

<h3 align="center">🧑‍💼 Employer Screens</h3>

<p align="center">
  <img src="https://github.com/user-attachments/assets/2d0a859a-43d2-443a-9cc0-2b09df567b84" width="35%" />
  <img src="https://github.com/user-attachments/assets/20a76c39-fc57-4e10-ad50-a62b93eab8c0" width="35%" />
</p>
<p align="center">
  <b>Employer Dashboard</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Post Job</b>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a4a8f158-b6c7-47ab-b8f3-1a016779bb07" width="35%" />
</p>
<p align="center">
  <b>Company Profile</b>
</p>










   


   

🏁 Conclusion
1. This project gave me hands-on experience in:
    Android development with Compose
    Authentication & Firebase integration
    Backend integration using MongoDB
    Designing user-focused features for two distinct user types

   
