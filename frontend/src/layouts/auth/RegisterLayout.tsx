import React from "react";
import AuthLayout from "./AuthLayout.tsx";
import RegisterPage from "../../pages/auth/RegisterPage.tsx";

const RegisterLayout: React.FC = () => {
    return (
        <AuthLayout>
            <RegisterPage />
        </AuthLayout>
    );
};

export default RegisterLayout;