import React from 'react';
import '../styles/DocumentationPage.css';
import {useNavigate} from "react-router-dom";

const DocumentationPage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="documentation-container">
            <header className="documentation-header">
                <h1>Documentation</h1>
                <p>
                    Welcome to application documentation. Here you will find detailed descriptions of the functionality, installation instructions, and usage examples.
                </p>
            </header>

            <main className="documentation-content">
                <section className="doc-section">
                    <h2>Overview</h2>
                    <p>
                        This application provides functionality for interacting with Twitter/X, working with the NEAR blockchain, and much more.
                    </p>
                </section>

                <section className="doc-section">
                    <h2>Installation</h2>
                    <p>To install the application, follow these steps:</p>
                    <pre className="code-block">
{`npm install
npm start`}
          </pre>
                </section>

                <section className="doc-section">
                    <h2>API Endpoints</h2>
                    <ul>
                        <li>
                            <strong>GET /api/auth/login</strong> — User login.
                        </li>
                        <li>
                            <strong>POST /api/auth/register</strong> — Register a new user.
                        </li>
                        <li>
                            <strong>GET /api/blockchain/logs</strong> — Retrieve logs from the blockchain.
                        </li>
                    </ul>
                </section>

                <section className="doc-section">
                    <h2>Configuration</h2>
                    <p>
                        For application configuration, please refer to the configuration file and the settings section in the user interface.
                    </p>
                </section>

                <section className="doc-section">
                    <h2>FAQ</h2>
                    <p>
                        This section contains answers to frequently asked questions. If you have any further questions, please contact support.
                    </p>
                </section>
                <button onClick={() => navigate("/")}>⬅ Back</button>
            </main>

            <footer className="documentation-footer">
                <p>&copy; 2025 XBot AI. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default DocumentationPage;
