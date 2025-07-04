# 🤖 XBot AI

**XBot AI** is an **X-based intelligent agent** that integrates **Java Spring Boot, Gemini AI for getting trends and post generation, and blockchain logging on NEAR Testnet**. Users interact with the bot via **X mentions and replies**, enabling real-time trend analysis, AI-generated posts, and transparent blockchain logging.


## 🎯 Originally Submitted to

[One Trillion Agents Hackathon](https://devpost.com/software/xbot-ai)

### ✨ Core Features

- **X (formerly Twitter) Integration:** Users can interact with the bot via **X mentions and replies**.
- **Trending Topics Analysis:** Fetch **real-time trending topics** using **Gemini**.
- **AI-Powered Post Generation:** Uses **Gemini AI** to create engaging posts based on **selected trends**.
- **Blockchain Logging (NEAR Testnet):** Logs posts and interactions for transparency.
- **React-Based UI:** A **dashboard** for managing accounts, checking blockchain logs.


## 🚀 Extended for

[PL_Genesis: Modular Worlds Hackathon](https://plgenesis.devspot.app/)

### 🔧 New Features:

- ✅ Bearer Token support: Replaced manual userId input with automatic retrieval via username and token.

- ✅ Processed Tweet Tracking: Added DB-level deduplication of already-handled commands to prevent reprocessing.

- ✅ UI Enhancements: Updated layout, auto-filled saved values, clearer labels and structure.

- ✅ Reduced Redundant API Usage: Removed excess calls to X API (e.g., the country command and reply tweets after trend posts) to respect free-tier limits.

- ✅ Local-only auth bypass: JWT validation was disabled for internal endpoints (`/api/bot/**`) to simplify local development during MVP stage.


## ⚙️ Architecture

### **1️⃣ Core Technologies**
| **Technology**               | **Version**                      | **Usage** |
|------------------------------|----------------------------------|------------------------------------------------|
| **Java 21-23 (Spring Boot)** | v3.4.2                           | Backend for fetching data from X, AI content generation, blockchain API integration |
| **Apache Maven**             | v3.9.9                           | Dependency management & project build system for Java |
| **Lombok**                   | v1.18.36                         | Java annotation library to reduce boilerplate code    |
| **Vite, React, TypeScript**  | -                                | UI for bot management & monitoring logs in blockchain |
| **Node.js**                  | v23.4.0                          | JavaScript runtime for Vite & frontend tooling |
| **npm, npx**                 | v10.9.2                          | Package management & script execution for frontend |
| **Git & GitHub**             | -                                | Version control & code hosting |
| **Postman**                  | 11.33.5                          | API testing |

Editors: IntelliJ IDEA Ultimate (2025.1.3), VSCode.

### **2️⃣ Blockchain & Smart Contracts**
| **Component** | **Version** | **Usage** |
|--------------|------------|------------------------------------------------|
| **NEAR Protocol** | - | Blockchain platform for smart contracts, transaction processing |
| **NEAR CLI** | v4.0.13 | Command-line interface for NEAR blockchain interaction |
| **Node.js, JavaScript, WebAssembly (WASM)** | - | Smart contract deployment, method execution via near-api-js, NEAR interaction |
| **UnencryptedFileSystemKeyStore** | - | NEAR authentication & key storage (.near-credentials) |
| **WSL for Ubuntu** | - | NEAR contract compilation & deployment |
| **MyNearWallet** | - | NEAR Testnet wallet |

### **3️⃣ Data Fetching**
| **API** | **Plan** | **Usage** |
|--------|----------|---------------------------------------------|
| **X API** | Free     | X API v2 (OAuth 1.0a via ScribeJava) |

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
| **Service**                           | **Model**    -   | **Usage**                     |
|---------------------------------------|------------------|-------------------------------|
| **Gemini API (Google Cloud Console)** | gemini-1.5-flash | Get trends & generate post    |


## 📥 Installation

### 1️⃣ Clone the repository:

```bash
   git clone https://github.com/vero-git-hub/xbot-ai.git
   cd xbot-ai
   ```

### 2️⃣Start the Project (2 services need to be running):

✅ **Run backend service, start the Spring Boot service (Java):**

Create a file named **application-local.properties** in resources (example: ```src/main/resources/application-local-example.properties```). Fill in the file with your credentials:

##### 🔹 Obtain a Google Cloud API Key

1. Create an account on [Google Cloud](https://cloud.google.com/).
2. Create a new project in **Google Cloud Console**
3. Enable **Gemini API**
4. Generate an **API Key**:
  1. Go to **API & Services > Credentials**
  2. Click **"Create Credentials" > "API key"**
5. Add the API key to ```src/main/resources/application-local.properties```:

```bash
   google.cloud.api.key=your_google_cloud_api_key
   ```

##### 🔹 Generate jwt.secret

On **Windows (PowerShell):**

```bash
   [Convert]::ToBase64String((1..64 | ForEach-Object {Get-Random -Maximum 256}))
   ```

On **Linux/macOS**:

```bash
   openssl rand -base64 64
   ```

Add the **generated secret key** to ```src/main/resources/application.properties:```
```bash
   jwt.secret=your_jwt_secret
   ```


##### 🔹 Start the Spring Boot service:
```bash
   mvn spring-boot:run
   ```


✅ **Run frontend service, start Node.js (React UI):**
```bash
    cd frontend
    npm install
    npm run dev
   ```

---

### 3️⃣ Configure X accounts via UI
1. Open the UI interface:

   🔗 Go to http://localhost:5173/

2. Register a user:
* Click REGISTER → Enter your details → Click REGISTER
* You will be redirected to the login page
* Enter the credentials you just registered → Click LOGIN

#### ✅ Connect your X account

🔹 Click **"CONNECT YOUR ACCOUNT X"**

Fill in the fields with your credentials. How to find them?

1. **Log in or sign up** on [X Developer Platform](https://developer.x.com/en).
2. Go to [Developer Dashboard](https://developer.twitter.com/en/portal/dashboard).
3. In the left sidebar, click **Projects & Apps**, and select your project (default one).
4. In the **User authentication settings** block, click **Edit and configure**:
* **App permissions:** ```Read and write```
* **Type of App:** ```Web App, Automated App, or Bot```
* **App info:**
  * **Callback URI / Redirect URL:** ```http://localhost:8080/auth/callback```
  * **Website URL:** *(Any valid URL, e.g., GitHub repo)*
5. Click **Save**.
6. Generate keys in Keys and Tokens tab:

| Field              | Value |
| -------------------|-------------------|
| Username           | X account username (without @) |
| User ID            | Autofill after clicking the save settings button |
| API Key            | Consumer Keys: API Key     |
| API Secret         | Consumer Keys: API Secret     |
| JWT Token (Bearer) | Authentication Tokens: Bearer Token     |
| Access Token       | Authentication Tokens: Access Token    |
| Access Token Secret| Authentication Tokens: Access Token Secret     |

7. Click the **save settings button** and back button.

---

#### ✅ Connect bot X account

🔹 Click **"CONNECT BOT ACCOUNT X"** and follow the same steps as for the personal account.

📌 Note: Use your **bot's username** when mentioning the bot (e.g., ```@your_bot```) in your X account.

---

### 4️⃣Connect NEAR for blockchain logging

Need to set up Ubuntu (or WSL), then:

1. Create a project using `npx create-near-app@latest`.
2. Replace the default `src/contract.ts` with your custom contract file, located at `blockchain/ubuntu/src/contract.ts`.
3. Recompile contract to `.wasm` using `npm run build` (or use the pre-built file located at `blockchain/ubuntu/build/hello_near.wasm`).
4. Deploy contract to your testnet account using `near deploy your_account_id.testnet ./build/hello_near.wasm`.
5. Return to Windows environment. In `blockchain/near-logger.js`, change the contract name:

`const CONTRACT_NAME = 'your_account_id.testnet';`

6. Important for Windows users: ensure your account credentials are correctly set up in C:\Users\YOUR_USERNAME\.near-credentials

> See more in the official [documentation](https://docs.near.org/smart-contracts/quickstart) about Smart Contracts.

For testing, use the example payload file `blockchain/payload.json` with the command:

```bash
node blockchain/near-logger.js --file=blockchain/payload.json
```

---

### 5️⃣Test the Bot Interaction in X

#### 🟢 Step 1: Trigger the bot with a mention

1. Open your X account and click "Post".
2. Set **Who can reply? → Only accounts you mention can reply**.
3. Send a post with text:

```bash
@[your_bot] trends
```

4. Uncomment line in the `src/main/java/.../service/core/impl/SocialMediaBotMentionService.java` file:

```bash
@Scheduled(fixedDelay =  60000)
```

This line enables bot mention tracking.

❇️ API Free Plan has limitations. You can see them in [X Developer Platform](https://developer.twitter.com/en/portal/products) It's recommended to enable it only when everything is set up. ❇️
 
5. Start both servers:

```bash
mvn spring-boot:run
```
```bash
cd frontend
npm run dev
```

#### 🟢 Step 2: Select a trend for AI post generation

1. The bot will fetch trends and respond:

```bash
    Trend1, Trend2, Trend3
   ```

2. Reply with a trend:

```bash
    trend [selected-trend]
   ```

3. The bot will generate a post and publish it from your username.

#### 🟢 Step 3: Confirm post publication

If everything works correctly, you will see:

🔹 **Check your X account** to verify the post is published.

🔹 **If the tweet doesn't appear**, make sure the services are running correctly and check the logs in Spring Boot.

---

### 6️⃣Test the Blockchain Interaction via UI

1. **Log in to the UI**
2. Click **"GO TO NEAR CONSOLE"**
3. Check if your tweet is recorded **with a timestamp.**

📌 **If no logs appear, make sure your NEAR contract is deployed successfully.**

🎯 Final Notes

🔹 API **rate limits** apply! Monitor them in **X Developer Portal**.

🔹 **Automate your social media posts securely with XBot AI!** 🚀


## 📡 API Endpoints

### 🔑 Authentication

* POST ```/api/auth/login``` — Log in and receive a JWT token.
* POST ```/api/auth/register``` — Register a new user.

### 🌍 Fetching Trends Data
* GET ```/api/bot/trends``` — Retrieve trending topics.

### 📝 Post Generation & AI

* POST ```/api/bot/select-trend``` — Select a trend.
* GET ```/api/bot/generate-tweet``` — Generate a post.
* POST ```/api/bot/post-tweet``` — Post a tweet.

### 🔗 Blockchain Logging

* GET ```/api/blockchain/logs``` — View post logs stored on the blockchain.

### 🪪 Local Links

* Frontend UI: http://localhost:5173

* H2 Database Console: http://localhost:8080/h2-console:

   - JDBC URL — jdbc:h2:file:./data/xbot-ai-db

### X API v2 Endpoints

| Endpoint           | Free Limit |
| -------------------|------------------|
| GET /2/users/by/username/:username   | 3 requests / 15 mins |
| GET /2/tweets/search/recent          | 1 requests / 15 mins |
| GET /2/tweets                        | 1 requests / 15 mins |
| GET /2/users/:id/mentions            | 1 requests / 15 mins |

## 🧠 Gemini AI Integration

🔹 **How It Works:**

1️⃣ Retrieves **trending topics** from **Gemini**.

2️⃣ Processes the trend and sends it to **Gemini**.

3️⃣ Generates on **AI-powered post**.

4️⃣ Publishes the post on **X**.

5️⃣ Logs the interaction on **NEAR Testnet** for transparency.


## 🚀 Future Improvements

🔹 More blockchain integrations.

🔹 AI-powered trend analysis for better post engagement.

🔹 Multimedia post support (images, videos, polls).


## 📜 License

🔹 **MIT License** – Open-source and free to modify.

🔹 **XBot AI – your AI-powered assistant for X**.

✨ Automate, publish, and analyze trends effortlessly! 🚀