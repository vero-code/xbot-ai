import { Navigate, Outlet, useLocation } from "react-router-dom";

const ProtectedRoute = () => {
    const token = localStorage.getItem("token");
    const location = useLocation();

    if (!token && location.pathname !== "/login" && location.pathname !== "/register") {
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
};

export default ProtectedRoute;