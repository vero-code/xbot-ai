import React, { useState, useEffect } from "react";
import "../styles/page/SocialAccountPage.css";
import API from "../api.ts";
import { useNavigate } from "react-router-dom";

const SocialAccountPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [userId, setUserId] = useState("");
    const [apiKey, setApiKey] = useState("");
    const [apiSecretKey, setApiSecretKey] = useState("");
    const [jwtToken, setJwtToken] = useState("");
    const [accessToken, setAccessToken] = useState("");
    const [accessTokenSecret, setAccessTokenSecret] = useState("");
    const [message, setMessage] = useState<string | null>(null);

    const navigate = useNavigate();

    // ✅ Loading saved values
    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem("token");
            if (!token) {
                navigate("/login");
                return;
            }

            try {
                const config = {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                };

                const userRes = await API.get("/social-account/me", config);
                const userIdFromProfile = userRes.data.id;

                const response = await API.get(`/social-account/${userIdFromProfile}`, config);
                const data = response.data;

                setUsername(data.username || "");
                setUserId(data.userId || "");
                setApiKey(data.apiKey || "");
                setApiSecretKey(data.apiSecretKey || "");
                setJwtToken(data.jwtToken || "");
                setAccessToken(data.accessToken || "");
                setAccessTokenSecret(data.accessTokenSecret || "");
            } catch (err) {
                console.error("Error loading social account:", err);
            }
        };

        fetchData();
    }, [navigate]);

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
            jwtToken,
            accessToken,
            accessTokenSecret,
        };

        try {
            await API.post("/social-account/save", data, config);
            setMessage("Settings saved successfully!");
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
        <div className="page-container">
            <header>
                <h1>Social Account Settings</h1>
                <p>Connect your X Developer Platform account to enable bot functionality</p>
                <p>You can find these credentials in your X Developer Platform dashboard</p>
            </header>
            <main>
                <form onSubmit={handleSubmit} className="form-container">
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="username"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="username">Username</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="userId"
                            type="text"
                            value={userId}
                            onChange={(e) => setUserId(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="userId">User ID</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="apiKey"
                            type="text"
                            value={apiKey}
                            onChange={(e) => setApiKey(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="apiKey">API Key</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="apiSecretKey"
                            type="text"
                            value={apiSecretKey}
                            onChange={(e) => setApiSecretKey(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="apiSecretKey">API Secret</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="jwtToken"
                            type="text"
                            value={jwtToken}
                            onChange={(e) => setJwtToken(e.target.value)}
                            placeholder=" "
                        />
                        <label htmlFor="jwtToken">JWT Token (Bearer)</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="accessToken"
                            type="text"
                            value={accessToken}
                            onChange={(e) => setAccessToken(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="accessToken">Access Token</label>
                    </div>
                    <div className="form-group">
                        <input
                            className="social-input"
                            id="accessTokenSecret"
                            type="text"
                            value={accessTokenSecret}
                            onChange={(e) => setAccessTokenSecret(e.target.value)}
                            placeholder=" "
                            required
                        />
                        <label htmlFor="accessTokenSecret">Access Token Secret</label>
                    </div>
                    <div className="button-group">
                        <button type="button" onClick={() => navigate("/")}>⬅ Back to Dashboard</button>
                        <button type="submit" className="submit-button">Save Settings</button>
                    </div>
                </form>
                {message && <p className={message.includes("successfully") ? "success" : "error"}>{message}</p>}
            </main>
        </div>
    );
};

export default SocialAccountPage;
