import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import SocialAccountBotPage from "../pages/SocialAccountBotPage.tsx";

const SocialAccountBotLayout: React.FC = () => {
    return (
        <BaseLayout>
            <SocialAccountBotPage />
        </BaseLayout>
    );
};

export default SocialAccountBotLayout;