import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import API from "../../api.ts";

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
        <div>
            <h2>Register Form</h2>
            {error && <p>{error}</p>}
            {success && <p>{success}</p>}
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Confirm Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                />
                <button type="submit">Create account</button>
            </form>

            <p>
                Have already an account? <Link to="/login">Login here</Link>
            </p>
        </div>
    );
};

export default RegisterPage;