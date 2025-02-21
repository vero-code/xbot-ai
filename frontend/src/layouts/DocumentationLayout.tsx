import React from 'react';
import BaseLayout from './BaseLayout.tsx';
import DocumentationPage from "../pages/DocumentationPage.tsx";

const DocumentationLayout: React.FC = () => {
    return (
        <BaseLayout>
            <DocumentationPage />
        </BaseLayout>
    );
};

export default DocumentationLayout;