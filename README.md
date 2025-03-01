# 🤖 XBot AI

**XBot AI** is an **X-based intelligent agent** that integrates **Java Spring Boot, Python library for Google Trends, Gemini AI for post generation, and blockchain logging on NEAR Testnet**. Users interact with the bot via **X mentions and replies**, enabling real-time trend analysis, AI-generated posts, and transparent blockchain logging.

## ✨ Features

- **X (formerly Twitter) Integration:** Users can interact with the bot via **X mentions and replies**.
- **Trending Topics Analysis:** Fetch **real-time trending topics** using **pytrends** for Google Trends.
- **AI-Powered Post Generation:** Uses **Gemini AI** to create engaging posts based on **selected trends**.
- **Blockchain Logging (NEAR Testnet):** Logs posts and interactions for transparency.
- **React-Based UI:** A **dashboard** for managing accounts, checking blockchain logs.

🎯 **This project was created for the** [One Trillion Agents Hackathon](https://devpost.com/software/xbot-ai).


## ⚙️ Architecture

### **1️⃣ Core Technologies**
| **Technology**             | **Version** | **Usage** |
|----------------------------|-------------|------------------------------------------------|
| **Java 23+ (Spring Boot)** | v3.4.2      | Backend for fetching data from X, AI content generation, blockchain API integration |
| **Apache Maven**           | v3.9.9      | Dependency management & project build system for Java |
| **Lombok**                 | v1.18.36    | Java annotation library to reduce boilerplate code    |
| **Vite, React, TypeScript** | -           | UI for bot management & monitoring logs in blockchain |
| **Node.js**                | v23.4.0     | JavaScript runtime for Vite & frontend tooling |
| **npm, npx**               | v10.9.2     | Package management & script execution for frontend |
| **Git & GitHub**           | -           | Version control & code hosting |
| **Postman**                | 11.33.5     | API testing |
| **IntelliJ IDEA**          | 2024.3.3    | Development environment |

### **2️⃣ Blockchain & Smart Contracts**
| **Component** | **Version** | **Usage** |
|--------------|------------|------------------------------------------------|
| **NEAR Protocol** | - | Blockchain platform for smart contracts, transaction processing |
| **NEAR CLI** | v4.0.13 | Command-line interface for NEAR blockchain interaction |
| **Node.js, JavaScript, WebAssembly (WASM)** | - | Smart contract deployment, method execution via near-api-js, NEAR interaction |
| **UnencryptedFileSystemKeyStore** | - | NEAR authentication & key storage (.near-credentials) |
| **WSL for Ubuntu** | - | NEAR contract compilation & deployment |
| **MyNearWallet** | - | NEAR testnet wallet |

### **3️⃣ Data Fetching**
| **API** | **Version** | **Usage** |
|--------|------------|------------------------------------------------|
| **X API** | - | Free plan, X API v2 (OAuth 1.0a via ScribeJava) |
| **pytrends** | - | Python library for fetching trending topics from Google Trends |
| **Python** | v3.13.2 | Used for API integration & data processing |
| **pip** | v24.3.1 | Package management for Python libraries |
| **Flask** | - | Lightweight API backend for handling requests |

### **4️⃣ X Developer Platform**
| **Component** | **Usage**                                       |
|--------------|-------------------------------------------------|
| **X Developer Platform** | Two developer accounts for XBot AI (user & bot) |

### **5️⃣ X API Endpoints**
| **Endpoint** | **Usage** |
|-------------|----------------------------------------------|
| `POST /2/tweets` | Post publishing |
| `GET /2/users/:id/mentions` | Replying to mentions |
| `GET /2/tweets/search/recent` | Searching for recent posts |
| `GET /2/users/by/username/:username` | Fetching user details |

### **6️⃣ Databases & Storage**
| **Database**                           | **Usage** |
|----------------------------------------|-------------------------------------------------------------|
| **H2 (SQL), JPA, Lombok, Spring Boot** | Temporary local storage, ORM, and entity relationship management |
| **User-SocialAccount Mapping**         | One-to-One entity linkage between User & SocialAccount/SocialAccountBot |
| **JWT (JSON Web Token)**               | Authentication & session management |

### **7️⃣ AI Integration**
| **Service** | **Usage**                       |
|------------|---------------------------------|
| **Gemini API (Google Cloud Console)** | AI content generation for posts |


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
* Google Trends fetching is handled via Python library.

### 2️⃣ **Setting Credentials(React, TypeScript)**

* Users **must configure two accounts** – one for **themselves** and one for **the bot**.
* After registering both accounts in the **X Developer Platform**, they should **enter API keys** in the UI.

## 🚀 Running the Application

### 1️⃣ Start the Flask Server (Python)

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

### 🌍 Fetching Trends Data
* GET ```/api/bot/trends``` — Retrieve trending topics using **pytrends**.

### 📝 Post Generation & AI

* POST ```/api/bot/select-trend``` — Select a trend for AI-powered post generation.
* GET ```/api/bot/generate-tweet``` — Generate a post using Gemini AI.

### 🔗 Blockchain Logging

* GET ```/api/blockchain/logs``` — View post logs stored on the blockchain.


## 🤖 Usage

### 1️⃣ User Registration & Login:

* Sign up via the API or UI.
* Configure **social media accounts** and API keys.

### 2️⃣ Mentioning the Bot in X:

* Post:

   ```
   @xbot_ai_ trends
   ```

* The bot responds:

   ```
   Enter your country to search for trends (United States or Canada).
   ```

* Reply with:

   ```
   country United States
   ```

* The bot retrieves **actual trends** and responds with **trending topics**.

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

1️⃣ Retrieves **trending topics**.

2️⃣ Processes the trend and sends it to **Gemini AI**.

3️⃣ Generates on **AI-powered post**.

4️⃣ Publishes the post on **X**.

5️⃣ Logs the interaction on **NEAR Testnet** for transparency.


## 🚀 Future Improvements

🔹 Enhanced error handling and logging.

🔹 More blockchain integrations.

🔹 AI-powered trend analysis for better post engagement.

🔹 Multimedia post support (images, videos, polls).


## 📜 License

🔹 **MIT License** – Open-source and free to modify.

🔹 **XBot AI – your AI-powered assistant for X**.

✨ Automate, publish, and analyze trends effortlessly! 🚀