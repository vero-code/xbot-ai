import React from 'react';
import {Link} from "react-router-dom";

const HomePage: React.FC = () => {

    return (
        <div>
            <header>
                <div>
                    <h1>Welcome to XBot AI 🤖</h1>
                    <p>AI agent that creates tweets using and logs them into the blockchain.</p>

                    <p>Connect <Link to="/social-account">account X</Link></p>
                </div>
            </header>
        </div>
    );
};

export default HomePage;