import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import BotSocialAccountPage from "../pages/BotSocialAccountPage.tsx";

const BotSocialAccountLayout: React.FC = () => {
    return (
        <BaseLayout>
            <BotSocialAccountPage />
        </BaseLayout>
    );
};

export default BotSocialAccountLayout;