// blockchain/near-logger.js
const nearAPI = require('near-api-js');
const { connect, keyStores } = nearAPI;
const fs = require('fs');

const CONTRACT_NAME = 'xbot-logger.testnet';

async function connectToNear() {
    const PRIVATE_KEY = process.env.NEAR_PRIVATE_KEY;
    const ACCOUNT_ID = process.env.NEAR_ACCOUNT_ID;

    if (!PRIVATE_KEY || !ACCOUNT_ID) {
        throw new Error("The environment variables NEAR_PRIVATE_KEY and NEAR_ACCOUNT_ID must be set!");
    }

    const keyStore = new keyStores.InMemoryKeyStore();
    const keyPair = nearAPI.utils.key_pair.KeyPairEd25519.fromString(PRIVATE_KEY);
    await keyStore.setKey('testnet', ACCOUNT_ID, keyPair);

    const connectionConfig = {
        networkId: 'testnet',
        keyStore,
        nodeUrl: 'https://rpc.testnet.fastnear.com'
    };

    const near = await connect(connectionConfig);
    const signerAccount = await near.account(ACCOUNT_ID);

    const contract = new nearAPI.Contract(
        signerAccount,
        CONTRACT_NAME,
        {
            viewMethods: ['getLogs'],
            changeMethods: ['addLog']
        }
    );

    return { contract, account: signerAccount };
}

async function addLog(payload) {
    const { contract, account } = await connectToNear();

    console.log("🚀 Logging payload to blockchain:", JSON.stringify(payload, null, 2));

    try {
        await contract.addLog({
            args: {
                tweetId: payload.tweetId.toString(),
                url: payload.url.toString(),
                userId: payload.userId.toString(),
                trend: payload.trend ? payload.trend.toString() : ""
            },
            gas: '30000000000000',
            amount: '0'
        });
        console.log("✅ Log successfully added to blockchain!");
    } catch (error) {
        console.error("❌ Error adding log to blockchain:", error);
        throw error;
    }
}

async function getLogs() {
    const { contract } = await connectToNear();
    const logs = await contract.getLogs();

    console.log("📝 Retrieved logs from blockchain:");
    if (logs && logs.length > 0) {
        const formattedLogs = logs
            .map(log => {
                const timestamp = typeof log.timestamp === 'bigint' ? log.timestamp.toString() : log.timestamp;
                return `Tweet ID: ${log.tweetId}, URL: ${log.url}, User: ${log.userId}, Trend: ${log.trend}, Timestamp: ${timestamp}`;
            })
            .join("\n");
        console.log(formattedLogs);
    } else {
        console.log("No logs found.");
    }
    return logs;
}


(async () => {
    if (require.main !== module) return;

    const args = process.argv.slice(2);
    if (args.length > 0) {
        let raw;
        const arg = args[0];

        if (arg.startsWith('--file=')) {
            const filePath = arg.substring('--file='.length);
            try {
                raw = fs.readFileSync(filePath, 'utf8');
                console.log("🧾 Reading JSON from file:", filePath);
            } catch (e) {
                console.error(`❌ Error reading file ${filePath}:`, e.message);
                process.exit(1);
            }
        } else {
            raw = arg;
            console.log("🧾 Raw JSON argument:", raw);

            if (raw.startsWith('"') && raw.endsWith('"')) {
                raw = raw.slice(1, -1).replace(/\\"/g, '"');
            }
        }

        try {
            const payload = JSON.parse(raw);
            await addLog(payload);
        } catch (e) {
            console.error("❌ Invalid JSON passed as argument.");
            console.error("Raw input was:", raw);
            console.error("Parse error:", e.message);
            process.exit(1);
        }
    } else {
        console.log("📝 Retrieved logs from blockchain:");
        const logs = await getLogs();
        console.log(JSON.stringify(logs, null, 2));
    }
})();
