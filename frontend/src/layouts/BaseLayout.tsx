import React, { ReactNode } from 'react';

interface LayoutProps {
    children: ReactNode;
}

const BaseLayout: React.FC<LayoutProps> = ({ children }) => {
    return (
        <div>
            <main>
                {children}
            </main>
        </div>
    );
};

export default BaseLayout;