import React from "react";
import { MdSearch } from "react-icons/md";

function SignupPage() {
  return (
    <div className="min-h-screen bg-white text-gray-800">
      {/* Header */}
      <header className="bg-white shadow-md py-4">
        <div className="container mx-auto px-4 flex justify-between items-center">
          <h1
            className="text-2xl font-bold"
            style={{ color: "var(--grayscale-title-active)" }}
          >
            Exclusive
          </h1>
          <nav className="hidden md:flex space-x-6">
            <a href="#" className="hover:text-gray-600">
              Home
            </a>
            <a href="#" className="hover:text-gray-600">
              Contact
            </a>
            <a href="#" className="hover:text-gray-600">
              About
            </a>
            <a href="#" className="hover:text-gray-600">
              Sign Up
            </a>
          </nav>
          <div className="flex items-center space-x-4">
            <div className="relative">
              <input
                type="text"
                placeholder="What are you looking for?"
                className="pl-8 pr-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-primary-500 focus:border-primary-500"
              />
              <MdSearch className="absolute left-2 top-3 text-gray-400" />
            </div>
            <button className="md:hidden">
              <MdSearch size={24} />
            </button>
          </div>
        </div>
      </header>

      {/* Promotional Banner */}
      <div className="bg-black text-white text-center py-2 text-sm">
        <p>
          Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!{" "}
          <a href="#" className="underline ml-2">
            ShopNow
          </a>
        </p>
      </div>

      {/* Main Section */}
      <main className="container mx-auto px-4 py-12 flex flex-col md:flex-row items-center justify-center gap-12">
        {/* Left Side - Image */}
        <div className="w-full md:w-1/2 flex justify-center">
          <img
            src="https://via.placeholder.com/500" // Replace with your image URL
            alt="Promotional Image"
            className="rounded-lg shadow-lg max-w-full h-auto"
          />
        </div>

        {/* Right Side - Login Form */}
        <div className="w-full md:w-1/2 max-w-md">
          <div className="bg-white p-8 rounded-lg shadow-md">
            <h2
              className="text-2xl font-bold mb-2"
              style={{ color: "var(--grayscale-title-active)" }}
            >
              Create an account
            </h2>
            <p className="text-gray-600 mb-6">Enter your details below</p>

            <form className="space-y-4">
              <div>
                <input
                  type="text"
                  placeholder="Name"
                  className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
              <div>
                <input
                  type="text"
                  placeholder="Email or Phone Number"
                  className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
              <div>
                <input
                  type="password"
                  placeholder="Password"
                  className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
              <button
                type="submit"
                className="w-full px-4 py-2 rounded-md text-white font-semibold"
                style={{ backgroundColor: "var(--color-primary)" }}
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

            <div className="text-center mt-6">
              <p className="text-gray-600">
                Already have account?{" "}
                <a href="#" className="font-semibold hover:underline">
                  Log in
                </a>
              </p>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-black text-white py-8">
        <div className="container mx-auto px-4 grid grid-cols-1 md:grid-cols-5 gap-8">
          <div>
            <h3
              className="text-xl font-bold mb-4"
              style={{ color: "var(--grayscale-off-white)" }}
            >
              Exclusive
            </h3>
            <p className="text-sm text-gray-400 mb-2">Subscribe</p>
            <p className="text-sm text-gray-400 mb-4">
              Get 10% off your first order
            </p>
            <div className="flex">
              <input
                type="email"
                placeholder="Enter your email"
                className="px-4 py-2 rounded-l-md focus:outline-none text-gray-800"
              />
              <button className="bg-primary-500 px-4 py-2 rounded-r-md">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-6 w-6 text-white"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M14 5l7 7m0 0l-7 7m7-7H3"
                  />
                </svg>
              </button>
            </div>
          </div>

          <div>
            <h3
              className="text-xl font-bold mb-4"
              style={{ color: "var(--grayscale-off-white)" }}
            >
              Support
            </h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li>
                <a href="#" className="hover:underline">
                  111 Bijoy sarani, Dhaka, DH 1515, Bangladesh.
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  exclusive@gmail.com
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  +88015-88888-9999
                </a>
              </li>
            </ul>
          </div>

          <div>
            <h3
              className="text-xl font-bold mb-4"
              style={{ color: "var(--grayscale-off-white)" }}
            >
              Account
            </h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li>
                <a href="#" className="hover:underline">
                  My Account
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Login / Register
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Cart
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Wishlist
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Shop
                </a>
              </li>
            </ul>
          </div>

          <div>
            <h3
              className="text-xl font-bold mb-4"
              style={{ color: "var(--grayscale-off-white)" }}
            >
              Quick Link
            </h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li>
                <a href="#" className="hover:underline">
                  Privacy Policy
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Terms Of Use
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  FAQ
                </a>
              </li>
              <li>
                <a href="#" className="hover:underline">
                  Contact
                </a>
              </li>
            </ul>
          </div>

          <div>
            <h3
              className="text-xl font-bold mb-4"
              style={{ color: "var(--grayscale-off-white)" }}
            >
              Download App
            </h3>
            <p className="text-sm text-gray-400 mb-4">
              Save $3 with App New User Discount
            </p>
            <div className="flex space-x-2">
              {/* Placeholder for app store badges */}
              <img
                src="https://via.placeholder.com/100x40?text=App+Store"
                alt="App Store"
                className="rounded-md"
              />
              <img
                src="https://via.placeholder.com/100x40?text=Google+Play"
                alt="Google Play"
                className="rounded-md"
              />
            </div>
          </div>
        </div>
        <div className="text-center text-gray-500 text-sm mt-8">
          &copy; Copyright 2025. All right reserved
        </div>
      </footer>
    </div>
  );
}

export default SignupPage;
