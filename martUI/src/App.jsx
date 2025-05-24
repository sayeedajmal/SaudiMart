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
    console.log("PrivateRoute: Redirecting to /login");
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole?.toLowerCase())) {
    console.log("PrivateRoute: Redirecting to /");
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
    if (!dispatchedDummyNotificationRef.current) {
      dispatchedDummyNotificationRef.current = true;

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
      }, 60000);

      return () => clearTimeout(timer);
    }
  }, [dispatch, dispatchedDummyNotificationRef]);

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
