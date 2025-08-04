import React from 'react';
import '../styles/page/DocumentationPage.css';
import { useNavigate } from "react-router-dom";

const DocumentationPage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="documentation-container">
            <header className="documentation-header">
                <h1>Documentation</h1>
                <p>Welcome to the XBot AI documentation. This guide provides detailed descriptions of functionality, step-by-step instructions for setting up and testing the project, along with API references and FAQ.</p>
            </header>

            <main className="documentation-content">
                <div className="doc-columns">
                    <div className="doc-column">
                        <section className="doc-section">
                            <h2>Overview</h2>
                            <p>XBot AI is an intelligent X agent that leverages AI to generate tweets based on real-time trends. The application also integrates with the NEAR blockchain to ensure transparency and data immutability, while also supporting seamless interaction via a web-based UI and automated posting through the X API.</p>
                            <ul>
                                <li>✅ Secure authentication using JWT and OAuth for X API access</li>
                                <li>✅ AI-powered trend retrieval</li>
                                <li>✅ AI-driven tweet generation</li>
                                <li>✅ Automated interaction via X mentions and replies</li>
                                <li>✅ Blockchain logging (NEAR Testnet) for transparent tweet tracking</li>
                                <li>✅ NEAR smart contract integration for immutable data storage</li>
                                <li>✅ API-driven architecture for scalability and modular expansion</li>
                            </ul>
                        </section>

                        <section className="doc-section">
                            <h2>Setup Guide</h2>
                            <p>Follow these steps to set up the project for local development:</p>
                            <p>1️⃣ Clone the repository</p>
                            <pre className="code-block">
                                {`git clone https://github.com/vero-code/xbot-ai.git\ncd xbot-ai`}
                            </pre>

                            <p>2️⃣ Configure the Backend</p>
                            <p>
                                Copy the example configuration file `src/main/resources/application-local-example.properties` to a new file named `application-local.properties`.
                                And filled the fields.
                            </p>

                            <p>3️⃣ Start the Backend (Spring Boot)</p>
                            <pre className="code-block">
                                mvn spring-boot:run
                            </pre>
                            <p>Wait until the Spring Boot service fully starts.</p>

                            <p>4️⃣ Start the Frontend (React UI)</p>
                            <pre className="code-block">
                                {`cd frontend\nnpm install\nnpm run dev`}
                            </pre>

                            <p>5️⃣ Connect Your Personal X Account</p>
                            <p>
                                Return to home page, and click to the **CONNECT YOUR ACCOUNT X** button to connect your personal X account.
                            </p>
                        </section>
                    </div>

                    <div className="doc-column">
                        <section className="doc-section">
                            <h2>API Endpoints</h2>
                            <p>The backend provides a set of RESTful APIs to interact with XBot AI.</p>
                            <ul>
                                <li><strong>🔐 Authentication</strong></li>
                                <ul>
                                    <li><strong>POST /api/auth/login</strong> — User login.</li>
                                    <li><strong>POST /api/auth/register</strong> — Register a new user.</li>
                                </ul>
                                <li><strong>📡 Blockchain</strong></li>
                                <ul>
                                    <li><strong>GET /api/blockchain/logs</strong> — Retrieve logs from the blockchain.</li>
                                </ul>
                                <li><strong>🐦 Social network X</strong></li>
                                <ul>
                                    <li><strong>GET /api/bot/trends</strong> — Fetch trending topics.</li>
                                    <li><strong>POST /api/bot/select-trend</strong> — Select a trend for generation.</li>
                                    <li><strong>GET /api/bot/generate-tweet</strong> — Generate an AI-powered tweet.</li>
                                    <li><strong>POST /api/bot/post-tweet</strong> — Post a tweet.</li>
                                </ul>
                            </ul>
                        </section>

                        <section className="doc-section">
                            <h2>Testing Bot Interaction</h2>
                            <p>
                                After setting up the project and connecting your **personal X account** in the UI, you can test the bot.
                            </p>
                            <p>1️⃣ **Trigger the bot with a mention**</p>
                            <p>In your personal X account, post a tweet mentioning your bot's username:</p>
                            <pre className="code-block">
                                {`@your_bot_username trends`}
                            </pre>
                            <p>
                                2️⃣ **Activate Mention Polling**
                            </p>
                            <p>
                                To make the bot check for mentions, you may need to uncomment the `@Scheduled` annotation in the `SocialMediaBotMentionService.java` file in the backend code.
                            </p>
                            <p>
                                3️⃣ **Reply to the Bot**
                            </p>
                            <p>The bot will reply with a list of trends. Reply to that tweet with your chosen trend:</p>
                            <pre className="code-block">
                                {`trend [selected-trend]`}
                            </pre>
                            <p>The bot should then post a new AI-generated tweet from your personal account.</p>
                        </section>
                    </div>
                </div>
            </main>

            <button className="docs-btn" onClick={() => navigate("/")}>⬅ Back to Home</button>

            <footer className="documentation-footer">
                <p>&copy; 2025 XBot AI. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default DocumentationPage;
