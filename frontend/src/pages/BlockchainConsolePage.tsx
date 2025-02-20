import React, { useState, useEffect } from 'react';
import '../styles/BlockchainConsolePage.css';
import {useNavigate} from "react-router-dom";
import API from "../api.ts";

const BlockchainConsolePage: React.FC = () => {
    const [logs, setLogs] = useState<string[]>([]);
    const [message, setMessage] = useState<string>('');

    const navigate = useNavigate();

    const fetchLogs = async () => {
        try {
            const response = await API.get('/blockchain/logs');
            setLogs(response.data);
        } catch (error) {
            console.error("Error fetching logs", error);
            setMessage("Error fetching logs");
        }
    };

    useEffect(() => {
        fetchLogs();
    }, []);

    return (
        <div className="blockchain-console-container">
            <h1>Blockchain Console</h1>
            {message && <p className="message">{message}</p>}

            <section className="log-section">
                <h2>Logs</h2>
                {logs.length === 0 ? (
                    <p>No logs found.</p>
                ) : (
                    <ul>
                        {logs.map((log, index) => (
                            <li key={index}>{log}</li>
                        ))}
                    </ul>
                )}
            </section>
            <button onClick={() => navigate("/")}>⬅ Back</button>
        </div>
    );
};

export default BlockchainConsolePage;
