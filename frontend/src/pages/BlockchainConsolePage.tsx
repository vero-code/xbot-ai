import React, { useState, useEffect } from 'react';
import '../styles/page/BlockchainConsolePage.css';
import {useNavigate} from "react-router-dom";
import API from "../api.ts";

const BlockchainConsolePage: React.FC = () => {
    const [logs, setLogs] = useState<string[]>([]);
    const [message, setMessage] = useState<string>('');
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const navigate = useNavigate();

    const fetchLogs = async () => {
        try {
            const response = await API.get('/blockchain/logs');
            setLogs(response.data);
        } catch (error) {
            console.error("Error fetching logs", error);
            setMessage("Error fetching logs");
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchLogs();
    }, []);

    return (
        <div className="blockchain-console-container">
            <h1>Blockchain Console</h1>
            {message && <p className={`message ${message.includes("Error") ? "error" : ""}`}>{message}</p>}

            <section className="log-section">
                <h2>Logs</h2>
                {isLoading ? (
                    <p className="loading">Loading logs...</p>
                ) : logs.length === 0 ? (
                    <p className="empty-state">No logs found.</p>
                ) : (
                    <ul>
                        {logs.map((log, index) => (
                            <li key={index}>
                                {log.split(', Timestamp:').map((part, i) => (
                                    <React.Fragment key={i}>
                                        {i === 0 ? part : <span className="timestamp">Timestamp: {part}</span>}
                                    </React.Fragment>
                                ))}
                            </li>
                        ))}
                    </ul>
                )}
            </section>
            <button className="blockchain-btn" onClick={() => navigate("/")}>⬅ Back to Dashboard</button>
        </div>
    );
};

export default BlockchainConsolePage;
