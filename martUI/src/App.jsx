import {
  MdAccountCircle,
  MdDashboard,
  MdNotifications,
  MdSearch,
} from "react-icons/md";
import LoginPage from "./LoginPage"
import BuyerDashboard from "./Buyer/BuyerDashboard"
import SignupPage from "./SignupPage";
import { useState } from "react";
import Footer from "./Footer"

function App() {
  const [showLogin, setShowLogin] = useState(true);

  const togglePage = () => {
    setShowLogin(!showLogin);
  };
  return (
    <div>
      <LoginPage />
      <Footer />
    </div>
  );
}

export default App;
