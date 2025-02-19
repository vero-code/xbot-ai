import React, { useState } from "react";
import "../styles/SocialAccountPage.css";

const SocialAccountPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [userId, setUserId] = useState("");
    const [apiKey, setApiKey] = useState("");
    const [apiSecretKey, setApiSecretKey] = useState("");
    const [accessToken, setAccessToken] = useState("");
    const [accessTokenSecret, setAccessTokenSecret] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // TODO: Implement sending data to the server
        console.log({
            username,
            userId,
            apiKey,
            apiSecretKey,
            accessToken,
            accessTokenSecret,
        });
    };

    return (
        <div>
            <header>
                <div>
                    <h1>Your Social Account Page</h1>
                    <p>For the bot to work effectively, you need to connect your account information.</p>
                    <p>Find these settings in your X Developer Platform account.</p>
                </div>
            </header>
            <main>
                <form onSubmit={handleSubmit} className="form-container">
                    <div className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input
                            id="username"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="userId">User ID:</label>
                        <input
                            id="userId"
                            type="text"
                            value={userId}
                            onChange={(e) => setUserId(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="apiKey">API Key:</label>
                        <input
                            id="apiKey"
                            type="text"
                            value={apiKey}
                            onChange={(e) => setApiKey(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="apiSecretKey">API Secret Key:</label>
                        <input
                            id="apiSecretKey"
                            type="text"
                            value={apiSecretKey}
                            onChange={(e) => setApiSecretKey(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="accessToken">Access Token:</label>
                        <input
                            id="accessToken"
                            type="text"
                            value={accessToken}
                            onChange={(e) => setAccessToken(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="accessTokenSecret">Access Token Secret:</label>
                        <input
                            id="accessTokenSecret"
                            type="text"
                            value={accessTokenSecret}
                            onChange={(e) => setAccessTokenSecret(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="submit-button">Save Settings</button>
                </form>
            </main>
        </div>
    );
};

export default SocialAccountPage;
