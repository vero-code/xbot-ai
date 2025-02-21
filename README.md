# 🤖 Xbot AI

**Xbot AI** is a **X-based agent** that leverages **Google Trends (Pytrends, Python)**, **Gemini AI for tweet generation**, and **blockchain logging on NEAR Testnet**. Users interact with the bot via **X mentions and replies**, allowing them to fetch trending topics, generate posts, and log interactions on the blockchain.

📌 **Key Features:**

✅ **Google Trends & Pytrends** for real-time trending topics 📊

✅ **Blockchain Logging (NEAR Testnet)** for transparency 🔗

✅ **AI-Powered Post Generation** using Gemini AI 🤖

✅ **X Automation** via mentions and replies ✨

✅ **React-Based UI** for easy management 🎨

🎯 **This project was created for the** [One Trillion Agents Hackathon](https://devpost.com/software/xbot-ai).


## 📑 Table of Contents

- [✨ Features](#features)
- [⚙️ Architecture](#architecture)
- [📌 Prerequisites](#prerequisites)
- [📥 Installation](#installation)
- [🔧 Configuration](#configuration)
- [🚀 Running the Application](#running-the-application)
- [📡 API Endpoints](#api-endpoints)
- [🤖 Usage](#usage)
- [🧠 Gemini AI Integration](#gemini-ai-integration)
- [🚀 Future Improvements](#future-improvements)
- [📜 License](#license)


## ✨ Features

- **X (formerly Twitter) Integration:** Users can interact with the bot via **X mentions and replies**.
- **Google Trends (Pytrends) Analysis:** Fetch **real-time trending topics** from **Google Trends** dynamically.
- **Blockchain Logging (NEAR Testnet):** Logs posts and interactions for transparency.
- **Social Account Configuration:** Allows users to configure their account and bot credentials via the UI, stored in a database.
- **JWT-based Authentication:** Secure API endpoints with JWT tokens.
- **AI-Powered Post Generation:** Uses **Gemini AI** to create engaging posts based on **selected trends**.
- **React-Based UI:** A **dashboard** for managing accounts, checking blockchain logs, and configuring user/bot settings.

## ⚙️ Architecture

### Backend (Spring Boot + Flask + Python)

- **REST API** for **X & Blockchain integration**.
- **Pytrends (Python)** for fetching **Google Trends**.
- **JWT-based authentication** for user security.
- **H2 Database** for storing user data and social account info.
- **Spring Boot API** handles request processing.

### Blockchain Integration (NEAR API)

- Uses `near-api-js` to interact with the **NEAR blockchain**.
- Logs **AI-generated posts and interactions** for transparency.

### Frontend (React + Vite)

- UI for **social media configuration, blockchain logs, and user settings**.

## 📌 Prerequisites

- **Java 23+, Spring Boot v3.4.2**
- **Flask, Python v3.13.2, pip v24.3.1**
- **Node.js v23.4.0**, npm v10.9.2
- **Maven, Git, GitHub**
- **NEAR CLI v4.0.13** (for blockchain integration)
- **Vite, React, TypeScript**
- **Postman, IntelliJ IDEA**
- **Blockchain Wallet:** MyNearWallet (testnet)
- **X Developer Platform (Free Plan)** – Requires **two X accounts** (one for the user, one for the bot).
- **X API v2** (authentication via OAuth 1.0a using ScribeJava).
- **X (Twitter) API:**
  - https://api.twitter.com/2/tweets (generate tweet)
  - GET /2/users/:id/mentions, GET /2/tweets/search/recent (X mention tracking)
  - GET /2/users/by/username/:username (can be used for get user info)
- **Google Trends API (Pytrends)**
- **SQL, H2 Database**
- **Gemini AI API (Google Cloud Console)**

## 📥 Installation

### 1️⃣ Clone the repository:

```bash
   git clone https://github.com/vero-git-hub/xbot-ai.git
   cd xbot-ai
   ```
### 2️⃣ Set up the Backend:

```bash
   mvn clean install
   ```

### 3️⃣ Set up the Frontend:

```bash
   npm install
   ```

## 🔧 Configuration

### 1️⃣ **Backend Configuration (Spring Boot, Flask)**

* The backend uses ```application.properties``` for database and API configurations.
* Google Trends fetching is handled via **Pytrends** (Python).

### 2️⃣ **Setting Credentials(React, TypeScript)**

* Users **must configure two accounts** – one for **themselves** and one for **the bot**.
* After registering both accounts in the **X Developer Platform**, they should **enter API keys** in the UI.

## 🚀 Running the Application

### 1️⃣ Start the Google Trends API (Python)

```bash
   python trends_api.py
   ```

### 2️⃣ Start the Backend (Spring Boot)

```bash
   mvn spring-boot:run
   ```

### 3️⃣ **Start the Frontend (React)**

```bash
   npm run dev
   ```

### 4️⃣ Access the Application:

* **Frontend UI:** http://localhost:5173
* **H2 Database Console:** http://localhost:8080/h2-console


## 📡 API Endpoints

### 🔑 Authentication

* POST ```/api/auth/login``` — Log in and receive a JWT token.
* POST ```/api/auth/register``` — Register a new user.

### 🌍 Google Trends Fetching (Pytrends)
* GET ```/api/bot/trends``` — Fetch trending topics from **Google Trends** (Python-based).

### 📝 Post Generation & AI

* POST ```/api/bot/select-trend``` — Select a trend for AI-powered tweet generation.
* GET ```/api/bot/generate-tweet``` — Generate a tweet using Gemini AI.

### 🔗 Blockchain Logging

* GET ```/api/blockchain/logs``` — View tweet logs stored on the blockchain.


## 🤖 Usage

### 1️⃣ User Registration & Login:

* Sign up via the API or UI.
* Configure **social media accounts** and API keys.

### 2️⃣ Mentioning the Bot in X:

* Post:

   ```
   @xbot_ai trends
   ```

* The bot responds:

   ```
   Enter your country to search for trends (United States or Canada).
   ```

* Reply with:

   ```
   country United States
   ```

* The bot retrieves **Google Trends** using **Pytrends** and responds with **trending topics**.

### 3️⃣ Selecting a Trend for AI Post Generation

* Reply with:

   ```
   trend Innovation
   ```

* The bot:
  * Recognizes the trend.
  * Generates an AI-powered post using **Gemini AI**.
  * Posts the post on behalf of the user.

### 4️⃣ Viewing Blockchain Logs

Visit the **Blockchain Console** to view **logs recorded on NEAR Testnet**.

## 🧠 Gemini AI Integration

🔹 **How It Works:**

1️⃣ Retrieves **trending topics** via **Google Trends (Pytrends, Python)**.

2️⃣ Processes the trend and sends it to **Gemini AI**.

3️⃣ Generates on **AI-powered post**.

4️⃣ Publishes the post on **X(Twitter)**.

5️⃣ Logs the interaction on **NEAR Testnet** for transparency.


## 🚀 Future Improvements

🔹 Enhanced error handling and logging.

🔹 More blockchain integrations.

🔹 AI-powered trend analysis for better post engagement.

🔹 Multimedia post support (images, videos, polls).


## 📜 License

🔹 **MIT License** – Open-source and free to modify.

🔹 **XBot AI – your AI-powered assistant for X (formerly Twitter)**.

✨ Automate, publish, and analyze trends effortlessly! 🚀