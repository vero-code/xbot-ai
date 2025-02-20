import React, { useState } from "react";
import "../styles/SocialAccountPage.css";
import API from "../api.ts";
import { useNavigate } from "react-router-dom";

const SocialAccountPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [userId, setUserId] = useState("");
    const [apiKey, setApiKey] = useState("");
    const [apiSecretKey, setApiSecretKey] = useState("");
    const [accessToken, setAccessToken] = useState("");
    const [accessTokenSecret, setAccessTokenSecret] = useState("");
    const [message, setMessage] = useState<string | null>(null);

    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const token = localStorage.getItem("token");
        if (!token) {
            navigate("/login");
            return;
        }

        const config = {
            headers: {
                Authorization: `Bearer ${token}`
            }
        };

        const data = {
            username,
            userId,
            apiKey,
            apiSecretKey,
            accessToken,
            accessTokenSecret,
        };

        try {
            const response = await API.post("/social-account/save", data, config);
            setMessage("Settings saved successfully!");
            console.log(response.data);
        } catch (error: unknown) {
            let errorMessage = "Unknown error";
            if (error instanceof Error) {
                errorMessage = error.message;
            }
            console.error("Error saving settings:", errorMessage);
            setMessage("Error saving settings. Please try again.");
        }
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
                    <button type="button" onClick={() => navigate("/")}>⬅ Back</button>
                </form>
                {message && <p>{message}</p>}
            </main>
        </div>
    );
};

export default SocialAccountPage;
