import React from 'react';
import '../styles/page/DocumentationPage.css';
import { useNavigate } from "react-router-dom";

const DocumentationPage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="documentation-container">
            <header className="documentation-header">
                <h1>Documentation</h1>
                <p>
                    Welcome to the XBot AI documentation. Here you will find detailed descriptions of functionality, installation instructions, API references, and usage examples.
                </p>
            </header>

            <main className="documentation-content">
                <div className="doc-columns">
                    <div className="doc-column">
                        <section className="doc-section">
                            <h2>Overview</h2>
                            <p>
                                XBot AI is an intelligent Twitter/X agent that leverages AI to generate tweets based on real-time trends.
                                The application also integrates with the NEAR blockchain to ensure transparency and data immutability.
                            </p>
                            <ul>
                                <li>✅ AI-powered tweet generation (Gemini AI)</li>
                                <li>✅ Google Trends integration via Pytrends</li>
                                <li>✅ Blockchain logging (NEAR Testnet)</li>
                                <li>✅ Secure authentication using JWT</li>
                                <li>✅ Web-based UI for configuration</li>
                            </ul>
                        </section>

                        <section className="doc-section">
                            <h2>Configuration</h2>
                            <p>Before starting, set up access credentials for your personal and bot X (Twitter) accounts. Enter on pages:</p>
                            <ul>
                                <li>🔹 <strong>Social Account Page for Bot</strong> – for bot account configuration.</li>
                                <li>🔹 <strong>Social Account Settings</strong> – for personal user account setup.</li>
                            </ul>

                            <p>To proceed:</p>
                            <ol>
                                <li>➡ Go to the home page.</li>
                                <li>➡ Click CONNECT YOUR ACCOUNT X to enter your personal credentials.</li>
                                <li>➡ Click CONNECT BOT ACCOUNT X to configure your bot credentials.</li>
                            </ol>

                            <p>Once both accounts are configured, the bot will be able to interact with X (Twitter) and generate AI-powered tweets on your behalf.</p>
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
                                <li><strong>🐦 Twitter/X</strong></li>
                                <ul>
                                    <li><strong>GET /api/bot/trends</strong> — Fetch trending topics.</li>
                                    <li><strong>POST /api/bot/select-trend</strong> — Select a trend for tweet generation.</li>
                                    <li><strong>GET /api/bot/generate-tweet</strong> — Generate an AI-powered tweet.</li>
                                </ul>
                            </ul>
                        </section>

                        <section className="doc-section">
                            <h2>FAQ</h2>
                            <p>Here are some common questions and solutions:</p>
                            <ul>
                                <li><strong>Q: How do I reset my credentials?</strong></li>
                                <p>A: Go to the settings page and update your API keys.</p>

                                <li><strong>Q: Can I use a different AI model?</strong></li>
                                <p>A: Currently, Gemini AI is used, but additional AI integrations are planned.</p>

                                <li><strong>Q: How do I check blockchain logs?</strong></li>
                                <p>A: Visit the Blockchain Console page in the UI to view logged transactions.</p>
                            </ul>
                        </section>
                    </div>

                    <div className="doc-column">
                        <section className="doc-section">
                            <h2>Usage Guide</h2>
                            <p>1️⃣ Trigger the bot with a mention:</p>
                            <pre className="code-block">
                                {`@xbot_ai_ trends`}
                            </pre>
                            <p>The bot will reply:</p>
                            <pre className="code-block">
                                {`Enter your country to search for trends`}
                            </pre>

                            <p>2️⃣ Provide a country:</p>
                            <p>The user replies:</p>
                            <pre className="code-block">
                                {`country United States`}
                            </pre>

                            <p>The bot fetches real-time trending topics:</p>
                            <pre className="code-block">
                                {`Trend1, Trend2, Trend3`}
                            </pre>
                            <p>3️⃣ Generate an AI-powered tweet:</p>
                            <pre className="code-block">
                                {`trend AI`}
                            </pre>
                            <p>The bot will generate a tweet using AI and post it on your behalf.</p>
                        </section>

                        <section className="doc-section">
                            <h2>Installation</h2>
                            <p>Follow these steps to set up the application:</p>
                            <pre className="code-block">
                                {`git clone https://github.com/vero-git-hub/xbot-ai.git
cd xbot-ai

# Install dependencies
npm install

# Start the frontend
npm start

# Run the backend
mvn spring-boot:run`}
                            </pre>
                            <p>Ensure that you have all prerequisites installed before running the application.</p>
                        </section>
                    </div>
                </div>
            </main>

            <button onClick={() => navigate("/")}>⬅ Back</button>

            <footer className="documentation-footer">
                <p>&copy; 2025 XBot AI. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default DocumentationPage;
