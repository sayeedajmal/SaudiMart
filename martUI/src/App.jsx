import { useCallback, useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate, Route, Routes } from "react-router-dom";
import BuyerDashboard from "./Buyer/BuyerDashboard";
import LoginPage from "./LoginPage";
import NotificationDisplay from "./NotificationDisplay";
import SellerDashboard from "./Seller/SellerDashboard";
import SignupPage from "./SignupPage";
import {
  addNotification,
  removeNotification,
} from "./redux/notificationActions";


import { useNavigate } from "react-router-dom";
const PrivateRoute = ({ element, allowedRoles }) => {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const userRole = useSelector((state) => state.auth.user?.role);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole?.toLowerCase())) {
    return <Navigate to="/" replace />;
  }

  return element;
};

function App() {
  const navigate = useNavigate();

  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const user = useSelector((state) => state.auth.user);

  const toggleToSignup = useCallback(() => {
    navigate("/signup");
  }, [navigate]);
  const toggleToLogin = useCallback(() => {
    navigate("/login");
  }, [navigate]);

  const dispatch = useDispatch();
  const dispatchedDummyNotificationRef = useRef(false);
  useEffect(() => {
    // Prevent double dispatch in Strict Mode
    if (!dispatchedDummyNotificationRef.current) {
      dispatchedDummyNotificationRef.current = true;

      // Add a dummy notification on app load
      const dummyNotification = {
        message: "This is a dummy notification!",
        type: "success",
      };

      const action = addNotification(
        dummyNotification.message,
        dummyNotification.type
      );
      dispatch(action);
      const notificationId = action.payload.id;

      const timer = setTimeout(() => {
        dispatch(removeNotification(notificationId));
      }, 60000); // 60000 milliseconds = 1 minute

      return () => clearTimeout(timer); // Clean up the timer
    }
  }, [dispatch, dispatchedDummyNotificationRef]);

  // Effect to handle navigation after authentication
  useEffect(() => {
    if (isAuthenticated && user) {
      // Check if authenticated and user data is available
      if (user.role === "BUYER") {
        navigate("/buyer-dashboard");
      } else if (user.role === "SELLER") {
        navigate("/seller-dashboard");
      }
      // Add more role-based navigation if needed
    }
  }, [isAuthenticated, user, navigate]); // Rerun effect when these values change
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
          path="/seller-dashboard"
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
                <Navigate to="/buyer-dashboard" replace />
              ) : user?.role === "SELLER" ? (
                <Navigate to="/seller-dashboard" replace />
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
