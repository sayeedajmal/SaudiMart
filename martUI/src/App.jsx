// martUI/src/App.jsx

import React from 'react';
import {
  BrowserRouter as Router,
  Routes, // Use Routes instead of Switch
  Route,
  Navigate, // Use Navigate instead of Redirect
} from 'react-router-dom';
import { Provider, useSelector } from 'react-redux';
import { createStore, combineReducers } from 'redux'; // Or use configureStore from Redux Toolkit
import authReducer from './redux/authReducer'; // Import your auth reducer

import LoginPage from './LoginPage';
import SignupPage from './SignupPage';
import BuyerDashboard from './Buyer/BuyerDashboard'; // Assuming you have a BuyerDashboard
import SellerDashboard from './Seller/SellerDashboard'; // Assuming you have a SellerDashboard

const rootReducer = {
  auth: authReducer, // Your auth reducer
}; // Combine your reducers here

const store = createStore(combineReducers(rootReducer));


const PrivateRoute = ({ element, allowedRoles }) => {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const userRole = useSelector((state) => state.auth.user?.role); // Assuming user and role are in the state

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(userRole)) {
    // Optional: Redirect to an unauthorized page or home if role doesn't match
    return <Navigate to="/" replace />;
  }

  return element;
};

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          {/* Protected routes */}
          <Route
            path="/buyer-dashboard"
            element={<PrivateRoute element={<BuyerDashboard />} allowedRoles={['buyer']} />}
          />
          <Route
            path="/seller-dashboard"
            element={<PrivateRoute element={<SellerDashboard />} allowedRoles={['seller']} />}
          />
          {/* Redirect to login if no matching route */}
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </Router>
    </Provider>
  );
}

export default App;

