import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import BlockchainConsolePage from '../pages/BlockchainConsolePage.tsx';

const BlockchainConsoleLayout: React.FC = () => {
    return (
        <BaseLayout>
            <BlockchainConsolePage />
        </BaseLayout>
    );
};

export default BlockchainConsoleLayout;