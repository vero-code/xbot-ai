import React, { useState, useEffect } from "react";
import "../styles/page/SocialAccountPage.css";
import API from "../api.ts";
import { useNavigate } from "react-router-dom";
import { FiArrowLeft, FiSave } from "react-icons/fi";

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

            const userRes = await API.get("/users/me", config);
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
        } catch (err: any) {
            if (err.response && (err.response.status === 403 || err.response.status === 404 || err.response.status === 204)) {
                console.log("No social account found for this user. Displaying a clean form.");
            } else {
                console.error("Error loading social account:", err);
            }
        }
    };

    useEffect(() => {
        fetchData();
    }, [navigate]);

    const fetchUserIdFromBackend = async (username: string, jwtToken: string): Promise<string> => {
        const response = await API.get("/social-account/get-user-id", {
            params: { username },
            headers: {
                "X-Token": `Bearer ${jwtToken}`,
            },
        });
        return response.data.userId;
    };

    const saveSocialAccountToBackend = async (
        data: {
            username: string;
            userId: string;
            apiKey: string;
            apiSecretKey: string;
            jwtToken: string;
            accessToken: string;
            accessTokenSecret: string;
        },
        token: string
    ): Promise<void> => {
        await API.post("/social-account/save", data, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const token = localStorage.getItem("token");
        if (!token) {
            navigate("/login");
            return;
        }

        if (!username) {
            setMessage("Username is required.");
            return;
        }

        try {
            // 1. Get UserID by Username
            const userId = await fetchUserIdFromBackend(username, jwtToken);

            // 2. Collect all data
            const data = {
                username,
                userId,
                apiKey,
                apiSecretKey,
                jwtToken,
                accessToken,
                accessTokenSecret,
            };

            // 3. Save settings
            await saveSocialAccountToBackend(data, token);
            await fetchData();
            setMessage("Settings saved successfully!");
        } catch (error: any) {
            console.error("❌ Error saving settings:", error);

            if (error.response) {
                console.log("🔴 Server responded with:", error.response.status, error.response.data);
            } else if (error.request) {
                console.log("⚠️ No response received:", error.request);
            } else {
                console.log("⚠️ Error setting up request:", error.message);
            }
            
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
                            disabled
                        />
                        <label htmlFor="userId">User ID creates on button press</label>
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
                            required
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
                        <button type="button" className="social-btn" onClick={() => navigate("/")}>
                            <FiArrowLeft size={20} />
                        </button>
                        <button type="submit" className="social-btn submit-button">
                            <FiSave size={20} />
                        </button>
                    </div>
                </form>
                {message && <p className={message.includes("successfully") ? "success" : "error"}>{message}</p>}
            </main>
        </div>
    );
};

export default SocialAccountPage;
