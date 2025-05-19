import {
    MdAccountCircle,
    MdDashboard,
    MdNotifications,
    MdSearch,
} from "react-icons/md";
import LoginPage from "./LoginPage"

import SignupPage from "./SignupPage";
import { useState } from "react";


function App() {
  const [showLogin, setShowLogin] = useState(true);

  const togglePage = () => {
    setShowLogin(!showLogin);
  };
  return (
    <div>
      <SignupPage/>
    </div>
  );
}

export default App;
