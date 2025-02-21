import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import {AuthProvider} from "./contexts/AuthContext.tsx";
import LoginLayout from "./layouts/auth/LoginLayout.tsx";
import ProtectedRoute from "./routes/ProtectedRoute.tsx";
import HomeLayout from "./layouts/HomeLayout.tsx";
import RegisterLayout from "./layouts/auth/RegisterLayout.tsx";
import SocialAccountLayout from "./layouts/SocialAccountLayout.tsx";
import BlockchainConsoleLayout from "./layouts/BlockchainConsoleLayout.tsx";
import DocumentationLayout from "./layouts/DocumentationLayout.tsx";

function App() {

  return (
    <Router>
        <AuthProvider>
            <Routes>
                <Route path="/login" element={<LoginLayout />} />
                <Route path="/register" element={<RegisterLayout />} />

                <Route element={<ProtectedRoute />}>
                    <Route path="/" element={<HomeLayout />} />
                    <Route path="/social-account" element={<SocialAccountLayout />} />
                    <Route path="/blockchain-console" element={<BlockchainConsoleLayout />} />
                    <Route path="/documentation" element={<DocumentationLayout />} />
                </Route>
            </Routes>
        </AuthProvider>
    </Router>
  )
}

export default App;
