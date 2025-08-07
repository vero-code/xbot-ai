import React, { useState, useEffect } from 'react';
import '../styles/page/BlockchainConsolePage.css';
import {useNavigate} from "react-router-dom";
import API from "../api.ts";

interface TweetLog {
    tweetId: string;
    url: string;
    userId: string;
    trend: string;
}

const BlockchainConsolePage: React.FC = () => {
    const [logs, setLogs] = useState<TweetLog[]>([]);
    const [message, setMessage] = useState<string>('');
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const navigate = useNavigate();

    useEffect(() => {
        const userId = localStorage.getItem('userId');
        console.log(userId);

        if (!userId) {
            setMessage("User not found. Please log in.");
            setIsLoading(false);
            return;
        }

        const fetchLogs = async () => {
            try {
                const response = await API.get(`/blockchain/logs?userId=${userId}`);
                setLogs(response.data);
            } catch (error) {
                console.error("Error fetching logs", error);
                setMessage("Error fetching logs");
            } finally {
                setIsLoading(false);
            }
        };

        fetchLogs();
    }, []);

    return (
        <div className="blockchain-console-container">
            <h1>Blockchain Console</h1>
            {message && <p className={`message ${message.includes("Error") ? "error" : ""}`}>{message}</p>}

            <section className="log-section">
                <h2>Your Logs</h2>
                {isLoading ? (
                    <p className="loading">Loading logs...</p>
                ) : logs.length === 0 ? (
                    <p className="empty-state">No logs found for your account.</p>
                ) : (
                    <ul className="log-list">
                        {logs.map((log) => (
                            <li key={log.tweetId} className="log-item">
                                <p><strong>Tweet ID:</strong> {log.tweetId}</p>
                                <p><strong>User ID:</strong> {log.userId}</p>
                                <p><strong>Trend:</strong> {log.trend || 'N/A'}</p>
                                <p><strong>URL:</strong> <a href={log.url} target="_blank" rel="noopener noreferrer">{log.url}</a></p>
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
