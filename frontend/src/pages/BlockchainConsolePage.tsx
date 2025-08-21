import React, { useState, useEffect, ReactNode } from 'react';
import '../styles/page/BlockchainConsolePage.css';
import {useNavigate} from "react-router-dom";
import API from "../api.ts";

interface TweetLog {
    tweetId: string;
    url: string;
    userId: string;
    trend: string;
    timestamp: string;
}

const BlockchainConsolePage: React.FC = () => {
    const [logs, setLogs] = useState<TweetLog[]>([]);
    const [message, setMessage] = useState<ReactNode>('');
    const [messageType, setMessageType] = useState<'success' | 'error' | 'info'>('info');
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const navigate = useNavigate();

    const formatTimestamp = (nanoTimestamp: string): string => {
        try {
            const milliTimestamp = Number(BigInt(nanoTimestamp) / 1000000n);
            return new Date(milliTimestamp).toLocaleString();
        } catch (e) {
            console.error("Error formatting timestamp", e);
            return "Invalid date";
        }
    };

    useEffect(() => {
        const xUserId = localStorage.getItem('xUserId');
        console.log("Fetching logs for X User ID:", xUserId);

        if (!xUserId) {
            setMessage(
                <span>
                    X account is not linked. Please go to
                    <a href="/social-account" onClick={(e) => { e.preventDefault(); navigate('/social-account'); }}>
                         your settings
                    </a>
                    to connect it.
                </span>
            );
            setMessageType('info');
            setIsLoading(false);
            return;
        }

        const fetchLogs = async () => {
            try {
                const response = await API.get(`/blockchain/logs?userId=${xUserId}`);
                setLogs(response.data);
                if (response.data.length === 0) {
                    setMessage("No logs found for your account.");
                    setMessageType('info');
                } else {
                    setMessage('');
                }
            } catch (error) {
                console.error("Error fetching logs", error);
                setMessage("Error fetching logs");
                setMessageType('error');
            } finally {
                setIsLoading(false);
            }
        };

        fetchLogs();
    }, [navigate]);

    return (
        <div className="blockchain-console-container">
            <h1>Blockchain Console</h1>
            {message && <p className={`message ${messageType}`}>{message}</p>}

            <section className="log-section">
                <h2>Your Logs</h2>
                {isLoading ? (
                    <p className="loading">Loading logs...</p>
                ) : logs.length > 0 ? (
                    <ul className="log-list">
                        {logs.map((log) => (
                            <li key={log.tweetId} className="log-item">
                                <p><strong>Tweet ID:</strong> {log.tweetId}</p>
                                <p><strong>User ID:</strong> {log.userId}</p>
                                <p><strong>Trend:</strong> {log.trend || 'N/A'}</p>
                                <p><strong>URL:</strong> <a href={log.url} target="_blank" rel="noopener noreferrer">{log.url}</a></p>
                                <p><strong>Timestamp:</strong> {formatTimestamp(log.timestamp)}</p>
                            </li>
                        ))}
                    </ul>
                ) : !message ? (
                    <p className="empty-state">No logs found for your account.</p>
                ) : null}
            </section>
            <button className="blockchain-btn" onClick={() => navigate("/")}>⬅ Back to Dashboard</button>
        </div>
    );
};

export default BlockchainConsolePage;
