import React from 'react';
import {Link} from "react-router-dom";
import {useAuth} from "../contexts/AuthContext.tsx";
import "../styles/page/HomePage.css";
import { FiLogOut } from "react-icons/fi";

const HomePage: React.FC = () => {

    const { logout } = useAuth();

    return (
        <div className="glass-container">
            <header>
                <div>
                    <h1>Welcome to XBot AI</h1>
                    <p>AI agent that creates tweets using and logs them into the blockchain.</p>
                </div>
            </header>

            <div className="content-container">
                <div className="links-container">
                    <Link to="/social-account" className="styled-link">🔗 CONNECT YOUR ACCOUNT X</Link>
                    <Link to="/social-account-bot" className="styled-link">🤖 CONNECT BOT ACCOUNT X</Link>
                    <Link to="/blockchain-console" className="styled-link">📜 GO TO NEAR CONSOLE</Link>
                    <Link to="/documentation" className="styled-link">📚 READ DOCUMENTATION</Link>
                </div>

                <div className="home-image-wrapper">
                    <div className="home-image"></div>
                </div>
            </div>

            <button 
                className="main-btn action-btn"
                style={{
                        alignItems: "center",
                        justifyContent: "center",
                        gap: "8px",
                    }}
                onClick={logout}
            >
                <FiLogOut size={20} /> LOGOUT
            </button>
        </div>
    );
};

export default HomePage;