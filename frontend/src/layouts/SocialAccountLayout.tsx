import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import SocialAccountPage from "../pages/SocialAccountPage.tsx";

const SocialAccountLayout: React.FC = () => {
    return (
        <BaseLayout>
            <SocialAccountPage />
        </BaseLayout>
    );
};

export default SocialAccountLayout;