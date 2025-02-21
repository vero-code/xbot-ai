import React from 'react';
import {Link} from "react-router-dom";
import {useAuth} from "../contexts/AuthContext.tsx";

const HomePage: React.FC = () => {

    const { logout } = useAuth();

    return (
        <div>
            <header>
                <div>
                    <h1>Welcome to XBot AI 🤖</h1>
                    <p>AI agent that creates tweets using and logs them into the blockchain.</p>
                </div>
            </header>

            <p>Connect <Link to="/social-account">your account X</Link></p>
            <p>Connect <Link to="/bot-social-account">bot account X</Link></p>
            <p>Go to <Link to="/blockchain-console">NEAR console</Link></p>
            <p>Read <Link to="/documentation">documentation</Link></p>

            <p>
              <span
                  onClick={logout}
                  style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}>
                Logout
              </span>
            </p>
        </div>
    );
};

export default HomePage;