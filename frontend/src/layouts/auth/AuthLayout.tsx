import React, { ReactNode } from "react";

interface AuthLayoutProps {
    children: ReactNode;
}

const AuthLayout: React.FC<AuthLayoutProps> = ({ children }) => {
    return (
        <div>
            {children}
        </div>
    );
};

export default AuthLayout;