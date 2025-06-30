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
                            <p>Follow these steps to set up the project:</p>
                            <p>1️⃣ Clone the repository</p>
                            <pre className="code-block">
                                git clone https://github.com/vero-git-hub/xbot-ai.git
                            </pre>
                            <pre className="code-block">
                                cd xbot-ai
                            </pre>

                            <p>2️⃣ Start the backend (Spring Boot)</p>
                            <pre className="code-block">
                                mvn spring-boot:run
                            </pre>
                            Wait until the Spring Boot service fully starts before running the frontend.

                            <p>3️⃣ Start the frontend (React UI)</p>
                            <pre className="code-block">
                                cd frontend
                            </pre>
                            <pre className="code-block">
                                npm install
                            </pre>
                            <pre className="code-block">
                                npm run dev
                            </pre>
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
                            <h2>FAQ</h2>
                            <p>Here are some common questions and solutions:</p>
                            <ul>
                                <li><strong>Q: How do I troubleshoot issues with setup?</strong></li>
                                <p>A: Check <a href={"https://github.com/vero-git-hub/xbot-ai/blob/master/README.md"}>README.md</a> for troubleshooting steps and ensure API credentials are correctly set.</p>

                                <li><strong>Q: Can I use a different AI model?</strong></li>
                                <p>A: Currently, Gemini AI is used, but additional AI integrations are planned.</p>

                                <li><strong>Q: How do I check blockchain logs?</strong></li>
                                <p>A: Visit the Blockchain Console page in the UI to view logged transactions.</p>
                            </ul>
                        </section>
                    </div>

                    <div className="doc-column">
                        <section className="doc-section">
                            <h2>Testing Bot Interaction</h2>
                            <p>1️⃣ Trigger the bot with a mention:</p>
                            <pre className="code-block">
                                {`@your_bot trends`}
                            </pre>
                            <p>The bot will prompt for a country.</p>

                            <p>2️⃣ Provide a country:</p>
                            <pre className="code-block">
                                {`country [your-country]`}
                            </pre>

                            <p>The bot fetches trending topics and replies.</p>
                            <p>3️⃣ Generate an AI-powered tweet:</p>
                            <p>Choose trend and reply:</p>
                            <pre className="code-block">
                                {`trend [selected-trend]`}
                            </pre>
                            <p>If the bot does not respond immediately, ensure that the scheduled task is enabled and API limits are not exceeded.</p>
                        </section>

                        <section className="doc-section">
                            <h2>Blockchain Logging</h2>
                            <p>To log interactions to the NEAR blockchain:</p>

                            <p>Install NEAR CLI</p>
                            <pre className="code-block">
                                npm install -g near-cli
                            </pre>
                            <p>Login to NEAR Testnet</p>
                            <pre className="code-block">
                                near login --networkId testnet
                            </pre>

                            <p>Deploy contract</p>
                            <pre className="code-block">
                                cd blockchain
                            </pre>
                            <pre className="code-block">
                                near deploy [your-name].testnet ubuntu/near-logger-contract.wasm
                            </pre>
                            <pre className="code-block">
                                npm install near-api-js
                            </pre>
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
