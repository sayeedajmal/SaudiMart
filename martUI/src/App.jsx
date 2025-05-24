import React, { useCallback, useEffect, useRef } from "react";
import { useSelector, useDispatch,  } from "react-redux";
import { Navigate, Route, Routes } from "react-router-dom";
import authReducer from "./redux/authReducer";
import NotificationDisplay from './NotificationDisplay';
import notificationReducer from './redux/notificationReducer';
import BuyerDashboard from "./Buyer/BuyerDashboard";
import LoginPage from "./LoginPage";
import SellerDashboard from "./Seller/SellerDashboard";
import SignupPage from "./SignupPage";
import { addNotification, removeNotification } from './redux/notificationActions';

import { combineReducers } from "redux";


import { useNavigate } from "react-router-dom";
const PrivateRoute = ({ element, allowedRoles }) => {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const userRole = useSelector((state) => state.auth.user?.role);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole)) {
    return <Navigate to="/" replace />;
  }

  return element;
};

function App() {
  const navigate = useNavigate();

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


      const action = addNotification(dummyNotification.message, dummyNotification.type);
      dispatch(action);
      const notificationId = action.payload.id;

      const timer = setTimeout(() => {
        dispatch(removeNotification(notificationId));
      }, 60000); // 60000 milliseconds = 1 minute

      return () => clearTimeout(timer); // Clean up the timer
    }
  }, [dispatch, dispatchedDummyNotificationRef]);
  return (
    <><NotificationDisplay /><Routes>
      <Route
        path="/login"
        element={<LoginPage toggleToSignup={toggleToSignup} />} />{" "}
      <Route
        path="/signup"
        element={<SignupPage toggleToLogin={toggleToLogin} />} />
      <Route
        path="/buyer-dashboard"
        element={<PrivateRoute
          element={<BuyerDashboard />}
          allowedRoles={["buyer"]} />} />
      <Route
        path="/seller-dashboard"
        element={<PrivateRoute
          element={<SellerDashboard />}
          allowedRoles={["seller"]} />} />
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes></>
  );
}

export default App;
