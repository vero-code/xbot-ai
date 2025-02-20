import React, { useState } from "react";
import API from "../../api.ts";
import { Link } from "react-router-dom";

interface AuthResponse {
    token: string;
}

const LoginPage: React.FC = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);

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
        <div>
            <h2>Login Form</h2>
            {error && <p>{error}</p>}
            <form onSubmit={handleLogin}>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <button type="submit">Login</button>
            </form>

            <p>
                Have not account yet? <Link to="/register">Sign Up</Link>
            </p>
        </div>
    );
};

export default LoginPage;