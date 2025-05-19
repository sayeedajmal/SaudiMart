import LoginPage from "./LoginPage";
import SignupPage from "./SignupPage";
import Footer from "./Footer";
import { useState } from "react";

function App() {
  const [showLogin, setShowLogin] = useState(true);

  const togglePage = () => {
    setShowLogin((prev) => !prev);
  };

  return (
    <div className="min-h-screen flex flex-col justify-between">
      <div className="flex-grow">
        {showLogin ? (
          <LoginPage toggleToSignup={togglePage} />
        ) : (
          <SignupPage toggleToLogin={togglePage} />
        )}
      </div>
      <Footer />
    </div>
  );
}

export default App;
