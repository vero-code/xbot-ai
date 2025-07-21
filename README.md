# 🤖 XBot AI 🏆🚀

**XBot AI** is an intelligent agent for **X (formerly Twitter)** that integrates **Gemini AI** for trend detection and content generation, combined with **blockchain logging on NEAR Testnet**. Users interact with the bot via **mentions and replies**, enabling real-time trend analysis, AI-generated content, and transparent on-chain activity logging.

## ✨ **Recognitions & Awards** ✨

* **Wildcard Track Winner** (AI & Autonomous Infrastructure) at **PL_Genesis: Modular Worlds Hackathon**
* **Founders Forge Top 15 Pick** (selected among the top ~4% of projects, includes a $10K non-dilutive grant & mentorship)

## 🎯 Originally Submitted to

[One Trillion Agents Hackathon](https://devpost.com/software/xbot-ai)

### ✨ Initial Features

- **X Integration:** Interact with the bot via *X mentions and replies* on X.
- **Trending Topics Analysis:** Fetch *trending topics* using Gemini AI.
- **AI-Powered Post Generation:** Create post based on *selected trend*.
- **Blockchain Logging (v1):** Logs posts and interactions for *transparency*.
- **React-Based UI:** Manage accounts, check *blockchain logs*.


## 🚀 Extended for

[PL_Genesis: Modular Worlds Hackathon](https://devspot.app/en/projects/132)

### 🔧 New Features:

- **Bearer Token Support:** Auto-fetch `userId` via username and token.
- **Processed Tweet Tracking:** DB-level deduplication of handled tweets.
- **UI Enhancements:** Improved layout, auto-fill, clearer labels.
- **Reduced API Usage:** Removed redundant X API calls to respect limits.
- **Local Auth Bypass:** Disabled JWT for internal endpoints during local dev.
- **Optimized Logging (v2):** Logs minimal tweet metadata to NEAR for *verifiable, gas-efficient tracking*.

### 🧩 Alignment with Submission Categories

1. ⚡️ **The Agentic Internet & AI-led Web3 Experiences**

XBot AI is an autonomous agent that interacts with users on X, generates AI-driven content, and logs activity to the **NEAR Testnet**, forming a working prototype of an AI-led Web3 integration.

2. 🧠 **Wildcard: AI & Autonomous Infrastructure**

A non-traditional and expressive AI agent that exhibits autonomy, input-aware behavior, and meaningful decision-making. It uses trending data, filters duplicates, and acts independently based on user prompts.

3. 🧱 **Protocol Labs: Existing Code**

This project began earlier and has evolved significantly for PL_Genesis. New features were added, the UI and backend logic improved, and the architecture was refined to match hackathon goals.


## ⚙️ Architecture

### **1️⃣ Core Technologies**
| **Technology**               | **Version**  | **Usage**                                                 |
|------------------------------|--------------|-----------------------------------------------------------|
| **Java 21+ (Spring)**        | 3.4.2        | Fetching data, content generation, blockchain integration |
| **Apache Maven**             | 3.9.9        | Dependency management                                     |
| **Lombok**                   | 1.18.36      | Java annotation library                                   |
| **Vite, React, TypeScript**  | -            | Frontend UI to monitor blockchain logs                    |
| **Node.js**                  | v23.4.0      | JavaScript runtime used with Vite                         |
| **npm, npx**                 | v10.9.2      | Package and script management                             |
| **Git & GitHub**             | -            | Version control & code hosting                            |
| **Postman**                  | 11.33.5      | API testing                                               |

**Editors:** IntelliJ IDEA Ultimate (2025.1.3), VS Code.

### **2️⃣ Blockchain & Smart Contracts**
| **Component**            | **Usage**                                     |
|--------------------------|-----------------------------------------------|
| **NEAR Protocol**        | Blockchain platform for smart contracts       |
| **NEAR CLI** v4.0.13     | Command-line interaction with NEAR            |
| **Node.js, JS, WASM**    | Contract deployment & calls via `near-api-js` |
| **Local NEAR Key Store** | NEAR key management via `.near-credentials`   |
| **WSL (Ubuntu)**         | Contract compilation & deployment environment |
| **MyNearWallet**         | Testnet wallet for contract interaction       |

### **3️⃣ X Developer Platform**
| **API**   | **Plan** | **Usage**                            |  
|-----------|----------|--------------------------------------|  
| **X API** | Free     | X API v2 via OAuth 1.0a (ScribeJava) |  

Two developer accounts used (bot & user) on X platform.

| **Endpoint**                         | **Usage**           | **Free Limit**     |
|--------------------------------------|---------------------|--------------------|
| `POST /2/tweets`                     | Posting tweets      | 1 requests/15 mins |
| `GET /2/users/:id/mentions`          | Getting mentions    | 1 requests/15 mins |
| `GET /2/tweets/search/recent`        | Searching for posts | 1 requests/15 mins |
| `GET /2/users/by/username/:username` | Getting user info   | 3 requests/15 mins |

### **4️⃣ Databases & Storage**
| **Component**                   | **Usage** |  
|---------------------------------|-----------------------------------------------------|  
| **H2 (SQL), Spring Data JPA**   | Local storage, ORM, entity relationships            |  
| **User↔SocialAccount mapping**  | One-to-one link between users and social identities |  
| **JWT (JSON Web Token)**        | Auth & session handling                             |

### **5️⃣ AI Integration**
| **Service**                   | **Model**        | **Usage**                     |
|-------------------------------|------------------|-------------------------------|
| **Gemini API (Google Cloud)** | gemini-1.5-flash | Get trends & generate post    |


## 📥 Installation

### 1️⃣ Clone the Repository:

```bash
 git clone https://github.com/vero-git-hub/xbot-ai.git
 cd xbot-ai
 ```

### 2️⃣Configure Project:

#### ✅ Setting Backend:

Create a file named **application-local.properties** in resources (example: ```src/main/resources/application-local-example.properties```). Fill in the file with your credentials:

🔹 Obtain a Google Cloud API key:

1. Create an account on [Google Cloud](https://cloud.google.com/).
2. Create a new project in **Google Cloud Console**
3. Enable **Gemini API**
4. Generate an **API Key**:
   1. Go to **API & Services > Credentials**
   2. Click **"Create Credentials" > "API key"**

🔹Add the API key to ```src/main/resources/application-local.properties```:

```bash
 google.cloud.api.key=your_google_cloud_api_key
 ```

🔹 Generate jwt.secret:

On **Windows (PowerShell):**

```bash
 [Convert]::ToBase64String((1..64 | ForEach-Object {Get-Random -Maximum 256}))
```

On **Linux/macOS**:

```bash
 openssl rand -base64 64
 ```

🔹 Add the generated secret key to ```src/main/resources/application.properties:```
```bash
 jwt.secret=your_jwt_secret
 ```

#### ✅ Setting Frontend:
```bash
 cd frontend
 npm install
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

| Field              | Value                                            |
| -------------------|--------------------------------------------------|
| Username           | X account username (without @)                   |
| User ID            | Autofill after clicking the save settings button |
| API Key            | Consumer Keys: API Key                           |
| API Secret         | Consumer Keys: API Secret                        |
| JWT Token (Bearer) | Authentication Tokens: Bearer Token              |
| Access Token       | Authentication Tokens: Access Token              |
| Access Token Secret| Authentication Tokens: Access Token Secret       |

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
@Scheduled(fixedDelay = 60000)
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

1. Log in to the UI: http://localhost:5173/ .
2. Click **"GO TO NEAR CONSOLE"**
3. Check if your tweet is recorded **with a metadata**:  Tweet ID, URL, User ID, Trend, Timestamp.

📌 **If no logs appear, make sure your NEAR contract is deployed successfully.**


## 📡 API Endpoints

### 🔑 Authentication

* POST ```/api/auth/login``` — Log in and receive a JWT token.
* POST ```/api/auth/register``` — Register a new user.

### 🌍 Fetching Trends Data
* GET ```/api/bot/trends``` — Retrieve trending topics.

### 📝 Post Generation & AI

* POST ```/api/bot/select-trend``` — Select a trend.
* GET ```/api/bot/generate-tweet``` — Generate a tweet.
* POST ```/api/bot/post-tweet``` — Post a tweet.

### 🔗 Blockchain Logging

* GET ```/api/blockchain/logs``` — View post logs stored on the blockchain.

### 🪪 Database Links

* H2 Database Console: http://localhost:8080/h2-console:

  - JDBC URL — jdbc:h2:file:./data/xbot-ai-db


## 📜 License

This project is licensed under the **MIT License** – free to use, modify, and distribute.

**Contributions are welcome!**  
If you find bugs, want to add features, or improve documentation, feel free to open an issue or submit a pull request.