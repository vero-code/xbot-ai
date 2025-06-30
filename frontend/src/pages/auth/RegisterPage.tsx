import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../api.ts";
import "../../styles/auth/AuthPage.css";
import "../../styles/Button.css";

const RegisterPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);

    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (password !== confirmPassword) {
            setError("Passwords do not match!");
            return;
        }

        try {
            await API.post("/auth/register", {
                username,
                email,
                password
            });

            setSuccess("You have successfully registered. Redirect to login...");
            setTimeout(() => navigate("/login"), 2000);
        } catch (error) {
            console.error("You were unable to register:", error);
            setError("Error during registration. Try again.");
        }
    };

    return (
        <div className="glass-container">
            <h2 className="glass-title">Register Form</h2>
            {error && <p className="error-message">{error}</p>}
            {success && <p>{success}</p>}
            <form onSubmit={handleRegister}>
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
                    <i className="field-icon">✉️</i>
                    <input
                        className={`auth-input ${error ? "input-error" : ""}`}
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
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
                <div className="form-group">
                    <i className="field-icon">🔒</i>
                    <input
                        className={`auth-input ${error ? "input-error" : ""}`}
                        type="password"
                        placeholder="Confirm Password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
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
                    REGISTER
                </button>
                <div className="form-text">
                    <span>... or log in below 🔽 </span>
                </div>
            </form>

            <button
                className="center-btn"
                onClick={() => navigate("/login")}
            >
                LOGIN
            </button>
        </div>
    );
};

export default RegisterPage;