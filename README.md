# 🤖 XBot AI

**XBot AI** is an **X-based intelligent agent** that integrates **Java Spring Boot, Gemini AI for getting trends and post generation, and blockchain logging on NEAR Testnet**. Users interact with the bot via **X mentions and replies**, enabling real-time trend analysis, AI-generated posts, and transparent blockchain logging.


## ✨ Features

- **X (formerly Twitter) Integration:** Users can interact with the bot via **X mentions and replies**.
- **Trending Topics Analysis:** Fetch **real-time trending topics** using **Gemini**.
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
| API Key            | Consumer Keys: API Key     |
| API Secret         | Consumer Keys: API Secret     |
| JWT Token (Bearer) | Authentication Tokens: Bearer Token     |
| Access Token       | Authentication Tokens: Access Token    |
| Access Token Secret| Authentication Tokens: Access Token Secret     |


7. Get the User ID:

* Retrieve the **Bearer Token** from the **Keys and tokens** tab

* Use **Postman** or another API tool to make a request with **Bearer Token**:

```bash
   GET https://api.twitter.com/2/users/by/username/YOUR_USERNAME
   ```

* The response will contain an ```id``` field. Use this as **User ID** in the UI.

9. Click **Save Settings → Back to Dashboard**.

---

#### ✅ Connect bot X account

🔹 Click **"CONNECT BOT ACCOUNT X"** and follow the same steps as for the personal account.

📌 Note: Use your **bot's username** when mentioning the bot (e.g., ```@your_bot```).

---

### 4️⃣Connect NEAR for blockchain logging

1. **Install NEAR CLI:**

```bash
   npm install -g near-cli
   near --version
   ```

2. **Create a MyNearWallet account on the Testnet:**

```bash
   near login --networkId testnet
   ```

This will store your credentials locally:

```C:\Users\<YOUR_USERNAME>\.near-credentials\testnet```

3. **Deploy the smart contract:**

The contract is already compiled into .wasm format and located at:

```blockchain/ubuntu/near-logger-contract.wasm```

To deploy it to your Testnet account, run:

```bash
    cd blockchain
    near deploy [your-name].testnet ubuntu/near-logger-contract.wasm
    npm install near-api-js
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
@Scheduled(fixedDelay =  900000)
```

This line enables bot mention tracking.

❇️ API Free Plan has limitations. You can see them in [X Developer Platform](https://developer.twitter.com/en/portal/products) It's recommended to enable it only when everything is set up. ❇️
 
5. Start both servers:

```bash
mvn spring-boot:run
npm run dev
```

6. Check if the bot responds:

```bash
Enter your  country  to  search  for  trends (United States,  Canada)
```

#### 🟢 Step 2: Provide a country for trend search

1. Reply:

```bash
    country United States
   ```

2. **Restart the Java server** (to activate an additional endpoint to search for bot mentions):
```bash
    mvn spring-boot:run
   ```

3. The bot will fetch trends and respond:

```bash
    Trend1, Trend2, Trend3
   ```

#### 🟢 Step 3: Select a trend for AI post generation

1. Reply with a trend:

```bash
    trend [selected-trend]
   ```

2. The bot will generate a post and publish it **after 15 minutes.**

#### 🟢 Step 4: Bot confirms post publication

✅ If everything works correctly, you will see:

```bash
    The post was posted on your behalf. Contact me again!
   ```
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


## 🧠 Gemini AI Integration

🔹 **How It Works:**

1️⃣ Retrieves **trending topics** by country from **Gemini**.

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