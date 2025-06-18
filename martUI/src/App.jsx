import { useSelector } from "react-redux";
import { Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "./components/LoginPage"
import NotificationDisplay from "./components/NotificationDisplay";
import SignupPage from "./components/SignupPage";
import BuyerDashboard from "./pages/buyer/BuyerDashboard"
import Contact from "./components/Contact"
import SellerDashboard from "./pages/seller/SellerDashboard"
import FullScreenLoader from "./components/FullScreenLoader";
import Header from "./components/Header";
import Footer from "./components/Footer"
import ProductDetails from "./pages/components/ProductDetails"
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
  const loading = useSelector((state) => state.auth.loading);

  const toggleToSignup = () => {
    navigate("/signup");
  };
  const toggleToLogin = () => {
    navigate("/login");
  };


  console.log("Auth state changed:", { isAuthenticated, user });
  if (isAuthenticated && user) {
    console.log("User role for navigation:", user.role);

    if (user.role === "BUYER") {
      console.log("Navigating to buyer dashboard...");
      navigate("/");
    } else if (user.role === "SELLER") {
      console.log("Navigating to seller dashboard...");
      navigate("/seller");
    }
  }


  if (loading) {
    return <FullScreenLoader />;
  }
  return (
    <>
      <Header />
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
        path="/contact"
          element={<Contact />}
        />
        <Route
        path="/about"
          element={<Contact />}
        />
        <Route
          path="/"
          element={<BuyerDashboard />} />
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
      <Footer />
    </>
  );
}

export default App;
