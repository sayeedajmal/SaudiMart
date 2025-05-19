import React from 'react';
import { MdSearch } from 'react-icons/md'; // Assuming you have react-icons installed

function LoginPage() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Promotional Banner */}
      <div className="bg-neutral-800 text-white text-center py-2 text-sm">
        Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%! <a href="#" className="underline">ShopNow</a>
      </div>

      {/* Header */}
      <header className="bg-white shadow-sm py-4 px-6 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-neutral-900">Exclusive</h1>
        <nav className="hidden md:flex space-x-6">
          <a href="#" className="text-neutral-700 hover:text-primary-600 transition">Home</a>
          <a href="#" className="text-neutral-700 hover:text-primary-600 transition">Contact</a>
          <a href="#" className="text-neutral-700 hover:text-primary-600 transition">About</a>
          <a href="#" className="text-neutral-700 hover:text-primary-600 transition">Sign Up</a>
        </nav>
        <div className="flex items-center space-x-4">
          <div className="relative">
            <input
              type="text"
              placeholder="What are you looking for?"
              className="pl-8 pr-3 py-2 rounded-md bg-neutral-100 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-primary-500 transition text-sm"
            />
            <MdSearch className="absolute left-2 top-2.5 text-gray-400" size={18} />
          </div>
          {/* Add other header icons if needed */}
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-grow flex flex-col md:flex-row">
        {/* Left Side - Image */}
        <div className="md:w-1/2 bg-blue-200 flex items-center justify-center p-8">
          {/* Placeholder for image - replace with actual image tag */}
          <img src="https://images.unsplash.com/photo-1620712949543-ee46f1ea7b7a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80" alt="Exclusive Promotion" className="max-w-full h-auto rounded-lg shadow-lg" />
        </div>

        {/* Right Side - Login Form */}
        <div className="md:w-1/2 flex items-center justify-center p-8">
          <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
            <h2 className="text-2xl font-bold mb-6 text-center">Log in to Exclusive</h2>
            <form className="space-y-6">
              <div>
                <label htmlFor="emailOrPhone" className="block text-sm font-medium text-gray-700 sr-only">Email or Phone Number</label>
                <input
                  id="emailOrPhone"
                  name="emailOrPhone"
                  type="text"
                  required
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
                  placeholder="Email or Phone Number"
                />
              </div>

              <div>
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 sr-only">Password</label>
                <input
                  id="password"
                  name="password"
                  type="password"
                  required
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
                  placeholder="Password"
                />
              </div>

              <div className="flex items-center justify-between">
                 <div className="text-sm">
                  <a href="#" className="font-medium text-primary-600 hover:text-primary-500">
                    Forget Password?
                  </a>
                </div>
              </div>

              <div>
                <button
                  type="submit"
                  className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-error-600 hover:bg-error-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-error-500"
                >
                  Log In
                </button>
              </div>
            </form>

            <div className="mt-6 text-center">
              <p className="text-sm text-gray-600">
                Don't have an account? <a href="#" className="font-medium text-primary-600 hover:text-primary-500">Sign up</a>
              </p>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-neutral-900 text-white py-12 px-6">
        <div className="container mx-auto grid grid-cols-1 md:grid-cols-5 gap-8">
          {/* Exclusive Column */}
          <div>
            <h3 className="text-xl font-bold mb-4">Exclusive</h3>
            <p className="text-sm text-gray-400 mb-4">Subscribe</p>
            <p className="text-sm text-gray-400 mb-2">Get 10% off your first order</p>
            <div className="flex">
              <input
                type="email"
                placeholder="Enter your email"
                className="px-3 py-2 rounded-l-md bg-neutral-800 text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-primary-500 flex-grow"
              />
              <button className="bg-error-600 hover:bg-error-700 px-3 py-2 rounded-r-md flex items-center justify-center">
                <span className="material-symbols-outlined text-white text-sm">send</span> {/* Assuming material icons */}
              </button>
            </div>
          </div>

          {/* Support Column */}
          <div>
            <h3 className="text-xl font-bold mb-4">Support</h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li><a href="#" className="hover:text-primary-600">111 Bijoy sarani, Dhaka, DH 1515, Bangladesh.</a></li>
              <li><a href="#" className="hover:text-primary-600">exclusive@gmail.com</a></li>
              <li><a href="#" className="hover:text-primary-600">+88015-88888-9999</a></li>
            </ul>
          </div>

          {/* Account Column */}
          <div>
            <h3 className="text-xl font-bold mb-4">Account</h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li><a href="#" className="hover:text-primary-600">My Account</a></li>
              <li><a href="#" className="hover:text-primary-600">Login / Register</a></li>
              <li><a href="#" className="hover:text-primary-600">Cart</a></li>
              <li><a href="#" className="hover:text-primary-600">Wishlist</a></li>
              <li><a href="#" className="hover:text-primary-600">Shop</a></li>
            </ul>
          </div>

          {/* Quick Link Column */}
          <div>
            <h3 className="text-xl font-bold mb-4">Quick Link</h3>
            <ul className="space-y-2 text-sm text-gray-400">
              <li><a href="#" className="hover:text-primary-600">Privacy Policy</a></li>
              <li><a href="#" className="hover:text-primary-600">Terms Of Use</a></li>
              <li><a href="#" className="hover:text-primary-600">FAQ</a></li>
              <li><a href="#" className="hover:text-primary-600">Contact</a></li>
            </ul>
          </div>

          {/* Download App Column */}
          <div>
            <h3 className="text-xl font-bold mb-4">Download App</h3>
            <p className="text-sm text-gray-400 mb-4">Save $3 with App New User Only</p>
            <div className="flex items-center mb-4">
              {/* Placeholder for QR code and app store badges */}
              <div className="w-20 h-20 bg-gray-700 rounded-md mr-4 flex items-center justify-center text-gray-400 text-xs">QR Code</div>
              <div className="flex flex-col space-y-2">
                 <div className="w-24 h-8 bg-gray-700 rounded-md flex items-center justify-center text-gray-400 text-xs">Google Play</div>
                 <div className="w-24 h-8 bg-gray-700 rounded-md flex items-center justify-center text-gray-400 text-xs">App Store</div>
              </div>
            </div>
            <div className="flex space-x-4">
              {/* Placeholder for social media icons */}
              <a href="#" claSignupPagessName="text-gray-400 hover:text-primary-600"><i className="fab fa-facebook-f"></i></a> {/* Assuming Font Awesome */}
              <a href="#" className="text-gray-400 hover:text-primary-600"><i className="fab fa-twitter"></i></a>
              <a href="#" className="text-gray-400 hover:text-primary-600"><i className="fab fa-instagram"></i></a>
              <a href="#" className="text-gray-400 hover:text-primary-600"><i className="fab fa-linkedin-in"></i></a>
            </div>
          </div>
        </div>
        <div className="text-center text-gray-500 text-sm mt-8 border-t border-neutral-800 pt-6">
          &copy; Copyright Sayeed Ajmal 2025. All right reserved.
        </div>
      </footer>
    </div>
  );
}

export default LoginPage;