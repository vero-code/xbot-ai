import React, { useState } from "react";
import API from "../../api.ts";
import { useNavigate } from "react-router-dom";
import "../../styles/auth/AuthPage.css";
import "../../styles/auth/LoginPage.css";
import "../../styles/Button.css";

interface AuthResponse {
    token: string;
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

            window.location.href = "/";
        } catch (error) {
            console.error("LoginPage error:", error);
            setError("Invalid username or password");
        }
    };

    return (
        <div className="auth-container">
            <div className="avatar">
                <div className="avatar-icon"></div>
            </div>

            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <i className="field-icon">👨🏻‍💻</i>
                    <input
                        className={`glass-input ${error ? "input-error" : ""}`}
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
                        className={`glass-input ${error ? "input-error" : ""}`}
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button type="submit" className="main-btn action-btn">LOGIN</button>
                <div className="form-text">
                    <span>... or register below 🔽 </span>
                </div>
            </form>

            <button
                className="bottom-btn"
                onClick={() => navigate("/register")}
            >
                REGISTER
            </button>
        </div>
    );
};

export default LoginPage;