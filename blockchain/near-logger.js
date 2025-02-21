const nearAPI = require('near-api-js');
const { connect, keyStores } = nearAPI;
const os = require('os');
const homedir = os.homedir();

const CONTRACT_NAME = 'xbotai.testnet';

async function connectToNear() {
    const keyStore = new keyStores.UnencryptedFileSystemKeyStore(`${homedir}/.near-credentials`);
    const near = await connect({
        networkId: 'testnet',
        keyStore,
        nodeUrl: 'https://rpc.testnet.near.org'
    });

    const account = await near.account(CONTRACT_NAME);

    const contract = new nearAPI.Contract(account, CONTRACT_NAME, {
        viewMethods: ['getLogs'],
        changeMethods: ['addLog']
    });

    return { contract, account };
}

async function addLog(tweet) {
    const { contract, account } = await connectToNear();
    await contract.addLog({
        signerAccount: account,
        args: { tweet },
        gas: '30000000000000',
        amount: '0'
    });
}

async function getLogs() {
    const { contract } = await connectToNear();
    const logs = await contract.getLogs();

    const formattedLogs = logs
        .map(log => `Tweet: ${log.tweet}, Timestamp: ${log.timestamp}`)
        .join("\n");

    console.log(formattedLogs);
    return logs;
}

module.exports = { addLog, getLogs };

(async () => {
    const args = process.argv.slice(2);
    if (args.length > 0) {
        await addLog(args[0]);
    } else {
        const logs = await getLogs();
    }
})();
