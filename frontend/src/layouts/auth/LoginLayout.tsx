import React from "react";
import AuthLayout from "./AuthLayout.tsx";
import LoginPage from "../../pages/auth/LoginPage.tsx";

const LoginLayout: React.FC = () => {
    return (
        <AuthLayout>
            <LoginPage />
        </AuthLayout>
    );
};

export default LoginLayout;