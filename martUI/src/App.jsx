import { useCallback, useEffect } from "react";
import { useSelector } from "react-redux";
import { Navigate, Route, Routes } from "react-router-dom";
import BuyerDashboard from "./Buyer/BuyerDashboard";
import LoginPage from "./LoginPage";
import NotificationDisplay from "./NotificationDisplay";
import SellerDashboard from "./Seller/SellerDashboard";
import SignupPage from "./SignupPage";
import FullScreenLoader from "./components/FullScreenLoader";

import { useNavigate } from "react-router-dom";
const PrivateRoute = ({ element, allowedRoles }) => {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const userRole = useSelector((state) => state.auth.user?.role);

  if (!isAuthenticated) {
    console.log("PrivateRoute: Redirecting to /login");
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole?.toLowerCase())) {
    console.log("PrivateRoute: Redirecting to /");
    return <Navigate to="/" replace />;
  }

  return element;
};

/**
 * Main application component that manages authentication state, routing, and role-based dashboard navigation for the marketplace UI.
 *
 * Redirects authenticated users to their respective dashboards based on role, displays a full-screen loader during authentication checks, and renders global notifications. Public routes for login and signup are provided, while dashboard routes are protected and role-restricted.
 *
 * @returns {JSX.Element} The rendered application UI with routing and authentication logic.
 */
function App() {
  const navigate = useNavigate();

  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const user = useSelector((state) => state.auth.user);
  const loading = useSelector((state) => state.auth.loading);

  const toggleToSignup = useCallback(() => {
    navigate("/signup");
  }, [navigate]);
  const toggleToLogin = useCallback(() => {
    navigate("/login");
  }, [navigate]);

  useEffect(() => {
    console.log("Auth state changed:", { isAuthenticated, user });
    if (isAuthenticated && user) {
      console.log("User role for navigation:", user.role);

      if (user.role === "BUYER") {
        console.log("Navigating to buyer dashboard...");
        navigate("/buyer-dashboard");
      } else if (user.role === "SELLER") {
        console.log("Navigating to seller dashboard...");
        navigate("/seller-dashboard");
      }
    }
  }, [isAuthenticated, user]);

  if (loading) {
 return <FullScreenLoader />;
  }
  return (
    <>
      <NotificationDisplay />
      <Routes>
        <Route
          path="/login"
          element={<LoginPage toggleToSignup={toggleToSignup} />}
        />{" "}
        <Route
          path="/signup"
          element={<SignupPage toggleToLogin={toggleToLogin} />}
        />
        <Route
          path="/"
          element={<PrivateRoute element={<BuyerDashboard />} />}
          allowedRoles={["BUYER"]}
        />
        <Route
          path="/seller"
          element={
            <PrivateRoute
              element={<SellerDashboard />}
              allowedRoles={["SELLER"]}
            />
          }
        />
        <Route
          path="*"
          element={
            isAuthenticated ? (
              user?.role === "BUYER" ? (
                <Navigate to="/" replace />
              ) : user?.role === "SELLER" ? (
                <Navigate to="/seller" replace />
              ) : (
                <Navigate to="/login" replace />
              )
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
      </Routes>
    </>
  );
}

export default App;
