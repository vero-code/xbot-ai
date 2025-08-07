import React, { useState } from "react";
import API from "../../api.ts";
import { useNavigate } from "react-router-dom";
import "../../styles/auth/AuthPage.css";
import "../../styles/auth/LoginPage.css";
import "../../styles/Button.css";

interface AuthResponse {
    token: string;
    id: string;
    username: string;
}

const LoginPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);

    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await API.post<AuthResponse>("/auth/login", {
                username,
                password
            });

            const data = response.data;
            localStorage.setItem("token", data.token);
            localStorage.setItem("userId", data.id);
            localStorage.setItem("username", data.username);

            window.location.href = "/";
        } catch (error) {
            console.error("LoginPage error:", error);
            setError("Invalid username or password");
        }
    };

    return (
        <div className="glass-container">
            <div className="login-image-wrapper">
                <div className="login-image"></div>
            </div>

            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <i className="field-icon">👨🏻‍💻</i>
                    <input
                        className={`auth-input ${error ? "input-error" : ""}`}
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <i className="field-icon">🔒</i>
                    <input
                        className={`auth-input ${error ? "input-error" : ""}`}
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button type="submit"
                    className="main-btn action-btn"
                    style={{
                        alignItems: "center",
                        justifyContent: "center",
                        gap: "8px",
                    }}
                >
                    LOGIN
                </button>
                <div className="form-text">
                    <span>... or register below 🔽 </span>
                </div>
            </form>

            <button
                className="center-btn"
                onClick={() => navigate("/register")}
            >
                REGISTER
            </button>
        </div>
    );
};

export default LoginPage;