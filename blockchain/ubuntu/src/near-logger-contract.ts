import { NearBindgen, call, view, near } from 'near-sdk-js';

@NearBindgen({})
class TweetLogger {
    logs: { tweet: string, timestamp: number }[] = [];

    @call({})
    addLog({ tweet }: { tweet: string }): void {
        const timestamp = near.blockTimestamp();
        this.logs.push({ tweet, timestamp });
        near.log(`Tweet logged: ${tweet}`);
    }

    @view({})
    getLogs(): { tweet: string, timestamp: number }[] {
        return this.logs;
    }
}