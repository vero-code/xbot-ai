import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import HomePage from '../pages/HomePage.tsx';

const HomeLayout: React.FC = () => {
    return (
        <BaseLayout>
            <HomePage />
        </BaseLayout>
    );
};

export default HomeLayout;