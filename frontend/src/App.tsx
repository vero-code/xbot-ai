import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import {AuthProvider} from "./contexts/AuthContext.tsx";
import LoginLayout from "./layouts/auth/LoginLayout.tsx";
import ProtectedRoute from "./routes/ProtectedRoute.tsx";
import HomeLayout from "./layouts/HomeLayout.tsx";
import RegisterLayout from "./layouts/auth/RegisterLayout.tsx";

function App() {

  return (
    <Router>
        <AuthProvider>
            <Routes>
                <Route path="/login" element={<LoginLayout />} />
                <Route path="/register" element={<RegisterLayout />} />

                <Route element={<ProtectedRoute />}>
                    <Route path="/" element={<HomeLayout />} />
                </Route>
            </Routes>
        </AuthProvider>
    </Router>
  )
}

export default App;
