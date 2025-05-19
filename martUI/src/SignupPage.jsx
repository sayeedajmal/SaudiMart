function SignupPage({ toggleToLogin }) {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Main Section */}
      <main className="flex-grow flex flex-col md:flex-row">
        {/* Left Side - Image */}
        <div className="w-full md:w-1/2 flex justify-center items-center p-8">
          <img
            src="https://via.placeholder.com/500"
            alt="Promotional Image"
            className="rounded-lg shadow-lg max-w-full h-auto"
          />
        </div>

        {/* Right Side - Signup Form */}
        <div className="w-full md:w-1/2 flex items-center justify-center p-8">
          <div className="bg-white p-8 rounded-md shadow-md w-full max-w-md">
            <h2 className="text-2xl font-bold mb-2 text-gray-800">
              Create an account
            </h2>
            <p className="text-gray-600 mb-6">Enter your details below</p>

            <form className="space-y-4">
              <input
                type="text"
                placeholder="Name"
                className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
              />
              <input
                type="text"
                placeholder="Email or Phone Number"
                className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
              />
              <input
                type="password"
                placeholder="Password"
                className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
              />
              <button
                type="submit"
                className="w-full px-4 py-2 rounded-md text-white font-semibold bg-blue-600 hover:bg-blue-700"
              >
                Create Account
              </button>
              <button
                type="button"
                className="w-full px-4 py-2 rounded-md border border-gray-300 font-semibold flex items-center justify-center space-x-2 hover:bg-gray-100"
              >
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg"
                  alt="Google Logo"
                  className="w-5 h-5"
                />
                <span>Sign up with Google</span>
              </button>
            </form>

            {/* Login redirect */}
            <div className="text-center mt-6">
              <p className="text-gray-600">
                Already have an account?{" "}
                <button
                  onClick={toggleToLogin}
                  className="text-blue-600 font-semibold hover:underline"
                >
                  Log in
                </button>
              </p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default SignupPage;
