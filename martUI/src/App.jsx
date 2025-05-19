import { useCallback } from "react";
import { Provider, useSelector } from "react-redux";
import { Navigate, Route, Routes } from "react-router-dom";
import { combineReducers, createStore } from "redux";
import authReducer from "./redux/authReducer";

import BuyerDashboard from "./Buyer/BuyerDashboard";
import LoginPage from "./LoginPage";
import SellerDashboard from "./Seller/SellerDashboard";
import SignupPage from "./SignupPage";

const rootReducer = combineReducers({
  auth: authReducer,
});

import { useNavigate } from "react-router-dom";
const store = createStore(rootReducer);

// Protected Route
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

  return (
    <Provider store={store}>
      <Routes>
        {" "}
        <Route
          path="/login"
          element={<LoginPage toggleToSignup={toggleToSignup} />}
        />{" "}
        <Route
          path="/signup"
          element={<SignupPage toggleToLogin={toggleToLogin} />}
        />
        <Route
          path="/buyer-dashboard"
          element={
            <PrivateRoute
              element={<BuyerDashboard />}
              allowedRoles={["buyer"]}
            />
          }
        />
        <Route
          path="/seller-dashboard"
          element={
            <PrivateRoute
              element={<SellerDashboard />}
              allowedRoles={["seller"]}
            />
          }
        />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Provider>
  );
}

export default App;
