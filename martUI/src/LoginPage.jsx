import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { login } from "./api/auth";
import { loginSuccess } from "./redux/authActions";

function LoginPage({ toggleToSignup }) {
  const dispatch = useDispatch();
  const { error, loading } = useSelector((state) => state.auth);
  const [credentials, setCredentials] = useState({
    email: "",
    password: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(credentials);
      dispatch(loginSuccess(response.data));
    } catch (err) {
      console.error("Login form submission error:", err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Main Content */}
      <main className="flex-grow flex flex-col md:flex-row">
        {/* Left Side - Image */}
        <div className="md:w-1/2 bg-blue-200 flex items-center justify-center p-8">
          <img
            src="https://images.unsplash.com/photo-1620712949543-ee46f1ea7b7a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80"
            alt="Exclusive Promotion"
            className="max-w-full h-auto rounded-sm shadow-lg"
          />
        </div>

        {/* Right Side - Login Form */}
        <div className="md:w-1/2 flex items-center justify-center p-8">
          <div className="bg-white p-8 rounded-sm shadow-lg w-full max-w-md">
            <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
              Log in to <span className="text-blue-600">Exclusive</span>
            </h2>

            <form className="space-y-5" onSubmit={handleSubmit}>
              {/* Email */}
              <div>
                <label htmlFor="email" className="sr-only">
                  Email
                </label>
                <input
                  id="email"
                  name="email"
                  type="email"
                  required
                  placeholder="Email"
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                  value={credentials.email}
                  onChange={(e) =>
                    setCredentials({
                      ...credentials,
                      email: e.target.value,
                    })
                  }
                />
              </div>

              {/* Password */}
              <div>
                <label htmlFor="password" className="sr-only">
                  Password
                </label>
                <input
                  id="password"
                  name="password"
                  type="password"
                  required
                  placeholder="Password"
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                  value={credentials.password}
                  onChange={(e) =>
                    setCredentials({ ...credentials, password: e.target.value })
                  }
                />
              </div>

              {/* Forgot Password */}
              <div className="flex justify-end">
                <a
                  href="#"
                  className="text-sm text-blue-600 hover:text-blue-500 font-medium"
                >
                  Forgot Password?
                </a>
              </div>

              {/* Login Button */}
              <div>
                <button
                  type="submit"
                  className={`w-full py-2 px-4 text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 rounded-md shadow focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 ${
                    loading ? "opacity-50 cursor-not-allowed" : ""
                  }`}
                  disabled={loading}
                >
                  {loading ? "Logging In..." : "Log In"}
                </button>
              </div>
            </form>

            {/* Sign Up Prompt */}
            <div className="mt-6 text-center">
              <p className="text-sm text-gray-600">
                Don’t have an account?{" "}
                <button
                  type="button"
                  onClick={toggleToSignup}
                  className="text-blue-600 hover:text-blue-500 font-medium"
                >
                  Sign up
                </button>
              </p>
            </div>

            {/* Error Message */}
            {error && (
              <p className="text-red-500 text-center mt-4">
                {error.message || "An error occurred during login."}
              </p>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}

export default LoginPage;
