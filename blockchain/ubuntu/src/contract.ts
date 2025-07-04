// Find all our documentation at https://docs.near.org
import { NearBindgen, near, call, view } from 'near-sdk-js';

@NearBindgen({})
class TweetLogger {

    static schema = {
        logs: [{
            tweetId: "string",
            url: "string",
            userId: "string",
            trend: "string",
            timestamp: "bigint"
        }]
    };

    logs: { tweetId: string, url: string, userId: string, trend: string, timestamp: bigint }[] = [];

    @call({})
    addLog({ tweetId, url, userId, trend }: { tweetId: string, url: string, userId: string, trend: string }): void {
        const timestamp = near.blockTimestamp();
        this.logs.push({ tweetId, url, userId, trend, timestamp });
        near.log(`[TweetLogger] Logged tweet ID: ${tweetId}`);
    }

    @view({})
    getLogs(): { tweetId: string, url: string, userId: string, trend: string, timestamp: bigint }[] {
        return this.logs;
    }
}